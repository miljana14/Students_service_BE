package miljana.andric.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examination_period")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ExaminationPeriod implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = 3127070469284592221L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name="name", nullable = false, unique = true)
	@NotBlank(message = "Name must be provided")
	private String name;
	@Column(name = "begindate", nullable = false)
	@NotNull(message = "Begin date must be provided")
//    @FutureOrPresent(message = "Begin date must be in present or future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate beginDate;
	@Column(name = "enddate", nullable = false)
	@NotNull(message = "End date must be provided")
//    @Future(message = "End date must be in future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	@Column(name="active", nullable = false)
	@NotNull(message = "Active status must be provided")
	@Type(type = "true_false")
	private boolean active;
//	@JsonIgnore
	@OneToMany(mappedBy = "examinationPeriod", fetch = FetchType.LAZY,cascade = {CascadeType.MERGE}, orphanRemoval = true)
	private List<ExamEntity> exams;
	
	public ExaminationPeriod() {
		super();
	}
	public ExaminationPeriod(Long id, @NotBlank(message = "Name must be provided") String name,
			@NotNull(message = "Begin date must be provided") LocalDate beginDate,
			@NotNull(message = "End date must be provided") LocalDate endDate,
			@NotNull(message = "Active status must be provided") boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.active = active;
	}
	
	public ExaminationPeriod(@NotBlank(message = "Name must be provided") String name,
			@NotNull(message = "Begin date must be provided") @FutureOrPresent(message = "Begin date must be in present or future") LocalDate beginDate,
			@NotNull(message = "End date must be provided") @Future(message = "End date must be in future") LocalDate endDate,
			@NotNull(message = "Active status must be provided") boolean active) {
		super();
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.active = active;
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
	public String toString() {
		return "ExaminationPeriod [id=" + id + ", name=" + name + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", active=" + active + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(beginDate, endDate, id, active, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationPeriod other = (ExaminationPeriod) obj;
		return Objects.equals(beginDate, other.beginDate) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && active == other.active && Objects.equals(name, other.name);
	}
	
	
	
}
