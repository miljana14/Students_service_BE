package miljana.andric.dtos;

import java.util.List;
import java.util.Objects;

import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.StudentEntity;

public class ExamApplicationResponseDto {
	
	private StudentEntity student;
	private List<ExamEntity> exams;
	public ExamApplicationResponseDto(StudentEntity student, List<ExamEntity> exams) {
		super();
		this.student = student;
		this.exams = exams;
	}
	public ExamApplicationResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentEntity getStudent() {
		return student;
	}
	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	public List<ExamEntity> getExams() {
		return exams;
	}
	public void setExams(List<ExamEntity> exams) {
		this.exams = exams;
	}
	@Override
	public String toString() {
		return "ExamApplicationResponseDto [student=" + student + ", exams=" + exams + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(exams, student);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExamApplicationResponseDto other = (ExamApplicationResponseDto) obj;
		return Objects.equals(exams, other.exams) && Objects.equals(student, other.student);
	}
	
	

}
