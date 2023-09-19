package miljana.andric.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subject")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SubjectEntity implements miljana.andric.entities.Entity {


	private static final long serialVersionUID = -8073331579084486603L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@Column(name="name", nullable = false, columnDefinition = "VARCHAR(30) NOT NULL CHECK (char_length(name) >= 3)")
	@NotNull(message="Name must be provided")
	@Size(min=3, message= "Name must be at least {min} characters long.")
	private String name;
	@Column(name="description", length = 200)
	private String description;
	@Column(name="noofesp", nullable = false, length = 1)
	@NotNull(message="No of espb must be provided")
	private Integer noOfESP;
	@Column(name="yearofstudy", nullable = false, length = 1)
	@NotNull(message="Year of study must be provided")
	@Min(value = 1, message = "Min value must be {value}")
    @Max(value = 5, message = "Max value must be {value}")
	private Integer yearOfStudy;
	@Column(name="semester", nullable = true)
	@NotNull(message="Semester must be provided")
	@Enumerated(EnumType.STRING)
	private Semester semester;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, targetEntity = ProfessorEntity.class)
	@JoinTable(name = "professor_subject", joinColumns = @JoinColumn(name = "subject_id"), 
	inverseJoinColumns = @JoinColumn(name = "professor_id"))
	private List<ProfessorEntity> professors;
	@JsonIgnore
	 @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	 private List<ExamEntity> exams;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, targetEntity = StudentEntity.class)
	@JoinTable(name = "subject_student", joinColumns = @JoinColumn(name = "subject"), 
	inverseJoinColumns = {@JoinColumn(name = "indexnumber"), @JoinColumn(name = "indexyear")})
	private List<StudentEntity> students;
	
	public SubjectEntity() {
		super();
	}
	public SubjectEntity(Long id,
			@NotNull(message = "Name must be provided") @Size(min = 3, message = "Name must be at least {min} characters long.") String name,
			String description, @NotNull(message = "No of espb must be provided") Integer noOfESP,
			@NotNull(message = "Year of study must be provided") Integer yearOfStudy,
			@NotNull(message = "Semester must be provided") Semester semester) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.noOfESP = noOfESP;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
	}
	
	public SubjectEntity(Long id,
			@NotNull(message = "Name must be provided") @Size(min = 3, message = "Name must be at least {min} characters long.") String name,
			String description, @NotNull(message = "No of espb must be provided") Integer noOfESP,
			@NotNull(message = "Year of study must be provided") Integer yearOfStudy,
			@NotNull(message = "Semester must be provided") Semester semester, List<ProfessorEntity> professors) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.noOfESP = noOfESP;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
		this.professors = professors;
	}
	
	
	public SubjectEntity(
			@NotNull(message = "Name must be provided") @Size(min = 3, message = "Name must be at least {min} characters long.") String name,
			String description, @NotNull(message = "No of espb must be provided") Integer noOfESP,
			@NotNull(message = "Year of study must be provided") Integer yearOfStudy,
			@NotNull(message = "Semester must be provided") Semester semester) {
		super();
		this.name = name;
		this.description = description;
		this.noOfESP = noOfESP;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
	}
	
	public SubjectEntity(Long id,
			@NotNull(message = "Name must be provided") @Size(min = 3, message = "Name must be at least {min} characters long.") String name,
			String description, @NotNull(message = "No of espb must be provided") Integer noOfESP,
			@NotNull(message = "Year of study must be provided") @Min(value = 1, message = "Min value must be {value}") @Max(value = 5, message = "Max value must be {value}") Integer yearOfStudy,
			@NotNull(message = "Semester must be provided") Semester semester, List<ProfessorEntity> professors,
			List<ExamEntity> exams, List<StudentEntity> students) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.noOfESP = noOfESP;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
		this.professors = professors;
		this.exams = exams;
		this.students = students;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getNoOfESP() {
		return noOfESP;
	}
	public void setNoOfESP(Integer noOfESP) {
		this.noOfESP = noOfESP;
	}
	public Integer getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public List<ProfessorEntity> getProfessors() {
		return professors;
	}
	public void setProfessors(List<ProfessorEntity> professors) {
		this.professors = professors;
	}

	public List<ExamEntity> getExams() {
		return exams;
	}
	public void setExams(List<ExamEntity> exams) {
		this.exams = exams;
	}
	public List<StudentEntity> getStudents() {
		return students;
	}
	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "SubjectEntity [id=" + id + ", name=" + name + ", description=" + description + ", noOfESP=" + noOfESP
				+ ", yearOfStudy=" + yearOfStudy + ", semester=" + semester + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, noOfESP, semester, yearOfStudy);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectEntity other = (SubjectEntity) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && noOfESP == other.noOfESP && semester == other.semester
				&& yearOfStudy == other.yearOfStudy;
	}

}
