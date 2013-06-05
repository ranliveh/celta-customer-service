package br.com.celta.customer.view.dialogs;

import br.com.celta.customer.application.ApplicationMainWindowProvider;
import javafx.animation.FadeTransitionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.inject.Inject;

/**
 * Dialogs.class
 *
 * @author Ranlive Hrysyk
 */
public class Dialogs {

    private static final long serialVersionUID = 1L;
    @Inject
    private ApplicationMainWindowProvider mainWindowProvider;

    public Dialogs() {
    }

    /**
     * Callback interface para ação de mostrar diálogo.
     */
    public interface OnShownAction {

        void perform();
    }

    /**
     * Mostra o diálogos do JavaFX.
     */
    public void showDialog(final Window owner, final Parent root, final OnShownAction onShown) {
        final Stage dialog = new Stage(StageStyle.UNDECORATED);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setScene(new Scene(root));
        dialog.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                dialog.setX(owner.getX() + owner.getWidth() / 2 - dialog.getWidth() / 2);
                dialog.setY(owner.getY() + owner.getHeight() / 2 - dialog.getHeight() / 2);

                if (onShown != null) {
                    onShown.perform();
                }
            }
        });

        FadeTransitionBuilder.create().node(owner.getScene().getRoot()).toValue(0.25).build().play();
        dialog.showAndWait();
        FadeTransitionBuilder.create().node(owner.getScene().getRoot()).toValue(1).build().play();
    }
}