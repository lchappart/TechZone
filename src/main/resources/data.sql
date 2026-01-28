INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

INSERT INTO users (email, password, nom, prenom, adresse) VALUES 
('admin@techzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ0C', 'Admin', 'TechZone', '123 Rue Admin, Paris 75001');
INSERT INTO users (email, password, nom, prenom, adresse) VALUES 
('admin2@techzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ0C', 'Dupont', 'Jean', '456 Avenue Admin, Lyon 69001');

INSERT INTO users (email, password, nom, prenom, adresse) VALUES 
('user@techzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ0C', 'Martin', 'Pierre', '789 Rue User, Marseille 13001');
INSERT INTO users (email, password, nom, prenom, adresse) VALUES 
('user2@techzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ0C', 'Bernard', 'Marie', '321 Boulevard User, Toulouse 31000');
INSERT INTO users (email, password, nom, prenom, adresse) VALUES 
('user3@techzone.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ0C', 'Dubois', 'Sophie', '654 Place User, Nice 06000');

INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'admin@techzone.com' AND r.name = 'ADMIN';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'admin2@techzone.com' AND r.name = 'ADMIN';

INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'user@techzone.com' AND r.name = 'USER';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'user2@techzone.com' AND r.name = 'USER';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'user3@techzone.com' AND r.name = 'USER';

INSERT INTO categories (nom, description) VALUES ('PC Portables', 'Ordinateurs portables de toutes marques et configurations');
INSERT INTO categories (nom, description) VALUES ('Smartphones', 'Smartphones dernière génération avec les meilleures performances');
INSERT INTO categories (nom, description) VALUES ('Accessoires', 'Accessoires high-tech : casques, chargeurs, étuis, etc.');
INSERT INTO categories (nom, description) VALUES ('PC Bureautique', 'Ordinateurs de bureau pour le travail et la productivité');
INSERT INTO categories (nom, description) VALUES ('Gaming', 'Équipements gaming : PC, périphériques, accessoires');

INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('MacBook Pro 16" M3', 'MacBook Pro 16 pouces avec puce M3, 16GB RAM, 512GB SSD. Parfait pour les professionnels créatifs.', 2499.99, 1, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Dell XPS 15', 'Dell XPS 15 avec Intel i7, 16GB RAM, écran 4K OLED. Idéal pour le travail et le divertissement.', 1899.99, 1, 'EN_STOCK', true);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Lenovo ThinkPad X1 Carbon', 'ThinkPad X1 Carbon ultra-léger, Intel i7, 16GB RAM. Parfait pour les professionnels en déplacement.', 1599.99, 1, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('ASUS ROG Zephyrus G15', 'PC portable gaming ASUS ROG avec RTX 4070, AMD Ryzen 9, 32GB RAM. Performance gaming exceptionnelle.', 2199.99, 1, 'RUPTURE', false);

INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('iPhone 15 Pro Max', 'iPhone 15 Pro Max 256GB, écran 6.7", puce A17 Pro, triple capteur photo. Le meilleur iPhone.', 1299.99, 2, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Samsung Galaxy S24 Ultra', 'Galaxy S24 Ultra 256GB, écran 6.8" AMOLED, S Pen inclus, zoom 100x. Smartphone premium Android.', 1199.99, 2, 'EN_STOCK', true);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Google Pixel 8 Pro', 'Pixel 8 Pro 128GB, écran 6.7", caméra exceptionnelle, Android pur. Le meilleur de Google.', 999.99, 2, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('OnePlus 12', 'OnePlus 12 256GB, Snapdragon 8 Gen 3, charge rapide 100W, écran 120Hz. Performance et rapidité.', 899.99, 2, 'RUPTURE', false);

INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('AirPods Pro 2', 'AirPods Pro 2ème génération avec réduction de bruit active, spatial audio, autonomie 6h. Audio premium.', 279.99, 3, 'EN_STOCK', true);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Sony WH-1000XM5', 'Casque sans fil Sony avec réduction de bruit exceptionnelle, autonomie 30h, qualité audio Hi-Res.', 399.99, 3, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Chargeur MagSafe Apple', 'Chargeur MagSafe officiel Apple pour iPhone, charge rapide 15W, design magnétique.', 39.99, 3, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Étui iPhone 15 Pro Max', 'Étui de protection transparent avec protection MagSafe, design élégant et résistant.', 29.99, 3, 'EN_STOCK', false);

INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('iMac 24" M3', 'iMac 24 pouces avec puce M3, 8GB RAM, 256GB SSD, écran Retina 4.5K. Design élégant et performance.', 1499.99, 4, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('HP Pavilion Desktop', 'PC de bureau HP Pavilion, Intel i5, 16GB RAM, 512GB SSD + 1TB HDD. Parfait pour le bureau.', 799.99, 4, 'EN_STOCK', true);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Dell OptiPlex 7090', 'PC de bureau professionnel Dell OptiPlex, Intel i7, 32GB RAM, 1TB SSD. Performance et fiabilité.', 1299.99, 4, 'RUPTURE', false);

INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Razer DeathAdder V3', 'Souris gaming Razer DeathAdder V3, capteur optique 30K DPI, design ergonomique. Précision extrême.', 79.99, 5, 'EN_STOCK', false);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Logitech G Pro X', 'Casque gaming Logitech G Pro X, audio 7.1 surround, micro Blue Voice. Communication claire.', 199.99, 5, 'EN_STOCK', true);
INSERT INTO products (nom, description, prix, category_id, stock_status, promotion) VALUES 
('Corsair K70 RGB TKL', 'Clavier mécanique gaming Corsair K70 RGB TKL, switches Cherry MX, rétroéclairage RGB. Performance et style.', 179.99, 5, 'EN_STOCK', false);

INSERT INTO orders (date, statut, total, user_id) VALUES 
('2024-01-15 10:30:00', 'LIVREE', 1299.99, (SELECT id FROM users WHERE email = 'user@techzone.com'));
INSERT INTO order_lines (quantity, prix_unitaire, product_id, order_id) VALUES 
(1, 1299.99, (SELECT id FROM products WHERE nom = 'iPhone 15 Pro Max'), (SELECT MAX(id) FROM orders));

INSERT INTO orders (date, statut, total, user_id) VALUES 
('2024-01-20 14:15:00', 'EXPEDIEE', 279.99, (SELECT id FROM users WHERE email = 'user@techzone.com'));
INSERT INTO order_lines (quantity, prix_unitaire, product_id, order_id) VALUES 
(1, 279.99, (SELECT id FROM products WHERE nom = 'AirPods Pro 2'), (SELECT MAX(id) FROM orders));

INSERT INTO orders (date, statut, total, user_id) VALUES 
('2024-01-18 09:00:00', 'CONFIRMEE', 1899.99, (SELECT id FROM users WHERE email = 'user2@techzone.com'));
INSERT INTO order_lines (quantity, prix_unitaire, product_id, order_id) VALUES 
(1, 1899.99, (SELECT id FROM products WHERE nom = 'Dell XPS 15'), (SELECT MAX(id) FROM orders));

INSERT INTO orders (date, statut, total, user_id) VALUES 
('2024-01-22 16:45:00', 'EN_ATTENTE', 1199.99, (SELECT id FROM users WHERE email = 'user3@techzone.com'));
INSERT INTO order_lines (quantity, prix_unitaire, product_id, order_id) VALUES 
(1, 1199.99, (SELECT id FROM products WHERE nom = 'Samsung Galaxy S24 Ultra'), (SELECT MAX(id) FROM orders));
