package br.com.celta.customer.security;

import br.com.celta.customer.entity.NivelAcessoEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Roles.class
 *
 * @author Ranlive Hrysyk
 */
public class Roles {

    private static final Map<NivelAcessoEnum, List<String>> roleMap = new HashMap<>();
    public static final String EMPRESA = "Empresa";
    public static final String ATENDENTE = "Atendente";
    public static final String CLIENTE = "Cliente";
    public static final String CLASSIFICACAO = "Classificação";
    public static final String CATEGORIA = "Categoria";
    public static final String ROOT = "Root";

    static {
        // Atendente
        List<String> atendenteRoles = new ArrayList<>();
        atendenteRoles.add(CLIENTE);
        roleMap.put(NivelAcessoEnum.ATENDENTE, atendenteRoles);

        // Supervisor
        List<String> suvervisorRoles = new ArrayList<>();
        suvervisorRoles.add(CLIENTE);
        suvervisorRoles.add(ATENDENTE);
        suvervisorRoles.add(CLASSIFICACAO);
        suvervisorRoles.add(CATEGORIA);
        roleMap.put(NivelAcessoEnum.SUPERVISOR, suvervisorRoles);

        // Administrador
        List<String> administradorRoles = new ArrayList<>();
        administradorRoles.add(EMPRESA);
        administradorRoles.add(CLIENTE);
        administradorRoles.add(ATENDENTE);
        administradorRoles.add(CLASSIFICACAO);
        administradorRoles.add(CATEGORIA);
        administradorRoles.add(ROOT);
        roleMap.put(NivelAcessoEnum.ADMINISTRADOR, administradorRoles);
    }

    public static boolean hasRole(NivelAcessoEnum nivel, final String role) {
        if (roleMap.containsKey(nivel)) {
            if (roleMap.get(nivel).contains(role)) {
                return true;
            }
        }

        return false;
    }
}