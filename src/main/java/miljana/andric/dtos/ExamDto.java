package miljana.andric.dtos;

import java.time.LocalDate;
import java.util.Objects;

import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.SubjectEntity;

public class ExamDto implements Dto{

	private ExaminationPeriod examinationPeriod;
	private SubjectEntity subject;
	private ProfessorEntity professor;
	private LocalDate examDate;
	
	public ExamDto() {
		super();
	}
	public ExamDto(ExaminationPeriod examinationPeriod, SubjectEntity subject, ProfessorEntity professor,
			LocalDate examDate) {
		super();
		this.examinationPeriod = examinationPeriod;
		this.subject = subject;
		this.professor = professor;
		this.examDate = examDate;
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
	@Override
	public String toString() {
		return "ExamDto [examinationPeriod=" + examinationPeriod + ", subject=" + subject + ", professor=" + professor
				+ ", examDate=" + examDate + "]";
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
		ExamDto other = (ExamDto) obj;
		return Objects.equals(examDate, other.examDate) && Objects.equals(examinationPeriod, other.examinationPeriod)
				&& Objects.equals(professor, other.professor) && Objects.equals(subject, other.subject);
	}
	
	
	
}
