package miljana.andric.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "student")
@IdClass(Index.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class StudentEntity implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = -3004781589803071782L;
	
	@Id
	@Column(name="indexnumber", nullable = false, columnDefinition = "VARCHAR(4) NOT NULL CHECK (char_length(indexnumber) = 4)")
	@NotNull(message="Index number must be provided")
	@Size(min=4,max=4, message= "Index number must be exact {max} numbers long.")
	private String indexNumber;
    @Id
	@Column(name="indexyear", nullable = false, columnDefinition = "NUMERIC(4) NOT NULL CHECK(indexyear>=2000 AND indexyear<=2100)")
    @NotNull(message="Index year must be provided")
    @Min(value = 2000, message = "Min value must be {value}")
    @Max(value = 2100, message = "Max value must be {value}")
	private Long indexYear;
	@Column(name="firstname", nullable = false, columnDefinition = "VARCHAR(30) NOT NULL CHECK (char_length(firstname) >= 3)")
	@NotNull(message="First name must be provided")
	@Size(min=3, message= "First name must be minimum {min} characters long.")
	private String firstName;
	@Column(name="lastname", nullable = false, columnDefinition = "VARCHAR(30) NOT NULL CHECK (char_length(lastname) >= 3)")
	@NotNull(message="Last name must be provided")
	@Size(min=3, message= "Last name must be minimum {min} characters long.")
	private String lastName;
	@Column(name="email", unique = true, columnDefinition = "VARCHAR(30) UNIQUE CHECK(email LIKE '%_@_%')")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;
	@Column(name="address", columnDefinition = "VARCHAR(50) CHECK (char_length(address) >= 3)")
	@Size(min=3, message= "Address must be minimum {min} characters long.")
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postalcode")
	private CityEntity postalCode;
	@Column(name="currentyearofstudy", nullable = false, columnDefinition = "NUMERIC(7) NOT NULL CHECK(currentyearofstudy>=1 AND currentyearofstudy<=5)")
	@NotNull(message="Current year of study must be provided")
	@Min(value = 1, message = "Min value must be {value}")
    @Max(value = 5, message = "Max value must be {value}")
	private Integer currentYearOfStudy;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, targetEntity = SubjectEntity.class)
	@JoinTable(name = "subject_student",
	               joinColumns ={@JoinColumn(name = "indexnumber"), @JoinColumn(name = "indexyear")},
	               inverseJoinColumns = @JoinColumn(name = "subject"))
    private List<SubjectEntity> subjects;

	public StudentEntity() {
		super();
	}
	
	public StudentEntity(
			@NotNull(message = "Index number must be provided") @Size(min = 4, max = 4, message = "Index number must be exact {max} numbers long.") String indexNumber,
			@NotNull(message = "Index year must be provided") @Min(value = 2000, message = "Min value must be {value}") @Max(value = 2100, message = "Max value must be {value}") Long indexYear,
			@NotNull(message = "First name must be provided") @Size(min = 3, message = "First name must be minimum {min} characters long.") String firstName,
			@NotNull(message = "Last name must be provided") @Size(min = 3, message = "Last name must be minimum {min} characters long.") String lastName,
			@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@Size(min = 3, message = "Address must be minimum {min} characters long.") String address,
			CityEntity postalCode,
			@NotNull(message = "Current year of study must be provided") @Min(value = 1, message = "Min value must be {value}") @Max(value = 5, message = "Max value must be {value}") Integer currentYearOfStudy) {
		super();
		this.indexNumber = indexNumber;
		this.indexYear = indexYear;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.currentYearOfStudy = currentYearOfStudy;
	}
	
	

	public StudentEntity(
			@NotNull(message = "Index number must be provided") @Size(min = 4, max = 4, message = "Index number must be exact {max} numbers long.") String indexNumber,
			@NotNull(message = "Index year must be provided") @Min(value = 2000, message = "Min value must be {value}") @Max(value = 2100, message = "Max value must be {value}") Long indexYear,
			@NotNull(message = "First name must be provided") @Size(min = 3, message = "First name must be minimum {min} characters long.") String firstName,
			@NotNull(message = "Last name must be provided") @Size(min = 3, message = "Last name must be minimum {min} characters long.") String lastName,
			@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@Size(min = 3, message = "Address must be minimum {min} characters long.") String address,
			CityEntity postalCode,
			@NotNull(message = "Current year of study must be provided") @Min(value = 1, message = "Min value must be {value}") @Max(value = 5, message = "Max value must be {value}") Integer currentYearOfStudy,
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


	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "StudentEntity [indexNumber=" + indexNumber + ", indexYear=" + indexYear + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", postalCode=" + postalCode
				+ ", currentYearOfStudy=" + currentYearOfStudy + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, currentYearOfStudy, email, firstName, indexNumber, indexYear, lastName,
				postalCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentEntity other = (StudentEntity) obj;
		return Objects.equals(address, other.address) && currentYearOfStudy == other.currentYearOfStudy
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(indexNumber, other.indexNumber) && Objects.equals(indexYear, other.indexYear)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(postalCode, other.postalCode);
	}
	
	

}
