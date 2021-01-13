package com.cims.jasper;

import com.cims.jasper.controllers.ReportsController;
import com.cims.jasper.controllers.vm.ReportDTO;
import com.cims.jasper.model.StockItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportsController.class)
@AutoConfigureMockMvc
public class ReportsTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWithSampleData() throws Exception {

        StockItem item = new StockItem();
            item.setItemName("Name");
            item.setItemCapacity(500.F);
            item.setMeasUnit("g");
            item.setTotalQuantity(5.0F);
            item.setStockBookFolio("110-11");
            item.setUnitPrice(1000.00F);

        List<StockItem> list = new ArrayList<>();
        list.add(item);

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setName("StockItem");
        reportDTO.setTitle("Title");
        reportDTO.setPayload(Collections.singletonList(list));


        this.mockMvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(reportDTO)))
                .andExpect(status().isOk());
    }
}
