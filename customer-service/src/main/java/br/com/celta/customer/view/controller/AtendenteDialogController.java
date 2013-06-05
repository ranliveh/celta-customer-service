package br.com.celta.customer.view.controller;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.UpdatePermission;
import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.entity.NivelAcessoEnum;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.com.celta.customer.security.Roles;
import br.com.celta.customer.view.LocalizarManager;
import br.com.celta.customer.view.validation.BeanValidation;
import br.com.celta.customer.view.validation.CustomValidation;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javax.inject.Inject;
import jfxtras.labs.scene.control.BeanPathAdapter;
import org.apache.commons.beanutils.BeanUtils;

/**
 * ClienteDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class AtendenteDialogController implements Initializable {
    
    private static final long serialVersionUID = 1L;
    @FXML
    private BorderPane inputPane;
    @FXML
    private Button buttonNovo;
    @FXML
    private Button buttonSalvar;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textTelefone;
    @FXML
    private TextField textLogin;
    @FXML
    private PasswordField textSenha;
    @FXML
    private ComboBox<NivelAcessoEnum> comboNivel;
    @FXML
    private CheckBox checkAtivo;
    @Inject
    private LocalizarManager localizarManager;
    @Inject
    private AtendenteDAO atendenteDAO;
    @Inject
    private SecurityContext securityContext;
    private Atendente atendente;
    private BooleanProperty editable = new SimpleBooleanProperty();
    private BooleanProperty isRoot = new SimpleBooleanProperty();
    private Atendente atendenteEditable = new Atendente();
    private final BeanPathAdapter<Atendente> adapter = new BeanPathAdapter<>(atendenteEditable);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editable.setValue(false);
        isRoot.setValue(securityContext.hasRole(Roles.ROOT));
        comboNivel.getItems().addAll(Arrays.asList(NivelAcessoEnum.values()));
        
        configureEditable(textNome);
        configureEditable(textEmail);
        configureEditable(textTelefone);
        configureEditable(textLogin);
        configureEditable(checkAtivo);
        
        BooleanBinding hasPermission = new BooleanBinding() {
            {
                super.bind(editable, isRoot);
            }
            
            @Override
            protected boolean computeValue() {
                return editable.get() && isRoot.get();
            }
        };
        textSenha.disableProperty().bind(hasPermission.not());
        comboNivel.disableProperty().bind(hasPermission.not());

        buttonSalvar.disableProperty().bind(editable.not());
        buttonNovo.disableProperty().bind(editable);
        
        adapter.bindBidirectional("nome", textNome.textProperty());
        adapter.bindBidirectional("email", textEmail.textProperty());
        adapter.bindBidirectional("telefone", textTelefone.textProperty());
        adapter.bindBidirectional("login", textLogin.textProperty());
        adapter.bindBidirectional("senha", textSenha.textProperty());
        adapter.bindBidirectional("nivel", comboNivel.valueProperty(), NivelAcessoEnum.class);
        adapter.bindBidirectional("ativo", checkAtivo.selectedProperty());
    }
    
    private void configureEditable(Node node) {
        node.disableProperty().bind(editable.not());
    }
    
    private void close() {
        inputPane.getScene().getWindow().hide();
    }
    
    private void configureBean(Atendente newValue) {
        try {
            BeanUtils.copyProperties(this.atendenteEditable, newValue);
            this.editable.setValue(atendente != null);
            this.adapter.setBean(atendenteEditable);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
    }
    
    private boolean validate(Object bean) {
        CustomValidation validation = new CustomValidation();
        validation.addValidateable(new BeanValidation(bean, "nome", textNome));
        validation.addValidateable(new BeanValidation(bean, "telefone", textTelefone));
        validation.addValidateable(new BeanValidation(bean, "email", textEmail));
        validation.addValidateable(new BeanValidation(bean, "login", textLogin));
        validation.addValidateable(new BeanValidation(bean, "senha", textSenha));
        
        return validation.validate();
    }
    
    @FXML
    void handleOnNewAction() {
        this.atendente = new Atendente();
        configureBean(atendente);
        this.textNome.requestFocus();
    }
    
    @FXML
    void handleOnSaveAction() {
        if (!validate(atendenteEditable)) {
            return;
        }
        
        try {
            if (atendenteEditable.getAtendenteID() == null) {
                atendenteDAO.insert(atendenteEditable);
            } else {
                atendenteDAO.update(atendenteEditable);
            }
            EventManager.fire(new UpdatePermission());
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
        
        close();
    }
    
    @FXML
    void handleOnCancelAction() {
        close();
    }
    
    @FXML
    void handleOnLocalizarAction() {
        this.atendente = localizarManager.localizarAtendente(inputPane.getScene().getWindow());
        configureBean(atendente == null ? new Atendente() : this.atendente);
    }
}