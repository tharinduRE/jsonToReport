package com.cims.jasper.controllers.vm;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ReportDTO {

    @NotNull
    private String title;

    @NotNull
    private List<Object> payload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getPayload() {
        return payload;
    }

    public void setPayload(List<Object> payload) {
        this.payload = payload;
    }
}
