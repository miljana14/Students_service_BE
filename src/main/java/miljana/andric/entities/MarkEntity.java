package miljana.andric.entities;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "mark")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MarkEntity implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = 502804254351839998L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumns({@JoinColumn(name = "indexnumber"), @JoinColumn(name = "indexyear")})
	private StudentEntity student;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "exam_id")
	private ExamEntity exam;

	@Column(name = "mark")
	private Integer mark;

	public MarkEntity(Long id, StudentEntity student, ExamEntity exam, Integer mark) {
		super();
		this.id = id;
		this.student = student;
		this.exam = exam;
		this.mark = mark;
	}

	public MarkEntity() {
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

	public ExamEntity getExam() {
		return exam;
	}

	public void setExam(ExamEntity exam) {
		this.exam = exam;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "MarkEntity [id=" + id + ", student=" + student + ", exam=" + exam + ", mark=" + mark + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(exam, id, mark, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkEntity other = (MarkEntity) obj;
		return Objects.equals(exam, other.exam) && Objects.equals(id, other.id) && Objects.equals(mark, other.mark)
				&& Objects.equals(student, other.student);
	}

}
