package br.com.celta.customer.view.controller;

import br.com.celta.customer.view.actions.LocalizarAction;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * LocalizarDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class LocalizarDialogController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private ResourceBundle resources;
    @FXML
    private VBox inputPane;
    @FXML
    private TextField textSearch;
    @FXML
    private ListView<?> listView;
    @FXML
    private Text textTitle;
    @FXML
    private Text textMessage;
    private LocalizarAction action;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        this.resources = resource;
        this.listView.setEditable(false);
    }

    private void close() {
        inputPane.getScene().getWindow().hide();
    }

    private void configureMesage() {
        textSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    textMessage.setText(resources.getString("localizar.message.enter"));
                } else if (!listView.getItems().isEmpty()) {
                    textMessage.setText(resources.getString("localizar.message.select"));
                } else {
                    textMessage.setText(null);
                }

                animateMessage();
            }
        });

    }

    private void animateMessage() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), textMessage);
        fade.setFromValue(0.0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    public void handleOnCancelAction() {
        close();
    }

    @FXML
    public void handleOnClickListAction(MouseEvent event) {            
        if (event.getClickCount() < 2 || listView.getItems().isEmpty()) {
            return;
        }

        Object selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null){
            return;
        }
        
        this.action.setObject(selected);
        close();
    }

    public void configureAction(final LocalizarAction action) {
        this.action = action;

        this.textTitle.setText(MessageFormat.format(resources.getString("localizar.title"), action.getClassTitle()));
        this.textSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode() == KeyCode.ENTER) {
                    listView.getItems().clear();
                    listView.getItems().addAll(action.localizar(textSearch.getText()));
                    
                    if (listView.getItems().isEmpty()) {
                        textMessage.setText(resources.getString("localizar.message.empty"));
                        animateMessage();
                    }                        
                }
            }
        });

        configureMesage();
    }
}