-- Script pour créer un utilisateur administrateur par défaut
-- Exécutez ce script dans votre base de données MySQL (sgaoa_db)

USE sgaoa_db;

-- Supprimer l'utilisateur admin s'il existe déjà
DELETE FROM utilisateurs WHERE email = 'admin@sgaoa.local';

-- Insérer un utilisateur administrateur par défaut
-- Mot de passe : admin123 (hash BCrypt)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, role, statut, email_verifie, date_creation) 
VALUES (
    'Admin',
    'SGAOA',
    'admin@sgaoa.local',
    '$2a$10$UkrgbfqRrZ6z463QK16xF.wJ/YfRlWPX.bFk.oqE.3S3ryfp5fuZe',
    NULL,
    'ADMINISTRATEUR',
    'ACTIF',
    true,
    NOW()
);

-- Vérifier que l'utilisateur a été créé
SELECT id, nom, prenom, email, role, statut, email_verifie, date_creation 
FROM utilisateurs 
WHERE email = 'admin@sgaoa.local';
