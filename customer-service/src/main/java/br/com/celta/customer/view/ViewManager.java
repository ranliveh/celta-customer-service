package br.com.celta.customer.view;

import br.com.celta.customer.application.ApplicationExceptionProvider;
import br.com.celta.customer.application.ApplicationMainWindowProvider;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.*;
import br.com.celta.customer.exceptions.CustomerException;
import br.com.celta.customer.factory.ParentLoaderFactory;
import static br.com.celta.customer.factory.ParentLoaderFactory.FXML_PATH;
import br.com.celta.customer.view.dialogs.Dialogs;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextBuilder;
import javafx.stage.Window;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * ViewManager.class
 *
 * @author Ranlive Hrysyk
 */
public class ViewManager {

    private static final long serialVersionUID = 1L;
    @Inject
    private Dialogs dialogs;
    @Inject
    private ApplicationExceptionProvider exceptionProvider;
    @Inject
    private ApplicationMainWindowProvider mainWindowProvider;
    @Inject
    @Name("messages")
    private ResourceBundle resourceMessage;
    @Inject
    private ParentLoaderFactory parentLoader;
    @Inject
    private FXMLLoader fxmlLoader;

    /**
     * Mostra o dialogo com os erros da aplicação.
     */
    public void showErrorDialog(@Observes final DisplayExceptions event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final String xmlPath = MessageFormat.format(FXML_PATH, "ErrorDialog");
        final InputStream stream = Dialogs.class.getResourceAsStream(xmlPath);

        while (exceptionProvider.hasException()) {
            try {
                fxmlLoader.setLocation(getClass().getResource(xmlPath));
                final Parent root = (Parent) fxmlLoader.load(stream);
                dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
                    @Override
                    public void perform() {

                        for (Node node : root.lookupAll(".button")) {
                            if (node instanceof Button && ((Button) node).isDefaultButton()) {
                                node.requestFocus();
                            }
                        }
                    }
                });
            } catch (IOException ex) {
                showBackupErrorDialog();
            }
        }
    }

    /**
     * Cria e mostra um backup do ErrorDialog caso não consiga carregar o
     * arquivo ErrorDialog.fxml.
     */
    private void showBackupErrorDialog() {
        final CustomerException exception = exceptionProvider.getNextException();

        final VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.getStylesheets().add(Dialogs.class.getResource("/styles/default.css").toExternalForm());
        root.getChildren().add(TextBuilder.create().text("Oops...").styleClass("heading").build());

        if (exception.isRecoverable()) {
            root.getChildren().add(new Label(resourceMessage.getString("error.message.continue")));
        } else {
            root.getChildren().add(new Label(resourceMessage.getString("error.message.unrecoverable")));
        }

        final HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        final Button quitButton = ButtonBuilder.create()
                .text(resourceMessage.getString("error.button.quit"))
                .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                EventManager.fire(new CloseApplication(1));
            }
        }).build();
        buttons.getChildren().add(quitButton);

        final Button continueButton = ButtonBuilder.create()
                .text(resourceMessage.getString("error.button.continue"))
                .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                root.getScene().getWindow().hide();
            }
        }).build();

        if (exception.isRecoverable()) {
            continueButton.setDefaultButton(true);
            buttons.getChildren().add(continueButton);
        } else {
            quitButton.setDefaultButton(true);
        }

        root.getChildren().add(buttons);

        final Window owner = mainWindowProvider.getMainWindow();
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                for (Node node : root.lookupAll(".button")) {
                    if (node instanceof Button && ((Button) node).isDefaultButton()) {
                        node.requestFocus();
                    }
                }
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Login.
     */
    public void showLoginDialog(@Observes final DisplayLogin event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("LoginDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Dados Cadastrais.
     */
    public void showMeusDadosDialog(@Observes final DisplayEmpresa event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("EmpresaDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Clientes.
     */
    public void showClienteDialog(@Observes final DisplayCliente event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("ClienteDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Atendentes.
     */
    public void showClienteDialog(@Observes final DisplayAtendente event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("AtendenteDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Classificações.
     */
    public void showCalassificacaoDialog(@Observes final DisplayClassificacao event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("ClassificacaoDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de Categorias.
     */
    public void showCategoriaDialog(@Observes final DisplayCategoria event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("CategoriaDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de novo chamado.
     */
    public void showNewChamadoDialog(@Observes final NewChamado event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("ChamadoCadastroDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }

    /**
     * Carrega e mostra o diálogo de edição de chamado.
     */
    public void showEditChamadoDialog(@Observes final EditChamado event) {
        final Window owner = mainWindowProvider.getMainWindow();
        final Parent root = parentLoader.load("ChamadoEdicaoDialog");
        dialogs.showDialog(owner, root, new Dialogs.OnShownAction() {
            @Override
            public void perform() {
                root.requestFocus();
            }
        });
    }
}