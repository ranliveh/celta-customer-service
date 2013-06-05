package br.com.celta.customer.view.controller;

import br.com.celta.customer.application.ApplicationAtendenteProvider;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.UpdateChamado;
import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.entity.Classificacao;
import br.com.celta.customer.entity.Cliente;
import br.com.celta.customer.entity.Iteracao;
import br.com.celta.customer.persistence.ChamadoDAO;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.utils.FormatUtils;
import br.com.celta.customer.view.LocalizarManager;
import br.com.celta.customer.view.validation.BeanValidation;
import br.com.celta.customer.view.validation.CustomValidation;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javax.inject.Inject;
import jfxtras.labs.scene.control.BeanPathAdapter;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.scene.control.CalendarTimeTextField;

/**
 * ChamadoCadastroDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class ChamadoCadastroDialogController implements Initializable {

    @FXML
    private BorderPane inputPane;
    @FXML
    private GridPane gridCadastro;
    @FXML
    private TextField textCliente;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textCategoria;
    @FXML
    private TextField textClassifcacao;
    @FXML
    private TextArea textSolicitacao;
    @FXML
    private TextArea textIteracao;
    @Inject
    private LocalizarManager localizarManager;
    @Inject
    private ApplicationAtendenteProvider atendenteProvider;
    @Inject
    private ChamadoDAO chamadoDAO;
    private Label procuraCliente;
    private Label procuraCategoria;
    private Label procuraClassificacao;
    private CalendarTextField textDataAbertura;
    private CalendarTimeTextField textHoraAbertura;
    private CalendarTextField textPrazo;
    private Chamado chamado;
    private Iteracao solicitacao;
    private Iteracao resposta;
    private BeanPathAdapter<Chamado> adapterChamado;
    private BeanPathAdapter<Iteracao> adapterSolicitacao;
    private BeanPathAdapter<Iteracao> adapterResposta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        procuraCliente = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Cliente cliente = localizarManager.localizarCliente(owner);
                chamado.setCliente(cliente);
                textCliente.setText(cliente != null ? cliente.getNome() : null);
            }
        });
        gridCadastro.add(procuraCliente, 6, 1);

        procuraCategoria = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Categoria categoria = localizarManager.localizarCategoria(owner);
                chamado.setCategoria(categoria);
                textCategoria.setText(categoria != null ? categoria.getDescricao() : null);
            }
        });
        gridCadastro.add(procuraCategoria, 3, 3);

        procuraClassificacao = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Classificacao classificacao = localizarManager.localizarClassificacao(owner);
                chamado.setClassificacao(classificacao);
                textClassifcacao.setText(classificacao != null ? classificacao.getDescricao() : null);
            }
        });
        gridCadastro.add(procuraClassificacao, 6, 3);

        textDataAbertura = createCalendarTextField();
        GridPane.setColumnSpan(textDataAbertura, 3);
        gridCadastro.add(textDataAbertura, 1, 4);

        textHoraAbertura = createCalendarTimeTextField();
        GridPane.setColumnSpan(textHoraAbertura, 2);
        gridCadastro.add(textHoraAbertura, 5, 4);

        textPrazo = createCalendarTextField();
        GridPane.setColumnSpan(textPrazo, 3);
        gridCadastro.add(textPrazo, 1, 5);

        chamado = new Chamado();
        adapterChamado = new BeanPathAdapter<>(chamado);
        adapterChamado.bindBidirectional("titulo", textTitle.textProperty());
        adapterChamado.bindBidirectional("dataAbertura", textDataAbertura.calendarProperty(), Calendar.class);
        adapterChamado.bindBidirectional("horaAbertura", textHoraAbertura.calendarProperty(), Calendar.class);
        adapterChamado.bindBidirectional("prazo", textPrazo.calendarProperty(), Calendar.class);

        solicitacao = new Iteracao();
        adapterSolicitacao = new BeanPathAdapter<>(solicitacao);
        adapterSolicitacao.bindBidirectional("iteracao", textSolicitacao.textProperty());

        resposta = new Iteracao();
        adapterResposta = new BeanPathAdapter<>(resposta);
        adapterResposta.bindBidirectional("iteracao", textIteracao.textProperty());
    }

    private Label createLabelProcura(EventHandler<MouseEvent> event) {
        Label label = new Label(null, new ImageView("/images/search.png"));
        label.setOnMouseClicked(event);
        return label;
    }

    private CalendarTextField createCalendarTextField() {
        CalendarTextField calendarTextField = new CalendarTextField();
        calendarTextField.setLocale(Locale.getDefault());
        calendarTextField.setDateFormat(FormatUtils.DATE_FORMAT);
        calendarTextField.setTooltip(null);
        return calendarTextField;
    }

    private CalendarTimeTextField createCalendarTimeTextField() {
        CalendarTimeTextField calendarTimeTextField = new CalendarTimeTextField();
        calendarTimeTextField.setDateFormat(FormatUtils.TIME_FORMAT);
        calendarTimeTextField.setTooltip(null);
        return calendarTimeTextField;
    }

    private boolean validate() {
        CustomValidation validation = new CustomValidation();
        validation.addValidateable(new BeanValidation(chamado, "cliente", textCliente));
        validation.addValidateable(new BeanValidation(chamado, "titulo", textTitle));
        validation.addValidateable(new BeanValidation(chamado, "categoria", textCategoria));
        validation.addValidateable(new BeanValidation(chamado, "classificacao", textClassifcacao));
        validation.addValidateable(new BeanValidation(solicitacao, "iteracao", textSolicitacao));

        return validation.validate();
    }

    private void close() {
        inputPane.getScene().getWindow().hide();
    }

    private void configureChamado() {
        Atendente atendente = atendenteProvider.getCurrentAtendente();
        chamado.setAtendente(atendente);
        
        solicitacao.setChamado(chamado);
        solicitacao.setAtendente(atendente);
        chamado.addIteracao(solicitacao);

        if (!textIteracao.getText().trim().isEmpty()) {
            resposta.setChamado(chamado);
            resposta.setAtendente(atendente);
            chamado.addIteracao(resposta);
        }
    }

    @FXML
    public void handleOnSaveAction(ActionEvent event) {
        if (!validate()) {
            return;
        }

        try {
            configureChamado();
            
            chamadoDAO.insert(chamado);
            EventManager.fire(new UpdateChamado());
        } catch (Exception exception) {
            ExceptionUtils.throwCustomerException(exception, true);
        }

        close();
    }

    @FXML
    public void handleOnCancelAction(ActionEvent event) {
        close();
    }
}