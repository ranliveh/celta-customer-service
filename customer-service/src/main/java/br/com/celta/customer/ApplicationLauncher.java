package br.com.celta.customer;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.qualifiers.StartupScene;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.enterprise.util.AnnotationLiteral;
import org.jboss.weld.environment.se.StartMain;

/**
 * ApplicationLauncher.class
 *
 * @author Ranlive Hrysyk
 */
public class ApplicationLauncher extends Application {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new StartMain(args).go();
        Application.launch(ApplicationLauncher.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        EventManager.fire(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }
}