package miljana.andric.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.dtos.UserRegisterDto;
import miljana.andric.dtos.UserResponseDto;
import miljana.andric.dtos.UserUpdateDto;
import miljana.andric.entities.RoleEnum;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.UserService;
import miljana.andric.services.security.MyUserDetails;
import miljana.andric.services.security.UserCustomValidator;
import miljana.andric.services.security.UserUpdateCustomValidator;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserCustomValidator userValidator;
	@Autowired
	private UserService userService;
	@Autowired
	private UserUpdateCustomValidator userUpdateValidator;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@CrossOrigin("http://localhost:4200")
	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<MyUserDetails> authenticateUser(UserRegisterDto userDto) throws EngineeringException {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
		MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
		if(userDetails.getRole().equals(RoleEnum.ROLE_STUDENT) && userDetails.isNewUser()) {
			return ResponseEntity.status(HttpStatus.OK).header("Location", "change-password?password?repeatedPassword").body(userDetails);
		}
		if(userDetails.getRole().equals(RoleEnum.ROLE_PROFESSOR) && userDetails.isNewUser()) {		
			return ResponseEntity.status(HttpStatus.OK).header("Location", "change-professor-password?password?repeatedPassword").body(userDetails);
			
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<?> add(@Valid @RequestBody UserRegisterDto user, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} else {
				userValidator.validate(user, result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
	}

	@GetMapping(value = "{username}")
	public @ResponseBody ResponseEntity<Object> findById(@PathVariable String username) throws EngineeringException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.findById(username));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public @ResponseBody ResponseEntity<String> deleteProfessor(@PathVariable String username) throws EngineeringException {
		try {
			userService.deleteUser(username);
			return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " + username);
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("{id}")
	public @ResponseBody ResponseEntity<?> editProfessor(@PathVariable String username,
			@Valid @RequestBody UserUpdateDto user, BindingResult result) throws EngineeringException {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} else {
				userUpdateValidator.validate(user, result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(username, user));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("filter")
	public ResponseEntity<Page<UserResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		return new ResponseEntity<Page<UserResponseDto>>(
				userService.findAll(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("search")
	public List<UserResponseDto> search(@RequestParam("keyword") String keyword) {
		return userService.search(keyword);
	}
	
	@CrossOrigin("http://localhost:4200")
	@PatchMapping("change-password")
	public ResponseEntity<?> changePassword(@RequestParam String password, @RequestParam String repeatedPassword) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(password, repeatedPassword));
		}catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
