package dad.recetapp.services;

@SuppressWarnings("serial")
public class ServicioException extends Exception {

	public ServicioException(String message) {
		super(message);
	}
	
	public ServicioException(Exception e) {
		super(e.getMessage(), e);
	}

	public ServicioException(String message, Exception e) {
		super(message, e);
	}
}
