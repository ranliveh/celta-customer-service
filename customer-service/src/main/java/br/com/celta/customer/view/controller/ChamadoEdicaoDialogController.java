package br.com.celta.customer.view.controller;

import br.com.celta.customer.application.ApplicationAtendenteProvider;
import br.com.celta.customer.application.ApplicationChamadoProvider;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.UpdateChamado;
import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.entity.Classificacao;
import br.com.celta.customer.entity.Iteracao;
import br.com.celta.customer.entity.StatusEnum;
import br.com.celta.customer.persistence.ChamadoDAO;
import br.com.celta.customer.utils.ExceptionUtils;
import br.com.celta.customer.utils.FormatUtils;
import br.com.celta.customer.view.LocalizarManager;
import br.com.celta.customer.view.custom.IteracaoCell;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.Callback;
import javax.inject.Inject;
import jfxtras.labs.scene.control.BeanPathAdapter;
import jfxtras.labs.scene.control.CalendarTextField;

/**
 * ChamadoCadastroDialogController.class
 *
 * @author Ranlive Hrysyk
 */
public class ChamadoEdicaoDialogController implements Initializable {

    @FXML
    private BorderPane inputPane;
    @FXML
    private GridPane gridDados;
    @FXML
    private Text textProtocolo;
    @FXML
    private TextField textAtendente;
    @FXML
    private Text textTitulo;
    @FXML
    private Text textCliente;
    @FXML
    private Text textDataAbertura;
    @FXML
    private Text textHoraAbertura;
    @FXML
    private TextField textCategoria;
    @FXML
    private TextField textClassifcacao;
    @FXML
    private ListView<Iteracao> listIteracoes;
    @FXML
    private TextArea textResposta;
    @Inject
    private LocalizarManager localizarManager;
    @Inject
    private ApplicationAtendenteProvider atendenteProvider;
    @Inject
    private ChamadoDAO chamadoDAO;
    private Label procuraAtendente;
    private Label procuraCategoria;
    private Label procuraClassificacao;
    private CalendarTextField textPrazo;
    private ComboBox<StatusEnum> comboStatus;
    private Chamado chamado;
    private BeanPathAdapter<Chamado> adapterChamado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.listIteracoes.setCellFactory(new Callback<ListView<Iteracao>, ListCell<Iteracao>>() {
            @Override
            public ListCell<Iteracao> call(ListView<Iteracao> p) {
                return new IteracaoCell();
            }
        });

        procuraAtendente = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Atendente atendente = localizarManager.localizarAtendente(owner);
                chamado.setAtendente(atendente);
                textAtendente.setText(atendente != null ? atendente.getNome() : null);
            }
        });
        gridDados.add(procuraAtendente, 6, 1);

        procuraCategoria = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Categoria categoria = localizarManager.localizarCategoria(owner);
                chamado.setCategoria(categoria);
                textCategoria.setText(categoria != null ? categoria.getDescricao() : null);
            }
        });
        gridDados.add(procuraCategoria, 3, 5);

        procuraClassificacao = createLabelProcura(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                final Window owner = inputPane.getScene().getWindow();
                Classificacao classificacao = localizarManager.localizarClassificacao(owner);
                chamado.setClassificacao(classificacao);
                textClassifcacao.setText(classificacao != null ? classificacao.getDescricao() : null);
            }
        });
        gridDados.add(procuraClassificacao, 6, 5);

        textPrazo = createCalendarTextField();
        GridPane.setColumnSpan(textPrazo, 3);
        gridDados.add(textPrazo, 1, 6);

        comboStatus = new ComboBox<>();
        comboStatus.getItems().addAll(Arrays.asList(StatusEnum.values()));
        GridPane.setColumnSpan(comboStatus, 2);
        gridDados.add(comboStatus, 5, 6);

        configureChamado();
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

    private void addIteracao() {
        Iteracao iteracao = new Iteracao();
        iteracao.setChamado(chamado);
        iteracao.setAtendente(atendenteProvider.getCurrentAtendente());
        iteracao.setIteracao(textResposta.getText());

        this.listIteracoes.getItems().add(iteracao);
    }

    private void close() {
        inputPane.getScene().getWindow().hide();
    }

    @FXML
    public void handleOnAddIteracaoAction(ActionEvent event) {
        if (!textResposta.getText().trim().isEmpty()) {
            addIteracao();
            textResposta.clear();
        }
    }

    @FXML
    public void handleOnSaveAction(ActionEvent event) {
        try {
            chamadoDAO.update(chamado);
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

    private void configureChamado() {
        this.chamado = ApplicationChamadoProvider.getInstance().getEditingChamado();

        this.textProtocolo.setText(FormatUtils.PROTOCOLO_FORMAT.format(chamado.getChamadoID()));
        this.textTitulo.setText(chamado.getTitulo());
        this.textCliente.setText(chamado.getCliente().getNome());
        this.textDataAbertura.setText(FormatUtils.DATE_FORMAT.format(chamado.getDataAbertura()));
        this.textHoraAbertura.setText(FormatUtils.TIME_FORMAT.format(chamado.getHoraAbertura()));

        this.adapterChamado = new BeanPathAdapter<>(this.chamado);
        this.adapterChamado.bindBidirectional("atendente.nome", textAtendente.textProperty());
        this.adapterChamado.bindBidirectional("categoria.descricao", textCategoria.textProperty());
        this.adapterChamado.bindBidirectional("classificacao.descricao", textClassifcacao.textProperty());
        this.adapterChamado.bindBidirectional("prazo", textPrazo.calendarProperty(), Calendar.class);
        this.adapterChamado.bindBidirectional("status", comboStatus.valueProperty(), StatusEnum.class);
        this.adapterChamado.bindContentBidirectional("iteracaoList", null, Iteracao.class, listIteracoes.getItems(), Iteracao.class, null, null);
    }
}