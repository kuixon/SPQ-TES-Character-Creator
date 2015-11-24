package es.deusto.spq.TEScc.ventanas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.utilidades.*;

public class PantallaRegistro extends javax.swing.JPanel implements ActionListener, KeyListener{
	private RMIServiceLocator rmi;
	
    public PantallaRegistro(RMIServiceLocator rmi) {
    	try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		this.rmi = rmi;
        initComponents();
    }
                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        t_Usuario = new javax.swing.JTextField();
        t_Nombre = new javax.swing.JTextField();
        t_Contrasena = new javax.swing.JPasswordField();
        b_Aceptar = new javax.swing.JButton();
        b_retroceso = new javax.swing.JButton();
	    fondo = new javax.swing.JLabel();

        setLayout(null);

        t_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_UsuarioActionPerformed(evt);
            }
        });
        add(t_Usuario);
        t_Usuario.setBounds(190, 170, 220, 30);

        t_Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_NombreActionPerformed(evt);
            }
        });
        add(t_Nombre);
        t_Nombre.setBounds(190, 120, 220, 30);

        b_Aceptar.setText("ACEPTAR");
        add(b_Aceptar);
        b_Aceptar.setBounds(260, 270, 100, 23);
        
        add(b_retroceso);
        b_retroceso.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\retroceso.png"));
        b_retroceso.setBounds(10, 10, 40, 40);

        t_Contrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_ContrasenaActionPerformed(evt);
            }
        });
        add(t_Contrasena);
        t_Contrasena.setBounds(220, 220, 190, 30);

        jLabel3.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 24));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contraseña:");
        add(jLabel3);
        jLabel3.setBounds(70, 220, 140, 30);

        jLabel2.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 24));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario:");
        add(jLabel2);
        jLabel2.setBounds(70, 170, 110, 30);

        jLabel1.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 24)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre:");
        add(jLabel1);
        jLabel1.setBounds(70, 120, 110, 30);
        
        fondo.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\fondo.jpg"));
        add(fondo);
        fondo.setBounds(0, 0, 629, 359);
        
        //A�adir funcionalidad a los botones.
        b_Aceptar.addActionListener(this);
        b_retroceso.addActionListener(this);
        t_Nombre.addKeyListener(this);
        t_Usuario.addKeyListener(this);
        t_Contrasena.addKeyListener(this);
        
        //Para que se vea el panel.
	    this.setVisible(true);
      		
	    //Para que se habra con este tama�o como m�nimo.
	    this.setMinimumSize(new Dimension(629, 359));
    }                        

    private void t_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                          
        
    }                                         

    private void t_NombreActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
    }                                        

    private void t_ContrasenaActionPerformed(java.awt.event.ActionEvent evt) {                                             
        
    }                                            
                    
    private javax.swing.JButton b_Aceptar;
    private javax.swing.JButton b_retroceso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField t_Contrasena;
    private javax.swing.JTextField t_Nombre;
    private javax.swing.JTextField t_Usuario;
    private javax.swing.JLabel fondo;
    private javax.swing.JFrame actual;
    private JPanel estePanel = this;
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b_Aceptar)
		{
			aceptar();
		}
		else if(e.getSource() == b_retroceso)
		{
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.getContentPane().remove(0);
			actual.getContentPane().add(new PantallaInicio(this.rmi));
			actual.setPreferredSize(new Dimension(629, 359));
			actual.pack();
			actual.repaint();
			actual.setLocationRelativeTo(null);
		}
	}

	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			aceptar();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void aceptar() {
		if(t_Nombre.getText().equals("")||t_Usuario.getText().equals("")||t_Contrasena.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Debes rellenar todos los campos", "Registro incompleto", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			try {
				if(ControllerTES.getInstance(rmi).registro(t_Nombre.getText(), t_Usuario.getText(), t_Contrasena.getText()))
				{
					JOptionPane.showMessageDialog(this, "Te has registrado correctamente", "Registro completado", JOptionPane.INFORMATION_MESSAGE);
					actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
					actual.getContentPane().remove(0);
					actual.getContentPane().add(new PantallaInicio(this.rmi));
					actual.setPreferredSize(new Dimension(629, 359));
					actual.pack();
					actual.repaint();
					actual.setLocationRelativeTo(null);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Este username ya esta en uso", "Fallo de registro", JOptionPane.ERROR_MESSAGE);
					t_Usuario.setText("");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}