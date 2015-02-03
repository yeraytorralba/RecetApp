package dad.recetapp;

import java.sql.SQLException;
import dad.recetapp.services.ServicioException;
import dad.recetapp.ui.MainFrame;

public class Main {

	public static void main(String[] args) throws ServicioException, SQLException {
		new MainFrame().setVisible(true);
	}

}
