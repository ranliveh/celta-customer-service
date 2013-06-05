package br.com.celta.customer.view.custom;

import br.com.celta.customer.entity.Iteracao;
import br.com.celta.customer.utils.FormatUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * IteracaoCell.class
 *
 * @author Ranlive Hrysyk
 */
public class IteracaoCell extends ListCell<Iteracao> {
    
    private static final long serialVersionUID = 1L;
    private Iteracao iteracao;
    private HBox content;
    private Label item;
    private Label data;
    private Label hora;
    private TextArea iteracaoText;
    private Label atendente;
    
    public IteracaoCell() {
        item = new Label("Item");
        HBox boxItem = new HBox();
        boxItem.setAlignment(Pos.CENTER_LEFT);
        boxItem.getStyleClass().add("heading-minor");
        boxItem.getChildren().add(item);
        
        data = new Label("Data");
        hora = new Label("Hora");
        VBox boxData = new VBox();
        boxData.setAlignment(Pos.CENTER);
        boxData.getStyleClass().add("heading-minor");
        boxData.getChildren().addAll(data, hora);
        
        iteracaoText = new TextArea();
        iteracaoText.getStyleClass().add("text-iteracao");
        iteracaoText.setEditable(false);
        HBox boxIteracao = new HBox();
        boxIteracao.setAlignment(Pos.CENTER);
        boxIteracao.getChildren().add(iteracaoText);
        
        atendente = new Label("Atendente");
        HBox boxAtendente = new HBox();
        boxAtendente.setAlignment(Pos.TOP_CENTER);
        boxAtendente.getStyleClass().add("heading-minor");
        boxAtendente.getChildren().add(atendente);
        
        content = new HBox(5);
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(boxItem, boxData, boxIteracao, boxAtendente);
    }
    
    @Override
    protected void updateItem(Iteracao i, boolean empty) {
        super.updateItem(i, empty);
        
        if (!empty) {
            this.iteracao = i;
            
            this.item.setText(Integer.toString(getIndex() + 1));
            this.data.setText(FormatUtils.DATE_FORMAT.format(iteracao.getData()));
            this.hora.setText(FormatUtils.TIME_FORMAT.format(iteracao.getHora()));
            this.iteracaoText.setText(iteracao.getIteracao());
            this.atendente.setText(iteracao.getAtendente().getNome());
            
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}