package br.com.celta.customer.factory;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.exceptions.CustomerException;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.io.InputStream;
import java.text.MessageFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.inject.Inject;

/**
 * ParentLoaderFactory.class
 *
 * @author Ranlive Hrysyk
 */
public class ParentLoaderFactory {

    private static final long serialVersionUID = 1L;
    public static final String FXML_PATH = "/fxml/{0}.fxml";
    @Inject
    private FXMLLoader loader;
    @Inject
    private ResourceBundle resources;

    /**
     * Obtém o Parent de um arquivo FXML.
     */
    public Parent load(String fxmlName) {
        final String xmlPath = MessageFormat.format(ParentLoaderFactory.FXML_PATH, fxmlName);
        final InputStream stream = ParentLoaderFactory.class.getResourceAsStream(xmlPath);

        Parent root = null;
        try {
            loader.setController(null);
            loader.setRoot(null);
            loader.setLocation(getClass().getResource(xmlPath));
            root = (Parent) loader.load(stream);
        } catch (Exception exception) {
            EventManager.fire(new CustomerException(resources.getString("exception.fileNotFound", xmlPath), exception, false));
        }

        return root;
    }
}