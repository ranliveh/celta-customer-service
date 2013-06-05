package br.com.celta.customer.persistence;

import br.com.celta.customer.entity.Empresa;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

/**
 * EmpresaDAO.class
 *
 * @author Ranlive Hrysyk
 */
@PersistenceController
public class EmpresaDAO extends JPACrud<Empresa, Integer> {

    private static final long serialVersionUID = 1L;
}