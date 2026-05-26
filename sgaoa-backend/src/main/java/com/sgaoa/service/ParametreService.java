package com.sgaoa.service;

import java.util.Map;

public interface ParametreService {
    Map<String, Object> getParametres();
    Map<String, Object> updateParametres(Map<String, Object> parametres);
    Map<String, Object> getParametresSysteme();
    Map<String, Object> getParametresNotifications();
}
