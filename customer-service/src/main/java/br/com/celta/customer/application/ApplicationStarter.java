package br.com.celta.customer.application;

import br.com.celta.customer.bus.qualifiers.StartupScene;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.CloseApplication;
import br.com.celta.customer.bus.events.DisplayLogin;
import br.com.celta.customer.bus.events.UpdateChamado;
import br.com.celta.customer.bus.events.UpdateEmpresa;
import br.com.celta.customer.bus.events.UpdatePermission;
import br.com.celta.customer.exceptions.CustomerException;
import br.com.celta.customer.factory.ParentLoaderFactory;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.view.controller.MainWindowController;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * ApplicationStarter.class<br> Classe de iniciação da aplicação.
 *
 * @author Ranlive Hrysyk
 */
@Singleton
public class ApplicationStarter {

    @Inject
    private FXMLLoader loader;
    @Inject
    @Name("messages")
    private ResourceBundle messages;
    @Inject
    private ApplicationConfig config;
    @Inject
    private SecurityContext securityContext;
    private MainWindowController controller;

    private Parent loadMainWindow() {
        final String xmlPath = MessageFormat.format(ParentLoaderFactory.FXML_PATH, "MainWindow");
        final InputStream stream = ParentLoaderFactory.class.getResourceAsStream(xmlPath);

        Parent root;
        try {
            loader.setLocation(getClass().getResource(xmlPath));
            root = (Parent) loader.load(stream);
            this.controller = loader.getController();
        } catch (IOException exception) {
            EventManager.fire(new CustomerException(messages.getString("exception.fileNotFound", xmlPath), exception, false));
            root = AnchorPaneBuilder.create().stylesheets(getClass().getResource("/styles/default.css").toExternalForm()).id("mainWindow").prefWidth(config.getApplicationWidth()).prefHeight(config.getApplicationHeigth()).build();
        }

        return root;
    }

    private void configureStage(final Stage stage) {
        String title = config.getApplicationTitle();
        stage.setScene(new Scene(loadMainWindow(), config.getApplicationWidth(), config.getApplicationHeigth(), Color.BLACK));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle(config.getApplicationTitle());
        stage.centerOnScreen();
        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (!securityContext.isLoggedIn()) {
                    EventManager.fire(new CloseApplication(CloseApplication.EXIT_CODE_OK));
                }
            }
        });
    }

    public void launchApplication(@Observes @StartupScene final Stage stage) throws IOException {
        configureStage(stage);

        EventManager.fire(new UpdateEmpresa());
        EventManager.fire(new DisplayLogin());
        EventManager.fire(new UpdatePermission());
        EventManager.fire(new UpdateChamado());

        stage.show();
    }

    /**
     * Captura o evento de atualizar empresa.
     */
    public void updateEmpresa(@Observes final UpdateEmpresa event) {
        this.controller.updateEmpresa();
    }

    /**
     * Captura o evento de atualizar permissões.
     */
    public void updatePermission(@Observes final UpdatePermission event) {
        this.controller.updatePermissions();
    }

    /**
     * Captura o evento de atualizar a lista de chamados.
     */
    public void updateChamados(@Observes final UpdateChamado event) {
        this.controller.updateChamados();
    }

    /**
     * Captura o evento de encerrar a aplicação.
     */
    public void closeApplication(@Observes final CloseApplication event) {
        System.exit(event.getExitCode());
    }
}