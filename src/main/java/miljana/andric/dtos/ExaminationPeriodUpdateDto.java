package miljana.andric.dtos;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;


public class ExaminationPeriodUpdateDto implements Dto{
	
	@Nullable
	private String name;
	@Nullable
	private LocalDate beginDate;
	@Nullable
	private LocalDate endDate;
	@Nullable
	private boolean isActive;
	
	public ExaminationPeriodUpdateDto(String name,
			LocalDate beginDate,
			LocalDate endDate, boolean isActive) {
		super();
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.isActive = isActive;
	}
	
	public ExaminationPeriodUpdateDto(boolean isActive) {
		super();
		this.isActive = isActive;
	}

	public ExaminationPeriodUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "ExaminationPeriodUpdateDto [name=" + name + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", isActive=" + isActive + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(beginDate, endDate, isActive, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationPeriodUpdateDto other = (ExaminationPeriodUpdateDto) obj;
		return Objects.equals(beginDate, other.beginDate) && Objects.equals(endDate, other.endDate)
				&& isActive == other.isActive && Objects.equals(name, other.name);
	}
	
	

}
