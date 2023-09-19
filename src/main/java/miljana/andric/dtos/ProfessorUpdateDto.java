package miljana.andric.dtos;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.format.annotation.DateTimeFormat;

import miljana.andric.entities.CityEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.entities.TitleEntity;

public class ProfessorUpdateDto implements Dto{
	
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
	@Size(min = 9, message = "Phone must have min 9 characters")
	private String phone;
	@Nullable
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reelectionDate;
	@Nullable
	private TitleEntity title;
	@Nullable
	private String username;
	@Nullable
	private String password;
	@Nullable
	private String repeatedPassword;
	@Nullable
	private List<SubjectEntity> subjects;
	
	public ProfessorUpdateDto(@Size(min = 3, message = " First name must have min 3 characters") String firstName,
			@Size(min = 3, message = " Last name must have min 3 characters") String lastName, @Email String email,
			@Size(min = 3, message = "Address must have min 3 characters") String address, CityEntity postalCode,
			@Size(min = 9, message = "Phone must have min 9 characters") String phone, Date reelectionDate,
			TitleEntity title, String username, String password, String repeatedPassword,
			List<SubjectEntity> subjects) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.reelectionDate = reelectionDate;
		this.title = title;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.subjects = subjects;
	}
	public ProfessorUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getReelectionDate() {
		return reelectionDate;
	}
	public void setReelectionDate(Date reelectionDate) {
		this.reelectionDate = reelectionDate;
	}
	public TitleEntity getTitle() {
		return title;
	}
	public void setTitle(TitleEntity title) {
		this.title = title;
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
	
	public List<SubjectEntity> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}
	@Override
	public String toString() {
		return "ProfessorUpdateDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", address=" + address + ", postalCode=" + postalCode + ", phone=" + phone + ", reelectionDate="
				+ reelectionDate + ", title=" + title + ", username=" + username + ", password=" + password
				+ ", repeatedPassword=" + repeatedPassword + ", subjects=" + subjects + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, email, firstName, lastName, password, phone, postalCode, reelectionDate,
				repeatedPassword, subjects, title, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessorUpdateDto other = (ProfessorUpdateDto) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(reelectionDate, other.reelectionDate)
				&& Objects.equals(repeatedPassword, other.repeatedPassword) && Objects.equals(subjects, other.subjects)
				&& Objects.equals(title, other.title) && Objects.equals(username, other.username);
	}
	
}
