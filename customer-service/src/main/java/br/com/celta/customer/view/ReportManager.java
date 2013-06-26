package br.com.celta.customer.view;

import br.com.celta.customer.bus.events.DisplayReportAtendente;
import br.com.celta.customer.bus.events.DisplayReportCliente;
import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.entity.Cliente;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.com.celta.customer.persistence.ClienteDAO;
import br.com.celta.customer.utils.ExceptionUtils;
import br.gov.frameworkdemoiselle.report.Report;
import br.gov.frameworkdemoiselle.report.annotation.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * ReportManager.class
 *
 * @author Ranlive Hrysyk
 */
@Singleton
public class ReportManager {

    private static final long serialVersionUID = 1L;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private AtendenteDAO atendenteDAO;
    @Inject
    @Path("reports/Clientes.jasper")
    private Report clientes;
    @Inject
    @Path("reports/Atendentes.jasper")
    private Report atendentes;

    /**
     * Mostra o relatório de cadastro de clientes.
     */
    public void showReportClientes(@Observes final DisplayReportCliente event) {
        try {
            List<Cliente> list = clienteDAO.findAll();
            Map<String, Object> parametros = new HashMap<>();
            clientes.prepare(list, parametros);
            JasperViewer.viewReport((JasperPrint) clientes.getReportObject(), false);
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
    }
//

    /**
     * Mostra o relatório de cadastro de clientes.
     */
    public void showReportAtendentes(@Observes final DisplayReportAtendente event) {
        try {
            List<Atendente> list = atendenteDAO.findAll();
            Map<String, Object> parametros = new HashMap<>();
            atendentes.prepare(list, parametros);
            JasperViewer.viewReport((JasperPrint) atendentes.getReportObject(), false);
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
    }
}