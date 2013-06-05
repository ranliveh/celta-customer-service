package br.com.celta.customer.view.controller;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.CloseApplication;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.security.Credenciais;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.inject.Inject;

/**
 * LoginDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class LoginDialogController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button buttonLogin;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField textLogin;
    @FXML
    private PasswordField textPassword;
    @Inject
    private Credenciais credenciais;
    @Inject
    private SecurityContext securityContext;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        this.resources = resource;
        errorMessage.setText("");
    }

    @FXML
    void cancelAction() {
        EventManager.fire(new CloseApplication(0));
    }

    @FXML
    void loginAction() {
        try {
            credenciais.setLogin(textLogin.getText());
            credenciais.setSenha(textPassword.getText());
            securityContext.login();

            if (securityContext.isLoggedIn()) {
                buttonLogin.getScene().getWindow().hide();
            } else {
                errorMessage.setText(resources.getString("login.errorMessage"));
                showMessageError();
            }
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, false);
        }
    }

    private void showMessageError() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), errorMessage);
        fade.setFromValue(0.0);
        fade.setToValue(1);
        fade.play();
    }
}