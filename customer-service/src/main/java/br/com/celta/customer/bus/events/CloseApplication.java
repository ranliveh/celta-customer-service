package br.com.celta.customer.bus.events;

/**
 * CloseApplication.class Evento lançado quando se quer encerrar a aplicação.
 *
 * @author Ranlive Hrysyk
 */
public class CloseApplication {

    private static final long serialVersionUID = 1L;
    public static final Integer EXIT_CODE_OK = 0;
    public static final Integer EXIT_CODE_ERROR = 1;
    private Integer exitCode;

    public CloseApplication(final Integer exitCode) {
        this.exitCode = exitCode;
    }

    public Integer getExitCode() {
        return this.exitCode;
    }
}