package miljana.andric.dtos;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CityDto implements Dto{

	@NotNull(message = "Postal code is required")
	@Size(min=5,max=5, message= "Postal code must be exact {max} numbers long.")
	private String postalCode;
	@NotNull(message="Name must be provided")
	@Size(min=2,max=30, message= "Name must be beetwen {min} and {max} characters long.")
	private String name;
	public CityDto() {
		super();
	}
	public CityDto(
			@NotNull(message = "Postal code is required") @Size(min = 5, max = 5, message = "Postal code must be exact {max} numbers long.") String postalCode,
			@NotNull(message = "Name must be provided") @Size(min = 2, max = 30, message = "Name must be beetwen {min} and {max} characters long.") String name) {
		super();
		this.postalCode = postalCode;
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
		return "CityDto [postalCode=" + postalCode + ", name=" + name + "]";
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
		CityDto other = (CityDto) obj;
		return Objects.equals(name, other.name) && Objects.equals(postalCode, other.postalCode);
	}
	
	
}
