package br.com.celta.customer.view.controller;

import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.persistence.CategoriaDAO;
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
public class CategoriaDialogController implements Initializable {

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
    private CategoriaDAO categoriaDAO;
    private Categoria categoria;
    private BooleanProperty editable = new SimpleBooleanProperty();
    private Categoria categoriaEditable = new Categoria();
    private final BeanPathAdapter<Categoria> adapter = new BeanPathAdapter<>(categoriaEditable);

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

    private void configureBean(Categoria newCategoria) {
        try {
            BeanUtils.copyProperties(this.categoriaEditable, newCategoria);
            this.editable.setValue(categoria != null);
            this.adapter.setBean(categoriaEditable);
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
        this.categoria = new Categoria();
        configureBean(categoria);
        this.textNome.requestFocus();
    }

    @FXML
    public void handleOnSaveAction() {
        if (!validate(categoriaEditable)) {
            return;
        }
        
        try {
            if (categoriaEditable.getCategoriaID() == null) {
                categoriaDAO.insert(categoriaEditable);
            } else {
                categoriaDAO.update(categoriaEditable);
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
        this.categoria = localizarManager.localizarCategoria(inputPane.getScene().getWindow());
        configureBean(categoria == null ? new Categoria() : categoria);
    }
}