package br.com.celta.customer.view.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomValidation.class
 *
 * @author Ranlive Hrysyk
 */
public class CustomValidation {

    private static final long serialVersionUID = 1L;
    private List<Validateable> validateList;

    public CustomValidation() {
        this.validateList = new ArrayList<>();
    }

    public void clearValidations() {
        this.validateList.clear();
    }

    public void addValidateable(Validateable validateable) {
        validateList.add(validateable);
    }

    public boolean validate() {
        boolean result = true;

        for (Validateable validateable : validateList) {
            if (!validateable.isValid()) {
                result = false;
            }
        }

        return result;
    }
}
