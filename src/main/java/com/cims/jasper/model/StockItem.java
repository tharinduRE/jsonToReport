package com.cims.jasper.model;

public class StockItem {

    private String itemName;
    private String stockBookFolio;
    private Float itemCapacity;
    private Float totalQuantity;
    private Float unitPrice;
    private String measUnit;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStockBookFolio() {
        return stockBookFolio;
    }

    public void setStockBookFolio(String stockBookFolio) {
        this.stockBookFolio = stockBookFolio;
    }

    public Float getItemCapacity() {
        return itemCapacity;
    }

    public void setItemCapacity(Float itemCapacity) {
        this.itemCapacity = itemCapacity;
    }

    public Float getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Float totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMeasUnit() {
        return measUnit;
    }

    public void setMeasUnit(String measUnit) {
        this.measUnit = measUnit;
    }
}
