-- Test 1: Customers table seeded correctly
SELECT
  COUNT(*) = 6 AS customers_count_correct
FROM customers;

-- Test 2: At least one customer has no orders
SELECT
  EXISTS (
    SELECT 1
    FROM customers c
    WHERE NOT EXISTS (
      SELECT 1 FROM orders o WHERE o.customer_id = c.customer_id
    )
  ) AS has_customers_with_no_orders;

-- Test 3: Revenue calculation excludes non-paid orders
SELECT
  COUNT(*) = 0 AS no_pending_orders_in_revenue
FROM orders
WHERE order_status = 'pending'
AND order_id IN (
  SELECT DISTINCT order_id
  FROM order_items oi
  JOIN orders o ON o.order_id = oi.order_id
  WHERE o.order_status IN ('paid', 'shipped')
);

-- Test 4: No negative prices or quantities
SELECT
  COUNT(*) = 0 AS no_invalid_order_items
FROM order_items
WHERE unit_price < 0 OR quantity <= 0;
