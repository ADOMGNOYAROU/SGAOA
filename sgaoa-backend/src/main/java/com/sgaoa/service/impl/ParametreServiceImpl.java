package com.sgaoa.service.impl;

import com.sgaoa.service.ParametreService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParametreServiceImpl implements ParametreService {

    @Override
    public Map<String, Object> getParametres() {
        Map<String, Object> parametres = new HashMap<>();
        parametres.put("nomApplication", "SGAOA");
        parametres.put("version", "1.0.0");
        parametres.put("emailSupport", "support@sgaoa.com");
        parametres.put("telephoneSupport", "+221 77 123 45 67");
        return parametres;
    }

    @Override
    public Map<String, Object> updateParametres(Map<String, Object> parametres) {
        // Pour l'instant, retourne les paramètres reçus
        // Dans une implémentation complète, on sauvegarderait en base de données
        return parametres;
    }

    @Override
    public Map<String, Object> getParametresSysteme() {
        Map<String, Object> parametres = new HashMap<>();
        parametres.put("maintenanceMode", false);
        parametres.put("enregistrementActif", true);
        parametres.put("delaiMaxDemande", 30); // jours
        return parametres;
    }

    @Override
    public Map<String, Object> getParametresNotifications() {
        Map<String, Object> parametres = new HashMap<>();
        parametres.put("emailActif", true);
        parametres.put("smsActif", false);
        parametres.put("notificationsPush", true);
        return parametres;
    }
}
