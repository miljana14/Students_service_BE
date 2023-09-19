package miljana.andric.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "exam_application")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ExamApplicationEntity implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = 1040241230934672864L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	@JsonIgnore 
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "indexnumber"),
    	@JoinColumn(name = "indexyear")
    })
	private StudentEntity student;
	@Column(name = "applicationdate")
	private LocalDate applicationDate;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	private Set<ExamEntity> exams;
	
	public ExamApplicationEntity(Long id, StudentEntity student, LocalDate applicationDate, Set<ExamEntity> exams) {
		super();
		this.id = id;
		this.student = student;
		this.applicationDate = applicationDate;
		this.exams = exams;
	}
	public ExamApplicationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudentEntity getStudent() {
		return student;
	}
	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	public LocalDate getDate() {
		return applicationDate;
	}
	public void setDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}
	public Set<ExamEntity> getExams() {
		return exams;
	}
	public void setExams(Set<ExamEntity> exams) {
		this.exams = exams;
	}
	@Override
	public String toString() {
		return "ExamApplicationEntity [id=" + id + ", student=" + student + ", applicationDate=" + applicationDate + ", exams=" + exams + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(applicationDate, exams, id, student);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExamApplicationEntity other = (ExamApplicationEntity) obj;
		return Objects.equals(applicationDate, other.applicationDate) && Objects.equals(exams, other.exams) && Objects.equals(id, other.id)
				&& Objects.equals(student, other.student);
	}
	
	
	
	
}
