package br.com.caelum.stella.faces.validation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.faces.ResourceBundleMessageProducer;
import br.com.caelum.stella.validation.CNPJError;
import br.com.caelum.stella.validation.CNPJValidator;

/**
 * Caso ocorra algum erro de validação, todas as mensagens serão enfileiradas no
 * FacesContext e associadas ao elemento inválido.
 *
 * @author Leonardo Bessa
 */
public class StellaCNPJValidator implements javax.faces.validator.Validator, javax.faces.component.StateHolder {
	public static final String VALIDATOR_ID = "StellaCNPJValidator";
	private boolean formatted;
	private boolean transientValue = false;
	
	public void setFormatted(boolean formatted) {
		this.formatted = formatted;
	}

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        Application application = facesContext.getApplication();
        String bundleName = application.getMessageBundle();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);

        ResourceBundleMessageProducer<CNPJError> producer = new ResourceBundleMessageProducer<CNPJError>(bundle);
        CNPJValidator validator = new CNPJValidator(producer, formatted);

        if (!validator.validate(value.toString())) {
            List<ValidationMessage> messages = validator.getLastValidationMessages();
            String firstErrorMessage = messages.remove(0).getMessage();
            registerAllMessages(facesContext, uiComponent, messages);
            throw new ValidatorException(new FacesMessage(firstErrorMessage));
        }
    }

    private void registerAllMessages(FacesContext facesContext, UIComponent uiComponent, List<ValidationMessage> messages) {
        for (ValidationMessage message : messages) {
            String componentId = uiComponent.getClientId(facesContext);
            facesContext.addMessage(componentId, new FacesMessage(message.getMessage()));
        }
    }

	public boolean isTransientValue() {
		return transientValue;
	}

	public void setTransientValue(boolean transientValue) {
		this.transientValue = transientValue;
	}

	public boolean isTransient() {
		return transientValue;
	}

	public void restoreState(FacesContext ctx, Object state) {
		this.formatted = (Boolean) state;
	}

	public Object saveState(FacesContext arg0) {
		return formatted;
	}

	public void setTransient(boolean transientValue) {
		this.transientValue = transientValue;
	}
}
