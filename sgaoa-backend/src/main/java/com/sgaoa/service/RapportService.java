package com.sgaoa.service;

import java.util.Map;

public interface RapportService {
    Map<String, Object> getStatistiquesGenerales();
    Map<String, Object> getStatistiquesOrphelins();
    Map<String, Object> getStatistiquesAdoptants();
    Map<String, Object> getStatistiquesDemandes();
    Map<String, Object> getStatistiquesAdoptions();
}
