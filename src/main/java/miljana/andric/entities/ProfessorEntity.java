package miljana.andric.entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "professor")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProfessorEntity implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = 6842891750590559863L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@Column(name="firstname", nullable = false, columnDefinition = "VARCHAR(30) NOT NULL CHECK (char_length(firstname) >= 3)")
	@NotNull(message="First name must be provided")
	@Size(min=3, message= "First name must be at least {min} characters long.")
	private String firstName;
	@Column(name="lastname", nullable = false, columnDefinition = "VARCHAR(30) NOT NULL CHECK (char_length(lastname) >= 3)")
	@NotNull(message="Last name must be provided")
	@Size(min=3, message= "Last name must be at least {min} characters long.")
	private String lastName;
	@Column(name="email", columnDefinition = "VARCHAR(30) UNIQUE CHECK(email LIKE '%_@_%')")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;
	@Column(name="address", columnDefinition = "VARCHAR(50) CHECK (char_length(address) >= 3)")
	@Size(min=3, message= "Address must be at least {min} characters long.")
	private String address;
//	@JsonIgnore 
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postalcode")
	private CityEntity postalCode;
	@Column(name="phone", nullable = true, columnDefinition = "VARCHAR(15) NOT NULL CHECK (char_length(phone) >= 9)")
	@Size(min=9, message= "Phone must be at least {min} characters long.")
	private String phone;
	@Column(name = "reelectiondate", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message="Reelection date must be provided")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reelectionDate;
//	@JsonIgnore 
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "title")
	private TitleEntity title;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH}, targetEntity = SubjectEntity.class)
	@JoinTable(name = "professor_subject", joinColumns = @JoinColumn(name = "professor_id"),
	inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private List<SubjectEntity> subjects;
	@JsonIgnore
	@OneToMany(mappedBy = "professor")
	private List<ExamEntity> exams;

	public ProfessorEntity() {
		super();
	}

	public ProfessorEntity(Long id,
			@NotNull(message = "First name must be provided") @Size(min = 3, message = "First name must be at least {min} characters long.") String firstName,
			@NotNull(message = "Last name must be provided") @Size(min = 3, message = "Last name must be at least {min} characters long.") String lastName,
			@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@Size(min = 3, message = "Address must be at least {min} characters long.") String address,
			CityEntity postalCode,
			@Size(min = 9, message = "Phone must be at least {min} characters long.") String phone,
			@NotNull(message = "Reelection date must be provided") Date reelectionDate, TitleEntity title,
			List<SubjectEntity> subjects, List<ExamEntity> exams) {
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
		this.exams = exams;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	public List<ExamEntity> getExams() {
		return exams;
	}

	public void setExams(List<ExamEntity> exams) {
		this.exams = exams;
	}

	@Override
	public String toString() {
		return "ProfessorEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", address=" + address + ", postalCode=" + postalCode + ", phone=" + phone + ", reelectionDate="
				+ reelectionDate + ", title=" + title + ", subjects=" + subjects + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, email, firstName, id, lastName, phone, postalCode, reelectionDate, subjects,
				title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessorEntity other = (ProfessorEntity) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phone, other.phone)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(reelectionDate, other.reelectionDate)
				&& Objects.equals(subjects, other.subjects) && Objects.equals(title, other.title);
	}
	
	
}
