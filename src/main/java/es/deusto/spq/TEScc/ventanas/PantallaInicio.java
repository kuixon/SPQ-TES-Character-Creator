package es.deusto.spq.TEScc.ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.utilidades.*;

public class PantallaInicio extends javax.swing.JPanel implements ActionListener, KeyListener {
	private RMIServiceLocator rmi;
	
    public PantallaInicio(RMIServiceLocator rmi) {
    	try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	this.rmi = rmi;
        initComponents();
    }
                         
    private void initComponents() {
        t_Contrasena = new javax.swing.JPasswordField();
        t_Usuario = new javax.swing.JTextField();
	    b_Entrar = new javax.swing.JButton();
	    b_Registro = new javax.swing.JButton();
	    b_salir = new javax.swing.JButton();
	    jLabel1 = new javax.swing.JLabel();
	    jLabel2 = new javax.swing.JLabel();
	    fondo = new javax.swing.JLabel();
	
	    setLayout(null);
	
	    t_Contrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_ContrasenaActionPerformed(evt);
            }
        });
        add(t_Contrasena);
        t_Contrasena.setBounds(220, 170, 190, 30);

        t_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_UsuarioActionPerformed(evt);
            }
        });
        add(t_Usuario);
        t_Usuario.setBounds(190, 120, 220, 30);
	
	    b_Entrar.setText("ENTRAR");
	    add(b_Entrar);
	    b_Entrar.setBounds(263, 220, 100, 23);
	    
	    b_Registro.setText("Registrarse");
	    add(b_Registro);
	    b_Registro.setBounds(260, 270, 110, 23);
	    
	    add(b_salir);
        b_salir.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\cerrar.jpg"));
        b_salir.setBounds(10, 10, 40, 40);
	
	    jLabel1.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 24));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario:");
        add(jLabel1);
        jLabel1.setBounds(70, 120, 110, 30);
	
	    jLabel2.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 24));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");
        add(jLabel2);
        jLabel2.setBounds(70, 170, 140, 30);
	    
	    fondo.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\fondo.jpg"));
        add(fondo);
        fondo.setBounds(0, 0, 629, 359);
        
	    //A�adir funcionalidad a los botones y textfields.
        b_Entrar.addActionListener(this);
        b_Registro.addActionListener(this);
        b_salir.addActionListener(this);
        t_Usuario.addKeyListener(this);
        t_Contrasena.addKeyListener(this);
	    
	    //Para que se vea el panel.
	    this.setVisible(true);
      		
	    //Para que se habra con este tama�o como m�nimo.
	    this.setMinimumSize(new Dimension(629, 359));
    }                  

    private void t_ContrasenaActionPerformed(java.awt.event.ActionEvent evt) {                                           

    }                                          

    private void t_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                            

    }                                           

                  
    private javax.swing.JButton b_Entrar;
    private javax.swing.JButton b_Registro;
    private javax.swing.JButton b_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField t_Contrasena;
    private javax.swing.JTextField t_Usuario;
    private javax.swing.JLabel fondo;
    private javax.swing.JFrame actual;
    private JPanel estePanel = this;
    
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == b_Registro)
		{
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.getContentPane().remove(0);
			actual.getContentPane().add(new PantallaRegistro(this.rmi));
			actual.setPreferredSize(new Dimension(629, 359));
			actual.pack();
			actual.repaint();
			actual.setLocationRelativeTo(null);
		}
		else if(arg0.getSource() == b_Entrar)
		{
			entrar();
		}
		else if(arg0.getSource() == b_salir)
		{
			Frame.cerrar();
		}
	}

	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			entrar();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void entrar()
	{
		if(t_Usuario.getText().equals("")||t_Contrasena.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Debes rellenar todos los campos", "Login incompleto", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			try {
				if(ControllerTES.getInstance(rmi).existeUsuario(t_Usuario.getText()))
				{
					if(ControllerTES.getInstance(rmi).login(t_Usuario.getText(), t_Contrasena.getText()))
					{
						actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
						actual.getContentPane().remove(0);
						actual.getContentPane().add(new PantallaLogin(t_Usuario.getText(), this.rmi));
						actual.setPreferredSize(new Dimension(629, 359));
						actual.pack();
						actual.repaint();
						actual.setLocationRelativeTo(null);
					}
					else {
						JOptionPane.showMessageDialog(this, "La contraseña es incorrecta", "Error de acceso", JOptionPane.ERROR_MESSAGE);
						t_Contrasena.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(this, "El nombre de usuario que has introducido no existe, registrese", "Usuario no existe", JOptionPane.ERROR_MESSAGE);
					t_Usuario.setText("");
					t_Contrasena.setText("");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}