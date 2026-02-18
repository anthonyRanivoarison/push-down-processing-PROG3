package com.invoices.models;

public class InvoiceStatusTotals {
    private Double totalPaid;
    private Double totalConfirmed;
    private Double totalDraft;

    public InvoiceStatusTotals() {}

    public Double getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Double totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Double getTotalDraft() {
        return totalDraft;
    }

    public void setTotalDraft(Double totalDraft) {
        this.totalDraft = totalDraft;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    @Override
    public String toString() {
        return "InvoiceStatusTotals{" +
                "totalConfirmed=" + totalConfirmed +
                ", totalPaid=" + totalPaid +
                ", totalDraft=" + totalDraft +
                '}';
    }
}
