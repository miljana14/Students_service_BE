package miljana.andric.dtos;

import java.util.List;
import java.util.Objects;

import miljana.andric.entities.CityEntity;
import miljana.andric.entities.SubjectEntity;

public class StudentResponseDto implements Dto {

	private String indexNumber;
	private Long indexYear;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private CityEntity postalCode;
	private Integer currentYearOfStudy;
	private String username;
	private String password;
	private List<SubjectEntity> subjects;
	public StudentResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentResponseDto(String indexNumber, Long indexYear, String firstName, String lastName, String email,
			String address, CityEntity postalCode, Integer currentYearOfStudy, String username, String password) {
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
	}
	
	public StudentResponseDto(String indexNumber, Long indexYear, String firstName, String lastName, String email,
			String address, CityEntity postalCode, Integer currentYearOfStudy, String username, String password,
			List<SubjectEntity> subjects) {
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
		this.subjects = subjects;
	}
	public List<SubjectEntity> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
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
	@Override
	public String toString() {
		return "StudentResponseDto [indexNumber=" + indexNumber + ", indexYear=" + indexYear + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", postalCode="
				+ postalCode + ", currentYearOfStudy=" + currentYearOfStudy + ", username=" + username + ", password="
				+ password + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, currentYearOfStudy, email, firstName, indexNumber, indexYear, lastName, password,
				postalCode, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentResponseDto other = (StudentResponseDto) obj;
		return Objects.equals(address, other.address) && currentYearOfStudy == other.currentYearOfStudy
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(indexNumber, other.indexNumber) && Objects.equals(indexYear, other.indexYear)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(username, other.username);
	}
	
	

}
