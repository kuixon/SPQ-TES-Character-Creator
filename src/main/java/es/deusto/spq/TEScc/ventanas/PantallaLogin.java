package es.deusto.spq.TEScc.ventanas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.datanucleus.transaction.jta.BTMTransactionManagerLocator;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.utilidades.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class PantallaLogin extends javax.swing.JPanel implements ActionListener {
	private RMIServiceLocator rmi;
    public static String nomUsuario;
    
	public PantallaLogin(String nom, RMIServiceLocator rmi) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.nomUsuario = nom;
		this.rmi = rmi;
		initComponents();        
	}

    private void initComponents() {
        b_Baja = new javax.swing.JButton();
	    b_salir = new javax.swing.JButton();
	    b_cargarPersonaje = new javax.swing.JButton();
	    b_nuevoPersonaje = new javax.swing.JButton();
	    fondo = new javax.swing.JLabel();


        setLayout(null);

	    lblUsu = new JLabel("");
	    lblUsu.setForeground(new Color(249, 149, 35));
	    lblUsu.setFont(new Font("Segoe UI", Font.BOLD, 15));
	    lblUsu.setBounds(28, 86, 510, 30);
	    lblUsu.setText("¡Bienvenido/a " + nomUsuario + "!");
	    add(lblUsu);
        
        b_Baja.setText("Darse de baja");
        add(b_Baja);
        b_Baja.setBounds(470, 30, 120, 30);
        
        add(b_salir);
        b_salir.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\cerrar.jpg"));
        b_salir.setBounds(10, 10, 40, 40);

        fondo.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\fondo.jpg"));
        add(fondo);
        fondo.setBounds(0, 0, 629, 359);
        
        b_nuevoPersonaje.setText("Nuevo Personaje");
        add(b_nuevoPersonaje);
        b_nuevoPersonaje.setBounds(250, 160, 150, 30);
        
        b_cargarPersonaje.setText("Cargar Personaje");
        add(b_cargarPersonaje);
        b_cargarPersonaje.setBounds(250, 230, 150, 30);
        
        fondo.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\fondo.jpg"));
        add(fondo);
        fondo.setBounds(0, 0, 629, 359);
        
        //A�adir funcionalidad a los botones.
        b_Baja.addActionListener(this);
        b_salir.addActionListener(this);
        b_nuevoPersonaje.addActionListener(this);
        b_cargarPersonaje.addActionListener(this);
        
        //Para que se vea el panel.
	    this.setVisible(true);
      		
	    //Para que se habra con este tama�o como m�nimo.
	    this.setMinimumSize(new Dimension(629, 359));
	    
    }
                     
    private javax.swing.JButton b_Baja;
    private javax.swing.JButton b_salir;
    private javax.swing.JButton b_cargarPersonaje;
    private javax.swing.JButton b_nuevoPersonaje;
    private javax.swing.JLabel fondo;
    private javax.swing.JFrame actual;
    private JPanel estePanel = this;
    private JLabel lblUsu;
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b_salir)
		{
			JOptionPane.showMessageDialog(this, "Ya no estas logueado", "Fin de sesion", JOptionPane.INFORMATION_MESSAGE);
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.getContentPane().remove(0);
			actual.getContentPane().add(new PantallaInicio(this.rmi));
			actual.setPreferredSize(new Dimension(629, 359));
			actual.pack();
			actual.repaint();
			actual.setLocationRelativeTo(null);
		}
		else if(e.getSource() == b_Baja) {
			try {
				ControllerTES.getInstance(rmi).darseBaja(this.nomUsuario);
				JOptionPane.showMessageDialog(this, "Te acabas de dar de baja de THE ELDER SCROLLS CHARACTER CREATOR", "Baja usuario", JOptionPane.INFORMATION_MESSAGE);
				actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
				actual.getContentPane().remove(0);
				actual.getContentPane().add(new PantallaInicio(this.rmi));
				actual.setPreferredSize(new Dimension(629, 359));
				actual.pack();
				actual.repaint();
				actual.setLocationRelativeTo(null);
			} catch (RemoteException e1) {
				JOptionPane.showMessageDialog(this, "No te has podido dar de baja de THE ELDER SCROLLS CHARACTER CREATOR", "Error baja usuario", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		} else if(e.getSource() == b_cargarPersonaje) {
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.getContentPane().remove(0);
			actual.getContentPane().add(new PantallaTablaPersonaje(nomUsuario, this.rmi));
			actual.setPreferredSize(new Dimension(629, 359));
			actual.pack();
			actual.repaint();
			actual.setLocationRelativeTo(null);
		} else if(e.getSource() == b_nuevoPersonaje) {
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.dispose();
			MenuPersonaje mp = new MenuPersonaje("PantallaLogin", this.nomUsuario, "", this.rmi);
		}
	}                   
}