package br.com.celta.customer.view.controller;

import br.com.celta.customer.entity.Classificacao;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.persistence.ClassificacaoDAO;
import br.com.celta.customer.view.LocalizarManager;
import br.com.celta.customer.view.validation.BeanValidation;
import br.com.celta.customer.view.validation.CustomValidation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javax.inject.Inject;
import jfxtras.labs.scene.control.BeanPathAdapter;
import org.apache.commons.beanutils.BeanUtils;

/**
 * CategoriaDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class ClassificacaoDialogController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private BorderPane inputPane;
    @FXML
    private TextField textNome;
    @FXML
    private Button buttonSalvar;
    @FXML
    private Button buttonNovo;
    @Inject
    private LocalizarManager localizarManager;
    @Inject
    private ClassificacaoDAO classificaoDAO;
    private Classificacao classificacao;
    private BooleanProperty editable = new SimpleBooleanProperty();
    private Classificacao classificacaoEditable = new Classificacao();
    private final BeanPathAdapter<Classificacao> adapter = new BeanPathAdapter<>(classificacaoEditable);

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        editable.setValue(false);
        textNome.disableProperty().bind(editable.not());
        buttonSalvar.disableProperty().bind(editable.not());
        buttonNovo.disableProperty().bind(editable);
        adapter.bindBidirectional("descricao", textNome.textProperty());
    }

    private void close() {
        inputPane.getScene().getWindow().hide();
    }

    private void configureBean(Classificacao newClassificacao) {
        try {
            BeanUtils.copyProperties(this.classificacaoEditable, newClassificacao);
            this.editable.setValue(classificacao != null);
            this.adapter.setBean(classificacaoEditable);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }
    }

    private boolean validate(Object bean) {
        CustomValidation validation = new CustomValidation();
        validation.addValidateable(new BeanValidation(bean, "descricao", textNome));

        return validation.validate();
    }

    @FXML
    public void handleOnNewAction() {
        this.classificacao = new Classificacao();
        configureBean(classificacao);
        this.textNome.requestFocus();
    }

    @FXML
    public void handleOnSaveAction() {
        if (!validate(classificacaoEditable)) {
            return;
        }

        try {
            if (classificacaoEditable.getClassificacaoID() == null) {
                classificaoDAO.insert(classificacaoEditable);
            } else {
                classificaoDAO.update(classificacaoEditable);
            }
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }

        close();
    }

    @FXML
    public void handleOnCancelAction() {
        close();
    }

    @FXML
    public void handleOnLocalizarAction() {
        this.classificacao = localizarManager.localizarClassificacao(inputPane.getScene().getWindow());
        configureBean(classificacao == null ? new Classificacao() : classificacao);
    }
}