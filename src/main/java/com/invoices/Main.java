package com.invoices;

import com.invoices.models.InvoiceTotal;
import com.invoices.services.DataRetriever;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("Total invoices");
        var invoices = dataRetriever.findInvoiceTotals();

        for (InvoiceTotal invoice : invoices) {
            System.out.printf(
                    "%d | %s | %.2f%n",
                    invoice.getId(),
                    invoice.getCustomerName(),
                    invoice.getTotalPrice()
            );
        }


        System.out.println("Invoice Tax Summaries");
        System.out.println(dataRetriever.findInvoiceTaxSummaries());

        System.out.println("Invoice Status Totals");
        System.out.println(dataRetriever.computeStatusTotals());

        System.out.println("Confirmed and Paid Invoice Totals");
        System.out.println(dataRetriever.findConfirmedAndPaidInvoiceTotals());

        System.out.println("Weighted Turnover");
        System.out.println(dataRetriever.computeWeightedTurnover());
    }
}
