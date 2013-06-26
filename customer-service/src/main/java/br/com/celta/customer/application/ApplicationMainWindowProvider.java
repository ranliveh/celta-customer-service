package br.com.celta.customer.application;

import br.com.celta.customer.bus.qualifiers.StartupScene;
import javafx.stage.Window;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

/**
 * ApplicationMainWindowProvider.class
 *
 * @author Ranlive Hrysyk
 */
@Singleton
public class ApplicationMainWindowProvider {

    private Window mainWindow;

    public Window getMainWindow() {
        return this.mainWindow;
    }

    public void setMainWindow(@Observes @StartupScene final Window window) {
        this.mainWindow = window;
    }
}