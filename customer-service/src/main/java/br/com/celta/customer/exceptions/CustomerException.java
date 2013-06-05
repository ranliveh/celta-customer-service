package br.com.celta.customer.exceptions;

import br.gov.frameworkdemoiselle.exception.ApplicationException;

/**
 * CustomerException.class<br> Exceção geral da aplicação. Cada
 * CustomerException contém uma flag que indica se a aplicação pode ou não
 * continuar.
 *
 * @author Ranlive Hrysyk
 */
@ApplicationException(rollback = true)
public class CustomerException extends Exception {

    private static final long serialVersionUID = 1L;
    private boolean recoverable;

    public CustomerException(String message, Throwable cause, boolean recoverable) {
        super(message, cause);
        this.recoverable = recoverable;
    }

    public boolean isRecoverable() {
        return this.recoverable;
    }
}