package miljana.andric.dtos;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import miljana.andric.entities.CityEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.entities.TitleEntity;

public class ProfessorResponseDto implements Dto{
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private CityEntity postalCode;
	private String phone;
	private Date reelectionDate;
	private TitleEntity title;
	private List<SubjectEntity> subjects;
	private String username;
	private String password;
	
	public ProfessorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProfessorResponseDto(String firstName, String lastName, String email, String address, CityEntity postalCode,
			String phone, Date reelectionDate, TitleEntity title, String username, String password) {
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
	}

	public ProfessorResponseDto(String firstName, String lastName, String email, String address, CityEntity postalCode,
			String phone, Date reelectionDate, TitleEntity title, List<SubjectEntity> subjects, String username,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.reelectionDate = reelectionDate;
		this.title = title;
		this.subjects = subjects;
		this.username = username;
		this.password = password;
	}

	public ProfessorResponseDto(Long id, String firstName, String lastName, String email, String address,
			CityEntity postalCode, String phone, Date reelectionDate, TitleEntity title, List<SubjectEntity> subjects,
			String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.reelectionDate = reelectionDate;
		this.title = title;
		this.subjects = subjects;
		this.username = username;
		this.password = password;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
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
	@Override
	public String toString() {
		return "ProfessorResponseDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", address=" + address + ", postalCode=" + postalCode + ", phone=" + phone + ", reelectionDate="
				+ reelectionDate + ", title=" + title + ", username=" + username + ", password=" + password + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, email, firstName, lastName, password, phone, postalCode, reelectionDate, title,
				username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessorResponseDto other = (ProfessorResponseDto) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(reelectionDate, other.reelectionDate)
				&& Objects.equals(title, other.title) && Objects.equals(username, other.username);
	}
	
	
}
