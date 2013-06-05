package br.com.celta.customer.producer;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * FXMLLoaderProducer.class<br> Classe responsavel pelo produção do FXMLLoader
 * para injeção.
 *
 * @author Ranlive Hrysyk
 */
public class FXMLLoaderProducer {

    private static final long serialVersionUID = 1L;
    @Inject
    private Instance<Object> instance;
    @Inject
    @Name("messages")
    private ResourceBundle resources;

    @Produces
    public FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resources);
        loader.setLocation(getClass().getResource("/fxml/"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return instance.select(param).get();
            }
        });
        return loader;
    }
}