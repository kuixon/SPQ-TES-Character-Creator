package es.deusto.spq.TEScc.ventanas;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.utilidades.*;

public class PantallaCargaInicial {

	public static void main(String[] args) {
		try{
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSplash pantallaCarga = new JSplash("target\\classes\\img\\PantallaCarga.jpg",
					true, true, false, "Version 3", null,
					Color.WHITE, Color.BLACK);
			pantallaCarga.splashOn();
			pantallaCarga.setProgress(20, "Iniciando");
			Thread.sleep(1000);
			pantallaCarga.setProgress(40, "Cargando datos");
			Thread.sleep(1000);
			pantallaCarga.setProgress(60, "Arrancando el servidor");
			RMIServiceLocator rmi = new RMIServiceLocator();
			try {	
				rmi.setService("127.0.0.1", "1099", "TESccServer");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No se ha podido conectar con el servidor", "Fallo en la conexion", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
			Thread.sleep(1000);
			pantallaCarga.setProgress(80, "Ejecutando aplicacion");
			Thread.sleep(1000);
			pantallaCarga.splashOff();
			new Frame(rmi).setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
