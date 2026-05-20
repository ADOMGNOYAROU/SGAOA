-- Script de vérification de la base de données MySQL SGAOA
-- Exécutez ce script dans phpMyAdmin ou MySQL CLI

-- 1. Vérifier que nous sommes sur la bonne base de données
USE sgaoa_db;

-- 2. Lister toutes les tables créées par Hibernate/JPA
SHOW TABLES;

-- 3. Voir la structure des tables principales
DESCRIBE utilisateurs;
DESCRIBE adoptants;
DESCRIBE orphelins;
DESCRIBE demandes_adoption;
DESCRIBE enquetes_sociales;
DESCRIBE documents;
DESCRIBE etapes_adoption;
DESCRIBE paiements;
DESCRIBE rapports_sociaux;

-- 4. Vérifier les données existantes
SELECT 'UTILISATEURS' as table_name, COUNT(*) as total_records FROM utilisateurs
UNION ALL
SELECT 'ADOPTANTS', COUNT(*) FROM adoptants
UNION ALL
SELECT 'ORPHELINS', COUNT(*) FROM orphelins
UNION ALL
SELECT 'DEMANDES_ADOPTION', COUNT(*) FROM demandes_adoption
UNION ALL
SELECT 'ENQUETES_SOCIALES', COUNT(*) FROM enquetes_sociales
UNION ALL
SELECT 'DOCUMENTS', COUNT(*) FROM documents
UNION ALL
SELECT 'ETAPES_ADOPTION', COUNT(*) FROM etapes_adoption
UNION ALL
SELECT 'PAIEMENTS', COUNT(*) FROM paiements
UNION ALL
SELECT 'RAPPORTS_SOCIAUX', COUNT(*) FROM rapports_sociaux;

-- 5. Voir les derniers utilisateurs créés
SELECT id, nom, prenom, email, role, statut, date_creation 
FROM utilisateurs 
ORDER BY date_creation DESC 
LIMIT 5;

-- 6. Vérifier les contraintes et clés étrangères
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE 
    TABLE_SCHEMA = 'sgaoa_db' 
    AND REFERENCED_TABLE_NAME IS NOT NULL;

-- 7. Configuration de la base de données
SELECT 
    DATABASE() as base_de_donnees,
    USER() as utilisateur_connecte,
    VERSION() as version_mysql;

-- 8. Statistiques de la base
SELECT 
    table_name,
    table_rows,
    data_length,
    index_length
FROM 
    information_schema.tables 
WHERE 
    table_schema = 'sgaoa_db'
ORDER BY 
    table_name;
