package miljana.andric.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "title")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TitleEntity implements miljana.andric.entities.Entity {

	private static final long serialVersionUID = -8009248829110958306L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "title", nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private TitleEnum title;

	public TitleEntity() {
		super();
	}
	

	public TitleEntity(TitleEnum title) {
		super();
		this.title = title;
	}


	public TitleEntity(Long id, TitleEnum title) {
		super();
		this.id = id;
		this.title = title;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public TitleEnum getTitle() {
		return title;
	}


	public void setTitle(TitleEnum title) {
		this.title = title;
	}


	@Override
	public String toString() {
		return "TitleEntity [id=" + id + ", title=" + title + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleEntity other = (TitleEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(title, other.title);
	}

}
