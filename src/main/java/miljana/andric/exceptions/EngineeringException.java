package miljana.andric.exceptions;

public class EngineeringException extends Exception{

	private static final long serialVersionUID = 5425408568125543316L;
	
	private Integer code;
	private String message;
	
	public EngineeringException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
