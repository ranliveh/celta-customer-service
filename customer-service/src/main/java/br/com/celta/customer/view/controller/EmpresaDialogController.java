package br.com.celta.customer.view.controller;

import br.com.celta.customer.application.ApplicationConfig;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.UpdateEmpresa;
import br.com.celta.customer.entity.Empresa;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.persistence.EmpresaDAO;
import br.com.celta.customer.security.Roles;
import br.com.celta.customer.view.validation.BeanValidation;
import br.com.celta.customer.view.validation.CustomValidation;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javax.inject.Inject;
import jfxtras.labs.scene.control.BeanPathAdapter;

/**
 * EmpresaDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class EmpresaDialogController implements Initializable {
    
    private static final long serialVersionUID = 1L;
    @FXML
    private BorderPane inputPane;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textTelefone;
    @FXML
    private Button buttonSalvar;
    @FXML
    private Button buttonCancelar;
    @Inject
    private ApplicationConfig config;
    @Inject
    private EmpresaDAO empresaDAO;
    @Inject
    private SecurityContext securityContext;
    private Empresa empresa;
    private BeanPathAdapter<Empresa> adapter;
    private BooleanProperty hasPermission = new SimpleBooleanProperty();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            hasPermission.setValue(securityContext.hasRole(Roles.EMPRESA));            
            
            final Integer id = config.getApplicationEmpresaId();
            empresa = empresaDAO.load(id);
            
            configurePermission(textNome);
            configurePermission(textEmail);
            configurePermission(textTelefone);
            configurePermission(buttonSalvar);
            
            adapter = new BeanPathAdapter<>(empresa);
            adapter.bindBidirectional("nome", textNome.textProperty());
            adapter.bindBidirectional("email", textEmail.textProperty());
            adapter.bindBidirectional("telefone", textTelefone.textProperty());
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, false);
        }
    }
    
    private void configurePermission(final Node node){
        node.disableProperty().bind(hasPermission.not());
    }
    
    private void close() {
        inputPane.getScene().getWindow().hide();
    }
    
    private boolean validate(Object bean) {
        CustomValidation validation = new CustomValidation();
        validation.addValidateable(new BeanValidation(bean, "nome", textNome));
        validation.addValidateable(new BeanValidation(bean, "email", textEmail));
        validation.addValidateable(new BeanValidation(bean, "telefone", textTelefone));
        
        return validation.validate();
    }
    
    @FXML
    public void handleOnSaveAction() {
        if (!validate(empresa)) {
            return;
        }
        
        try {
            empresaDAO.update(empresa);
            EventManager.fire(new UpdateEmpresa());
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        
        close();
    }
    
    @FXML
    public void handleOnCancelAction() {
        close();
    }
}