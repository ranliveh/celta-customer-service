package br.com.celta.customer.bus;


import br.gov.frameworkdemoiselle.util.Beans;
import java.lang.annotation.Annotation;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;

/**
 * EventManager.class<br> 
 * Gerenciador de eventos que tratam da comunicação entre View e Presenter.
 *
 * @author Ranlive Hrysyk
 */
public class EventManager {

    private static final long serialVersionUID = 1L;

    public static void fire(final Object event) {
        Beans.getBeanManager().fireEvent(event, new AnnotationLiteral<Any>() {
        });
    }

    public static void fire(final Object event, final Annotation... qualifiers) {
        Beans.getBeanManager().fireEvent(event, qualifiers);
    }
}