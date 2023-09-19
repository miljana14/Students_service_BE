package miljana.andric.dtos;

import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import miljana.andric.entities.Semester;

public class SubjectDto implements Dto{

	@NotNull(message = "Name is required!")
	@Size(min = 3, message = "Name must have min 3 characters")
	private String name;
	private String description;
	@NotNull(message = "NO of ESP is required!")
	private Integer noOfESP;
	@NotNull(message = "Year of study is required!")
	@Min(value = 1, message = "Min value must be {value}")
    @Max(value = 5, message = "Max value must be {value}")
	private Integer yearOfStudy;
	@NotNull(message = "Semester is required!")
	@Enumerated(EnumType.STRING)
	private Semester semester;
	
	
	
	public SubjectDto(
			@NotNull(message = "Name is required!") @Size(min = 3, message = "Name must have min 3 characters") String name,
			String description, @NotNull(message = "NO of ESP is required!") Integer noOfESP,
			@NotNull(message = "Year of study is required!") @Min(value = 1, message = "Min value must be {value}") @Max(value = 5, message = "Max value must be {value}") Integer yearOfStudy,
			@NotNull(message = "Semester is required!") Semester semester) {
		super();
		this.name = name;
		this.description = description;
		this.noOfESP = noOfESP;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
	}
	public SubjectDto() {
		super();
		// TODO Auto-generated constructor stub
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
	@Override
	public String toString() {
		return "SubjectDto [name=" + name + ", description=" + description + ", noOfESP=" + noOfESP + ", yearOfStudy="
				+ yearOfStudy + ", semester=" + semester + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(description, name, noOfESP, semester, yearOfStudy);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectDto other = (SubjectDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& noOfESP == other.noOfESP && Objects.equals(semester, other.semester)
				&& yearOfStudy == other.yearOfStudy;
	}

	
	
	
}
