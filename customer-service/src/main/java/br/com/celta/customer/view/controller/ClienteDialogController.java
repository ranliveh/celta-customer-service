package br.com.celta.customer.view.controller;

import br.com.celta.customer.entity.Cliente;
import br.com.celta.customer.entity.TipoPessoaEnum;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.persistence.ClienteDAO;
import br.com.celta.customer.view.LocalizarManager;
import br.com.celta.customer.view.validation.BeanValidation;
import br.com.celta.customer.view.validation.CustomValidation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
public class ClienteDialogController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private BorderPane inputPane;
    @FXML
    private Button buttonNovo;
    @FXML
    private Button buttonSalvar;
    @FXML
    private CheckBox checkAtivo;
    @FXML
    private ComboBox<TipoPessoaEnum> comboTipo;
    @FXML
    private TextField textDocumento;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textLogradouro;
    @FXML
    private TextField textBairro;
    @FXML
    private TextField textCEP;
    @FXML
    private TextField textComplemento;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textMunicipio;
    @FXML
    private TextField textNumero;
    @FXML
    private TextField textTelefone;
    @FXML
    private TextField textUF;
    @Inject
    private LocalizarManager localizarManager;
    @Inject
    private ClienteDAO clienteDAO;
    private Cliente cliente;
    private BooleanProperty editable = new SimpleBooleanProperty();
    private Cliente clienteEditable = new Cliente();
    private final BeanPathAdapter<Cliente> adapter = new BeanPathAdapter<>(clienteEditable);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editable.setValue(false);
        comboTipo.getItems().addAll(Arrays.asList(TipoPessoaEnum.values()));
        
        configureEditable(comboTipo);
        configureEditable(textDocumento);
        configureEditable(textNome);
        configureEditable(textLogradouro);
        configureEditable(textNumero);
        configureEditable(textComplemento);
        configureEditable(textBairro);
        configureEditable(textCEP);
        configureEditable(textMunicipio);
        configureEditable(textUF);
        configureEditable(textTelefone);
        configureEditable(textEmail);
        configureEditable(checkAtivo);

        buttonSalvar.disableProperty().bind(editable.not());
        buttonNovo.disableProperty().bind(editable);

        adapter.bindBidirectional("tipo", comboTipo.valueProperty(), TipoPessoaEnum.class);
        adapter.bindBidirectional("documento", textDocumento.textProperty());
        adapter.bindBidirectional("nome", textNome.textProperty());
        adapter.bindBidirectional("logradouro", textLogradouro.textProperty());
        adapter.bindBidirectional("numero", textNumero.textProperty());
        adapter.bindBidirectional("complemento", textComplemento.textProperty());
        adapter.bindBidirectional("bairro", textBairro.textProperty());
        adapter.bindBidirectional("cep", textCEP.textProperty());
        adapter.bindBidirectional("municipio", textMunicipio.textProperty());
        adapter.bindBidirectional("uf", textUF.textProperty());
        adapter.bindBidirectional("telefone", textTelefone.textProperty());
        adapter.bindBidirectional("email", textEmail.textProperty());
        adapter.bindBidirectional("ativo", checkAtivo.selectedProperty());
    }

    private void configureEditable(Node node) {
        node.disableProperty().bind(editable.not());
    }

    private void close() {
        inputPane.getScene().getWindow().hide();
    }

    private void configureBean(Cliente newValue) {
        try {
            BeanUtils.copyProperties(this.clienteEditable, newValue);
            this.editable.setValue(cliente != null);
            this.adapter.setBean(clienteEditable);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
    }

    private boolean validate(Object bean) {
        CustomValidation validation = new CustomValidation();
        validation.addValidateable(new BeanValidation(bean, "documento", textDocumento));
        validation.addValidateable(new BeanValidation(bean, "nome", textNome));
        validation.addValidateable(new BeanValidation(bean, "logradouro", textLogradouro));
        validation.addValidateable(new BeanValidation(bean, "numero", textNumero));
        validation.addValidateable(new BeanValidation(bean, "complemento", textComplemento));
        validation.addValidateable(new BeanValidation(bean, "bairro", textBairro));
        validation.addValidateable(new BeanValidation(bean, "cep", textCEP));
        validation.addValidateable(new BeanValidation(bean, "municipio", textMunicipio));
        validation.addValidateable(new BeanValidation(bean, "uf", textUF));
        validation.addValidateable(new BeanValidation(bean, "telefone", textTelefone));
        validation.addValidateable(new BeanValidation(bean, "email", textEmail));

        return validation.validate();
    }

    @FXML
    void handleOnNewAction() {
        this.cliente = new Cliente();
        configureBean(cliente);
        this.textNome.requestFocus();
    }

    @FXML
    void handleOnSaveAction() {
        if (!validate(clienteEditable)) {
            return;
        }

        try {
            if (clienteEditable.getClienteID() == null) {
                clienteDAO.insert(clienteEditable);
            } else {
                clienteDAO.update(clienteEditable);
            }
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
        this.cliente = localizarManager.localizarCliente(inputPane.getScene().getWindow());
        configureBean(cliente == null ? new Cliente() : this.cliente);
    }
}