package br.com.celta.customer.view.custom;

import br.com.celta.customer.application.ApplicationChamadoProvider;
import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.EditChamado;
import br.com.celta.customer.entity.Chamado;
import br.com.celta.customer.utils.FormatUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * ChamadoCell.class
 *
 * @author Ranlive Hrysyk
 */
public class ChamadoCell extends ListCell<Chamado> {

    private static final long serialVersionUID = 1L;
    private Chamado chamado;
    private HBox content;
    private Label protocolo;
    private Label titulo;
    private Label atendente;
    private Label abertura;
    private Label prazo;
    private Label cliente;
    private Label status;
    private HBox menu;

    public ChamadoCell() {        
        protocolo = new Label("Protocolo");
        titulo = new Label("Titulo");
        HBox boxTitulo = new HBox();
        boxTitulo.setMinWidth(350);
        boxTitulo.setAlignment(Pos.CENTER_LEFT);
        boxTitulo.getChildren().add(titulo);
        atendente = new Label("Atendente");
        HBox primeiraLinha = new HBox(10);
        primeiraLinha.setAlignment(Pos.TOP_LEFT);
        primeiraLinha.getStyleClass().add("heading-minor");
        primeiraLinha.getChildren().addAll(protocolo, boxTitulo, atendente);
        HBox.setHgrow(primeiraLinha, Priority.ALWAYS);
        
        abertura = new Label("Abertura");
        prazo = new Label("Prazo");
        cliente = new Label("Cliente");
        HBox boxCliente = new HBox();
        boxCliente.setMinWidth(280);
        boxCliente.setAlignment(Pos.CENTER_LEFT);
        boxCliente.getChildren().add(cliente);
        status = new Label("Status");
        HBox segundaLinha = new HBox(10);
        segundaLinha.setAlignment(Pos.TOP_LEFT);
        segundaLinha.getChildren().addAll(abertura, prazo, boxCliente, status);
        HBox.setHgrow(segundaLinha, Priority.ALWAYS);

        VBox dados = new VBox(5);
        dados.setAlignment(Pos.CENTER_LEFT);
        dados.getChildren().addAll(primeiraLinha, segundaLinha);

        menu = new HBox(2);
        menu.setAlignment(Pos.CENTER_RIGHT);
        Label edit = new Label(null, new ImageView("/images/edit.png"));
        edit.setOnMouseClicked(createOnClickEvent());
        HBox.setHgrow(menu, Priority.ALWAYS);
        menu.getChildren().add(edit);

        content = new HBox(5);
        content.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(dados, menu);
    }

    @Override
    protected void updateItem(Chamado c, boolean empty) {
        super.updateItem(c, empty);

        if (!empty) {
            this.chamado = c;

            this.protocolo.setText(FormatUtils.PROTOCOLO_FORMAT.format(chamado.getChamadoID()));
            this.titulo.setText(chamado.getTitulo());
            this.atendente.setText(chamado.getAtendente().getNome());
            this.abertura.setText(FormatUtils.DATE_FORMAT.format(chamado.getDataAbertura()));
            this.prazo.setText(FormatUtils.DATE_FORMAT.format(chamado.getPrazo()));
            this.cliente.setText(chamado.getCliente().getNome());
            this.status.setText(chamado.getStatus().toString());

            menu.setVisible(isSelected());

            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);

        menu.setVisible(isSelected());
    }

    private EventHandler<MouseEvent> createOnClickEvent() {
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                ApplicationChamadoProvider.getInstance().setEditingChamado(chamado);
                EventManager.fire(new EditChamado());
            }
        };

        return event;
    }
}