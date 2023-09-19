package miljana.andric.dtos;

import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import miljana.andric.entities.TitleEnum;

public class TitleDto implements Dto{
	
	@NotNull(message = "Title is required")
	@Enumerated(EnumType.STRING)
    private TitleEnum title;

	public TitleDto() {
		super();
	}

	public TitleDto(@NotNull(message = "Title is required") TitleEnum title) {
		super();
		this.title = title;
	}

	public TitleEnum getTitle() {
		return title;
	}

	public void setTitle(TitleEnum title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "TitleDto [title=" + title + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleDto other = (TitleDto) obj;
		return Objects.equals(title, other.title);
	}
	
	

}
