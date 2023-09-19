package miljana.andric.services.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import miljana.andric.dtos.StudentRegisterDto;

@Component
public class StudentCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return StudentRegisterDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StudentRegisterDto user=(StudentRegisterDto) target;
		if(!user.getPassword().equals(user.getRepeatedPassword())) {
			errors.reject("400","Passwords must to be same");
		}
	}

}
