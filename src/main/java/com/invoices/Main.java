package com.invoices;

import com.invoices.models.InvoiceTaxSummary;
import com.invoices.models.InvoiceTotal;
import com.invoices.services.DataRetriever;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("Total invoices");
        for (InvoiceTotal invoice : dataRetriever.findInvoiceTotals()) {
            System.out.printf(
                    "%d | %s | %.2f%n",
                    invoice.getId(),
                    invoice.getCustomerName(),
                    invoice.getTotalPrice()
            );
        }

        System.out.println("Invoice Tax Summaries");
        for (InvoiceTaxSummary invoiceTaxSummary: dataRetriever.findInvoiceTaxSummaries()) {
            System.out.printf(
                    "%d | %2f | %2f | %2f%n",
                    invoiceTaxSummary.getId(),
                    invoiceTaxSummary.getTotalHT(),
                    invoiceTaxSummary.getTotalTVA(),
                    invoiceTaxSummary.getTotalTTC()
            );
        }

        System.out.println("Invoice Status Totals");
        System.out.println(dataRetriever.computeStatusTotals());

        System.out.println("Confirmed and Paid Invoice Totals");
        for (InvoiceTotal invoiceTotal: dataRetriever.findConfirmedAndPaidInvoiceTotals()) {
            System.out.printf(
                    "%d | %s | %s | %.2f%n",
                    invoiceTotal.getId(),
                    invoiceTotal.getCustomerName(),
                    invoiceTotal.getStatus().toString(),
                    invoiceTotal.getTotalPrice()
            );
        }

        System.out.println("Weighted Turnover");
        System.out.println(dataRetriever.computeWeightedTurnover());
    }
}
