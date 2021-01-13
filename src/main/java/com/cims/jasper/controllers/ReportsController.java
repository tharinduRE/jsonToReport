package com.cims.jasper.controllers;

import com.cims.jasper.controllers.vm.ReportDTO;
import com.cims.jasper.controllers.vm.ReportVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ReportsController {

    private static final Path REPORT_FOLDER = Paths.get("./data/reports/");

    private static final Logger log = LoggerFactory.getLogger(ReportsController.class);

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(REPORT_FOLDER)) {
            Files.createDirectories(REPORT_FOLDER);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<ReportVM> createReport(@RequestBody ReportDTO reportDTO,
                                                 @RequestParam("name") String name) throws Exception {

        log.info("Report microservice generating report for {}", name);
        ObjectMapper mapper = new ObjectMapper();

        Class<?> model = Class.forName("com.cims.jasper.model." + StringUtils.capitalize(name));
        List<?> list = reportDTO.getPayload()
                .stream().map(obj -> mapper.convertValue(obj, model)).collect(Collectors.toList());

        InputStream jasperStream = new ClassPathResource("report/" + name + ".jasper").getInputStream();

        ReportVM report = new ReportVM(
                generateAndSaveReport(list,reportDTO.getTitle(), jasperStream));
        return ResponseEntity.ok().body(report);

    }

    public static String generateAndSaveReport(List<?> _listOfObjects,
                                               String title,
                                               InputStream jasperStream) throws IOException, JRException {
        String filename = "report-"
                + DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mma").format(LocalDateTime.now()) + ".pdf";

        BufferedImage headerImage = ImageIO.read(new ClassPathResource("templates/logo-header.png").getInputStream());
        Map<String, Object> params = new HashMap<>();
        params.put("logo", headerImage);
        params.put("title", title);

        long start = System.currentTimeMillis();

        JRDataSource dataSource = new JRBeanCollectionDataSource(_listOfObjects);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, params, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, REPORT_FOLDER + "/" + filename);

        long stop = System.currentTimeMillis();
        log.info("Jasper Reporting took {} ms", (stop - start));
        return filename;

    }
}
