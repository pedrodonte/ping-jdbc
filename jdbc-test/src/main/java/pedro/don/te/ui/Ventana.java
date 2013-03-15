package pedro.don.te.ui;

import static pedro.don.te.properties.PropiedadesManager.NOMBRE_BD;
import static pedro.don.te.properties.PropiedadesManager.NOMBRE_SERVIDOR;
import static pedro.don.te.properties.PropiedadesManager.PASSWORD;
import static pedro.don.te.properties.PropiedadesManager.USUARIO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import pedro.don.te.jdbc.JdbcSqlServer;
import pedro.don.te.properties.ProcesaPropiedades;
import pedro.don.te.properties.PropiedadesManager;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField cpoUsuario = new JTextField();
	JTextField cpoContrasena = new JTextField();
	JTextField cpoServidor = new JTextField();
	JTextField cpoBaseDatos = new JTextField();

	JButton btnEjecutar = new JButton("Ejecutar Test");
	JButton btnGuardar = new JButton("Guardar Configuración");

	JTextArea cpoLogText = new JTextArea("Log text", 10, 20);

	public Ventana() {

		this.getContentPane().add(getBarraHerramientas(), BorderLayout.PAGE_START);
		this.getContentPane().add(getPanelFormulario(), BorderLayout.CENTER);
		this.getContentPane().add(getTextLog(), BorderLayout.SOUTH);

		leerArchivoConfiguracion();

		init();
	}

	private Component getTextLog() {
		cpoLogText.setLineWrap(true);
		return cpoLogText;
	}

	private void leerArchivoConfiguracion() {
		Map<String, Object> propiedades = null;
		try {
			propiedades = ProcesaPropiedades.getInstancia()
					.obtenerConfiguracion();
			cpoUsuario.setText((String) propiedades.get(USUARIO));
			cpoContrasena.setText((String) propiedades.get(PASSWORD));
			cpoBaseDatos.setText((String) propiedades.get(NOMBRE_BD));
			cpoServidor.setText((String) propiedades.get(NOMBRE_SERVIDOR));
			
			cpoLogText.setText("Propiedades cargadas con exito");
		} catch (Exception exception) {
			cpoLogText.setText(exception.toString());
			//Falla la lectura porque no existe el archivo, se crea y se vuelve a cargar
			//si esta vez vuelve a fallar indica un error de lectura.
			try {
				propiedades = ProcesaPropiedades.getInstancia().obtenerConfiguracion();
				cpoUsuario.setText((String) propiedades.get(USUARIO));
				cpoContrasena.setText((String) propiedades.get(PASSWORD));
				cpoBaseDatos.setText((String) propiedades.get(NOMBRE_BD));
				cpoServidor.setText((String) propiedades.get(NOMBRE_SERVIDOR));
			} catch (Exception e1) {
				cpoLogText.setText("Error de lectura del archivo de configuración.");
			}
			
		}

	}

	private Component getBarraHerramientas() {
		JToolBar toolBar = new JToolBar("Barra de Herramientas");
		toolBar.add(btnEjecutar);
		toolBar.add(btnGuardar);

		btnEjecutar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				String server = cpoServidor.getText();
				String dbName = cpoBaseDatos.getText();
				String userName = cpoUsuario.getText();
				String psswd = cpoContrasena.getText();
				try {
					new JdbcSqlServer(server,dbName, userName, psswd);
				} catch (Exception e) {
					cpoLogText.setText(e.getMessage());
				}
			}
		});

		btnGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				prop.setProperty(USUARIO, cpoUsuario.getText());
				prop.setProperty(PASSWORD, cpoContrasena.getText());
				prop.setProperty(NOMBRE_BD, cpoBaseDatos.getText());
				prop.setProperty(NOMBRE_SERVIDOR, cpoServidor.getText());
				try {
					PropiedadesManager.getInstancia()
							.actualizaArchivoPropiedades(prop);
				} catch (Exception e1) {
					cpoLogText.setText(e1.toString());
				}
			}
		});

		return toolBar;
	}

	private Component getPanelFormulario() {
		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("USUARIO"));
		panel.add(cpoUsuario);
		panel.add(new JLabel("CONTRASEÑA"));
		panel.add(cpoContrasena);
		panel.add(new JLabel("SERVIDOR"));
		panel.add(cpoServidor);
		panel.add(new JLabel("BASE DE DATOS"));
		panel.add(cpoBaseDatos);
		return panel;
	}

	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		//this.pack();
		this.setSize(600, 400);
		this.setVisible(true);
		this.setTitle("Ping BD (MS SQL Server) - @pedrodonte v.1");
	}

}
