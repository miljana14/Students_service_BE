package miljana.andric.services.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import miljana.andric.dtos.UserRegisterDto;

@Component
public class UserCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserRegisterDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegisterDto user=(UserRegisterDto) target;
		if(!user.getPassword().equals(user.getRepeatedPassword())) {
			errors.reject("400","Passwords must to be same");
		}
		
	}

}
