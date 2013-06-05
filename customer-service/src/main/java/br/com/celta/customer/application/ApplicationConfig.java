package br.com.celta.customer.application;

import br.gov.frameworkdemoiselle.configuration.Configuration;
import java.io.Serializable;

/**
 * ApplicationConfig.class
 *
 * @author Ranlive Hrysyk
 */
@Configuration(resource = "system")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private String applicationTitle;
    private Double applicationWidth;
    private Double applicationHeigth;
    private Integer applicationEmpresaId;

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public Double getApplicationWidth() {
        return applicationWidth;
    }

    public Double getApplicationHeigth() {
        return applicationHeigth;
    }

    public Integer getApplicationEmpresaId() {
        return applicationEmpresaId;
    }
}