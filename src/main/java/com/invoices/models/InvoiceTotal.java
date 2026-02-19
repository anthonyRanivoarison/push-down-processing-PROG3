package com.invoices.models;

import java.util.Objects;

public class InvoiceTotal {
    private Integer id;
    private String customerName;
    private Double totalPrice;
    private InvoiceStatus status;

    public InvoiceTotal() {}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceTotal invoice = (InvoiceTotal) o;
        return Objects.equals(id, invoice.id) && Objects.equals(customerName, invoice.customerName) && Objects.equals(totalPrice, invoice.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, totalPrice);
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InvoiceTotal{" +
                "customerName='" + customerName + '\'' +
                ", id=" + id +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
