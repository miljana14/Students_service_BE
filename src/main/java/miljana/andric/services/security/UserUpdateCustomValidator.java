package miljana.andric.services.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import miljana.andric.dtos.UserUpdateDto;

@Component
public class UserUpdateCustomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserUpdateDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserUpdateDto user=(UserUpdateDto) target;
		if(user.getPassword() != null) {
			if(!user.getPassword().equals(user.getRepeatedPassword())) {
				errors.reject("400","Passwords must to be same");
			}
		}
	}
}
