package es.uv.etse.bdweb.hotel.web.util;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dateGananciasValidator")
public class DateGananciasValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//		Iterator<String> itIds = FacesContext.getCurrentInstance().getClientIdsWithMessages();
//		while (itIds.hasNext()) {
//		    List<FacesMessage> messageList = FacesContext.getCurrentInstance().getMessageList(itIds.next());
//		    if (!messageList.isEmpty()) { // if empty, it will be unmodifiable and throw UnsupportedOperationException...
//		        messageList.clear();
//		    }
//		}
		
		UIInput monthValidate = (UIInput) component.getAttributes().get("monthValidate");
	
		Integer year = (Integer) value;
		Integer mes = (Integer) monthValidate.getValue();
		
		if (year == null || mes == null) {
			return; // Let required="true" handle.
		}
		
		
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaConsulta = LocalDate.of(year,mes,fechaHoy.getDayOfMonth());
		
		if (fechaConsulta.isEqual(fechaHoy)) return;
		if (fechaConsulta.isAfter(fechaHoy)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Month of Date may not be after this!", null));
		}
	}

}