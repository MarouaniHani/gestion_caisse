package com.example.pfe.services;

public interface EmployeeService {
    boolean checkIsAdmin(String role);
    boolean checkIsAdminOrAgentServiceSocial(String role);
}
