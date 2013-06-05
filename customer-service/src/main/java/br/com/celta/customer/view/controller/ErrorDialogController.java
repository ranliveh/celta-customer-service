package br.com.celta.customer.view.controller;

import br.com.celta.customer.application.ApplicationExceptionProvider;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.CloseApplication;
import br.com.celta.customer.exceptions.CustomerException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javax.inject.Inject;

/**
 * ErrorDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class ErrorDialogController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private Label continueMessage;
    @FXML
    private TextArea errorMessage;
    @FXML
    private HBox buttonBox;
    @FXML
    private Button quitButton;
    @FXML
    private Button continueButton;
    @Inject
    private ApplicationExceptionProvider exceptionProvider;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        CustomerException exception = exceptionProvider.getNextException();

        if (exception.isRecoverable()) {
            continueMessage.setText(resource.getString("error.message.continue"));
        } else {
            continueMessage.setText(resource.getString("error.message.unrecoverable"));
            buttonBox.getChildren().remove(continueButton);
            quitButton.setDefaultButton(true);
        }

        StringWriter error = new StringWriter();
        exception.printStackTrace(new PrintWriter(error));
        errorMessage.setText(error.toString());
    }

    public void quitAction() {
        EventManager.fire(new CloseApplication(CloseApplication.EXIT_CODE_ERROR));
    }

    public void continueAction() {
        quitButton.getScene().getWindow().hide();
    }
}