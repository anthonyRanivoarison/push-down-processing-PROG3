package com.invoices.models;

public class InvoiceTaxSummary {
    private Integer id;
    private Double totalHT;
    private Double totalTVA;
    private Double totalTTC;

    public InvoiceTaxSummary() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(Double totalHT) {
        this.totalHT = totalHT;
    }

    public Double getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(Double totalTTC) {
        this.totalTTC = totalTTC;
    }

    public Double getTotalTVA() {
        return totalTVA;
    }

    public void setTotalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
    }

    @Override
    public String toString() {
        return "InvoiceTaxSummary{" +
                "totalHT=" + totalHT +
                ", totalTVA=" + totalTVA +
                ", totalTTC=" + totalTTC +
                '}';
    }
}
