package es.deusto.spq.TEScc.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import es.deusto.spq.TEScc.cliente.RMIServiceLocator;

public class Frame extends javax.swing.JFrame {
	private static RMIServiceLocator rmi;
	public static String elegirPantalla = "";
	public static String nombreLogin = "";
	
    public Frame(RMIServiceLocator rmi) {
    	try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	this.rmi = rmi;
        initComponents();
    }
                      
    private void initComponents() {
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		if(elegirPantalla.equals("")) {
			//A�adimos el panel al JFrame.
			getContentPane().add(new PantallaInicio(this.rmi));
		} else if(elegirPantalla.equals("MenuPersonaje")) {
			//A�adimos el panel al JFrame.
			getContentPane().add(new PantallaLogin(nombreLogin, rmi));
		} else if(elegirPantalla.equals("PantallaTablaPersonaje")) {
			//A�adimos el panel al JFrame.
			getContentPane().add(new PantallaTablaPersonaje(nombreLogin, rmi));
		}

		//Para que se vea el panel.
		this.setVisible(true);
		
		//Para que se habra con este tama�o como m�nimo.
        this.setMinimumSize(new Dimension(629, 359));

        //Para que no se pueda agrandar la ventana, que sea de tama�o fijo.
        this.setResizable(false);
        
        //Para que la ventana salga centrada.
        this.setLocationRelativeTo(null);
        
        //Para poner icono en la ventana.
        this.setIconImage(new ImageIcon("target\\classes\\img\\Dragon.png").getImage());
        
        pack();
    }
    
    public static void cerrar()
    {
    	System.exit(-1);
    }

    public static void main(String args[]) {
    
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame(rmi).setVisible(true);
            }
        });
    }                   
}