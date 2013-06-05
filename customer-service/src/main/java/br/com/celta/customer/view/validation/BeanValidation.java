package br.com.celta.customer.view.validation;

import br.gov.frameworkdemoiselle.util.Reflections;
import java.util.ResourceBundle;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * BeanValidation.class
 *
 * @author Ranlive Hrysyk
 */
public class BeanValidation implements Validateable {

    private static final long serialVersionUID = 1L;
    private final Object bean;
    private final String property;
    private final Control control;
    private List<String> messages = new ArrayList<>();
    private ResourceBundle resource;

    public BeanValidation(final Object bean, final String property, final Control control) {
        this.bean = bean;
        this.property = property;
        this.control = control;

        resource = ResourceBundle.getBundle("messages");
    }

    @Override
    public boolean isValid() {
        this.messages.clear();

        boolean isValid = validate();

        String styleBorder = isValid ? "-fx-border-color: null" : "-fx-border-color: red";
        control.setStyle(styleBorder);
        control.setTooltip(createToolTip());

        return isValid;
    }

    private Tooltip createToolTip() {
        if (!this.messages.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            for (String message : messages) {
                builder.append(message).append("\n");
            }

            Tooltip tooltip = new Tooltip(builder.toString());
            return tooltip;
        }

        return null;
    }

    private Field getField() {
        try {
            return bean.getClass().getDeclaredField(this.property);
        } catch (NoSuchFieldException | SecurityException ex) {
            return null;
        }
    }

    private boolean validate() {
        boolean result = true;

        Field field = getField();

        if (field == null) {
            return false;
        }

        Object value = Reflections.getFieldValue(field, bean);
        String stringValue = value != null ? value.toString() : "";

        if (field.isAnnotationPresent(NotNull.class) && value == null) {
            result = false;
            this.messages.add(resource.getString("validation.notNull"));
        }

        if (field.isAnnotationPresent(Size.class)) {
            Size size = field.getAnnotation(Size.class);
            Integer valueLength = stringValue.length();
            if ((size.min() > 0 && size.min() > valueLength) || (size.max() < Integer.MAX_VALUE && size.max() < valueLength)) {
                result = false;
                this.messages.add(MessageFormat.format(resource.getString("validation.size"), size.min(), size.max()));
            }
        }

        if (field.isAnnotationPresent(Pattern.class) && value != null && !stringValue.isEmpty()) {
            Pattern pattern = field.getAnnotation(Pattern.class);
            if (!stringValue.matches(pattern.regexp())) {
                result = false;
                this.messages.add(pattern.message());
            }
        }

        return result;
    }
}