package br.com.celta.customer.view.actions;

/**
 * FilterAction.class
 *
 * @author Ranlive Hrysyk
 */
public interface FilterAction {

    void select();
    
    void execute();

    @Override
    String toString();
}