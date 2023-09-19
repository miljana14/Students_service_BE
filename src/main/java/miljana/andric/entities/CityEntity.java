package miljana.andric.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "city")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CityEntity implements miljana.andric.entities.Entity {

	private static final long serialVersionUID = -5654920092821268193L;
	
	@Id
    @Column(name = "postalcode", unique = true, nullable = false, length = 5)
	@NotNull(message="Postal code must be provided")
	@Size(min=5,max=5, message= "Postal code must be exact {max} numbers long.")
    private String postalCode;
	@Column(name="name", nullable = false)
	@Size(min=2,max=30, message= "Name must be beetwen {min} and {max} characters long.")
    private String name;
	
	public CityEntity() {
		// TODO Auto-generated constructor stub
	}

	public CityEntity(
			@NotNull(message = "Postal code must be provided") @Size(min = 5, max = 5, message = "Postal code must be exact {max} numbers long.") String postalCode,
			@Size(min = 2, max = 30, message = "Name must be beetwen {min} and {max} characters long.") String name) {
		super();
		this.postalCode = postalCode;
		this.name = name;
	}

	
	public CityEntity(
			@Size(min = 2, max = 30, message = "Name must be beetwen {min} and {max} characters long.") String name) {
		super();
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "CityEntity [postalCode=" + postalCode + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, postalCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityEntity other = (CityEntity) obj;
		return Objects.equals(name, other.name) && Objects.equals(postalCode, other.postalCode);
	}
	
	

}
