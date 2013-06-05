package br.com.celta.customer.utils;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.exceptions.CustomerException;

/**
 * ExceptionUtils.class
 *
 * @author Ranlive Hrysyk
 */
public class ExceptionUtils {

    private static final long serialVersionUID = 1L;

    public static void throwCustomerException(Exception exception, boolean isRecoverable) {
        EventManager.fire(new CustomerException(exception.getMessage(), exception, isRecoverable));
    }
}