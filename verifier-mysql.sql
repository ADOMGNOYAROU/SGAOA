-- Script pour vérifier que les données sont bien dans MySQL
-- Exécutez ces commandes dans votre client MySQL (phpMyAdmin, MySQL CLI, etc.)

-- 1. Vérifier que vous utilisez la bonne base de données
USE sgaoa_db;

-- 2. Voir toutes les bases de données disponibles
SHOW DATABASES;

-- 3. Voir les tables dans la base sgaoa_db
SHOW TABLES;

-- 4. Voir la structure de la table utilisateurs
DESCRIBE utilisateurs;

-- 5. Voir toutes les données dans la table utilisateurs
SELECT * FROM utilisateurs;

-- 6. Compter le nombre d'utilisateurs
SELECT COUNT(*) as total_utilisateurs FROM utilisateurs;

-- 7. Voir les derniers utilisateurs créés
SELECT id, nom, prenom, email, statut, date_creation 
FROM utilisateurs 
ORDER BY date_creation DESC;

-- Si vous ne voyez rien, exécutez ceci pour créer la base manuellement :
-- CREATE DATABASE IF NOT EXISTS sgaoa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE sgaoa_db;
