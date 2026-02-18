package com.invoices.services;

import com.invoices.config.DBConnection;
import com.invoices.models.InvoiceStatusTotals;
import com.invoices.models.InvoiceTaxSummary;
import com.invoices.models.InvoiceTotal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    public List<InvoiceTotal> findInvoiceTotals() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            String query = """
                SELECT
                    i.id AS invoice_id, i.customer_name,
                    SUM(il.quantity * il.unit_price) AS total_price
                FROM invoice i
                         JOIN invoice_line il ON i.id = il.invoice_id
                GROUP BY i.id, i.customer_name, i.status
                ORDER BY i.id;
                """;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                List<InvoiceTotal> invoices = new ArrayList<>();
                while (rs.next()) {
                    InvoiceTotal invoice = new InvoiceTotal();
                    invoice.setId(rs.getInt("invoice_id"));
                    invoice.setCustomerName(rs.getString("customer_name"));
                    invoice.setTotalPrice(rs.getDouble("total_price"));
                    invoices.add(invoice);
                }
                return invoices;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            String query = """
                    SELECT
                    i.id AS invoice_id,
                    i.customer_name,
                    i.status,
                    SUM(il.quantity * il.unit_price) AS total_price
                FROM invoice i JOIN invoice_line il ON i.id = il.invoice_id
                WHERE CASE
                        WHEN i.status IN ('CONFIRMED', 'PAID') THEN 1
                        ELSE 0
                        END = 1
                GROUP BY i.id, i.customer_name, i.status ORDER BY i.id;
                """;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                List<InvoiceTotal> invoices = new ArrayList<>();
                while (rs.next()) {
                    InvoiceTotal invoice = new InvoiceTotal();
                    invoice.setId(rs.getInt("invoice_id"));
                    invoice.setCustomerName(rs.getString("customer_name"));
                    invoice.setTotalPrice(rs.getDouble("total_price"));
                    invoices.add(invoice);
                }
                return invoices;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InvoiceStatusTotals computeStatusTotals() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            String query = """
                    SELECT SUM(
                                CASE
                                    WHEN i.status = 'PAID' THEN il.quantity * il.unit_price
                                    ELSE 0
                                    END
                           ) as total_paid,
                           SUM(
                                CASE
                                    WHEN i.status = 'CONFIRMED' THEN il.quantity * il.unit_price
                                       ELSE 0
                                       END
                           ) as total_confirmed,
                           SUM(
                                   CASE
                                       WHEN i.status = 'DRAFT' THEN il.quantity * il.unit_price
                                       ELSE 0
                                       END
                           ) as total_draft
                    FROM invoice i JOIN invoice_line il on i.id = il.invoice_id;
                """;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                InvoiceStatusTotals invoiceStatusTotals = new InvoiceStatusTotals();
                while (rs.next()) {
                    invoiceStatusTotals.setTotalPaid(rs.getDouble("total_paid"));
                    invoiceStatusTotals.setTotalConfirmed(rs.getDouble("total_confirmed"));
                    invoiceStatusTotals.setTotalDraft(rs.getDouble("total_draft"));
                }
                return invoiceStatusTotals;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double computeWeightedTurnover() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            String query = """
                    SELECT SUM(
                               CASE
                                   WHEN i.status = 'PAID' THEN il.quantity * il.unit_price
                                   WHEN i.status = 'CONFIRMED' THEN (il.quantity * il.unit_price) * 0.5
                                   ELSE 0
                                   END
                           ) as weighted_turnover
                    FROM invoice i JOIN invoice_line il on i.id = il.invoice_id;
                """;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                double weightedTurnover = 0.0;
                while (rs.next()) {
                    weightedTurnover = rs.getDouble("weighted_turnover");
                }
                return weightedTurnover;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoiceTaxSummary> findInvoiceTaxSummaries() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            String query = """
                    SELECT
                        i.id AS invoice_id,
                        SUM(il.quantity * il.unit_price) AS total_ht,
                        SUM(il.quantity * il.unit_price) * (tc.rate / 100) AS total_tva,
                        SUM(il.quantity * il.unit_price) * (1 + tc.rate / 100) AS total_ttc
                    FROM invoice i
                             JOIN invoice_line il ON i.id = il.invoice_id
                             CROSS JOIN tax_config tc
                    GROUP BY i.id, i.customer_name, tc.rate
                    ORDER BY i.id;
                """;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                List<InvoiceTaxSummary> summaries = new ArrayList<>();
                while (rs.next()) {
                    InvoiceTaxSummary summary = new InvoiceTaxSummary();
                    summary.setId(rs.getInt("invoice_id"));
                    summary.setTotalHT(rs.getDouble("total_ht"));
                    summary.setTotalTVA(rs.getDouble("total_tva"));
                    summary.setTotalTTC(rs.getDouble("total_ttc"));
                    summaries.add(summary);
                }
                return summaries;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
