package miljana.andric.services.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import miljana.andric.dtos.ProfessorUpdateDto;

@Component
public class ProfessorUpdateCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfessorUpdateDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProfessorUpdateDto user=(ProfessorUpdateDto) target;
		if(user.getPassword() != null) {
			if(!user.getPassword().equals(user.getRepeatedPassword())) {
				errors.reject("400","Passwords must to be same");
			}
		}
	}
}
