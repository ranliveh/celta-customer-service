package br.com.celta.customer.application;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.bus.events.DisplayExceptions;
import br.com.celta.customer.exceptions.CustomerException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

/**
 * ApplicationExceptionProvider.class<br> Classe responsável pelo controle das
 * exceções da aplicação.
 *
 * @author Ranlive Hrysyk
 */
@Singleton
public class ApplicationExceptionProvider {

    private ObservableList<CustomerException> exceptions;

    @PostConstruct
    protected void onConstruct() {
        this.exceptions = FXCollections.observableList(new ArrayList<CustomerException>());
        this.exceptions.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (hasException()) {
                    EventManager.fire(new DisplayExceptions());
                }
            }
        });
    }

    public boolean hasException() {
        return !exceptions.isEmpty();
    }

    public void addException(@Observes CustomerException exception) {
        exceptions.add(exception);
    }

    public CustomerException getNextException() {
        if (hasException()) {
            return exceptions.remove(0);
        }
        return null;
    }
}