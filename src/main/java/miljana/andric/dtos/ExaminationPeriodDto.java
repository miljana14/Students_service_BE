package miljana.andric.dtos;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ExaminationPeriodDto implements Dto{
	
	@NotBlank(message = "Name must be provided")
	private String name;
	@NotNull(message = "Begin date must be provided")
    @FutureOrPresent(message = "Begin date must be in present or future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate beginDate;
	@NotNull(message = "End date must be provided")
    @Future(message = "End date must be in future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	@NotNull(message = "Active status must be provided")
	private boolean active;
	public ExaminationPeriodDto(@NotBlank(message = "Name must be provided") String name,
			@NotNull(message = "Begin date must be provided") @FutureOrPresent(message = "Begin date must be in present or future") LocalDate beginDate,
			@NotNull(message = "End date must be provided") @Future(message = "End date must be in future") LocalDate endDate,
			@NotNull(message = "Active status must be provided") boolean active) {
		super();
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.active = active;
	}
	public ExaminationPeriodDto() {
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
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public int hashCode() {
		return Objects.hash(beginDate, endDate, active, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationPeriodDto other = (ExaminationPeriodDto) obj;
		return Objects.equals(beginDate, other.beginDate) && Objects.equals(endDate, other.endDate)
				&& active == other.active && Objects.equals(name, other.name);
	}
	
	

}
