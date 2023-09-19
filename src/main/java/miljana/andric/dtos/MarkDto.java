package miljana.andric.dtos;

import java.util.Objects;

public class MarkDto implements Dto{

	private String student;
	
	private String subject;
	
	private Integer mark;

	public MarkDto(String student, String subject, Integer mark) {
		super();
		this.student = student;
		this.subject = subject;
		this.mark = mark;
	}

	public MarkDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "MarkDto [student=" + student + ", subject=" + subject + ", mark=" + mark + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(mark, student, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkDto other = (MarkDto) obj;
		return Objects.equals(mark, other.mark) && Objects.equals(student, other.student)
				&& Objects.equals(subject, other.subject);
	}
	
	
}
