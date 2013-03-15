package pedro.don.te.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropiedadesManager {

	private static PropiedadesManager INSTANCIA;

	public PropiedadesManager() {
	}

	private static void crearInstancia() {
		if (INSTANCIA == null) {
			if (INSTANCIA == null) {
				INSTANCIA = new PropiedadesManager();
			}
		}
	}

	public static PropiedadesManager getInstancia() {
		crearInstancia();
		return INSTANCIA;
	}

	/* METODOS DE LA CLASE */
	public static final String ARCHIVO_CONFIGURACION = "configuracion.properties";
	public static final String USUARIO = "user";
	public static final String PASSWORD = "password";
	public static final String NOMBRE_BD = "bdname";
	public static final String NOMBRE_SERVIDOR = "servname";

	public Properties leerArchivoConfiguracion() throws Exception {
		Properties prop = null;
		if (comprobarExistenciaArchivo()) {

			prop = new Properties();

			try {
				// carga propiedades del archivo
				prop.load(new FileInputStream(ARCHIVO_CONFIGURACION));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} else {
			try {
				crearArchivoPropiedades();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new Exception(
					"El Archivo no Existe, se creará automaticamente.");
		}
		return prop;
	}

	private boolean comprobarExistenciaArchivo() {
		File archivo = new File(ARCHIVO_CONFIGURACION);
		if (archivo.exists()) {
			return true;
		}
		return false;
	}

	protected void crearArchivoPropiedades() throws FileNotFoundException,
			IOException {
		Properties prop = new Properties();

		// definicion de propiedades y sus valores.
		prop.setProperty(USUARIO, "sa");
		prop.setProperty(PASSWORD, "12345");
		prop.setProperty(NOMBRE_BD, "bdsis");
		prop.setProperty(NOMBRE_SERVIDOR, "127.0.0.1");

		// Guarda el archivo en la raiz del proyecto.
		prop.store(new FileOutputStream(ARCHIVO_CONFIGURACION),
				"Archivo Configuración");
		System.out.println("Archivo Creado con Exito");
	}

	public void actualizaArchivoPropiedades(Properties prop)
			throws FileNotFoundException, IOException {

		prop.store(new FileOutputStream(ARCHIVO_CONFIGURACION),
				"Archivo Configuración");
		System.out.println("Archivo Actualizado con Exito");

	}

}
