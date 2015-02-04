package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.RecetappApplication;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.SeccionItem;

public class ComponenteReceta extends FillPane implements Bindable {

	@BXML
	private ComponenteReceta anotacionesPanel;
	@BXML
	private Button anadirIngredienteButton, anadirInstruccionButton;
	@BXML
	private Button editarIngredienteButton, editarInstruccionButton;
	@BXML
	private Button eliminarIngredienteButton, eliminarInstruccionButton;
	@BXML
	private TextInput seccionText;
	@BXML
	private Button eliminarPestanaButton;
	@BXML
	public TableView ingredientesTable;
	@BXML
	public TableView instruccionesTable;
	public org.apache.pivot.collections.List<IngredienteItem> variablesIngredientes;
	public org.apache.pivot.collections.List<InstruccionItem> variablesInstrucciones;
	public Window parent;

	@Override
	public void initialize(Map<String, Object> namespace, URL location,Resources resources) {

		variablesIngredientes = new org.apache.pivot.collections.ArrayList<IngredienteItem>();
		variablesInstrucciones = new org.apache.pivot.collections.ArrayList<InstruccionItem>();

		seccionText.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
			@SuppressWarnings("static-access")
			@Override
			public boolean keyTyped(Component arg0, char arg1) {
				
				if (parent instanceof NuevaRecetaWindow){
					try {
						((NuevaRecetaWindow) parent).cambiarTituloPestana(seccionText.getText());
						((NuevaRecetaWindow) parent).tabPaneNuevaReceta.repaint();
					} catch (NullPointerException e) {
					}
				} else {
					try {
						((EditarRecetaWindow) parent).cambiarTituloPestana(seccionText.getText());
						((EditarRecetaWindow) parent).tabPaneEditarReceta.repaint();
					} catch (NullPointerException e) {
					}
				}
				
				return false;
			}
		});

		eliminarPestanaButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				try {
					NuevaRecetaWindow.eliminarPestanaActual();
				} catch (NullPointerException e) {
				}
				try {
					EditarRecetaWindow.eliminarPestanaActual();
				} catch (NullPointerException e) {
				}
			}
		});

		anadirIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				anadirIngrediente();
			}
		});

		anadirInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				anadirInstruccion();
			}
		});

		editarIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				editarIngrediente();
			}
		});

		editarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				editarInstruccion();
			}
		});

		eliminarIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				eliminarIngrediente();
			}
		});

		eliminarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				eliminarInstruccion();
			}
		});
	}

	private void editarIngrediente() {
		Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
		if (seleccionados.getLength() != 1) {
			Prompt error = new Prompt("Debe seleccionar un ingrediente.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			EditarIngredienteDialog editarIngredienteDialog = null;
			try {
				editarIngredienteDialog = (EditarIngredienteDialog) RecetappApplication.loadWindow("dad/recetapp/ui/EditarIngredienteDialog.bxml");
				IngredienteItem ingredienteSeleccionado = null;

				for (int i = 0; i < seleccionados.getLength(); i++) {
					ingredienteSeleccionado = (IngredienteItem) seleccionados.get(i);
				}

				editarIngredienteDialog.setIngrediente(ingredienteSeleccionado);
				editarIngredienteDialog.open(this.getWindow(), new DialogCloseListener() {
					@Override
					public void dialogClosed(Dialog d, boolean arg1) {
						EditarIngredienteDialog nid = (EditarIngredienteDialog) d;
						if (nid.getAceptar()) {
							variablesIngredientes.update(ingredientesTable.getLastSelectedIndex(),nid.getIngrediente());
							ingredientesTable.setTableData(variablesIngredientes);
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		}
	}

	private void editarInstruccion() {
		Sequence<?> seleccionados = instruccionesTable.getSelectedRows();
		if (seleccionados.getLength() != 1) {
			Prompt error = new Prompt("Debe seleccionar una instrucción.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			EditarInstruccionDialog editarInstruccionDialog = null;
			try {
				editarInstruccionDialog = (EditarInstruccionDialog) RecetappApplication.loadWindow("dad/recetapp/ui/EditarInstruccionDialog.bxml");
				InstruccionItem instruccionSeleccionada = null;

				for (int i = 0; i < seleccionados.getLength(); i++) {
					instruccionSeleccionada = (InstruccionItem) seleccionados.get(i);
				}

				editarInstruccionDialog.setInstruccion(instruccionSeleccionada);
				editarInstruccionDialog.open(this.getWindow(), new DialogCloseListener() {
					@Override
					public void dialogClosed(Dialog d, boolean arg1) {
						EditarInstruccionDialog nid = (EditarInstruccionDialog) d;

						if (nid.getAceptar()) {
							variablesInstrucciones.update(instruccionesTable.getLastSelectedIndex(),nid.getInstruccion());
							instruccionesTable.setTableData(variablesInstrucciones);
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		}
	}

	private void anadirInstruccion() {
		NuevaInstruccionDialog nuevaInstruccionDialog = null;
		try {
			nuevaInstruccionDialog = (NuevaInstruccionDialog) RecetappApplication.loadWindow("dad/recetapp/ui/NuevaInstruccionDialog.bxml");
			nuevaInstruccionDialog.open(this.getWindow(), new DialogCloseListener() {
				@Override
				public void dialogClosed(Dialog d, boolean arg1) {
					NuevaInstruccionDialog nid = (NuevaInstruccionDialog) d;
					if (nid.getAceptar()) {
						variablesInstrucciones.add(nid.getInstruccion());
						instruccionesTable.setTableData(variablesInstrucciones);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}

	private void anadirIngrediente() {
		NuevoIngredienteDialog nuevoIngredienteDialog = null;
		try {
			nuevoIngredienteDialog = (NuevoIngredienteDialog) RecetappApplication.loadWindow("dad/recetapp/ui/NuevoIngredienteDialog.bxml");		
			nuevoIngredienteDialog.open(this.getWindow(), new DialogCloseListener() {
				@Override
				public void dialogClosed(Dialog d, boolean arg1) {
					NuevoIngredienteDialog nid = (NuevoIngredienteDialog) d;
					if (nid.getAceptar()) {
						variablesIngredientes.add(nid.getIngrediente());
						ingredientesTable.setTableData(variablesIngredientes);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}

	private void eliminarIngrediente() {
		Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
		if (seleccionados.getLength() == 0) {
			Prompt error = new Prompt("Debe seleccionar un ingrediente.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				IngredienteItem ingredienteSeleccionado = (IngredienteItem) seleccionados.get(i);
				variablesIngredientes.remove(ingredienteSeleccionado);
			}
		}
	}

	private void eliminarInstruccion() {
		Sequence<?> seleccionados = instruccionesTable.getSelectedRows();
		if (seleccionados.getLength() == 0) {
			Prompt error = new Prompt("Debe seleccionar una instrucción.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				InstruccionItem instruccionSeleccionada = (InstruccionItem) seleccionados.get(i);
				variablesInstrucciones.remove(instruccionSeleccionada);
			}
		}
	}

	public SeccionItem getSeccion() {
		SeccionItem seccion = new SeccionItem();
		seccion.setNombre(seccionText.getText());
		seccion.setIngredientes(convertirListIngredientes(variablesIngredientes));
		seccion.setInstrucciones(convertirListInstrucciones(variablesInstrucciones));
		return seccion;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static java.util.List<IngredienteItem> convertirListIngredientes(org.apache.pivot.collections.List listaUtil) {
		List listaApache = new ArrayList();
		for (int i = 0; i < listaUtil.getLength(); i++) {
			listaApache.add(listaUtil.get(i));
		}
		return listaApache;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static java.util.List<InstruccionItem> convertirListInstrucciones(org.apache.pivot.collections.List listaUtil) {
		List listaApache = new ArrayList();
		for (int i = 0; i < listaUtil.getLength(); i++) {
			listaApache.add(listaUtil.get(i));
		}
		return listaApache;
	}

	public void setParent(Window parent){
		this.parent = parent;
	}
	
	public void setSeccionText(String texto){
		this.seccionText.setText(texto);
	}
	
	public void setVariablesIngredientes(org.apache.pivot.collections.List<IngredienteItem> ingredientes){
		this.variablesIngredientes = ingredientes;
		ingredientesTable.setTableData(variablesIngredientes);
	}
	
	public void setVariablesInstrucciones(org.apache.pivot.collections.List<InstruccionItem> instrucciones){
		this.variablesInstrucciones = instrucciones;
		instruccionesTable.setTableData(variablesInstrucciones);
	}
}