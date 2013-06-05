package br.com.celta.customer.view.actions;

import java.util.List;

/**
 * LocalizarAction.class
 *
 * @author Ranlive Hrysyk
 */
public interface LocalizarAction<T extends Object> {
    
    String getClassTitle();

    List<T> localizar(String text);
    
    T getObject();
    
    void setObject(T value);
}