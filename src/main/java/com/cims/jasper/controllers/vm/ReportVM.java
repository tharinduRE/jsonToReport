package com.cims.jasper.controllers.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportVM {

    @JsonProperty("reportUrl")
    private String name;

    public ReportVM(String name) {
        this.name = "/reports/" + name;
    }

}
