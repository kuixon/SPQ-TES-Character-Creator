package es.deusto.spq.TEScc.servidor;

import java.awt.EventQueue;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.TEScc.servidor.IFacade;
import es.deusto.spq.TEScc.servidor.jdo.Objeto;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Servidor extends JFrame {
	
	private JPanel contentPane;
	private static String name;
	private static String serverName;
	private JLabel lblDesconectado;
	private JLabel lblConectado;
	private JLabel lblFondo;
	final Logger logger = LoggerFactory.getLogger(Servidor.class);
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		serverName = args[2];
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor frame = new Servidor(name, serverName, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Servidor(String n, String sn, boolean test) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("target\\classes\\img\\servidor.png"));
		setTitle("Servidor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(594, 292);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDesconectado = new JLabel("DESCONECTADO");
		lblDesconectado.setBounds(250, 110, 107, 17);
		lblDesconectado.setForeground(Color.RED);
		lblDesconectado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblDesconectado);
		
		lblConectado = new JLabel("CONECTADO: " + name);
		lblConectado.setBounds(150, 110, 295, 17);
		lblConectado.setForeground(Color.GREEN);
		lblConectado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConectado.setVisible(false);
		contentPane.add(lblConectado);
		
		lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("target\\classes\\img\\fondo.jpg"));
		lblFondo.setBounds(0, 0, 623, 330);
		contentPane.add(lblFondo);
		
			try {
				IFacade tesccServer = new Facade(sn);
				Naming.rebind(n, tesccServer);
				
				lblDesconectado.setVisible(false);
				lblConectado.setVisible(true);
				
				logger.info("- Servidor de TES Character Creator '" + n + "' activo y en espera...");
				
				logger.info("------------COMPROBACIONES-------------");
				logger.info("Numero de usuarios: " + DAODatabase.getInstance().numeroUsuarios());
				
				if(!test) {
					if(!DAODatabase.getInstance().existeObjeto("Yelmo Elfico"))
						DAODatabase.getInstance().crearObjeto("Yelmo Elfico", 0, 25, 10, 25, 15, 15, Objeto.YELMO);
					if(!DAODatabase.getInstance().existeObjeto("Armadura Elfica"))
						DAODatabase.getInstance().crearObjeto("Armadura Elfica", 0, 25, 15, 25, 10, 15, Objeto.ARMADURA);
					if(!DAODatabase.getInstance().existeObjeto("Brazaletes Elficos"))
						DAODatabase.getInstance().crearObjeto("Brazaletes Elficos", 0, 25, 10, 25, 15, 15, Objeto.BRAZALETE);
					if(!DAODatabase.getInstance().existeObjeto("Botas Elficas"))
						DAODatabase.getInstance().crearObjeto("Botas Elficas", 0, 25, 10, 25, 15, 15, Objeto.BOTAS);
					if(!DAODatabase.getInstance().existeObjeto("Yelmo Daedrico"))
						DAODatabase.getInstance().crearObjeto("Yelmo Daedrico", 0, 8, 25, 8, 20, 20, Objeto.YELMO);
					if(!DAODatabase.getInstance().existeObjeto("Armadura Daedrica"))
						DAODatabase.getInstance().crearObjeto("Armadura Daedrica", 0, 5, 25, 5, 25, 25, Objeto.ARMADURA);
					if(!DAODatabase.getInstance().existeObjeto("Brazaletes Daedricos"))
						DAODatabase.getInstance().crearObjeto("Brazaletes Daedricos", 0, 10, 25, 10, 18, 18, Objeto.BRAZALETE);
					if(!DAODatabase.getInstance().existeObjeto("Botas Daedricas"))
						DAODatabase.getInstance().crearObjeto("Botas Daedricas", 0, 5, 25, 2, 20, 20, Objeto.BOTAS);
				}
			} catch (Exception ex) {
				logger.error("$ Excepcion del servidor: " + ex.getMessage());
				ex.printStackTrace();
			}
	}
}
