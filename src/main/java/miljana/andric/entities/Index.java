package miljana.andric.entities;

import java.io.Serializable;
import java.util.Objects;

public class Index implements Serializable{

	private static final long serialVersionUID = -4441853501442329861L;
	
	private String indexNumber;
	private Long indexYear;
	
	public Index() {
		// TODO Auto-generated constructor stub
	}

	public Index(String indexNumber, Long indexYear) {
		super();
		this.indexNumber = indexNumber;
		this.indexYear = indexYear;
	}



	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	public Long getIndexYear() {
		return indexYear;
	}

	public void setIndexYear(Long indexYear) {
		this.indexYear = indexYear;
	}

	@Override
	public String toString() {
		return "Index [indexNumber=" + indexNumber + ", indexYear=" + indexYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(indexNumber, indexYear);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Index other = (Index) obj;
		return Objects.equals(indexNumber, other.indexNumber) && Objects.equals(indexYear, other.indexYear);
	}
	

}
