package miljana.andric.dtos;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;

import miljana.andric.entities.CityEntity;

public class StudentUpdateDto implements Dto{
	
	@Nullable
	@Size(min = 4, max= 4, message = " Index number must have exact 4 characters")
	private String indexNumber;
	@Nullable
	@Min(2000)
	@Max(2100)
	private Long indexYear;
	@Nullable
	@Size(min = 3, message = " First name must have min 3 characters")
	private String firstName;
	@Size(min = 3, message = " Last name must have min 3 characters")
	@Nullable
	private String lastName;
	@Email
	@Nullable
	private String email;
	@Size(min = 3, message = "Address must have min 3 characters")
	@Nullable
	private String address;
	@Nullable
	private CityEntity postalCode;
	@Nullable
	private Integer currentYearOfStudy;
	@Nullable
	private String username;
	@Nullable
	private String password;
	@Nullable
	private String repeatedPassword;

	public StudentUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentUpdateDto(
			@Size(min = 4, max = 4, message = " Index number must have exact 4 characters") String indexNumber,
			@Min(2000) @Max(2100) Long indexYear,
			@Size(min = 3, message = " First name must have min 3 characters") String firstName,
			@Size(min = 3, message = " Last name must have min 3 characters") String lastName, @Email String email,
			@Size(min = 3, message = "Address must have min 3 characters") String address, CityEntity postalCode,
			Integer currentYearOfStudy, String username, String password, String repeatedPassword) {
		super();
		this.indexNumber = indexNumber;
		this.indexYear = indexYear;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.currentYearOfStudy = currentYearOfStudy;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
	}

	public String getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}
	public Long getIndexYear() {
		return indexYear;
	}
	public void setIndexYear(Long indexYear) {
		this.indexYear = indexYear;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public CityEntity getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(CityEntity postalCode) {
		this.postalCode = postalCode;
	}
	public Integer getCurrentYearOfStudy() {
		return currentYearOfStudy;
	}
	public void setCurrentYearOfStudy(Integer currentYearOfStudy) {
		this.currentYearOfStudy = currentYearOfStudy;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	@Override
	public String toString() {
		return "StudentUpdateDto [indexNumber=" + indexNumber + ", indexYear=" + indexYear + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", postalCode=" + postalCode
				+ ", currentYearOfStudy=" + currentYearOfStudy + ", username=" + username + ", password=" + password
				+ ", repeatedPassword=" + repeatedPassword + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, currentYearOfStudy, email, firstName, indexNumber, indexYear, lastName, password,
				postalCode, repeatedPassword, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentUpdateDto other = (StudentUpdateDto) obj;
		return Objects.equals(address, other.address) && Objects.equals(currentYearOfStudy, other.currentYearOfStudy)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(indexNumber, other.indexNumber) && Objects.equals(indexYear, other.indexYear)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(repeatedPassword, other.repeatedPassword) && Objects.equals(username, other.username);
	}

	
}
