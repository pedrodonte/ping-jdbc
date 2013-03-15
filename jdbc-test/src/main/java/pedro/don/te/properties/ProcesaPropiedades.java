package pedro.don.te.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProcesaPropiedades {

	private static ProcesaPropiedades INSTANCIA;
	private Properties configuracion;
	
	public ProcesaPropiedades() throws Exception {
		inicializarLecturaArchivo();
	}
	
	private static void crearInstancia() throws Exception {
		if (INSTANCIA == null) {
			if (INSTANCIA == null) {
				INSTANCIA = new ProcesaPropiedades();
			}
		}
	}
	
	public static ProcesaPropiedades getInstancia() throws Exception {
		crearInstancia();
        return INSTANCIA;
    }
	
	private void inicializarLecturaArchivo() throws Exception {
		configuracion = PropiedadesManager.getInstancia()
				.leerArchivoConfiguracion();
	}


	public Map<String, Object> obtenerConfiguracion(){
		Map<String, Object> propieades = new HashMap<String, Object>();
		propieades.put(PropiedadesManager.NOMBRE_BD, configuracion.getProperty(PropiedadesManager.NOMBRE_BD));
		propieades.put(PropiedadesManager.NOMBRE_SERVIDOR, configuracion.getProperty(PropiedadesManager.NOMBRE_SERVIDOR));
		propieades.put(PropiedadesManager.USUARIO, configuracion.getProperty(PropiedadesManager.USUARIO));
		propieades.put(PropiedadesManager.PASSWORD, configuracion.getProperty(PropiedadesManager.PASSWORD));
		
		return propieades;
	}

}
