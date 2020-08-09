package com.example.pfe.services;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public boolean checkIsAdmin(String role) {
        return role.toLowerCase().equals("admin");
    }

    @Override
    public boolean checkIsCaissier(String role) {
        return role.toLowerCase().equals("caissier");
    }

    @Override
    public boolean checkIsAdminOrAgentServiceSocial(String role) {
        return role.toLowerCase().equals("admin") || role.toLowerCase().equals("agent_service_social");
    }

    @Override
    public boolean checkIsAdminOrAgentParcAuto(String role) {
        return role.toLowerCase().equals("admin") || role.toLowerCase().equals("agent_parc_auto");
    }
}
