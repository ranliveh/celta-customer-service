package br.com.celta.customer;

import br.com.celta.customer.application.ApplicationMainWindowProvider;
import br.com.celta.customer.bus.qualifiers.StartupScene;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.enterprise.util.AnnotationLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * ApplicationLauncher.class
 *
 * @author Ranlive Hrysyk
 */
public class ApplicationLauncher extends Application {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
//        new StartMain(args).go();
        Application.launch(ApplicationLauncher.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
//        EventManager.fire(primaryStage, new AnnotationLiteral<StartupScene>() {
//        });
        WeldContainer weldContainer = new Weld().initialize();
        weldContainer.instance().select(ApplicationMainWindowProvider.class).get().setMainWindow(primaryStage);
        weldContainer.event().select(Stage.class, new AnnotationLiteral<StartupScene>() {
        }).fire(primaryStage);
    }
}