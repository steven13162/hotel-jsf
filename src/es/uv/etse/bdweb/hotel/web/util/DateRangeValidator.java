package es.uv.etse.bdweb.hotel.web.util;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator {

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
		UIInput dayValidate = (UIInput) component.getAttributes().get("dayValidate");
	
		Integer year = (Integer) value;
		Integer mes = (Integer) monthValidate.getValue();
		Integer dia = (Integer) dayValidate.getValue();
		
		if (year == null || mes == null || dia == null) {
			return; // Let required="true" handle.
		}
		
		LocalDate fechaInicio = LocalDate.of(year,mes,dia);
		LocalDate fechaHoy = LocalDate.now();
		
		if (fechaInicio.isEqual(fechaHoy)) return;
		if (fechaInicio.isBefore(fechaHoy)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Start date may not be before today.", null));
		}
	}

}