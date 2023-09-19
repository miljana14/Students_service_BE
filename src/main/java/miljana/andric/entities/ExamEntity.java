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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "exam")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ExamEntity implements miljana.andric.entities.Entity {

	private static final long serialVersionUID = -5805715149675335312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "examinationperiod")
	private ExaminationPeriod examinationPeriod;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "subject")
	private SubjectEntity subject;

	@ManyToOne
	@JoinColumn(name = "professor")
	private ProfessorEntity professor;
	@Column(name = "examdate")
	@NotNull(message = "Exam date must be provided")
	@FutureOrPresent(message = "Exam date must be in present or future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate examDate;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	 @JoinTable(name = "exam_application_exams",
     joinColumns = @JoinColumn(name = "exams_id"),
     inverseJoinColumns = @JoinColumn(name = "exam_application_entity_id"))
	private Set<ExamApplicationEntity> examApplications;

	public ExamEntity() {
		super();
	}

	public ExamEntity(ExaminationPeriod examinationPeriod, SubjectEntity subject, ProfessorEntity professor,
			@NotNull(message = "Exam date must be provided") @FutureOrPresent(message = "Exam date must be in present or future") LocalDate examDate) {
		super();
		this.examinationPeriod = examinationPeriod;
		this.subject = subject;
		this.professor = professor;
		this.examDate = examDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExaminationPeriod getExaminationPeriod() {
		return examinationPeriod;
	}

	public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
		this.examinationPeriod = examinationPeriod;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public ProfessorEntity getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorEntity professor) {
		this.professor = professor;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}
	
	

	public Set<ExamApplicationEntity> getExamApplications() {
		return examApplications;
	}

	public void setExamApplications(Set<ExamApplicationEntity> examApplications) {
		this.examApplications = examApplications;
	}

	@Override
	public String toString() {
		return "ExamEntity [examinationPeriod=" + examinationPeriod + ", subject=" + subject + ", professor="
				+ professor + ", examDate=" + examDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(examDate, examinationPeriod, professor, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExamEntity other = (ExamEntity) obj;
		return Objects.equals(examDate, other.examDate) && Objects.equals(examinationPeriod, other.examinationPeriod)
				&& Objects.equals(professor, other.professor) && Objects.equals(subject, other.subject);
	}

}
