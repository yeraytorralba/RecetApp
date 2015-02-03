package dad.recetapp.services;

import dad.recetapp.services.impl.CategoriasServiceDB;
import dad.recetapp.services.impl.MedidasServiceDB;
import dad.recetapp.services.impl.RecetasServiceDB;
import dad.recetapp.services.impl.TipoAnotacionesServiceDB;
import dad.recetapp.services.impl.TiposIngredientesServiceDB;

public class ServicesLocator {

	private static CategoriasService categoriasService = null;
	private static MedidasService medidasService = null;
	private static RecetasService recetasService = null;
	private static TiposAnotacionesService tiposAnotacionesService = null;
	private static TiposIngredientesService tiposIngredientesService = null;

	public static CategoriasService getCategoriasService() {

		if (categoriasService == null) 
			categoriasService = new CategoriasServiceDB();

		return categoriasService;
	}

	public static MedidasService getMedidasService() {

		if (medidasService == null) 
			medidasService = new MedidasServiceDB();

		return medidasService;
	}

	public static RecetasService getRecetasService() {

		if (recetasService == null) 
			recetasService = new RecetasServiceDB();

		return recetasService;
	}

	public static TiposAnotacionesService getTiposAnotacionesService() {

		if (tiposAnotacionesService == null) 
			tiposAnotacionesService = new TipoAnotacionesServiceDB();

		return tiposAnotacionesService;
	}

	public static TiposIngredientesService getTiposIngredientesService() {

		if (tiposIngredientesService == null) 
			tiposIngredientesService = new TiposIngredientesServiceDB();

		return tiposIngredientesService;
	}

}
