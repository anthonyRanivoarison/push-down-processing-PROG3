SELECT
    i.id AS invoice_id,
    i.customer_name,
    SUM(il.quantity * il.unit_price) AS total_price
FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id
GROUP BY i.id, i.customer_name, i.status
ORDER BY i.id;

SELECT
    i.id AS invoice_id,
    i.customer_name,
    i.status,
    SUM(il.quantity * il.unit_price) AS total_price
FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id
WHERE
    CASE
        WHEN i.status IN ('CONFIRMED', 'PAID') THEN 1
        ELSE 0
        END = 1
GROUP BY i.id, i.customer_name, i.status
ORDER BY i.id;

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

SELECT
    SUM(
            (il.quantity * il.unit_price) *
            CASE
                WHEN i.status = 'PAID' THEN 1
                WHEN i.status = 'CONFIRMED' THEN 0.5
                ELSE 0
                END
    ) AS weighted_turnover
FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id;

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
