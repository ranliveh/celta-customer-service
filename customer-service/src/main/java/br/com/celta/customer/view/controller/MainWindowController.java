package br.com.celta.customer.view.controller;

import br.com.celta.customer.view.custom.ChamadoCell;
import br.com.celta.customer.application.ApplicationConfig;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.*;
import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.entity.Empresa;
import br.com.celta.customer.persistence.ChamadoDAO;
import br.com.celta.customer.persistence.EmpresaDAO;
import br.com.celta.customer.security.Roles;
import br.com.celta.customer.view.actions.FilterAction;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javax.inject.Inject;

/**
 * MainWindowController.class
 *
 * @author Ranlive Hrysyk
 */
public class MainWindowController implements Initializable {

    private static final long serialVersionUID = 1L;
    @FXML
    private ResourceBundle resource;
    @FXML
    private Text textTitle;
    @FXML
    private Button buttonEmpresa;
    @FXML
    private Button buttonCliente;
    @FXML
    private Button buttonAtendente;
    @FXML
    private Button buttonClassificacao;
    @FXML
    private Button buttonCategoria;
    @FXML
    private HBox boxFilter;
    @FXML
    private ComboBox<FilterAction> comboFilter;
    @FXML
    private ListView<Chamado> listChamados;
    @FXML
    private Hyperlink textNovoAtendimento;
    @Inject
    private ApplicationConfig config;
    @Inject
    private EmpresaDAO empresaDAO;
    @Inject
    private ChamadoDAO chamadoDAO;
    @Inject
    private SecurityContext securityContext;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        this.resource = resource;
        this.listChamados.setCellFactory(new Callback<ListView<Chamado>, ListCell<Chamado>>() {
            @Override
            public ListCell<Chamado> call(ListView<Chamado> p) {
                return new ChamadoCell();
            }
        });
        this.textNovoAtendimento.setGraphic(new ImageView("/images/new.png"));

        configureFilters();
    }

    private void clearBoxFilter() {
        this.boxFilter.getChildren().clear();
    }

    private void configureFilters() {
        this.comboFilter.getItems().add(new FilterAction() {
            @Override
            public void select() {
                clearBoxFilter();
                execute();
            }

            @Override
            public void execute() {
                List<Chamado> list = chamadoDAO.findAll();
                updateList(list);
            }

            @Override
            public String toString() {
                return resource.getString("main.filter.all");
            }
        });

        this.comboFilter.valueProperty().addListener(new ChangeListener<FilterAction>() {
            @Override
            public void changed(ObservableValue<? extends FilterAction> ov, FilterAction oldValue, FilterAction newValue) {
                newValue.select();
            }
        });
    }

    private void updateList(List<Chamado> list) {
        listChamados.getItems().clear();
        listChamados.getItems().addAll(list);
    }

    @FXML
    public void handleOnDadosAction() {
        EventManager.fire(new DisplayEmpresa());
    }

    @FXML
    public void handleOnClienteAction() {
        EventManager.fire(new DisplayCliente());
    }

    @FXML
    public void handleOnAtendenteAction() {
        EventManager.fire(new DisplayAtendente());
    }

    @FXML
    public void handleOnClassificacaoAction() {
        EventManager.fire(new DisplayClassificacao());
    }

    @FXML
    public void handleOnCategoriaAction() {
        EventManager.fire(new DisplayCategoria());
    }

    @FXML
    public void handleOnQuitAction() {
        EventManager.fire(new CloseApplication(CloseApplication.EXIT_CODE_OK));
    }

    @FXML
    public void handleOnNewChamadoAction() {
        EventManager.fire(new NewChamado());
    }

    @FXML
    public void handleOnRelatorioClienteAction() {
        EventManager.fire(new DisplayReportCliente());
    }

    @FXML
    public void handleOnRelatorioAtendenteAction() {
        EventManager.fire(new DisplayReportAtendente());
    }

    /**
     * Atualiza os dados da empresa.
     */
    public void updateEmpresa() {
        Empresa empresa = empresaDAO.load(config.getApplicationEmpresaId());
        this.textTitle.setText(MessageFormat.format(resource.getString("main.title"), empresa.getNome()));
    }

    /**
     * Atualiza as permissões de usuário.
     */
    public void updatePermissions() {
        buttonEmpresa.disableProperty().setValue(!securityContext.hasRole(Roles.EMPRESA));
        buttonCliente.disableProperty().setValue(!securityContext.hasRole(Roles.CLIENTE));
        buttonAtendente.disableProperty().setValue(!securityContext.hasRole(Roles.ATENDENTE));
        buttonClassificacao.disableProperty().setValue(!securityContext.hasRole(Roles.CLASSIFICACAO));
        buttonCategoria.disableProperty().setValue(!securityContext.hasRole(Roles.CATEGORIA));
    }

    /**
     * Atualiza a lista de chamados.
     */
    public void updateChamados() {
        FilterAction action = comboFilter.getValue();
        if (action != null) {
            action.execute();
        }
    }
}