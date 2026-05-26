-- Script pour créer un orphelin de test
INSERT INTO orphelins (nom, prenom, date_naissance, lieu_naissance, sexe, etat_sante, historique_medical, situation_familiale, statut, age, date_creation, date_modification)
VALUES ('Doe', 'John', '2015-05-15', 'Paris', 'M', 'Bon', 'Aucun antécédent médical', 'Parents décédés', 'DISPONIBLE', 9, NOW(), NOW())
ON DUPLICATE KEY UPDATE statut = 'DISPONIBLE';
