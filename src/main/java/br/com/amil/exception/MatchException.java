package br.com.amil.exception;

public class MatchException extends Exception {

	private static final long serialVersionUID = 4973851391265575186L;
	
	private Long code;

	public MatchException(){
		super();
	}
	
	public MatchException(Long code, Throwable e){
		super(e);
		this.code = code;
	}

	public Long getCode() {
		return code;
	}
}
