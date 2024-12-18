-- Insert data into the 'category' table
INSERT INTO category (id, description, name) VALUES
                                                 (nextval('category_sequence'), 'Electronics and gadgets', 'Electronics'),
                                                 (nextval('category_sequence'), 'Books and Stationery', 'Books'),
                                                 (nextval('category_sequence'), 'Groceries and household supplies', 'Groceries'),
                                                 (nextval('category_sequence'), 'Fashion and Apparel', 'Clothing');

-- Insert data into the 'product' table
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
                                                                                        (nextval('product_sequence'), 'Smartphone with 128GB storage', 'Smartphone', 50, 699.99, (SELECT id FROM category WHERE name = 'Electronics')),
                                                                                        (nextval('product_sequence'), 'Fiction novel bestseller', 'Novel', 100, 19.99, (SELECT id FROM category WHERE name = 'Books')),
                                                                                        (nextval('product_sequence'), 'Organic Apples 1kg', 'Apples', 200, 3.49, (SELECT id FROM category WHERE name = 'Groceries')),
                                                                                        (nextval('product_sequence'), 'Men Cotton T-Shirt', 'T-Shirt', 150, 15.99, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_sequence'), 'Noise-canceling headphones', 'Headphones', 25, 129.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_sequence'), 'Notebook 200 pages', 'Notebook', 300, 2.99, (SELECT id FROM category WHERE name = 'Books'));
