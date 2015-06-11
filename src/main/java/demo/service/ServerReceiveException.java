package demo.service;

public class ServerReceiveException extends Exception{

	private String message = null;
	
	public ServerReceiveException(){
		super();
	}
	
	public ServerReceiveException(String message) {
		this.message = message;
	}

	public String getMessage(){
		if(null != message)
			return message;
		else
			return super.getMessage();
	}
	
}
