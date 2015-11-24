package es.deusto.spq.TEScc.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.servidor.DAODatabase;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.dto.ObjetoDTO;

import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class MenuPersonaje extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private String nombreClase;
	private String nomUsuario;
	private String nomPersonaje;
	private RMIServiceLocator rmi;
	private JButton btnGuardar;
	private JButton btn_retroceso;
	private JButton btnBasura;
	private JButton btnCalcularHabilidades;
	private JLabel elfo;
	private JLabel brazDerElfoElfo;
	private JLabel brazIzqElfoElfo;
	private JLabel botaDerElfoElfo;
	private JLabel botaIzqElfoElfo;
	private JLabel cascoElfoElfo;
	private JLabel coraElfoElfo;
	private ImageIcon elfoI;
	private Set<ObjetoDTO> objetos;
	private Set<ObjetoDTO> yelmos = new HashSet<ObjetoDTO>();
	private String[] nomYelmos;
	private Set<ObjetoDTO> armaduras = new HashSet<ObjetoDTO>();
	private String[] nomArmaduras;
	private Set<ObjetoDTO> brazaletes = new HashSet<ObjetoDTO>();
	private String[] nomBrazaletes;
	private Set<ObjetoDTO> botas = new HashSet<ObjetoDTO>();
	private String[] nomBotas;
	private JComboBox comboBox_Tipo;
	private JProgressBar progressBar_vida;
	private JProgressBar progressBar_velocidad;
	private JProgressBar progressBar_armadura;
	private JProgressBar progressBar_defensa;
	private JProgressBar progressBar_ataque;
	private JProgressBar progressBar_discrecion;
	private JComboBox<String> comboBox_Yelmo;
	private JComboBox<String> comboBox_Armadura;
	private JComboBox<String> comboBox_Brazalete;
	private JComboBox<String> comboBox_Botas;
	private int tipo = 0;
	private boolean modificar = false;
	private PersonajeDTO per;
	private Set<PersonajeDTO> personajes = new HashSet<PersonajeDTO>();
	private JLabel lbl_RestaurarValores;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPersonaje frame = new MenuPersonaje("", "", "", null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPersonaje(String nombreClase, String nomUsuario, String nomPersonaje, RMIServiceLocator rmi) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.nombreClase = nombreClase;
		this.nomUsuario = nomUsuario;
		this.nomPersonaje = nomPersonaje;
		this.rmi = rmi;
		
		if(!nomPersonaje.equals("")) {
			modificar = true;
			try {
				per = ControllerTES.getInstance(this.rmi).getPersonaje(nomPersonaje);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.objetos = ControllerTES.getInstance(this.rmi).getObjetos();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 680);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 250, 641);
		contentPane.add(panel);
		panel.setLayout(null);
		
		progressBar_vida = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_vida.setForeground(new Color(255, 0, 0));
		progressBar_vida.setBackground(new Color(245, 222, 179));
		progressBar_vida.setBounds(21, 100, 17, 440);
		panel.add(progressBar_vida);
		
		progressBar_velocidad = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_velocidad.setBackground(new Color(245, 222, 179));
		progressBar_velocidad.setForeground(new Color(30, 144, 255));
		progressBar_velocidad.setBounds(59, 100, 17, 440);
		panel.add(progressBar_velocidad);
		
		progressBar_armadura = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_armadura.setForeground(new Color(46, 139, 87));
		progressBar_armadura.setBackground(new Color(245, 222, 179));
		progressBar_armadura.setBounds(98, 100, 17, 440);
		panel.add(progressBar_armadura);
		
		progressBar_defensa = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_defensa.setForeground(Color.YELLOW);
		progressBar_defensa.setBackground(new Color(245, 222, 179));
		progressBar_defensa.setBounds(212, 100, 17, 440);
		panel.add(progressBar_defensa);
		
		progressBar_ataque = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_ataque.setForeground(new Color(0, 0, 0));
		progressBar_ataque.setBackground(new Color(245, 222, 179));
		progressBar_ataque.setBounds(174, 100, 17, 440);
		panel.add(progressBar_ataque);
		
		progressBar_discrecion = new JProgressBar(SwingConstants.VERTICAL);
		progressBar_discrecion.setForeground(new Color(147, 112, 219));
		progressBar_discrecion.setBackground(new Color(245, 222, 179));
		progressBar_discrecion.setBounds(136, 100, 17, 440);
		panel.add(progressBar_discrecion);
		
		JLabel vida = new JLabel("");
		vida.setIcon(new ImageIcon("target\\classes\\img\\vida.png"));
		vida.setBounds(14, 555, 30, 30);
		panel.add(vida);
		
		JLabel velocidad = new JLabel("");
		velocidad.setIcon(new ImageIcon("target\\classes\\img\\velocidad.png"));
		velocidad.setBounds(52, 555, 30, 30);
		panel.add(velocidad);
		
		JLabel armadura = new JLabel("");
		armadura.setIcon(new ImageIcon("target\\classes\\img\\armadura.png"));
		armadura.setBounds(91, 555, 30, 30);
		panel.add(armadura);
		
		JLabel discrecion = new JLabel("");
		discrecion.setIcon(new ImageIcon("target\\classes\\img\\discrecion.png"));
		discrecion.setBounds(129, 555, 30, 30);
		panel.add(discrecion);
		
		JLabel ataque = new JLabel("");
		ataque.setIcon(new ImageIcon("target\\classes\\img\\ataque.png"));
		ataque.setBounds(167, 555, 30, 30);
		panel.add(ataque);
		
		JLabel defensa = new JLabel("");
		defensa.setIcon(new ImageIcon("target\\classes\\img\\escudo.png"));
		defensa.setBounds(205, 555, 30, 30);
		panel.add(defensa);
		
		btn_retroceso = new JButton();
		btn_retroceso.setBounds(21, 42, 40, 40);
		panel.add(btn_retroceso);
		btn_retroceso.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\retroceso.png"));
		btn_retroceso.addActionListener(this);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(784, 0, 250, 641);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombre.setBounds(32, 94, 68, 22);
		panel_1.add(lblNombre);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(32, 178, 46, 14);
		panel_1.add(lblTipo);
		
		comboBox_Tipo = new JComboBox();
		comboBox_Tipo.setModel(new DefaultComboBoxModel(new String[] {"Elfo", "Orco"}));
		comboBox_Tipo.setBounds(32, 203, 192, 20);
		panel_1.add(comboBox_Tipo);
		
		inizializarHashSets();
		
		inizializarStrings();
			
		JLabel lblYelmo = new JLabel("Yelmo");
		lblYelmo.setBounds(32, 234, 46, 14);
		panel_1.add(lblYelmo);
		
		comboBox_Yelmo = new JComboBox<String>();
		comboBox_Yelmo.setModel(new DefaultComboBoxModel<String>(nomYelmos));
		comboBox_Yelmo.setBounds(32, 259, 192, 20);
		panel_1.add(comboBox_Yelmo);
		
		JLabel lblArmadura = new JLabel("Armadura");
		lblArmadura.setBounds(32, 290, 60, 14);
		panel_1.add(lblArmadura);
		
		comboBox_Armadura = new JComboBox<String>();
		comboBox_Armadura.setModel(new DefaultComboBoxModel<String>(nomArmaduras));
		comboBox_Armadura.setBounds(32, 315, 192, 20);
		panel_1.add(comboBox_Armadura);
		
		JLabel lblBrazaletes = new JLabel("Brazaletes");
		lblBrazaletes.setBounds(32, 346, 68, 14);
		panel_1.add(lblBrazaletes);
		
		comboBox_Brazalete = new JComboBox<String>();
		comboBox_Brazalete.setModel(new DefaultComboBoxModel<String>(nomBrazaletes));
		comboBox_Brazalete.setBounds(32, 371, 192, 20);
		panel_1.add(comboBox_Brazalete);
		
		JLabel lblBotas = new JLabel("Botas");
		lblBotas.setBounds(32, 402, 46, 14);
		panel_1.add(lblBotas);
		
		comboBox_Botas = new JComboBox<String>();
		comboBox_Botas.setModel(new DefaultComboBoxModel<String>(nomBotas));
		comboBox_Botas.setBounds(32, 427, 192, 20);
		panel_1.add(comboBox_Botas);
		
		textField = new JTextField();
		textField.setBounds(32, 127, 192, 30);
		panel_1.add(textField);
		textField.setColumns(10);
		if(modificar)
			textField.setText(this.nomPersonaje);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnGuardar.setBounds(52, 531, 154, 62);
		panel_1.add(btnGuardar);
		
		btnCalcularHabilidades = new JButton("Calcular habilidades");
		btnCalcularHabilidades.setBounds(52, 490, 154, 30);
		panel_1.add(btnCalcularHabilidades);
		
		if(nombreClase.equals("PantallaTablaPersonaje")) {
			textField.setEnabled(false);
			btnGuardar.setText("Editar");
		}
		
		lbl_RestaurarValores = new JLabel("Restaurar valores:");
		lbl_RestaurarValores.setBounds(32, 465, 109, 14);
		panel_1.add(lbl_RestaurarValores);
		
		btnBasura = new JButton("");
		btnBasura.setIcon(new ImageIcon("target\\classes\\img\\basura.png"));
		btnBasura.setBounds(145, 458, 33, 30);
		panel_1.add(btnBasura);
		
		JLabel fondoDerecha = new JLabel("");
		fondoDerecha.setIcon(new ImageIcon("target\\classes\\img\\marcoMedieval.png"));
		fondoDerecha.setBounds(0, 0, 250, 641);
		panel_1.add(fondoDerecha);
		
		brazDerElfoElfo = new JLabel("");
		contentPane.add(brazDerElfoElfo);
		
		brazIzqElfoElfo = new JLabel("");
		contentPane.add(brazIzqElfoElfo);

		botaDerElfoElfo = new JLabel("");
		contentPane.add(botaDerElfoElfo);
        
        botaIzqElfoElfo = new JLabel("");
        contentPane.add(botaIzqElfoElfo);
		
		coraElfoElfo = new JLabel("");
		contentPane.add(coraElfoElfo);
		
		cascoElfoElfo = new JLabel("");
		contentPane.add(cascoElfoElfo);
		
		elfoI = new ImageIcon("target\\classes\\img\\Elfo.png");
		elfo = new JLabel("");
		elfo.setBounds(415, 21, 199, 609);
		contentPane.add(elfo);
		elfo.setIcon(elfoI);
		
		if(modificar) {
			try {
				inicializarObejtosPer();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			actualizarImagenPersonaje();
			calcularProgressBar();
		}
		
        JLabel fondoIzquierda = new JLabel("");
        fondoIzquierda.setIcon(new ImageIcon("target\\classes\\img\\marcoMedieval.png"));
        fondoIzquierda.setBounds(0, 0, 250, 641);
        panel.add(fondoIzquierda);
        
        JLabel fondoCentro = new JLabel("");
        fondoCentro.setIcon(new ImageIcon("target\\classes\\img\\fondoMenuPersonajes.png"));
        fondoCentro.setBounds(249, 0, 535, 641);
        contentPane.add(fondoCentro);
        
        actualizarProgressBar("comboBox_Tipo");
		
		//Añadir funcionalidad a los botones.
        btnGuardar.addActionListener(this);
        btnCalcularHabilidades.addActionListener(this);
        btnBasura.addActionListener(this);
        comboBox_Tipo.addActionListener(this);
        comboBox_Armadura.addActionListener(this);
        comboBox_Botas.addActionListener(this);
        comboBox_Brazalete.addActionListener(this);
        comboBox_Yelmo.addActionListener(this);
        
        //Para que se vea el panel.
	    this.setVisible(true);
	    
	    //Para que no se pueda agrandar la ventana, que sea de tama�o fijo.
        this.setResizable(false);
	    
	    //Para poner icono en la ventana.
        this.setIconImage(new ImageIcon("target\\classes\\img\\Dragon.png").getImage());
        
        //Ayuda en los botones.
        vida.setToolTipText("Vida");
        velocidad.setToolTipText("Velocidad");
        armadura.setToolTipText("Armadura");
        discrecion.setToolTipText("Discrecion");
        ataque.setToolTipText("Ataque");
        defensa.setToolTipText("Defensa");
        btnCalcularHabilidades.setToolTipText("Dandole a este boton calcularas la suma lo que te aportan los objetos seleccionados hasta el momento");
        btnBasura.setToolTipText("Pulsando este boton le quitaras a tu personaje los objetos que lleva hasta el momento");
      		
	    //Para que se habra con este tama�o como m�nimo.
	    this.setMinimumSize(new Dimension(1050, 680));
	}
	
	public boolean existePersonaje() {
		try {
			personajes = ControllerTES.getInstance(this.rmi).getPersonajesPorUsuario(nomUsuario);
			for(Iterator<PersonajeDTO> i = personajes.iterator(); i.hasNext();) {
				PersonajeDTO per = i.next();
				if(per.getApodo().equals(textField.getText())) {
					return true;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void reiniciarComboBoxes() {
		comboBox_Tipo.setModel(new DefaultComboBoxModel(new String[] {"Elfo", "Orco"}));
		comboBox_Yelmo.setModel(new DefaultComboBoxModel<String>(nomYelmos));
		comboBox_Armadura.setModel(new DefaultComboBoxModel<String>(nomArmaduras));
		comboBox_Brazalete.setModel(new DefaultComboBoxModel<String>(nomBrazaletes));
		comboBox_Botas.setModel(new DefaultComboBoxModel<String>(nomBotas));
	}
	
	public void inizializarHashSets() {
		for(Iterator<ObjetoDTO> i = objetos.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			switch(obj.getTipo()) {
				case 0: yelmos.add(obj);
					break;
				case 1: armaduras.add(obj);
					break;
				case 2: brazaletes.add(obj);
					break;
				case 3: botas.add(obj);
					break;
			}
		}
	}
	
	public void inicializarObejtosPer() throws RemoteException {
		Set<ObjetoDTO> objetos = ControllerTES.getInstance(this.rmi).getObjetosPorPersonaje(nomPersonaje);
		for(Iterator<ObjetoDTO> i = objetos.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			if(obj.getNombre().contains("Yelmo")){
				comboBox_Yelmo.setSelectedItem(obj.getNombre());
			} else if(obj.getNombre().contains("Armadura")){
				comboBox_Armadura.setSelectedItem(obj.getNombre());
			} else if(obj.getNombre().contains("Brazaletes")){
				comboBox_Brazalete.setSelectedItem(obj.getNombre());
			} else if(obj.getNombre().contains("Botas")){
				comboBox_Botas.setSelectedItem(obj.getNombre());
			}
		}
		
		if(per.getTipo() == PersonajeDTO.ELFO) {
			comboBox_Tipo.setSelectedItem("Elfo");
		} else if(per.getTipo() == PersonajeDTO.ORCO) {
			comboBox_Tipo.setSelectedItem("Orco");
		}
	}
	public void inizializarStrings() {
		for(Iterator<ObjetoDTO> i = yelmos.iterator(); i.hasNext();) {
			nomYelmos = new String[yelmos.size()+ 1];
			nomYelmos[0] = "";
			for(int j = 1; j < nomYelmos.length; j++)
				nomYelmos[j] = i.next().getNombre();
		}
		
		for(Iterator<ObjetoDTO> i = armaduras.iterator(); i.hasNext();) {
			nomArmaduras = new String[armaduras.size() + 1];
			nomArmaduras[0] = "";
			for(int j = 1; j < nomArmaduras.length; j++)
				nomArmaduras[j] = i.next().getNombre();
		}		
		
		for(Iterator<ObjetoDTO> i = brazaletes.iterator(); i.hasNext();) {
			nomBrazaletes = new String[brazaletes.size() + 1];
			nomBrazaletes[0] = "";
			for(int j = 1; j < nomBrazaletes.length; j++)
				nomBrazaletes[j] = i.next().getNombre();
		}	
		
		for(Iterator<ObjetoDTO> i = botas.iterator(); i.hasNext();) {
			nomBotas = new String[botas.size() + 1];
			nomBotas[0] = "";
			for(int j = 1; j < nomBotas.length; j++)
				nomBotas[j] = i.next().getNombre();
		}
	}
	
	public void reiniciarProgressBar() {
		progressBar_vida.setValue(0);
		progressBar_velocidad.setValue(0);
		progressBar_discrecion.setValue(0);
		progressBar_defensa.setValue(0);
		progressBar_ataque.setValue(0);
		progressBar_armadura.setValue(0);
	}
	
	public void actualizarProgressBar(String comboBoxAActualizar) {
		if(comboBoxAActualizar.equals("comboBox_Tipo")) {
			//Inicializamos las vidas
			if(comboBox_Tipo.getSelectedItem().toString().equals("Elfo")) {
				progressBar_vida.setValue(100);
			} else if(comboBox_Tipo.getSelectedItem().toString().equals("Orco")) {
				progressBar_vida.setValue(80);
			}
		} else if(comboBoxAActualizar.equals("comboBox_Yelmo")) {
			//Recorremos los yelmos
			for(Iterator<ObjetoDTO> i = yelmos.iterator(); i.hasNext();) {
				ObjetoDTO obj = i.next();
				if(comboBox_Yelmo.getSelectedItem().toString().equals(obj.getNombre())) {
					progressBar_vida.setValue(obj.getVida());
					progressBar_velocidad.setValue(obj.getVelocidad());
					progressBar_discrecion.setValue(obj.getDiscrecion());
					progressBar_defensa.setValue(obj.getDefensa());
					progressBar_ataque.setValue(obj.getAtaque());
					progressBar_armadura.setValue(obj.getArmadura());
				}
			}
		} else if(comboBoxAActualizar.equals("comboBox_Armadura")) {
			//Recorremos los armaduras
			for(Iterator<ObjetoDTO> i = armaduras.iterator(); i.hasNext();) {
				ObjetoDTO obj = i.next();
				if(comboBox_Armadura.getSelectedItem().toString().equals(obj.getNombre())) {
					progressBar_vida.setValue(obj.getVida());
					progressBar_velocidad.setValue(obj.getVelocidad());
					progressBar_discrecion.setValue(obj.getDiscrecion());
					progressBar_defensa.setValue(obj.getDefensa());
					progressBar_ataque.setValue(obj.getAtaque());
					progressBar_armadura.setValue(obj.getArmadura());
				}
			}
		} else if(comboBoxAActualizar.equals("comboBox_Brazalete")) {
			//Recorremos los brazaletes
			for(Iterator<ObjetoDTO> i = brazaletes.iterator(); i.hasNext();) {
				ObjetoDTO obj = i.next();
				if(comboBox_Brazalete.getSelectedItem().toString().equals(obj.getNombre())) {
					progressBar_vida.setValue(obj.getVida());
					progressBar_velocidad.setValue(obj.getVelocidad());
					progressBar_discrecion.setValue(obj.getDiscrecion());
					progressBar_defensa.setValue(obj.getDefensa());
					progressBar_ataque.setValue(obj.getAtaque());
					progressBar_armadura.setValue(obj.getArmadura());
				}
			}
		} else if(comboBoxAActualizar.equals("comboBox_Botas")) {
			//Recorremos los botas
			for(Iterator<ObjetoDTO> i = botas.iterator(); i.hasNext();) {
				ObjetoDTO obj = i.next();
				if(comboBox_Botas.getSelectedItem().toString().equals(obj.getNombre())) {
					progressBar_vida.setValue(obj.getVida());
					progressBar_velocidad.setValue(obj.getVelocidad());
					progressBar_discrecion.setValue(obj.getDiscrecion());
					progressBar_defensa.setValue(obj.getDefensa());
					progressBar_ataque.setValue(obj.getAtaque());
					progressBar_armadura.setValue(obj.getArmadura());
				}
			}
		}
	}
	
	public void calcularProgressBar() {
		//Inicializamos las vidas
		if(comboBox_Tipo.getSelectedItem().toString().equals("Elfo")) {
			progressBar_vida.setValue(100);
		} else if(comboBox_Tipo.getSelectedItem().toString().equals("Orco")) {
			progressBar_vida.setValue(80);
		}
		
		//Recorremos los yelmos
		for(Iterator<ObjetoDTO> i = yelmos.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			if(comboBox_Yelmo.getSelectedItem().toString().equals(obj.getNombre())) {
				progressBar_velocidad.setValue(progressBar_velocidad.getValue() + obj.getVelocidad());
				progressBar_discrecion.setValue(progressBar_discrecion.getValue() + obj.getDiscrecion());
				progressBar_defensa.setValue(progressBar_defensa.getValue() + obj.getDefensa());
				progressBar_ataque.setValue(progressBar_ataque.getValue() + obj.getAtaque());
				progressBar_armadura.setValue(progressBar_armadura.getValue() + obj.getArmadura());
			}
		}
		
		//Recorremos los armaduras
		for(Iterator<ObjetoDTO> i = armaduras.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			if(comboBox_Armadura.getSelectedItem().toString().equals(obj.getNombre())) {
				progressBar_velocidad.setValue(progressBar_velocidad.getValue() + obj.getVelocidad());
				progressBar_discrecion.setValue(progressBar_discrecion.getValue() + obj.getDiscrecion());
				progressBar_defensa.setValue(progressBar_defensa.getValue() + obj.getDefensa());
				progressBar_ataque.setValue(progressBar_ataque.getValue() + obj.getAtaque());
				progressBar_armadura.setValue(progressBar_armadura.getValue() + obj.getArmadura());
			}
		}
		
		//Recorremos los brazaletes
		for(Iterator<ObjetoDTO> i = brazaletes.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			if(comboBox_Brazalete.getSelectedItem().toString().equals(obj.getNombre())) {
				progressBar_velocidad.setValue(progressBar_velocidad.getValue() + obj.getVelocidad());
				progressBar_discrecion.setValue(progressBar_discrecion.getValue() + obj.getDiscrecion());
				progressBar_defensa.setValue(progressBar_defensa.getValue() + obj.getDefensa());
				progressBar_ataque.setValue(progressBar_ataque.getValue() + obj.getAtaque());
				progressBar_armadura.setValue(progressBar_armadura.getValue() + obj.getArmadura());
			}
		}
		
		//Recorremos los botas
		for(Iterator<ObjetoDTO> i = botas.iterator(); i.hasNext();) {
			ObjetoDTO obj = i.next();
			if(comboBox_Botas.getSelectedItem().toString().equals(obj.getNombre())) {
				progressBar_velocidad.setValue(progressBar_velocidad.getValue() + obj.getVelocidad());
				progressBar_discrecion.setValue(progressBar_discrecion.getValue() + obj.getDiscrecion());
				progressBar_defensa.setValue(progressBar_defensa.getValue() + obj.getDefensa());
				progressBar_ataque.setValue(progressBar_ataque.getValue() + obj.getAtaque());
				progressBar_armadura.setValue(progressBar_armadura.getValue() + obj.getArmadura());
			}
		}
	}
	
	public void calcularTipo() {
		if(comboBox_Tipo.getSelectedItem().toString().equals("Elfo")) {
			//ELFO
			tipo = PersonajeDTO.ELFO;
		} else if(comboBox_Tipo.getSelectedItem().toString().equals("Orco")) {
			//ORCO
			tipo = PersonajeDTO.ORCO;
		}
	}
	
	public String [] crearLista() {
		LinkedList <String> lista = new LinkedList<String>();
		lista.add(comboBox_Yelmo.getSelectedItem().toString());
		lista.add(comboBox_Armadura.getSelectedItem().toString());
		lista.add(comboBox_Brazalete.getSelectedItem().toString());
		lista.add(comboBox_Botas.getSelectedItem().toString());
		return volcarListAAarray(lista);
	}
	
	public String [] volcarListAAarray(LinkedList<String> lista) {
		String [] objs = new String [lista.size()];
		for(int i = 0; i < lista.size(); i++) {
			objs[i] = lista.get(i);
		}
		return objs;
	}
	
	public String obtenerFechaActual()
	{
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH)+1;
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		String a = Integer.toString(anio);
		String m = Integer.toString(mes);
		String d = Integer.toString(dia);
		String fechaCreacion = d + "/" + m + "/" + a;
		return fechaCreacion;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnGuardar) {
			String fechaCreacion = obtenerFechaActual();
			if(!textField.getText().equals("")) {
				calcularTipo();
				try {
					if(btnGuardar.getText().equals("Guardar")) {
						if(!existePersonaje()) {
							ControllerTES.getInstance(this.rmi).guardarPersonaje(nomUsuario, textField.getText(), fechaCreacion, fechaCreacion, 
									progressBar_vida.getValue(), progressBar_velocidad.getValue(), progressBar_armadura.getValue(), 
									progressBar_discrecion.getValue(), progressBar_ataque.getValue(), 
									progressBar_defensa.getValue(), tipo, crearLista());
							JOptionPane.showMessageDialog(this, "¡Felicidades!¡Te acabas de crear un nuevo personaje!", "Personaje creado", JOptionPane.INFORMATION_MESSAGE);
							Frame.nombreLogin = this.nomUsuario;
							Frame.elegirPantalla = "MenuPersonaje";
							this.dispose();
							Frame f = new Frame(this.rmi);
						} else {
							JOptionPane.showMessageDialog(this, "¡Ya tienes un personaje con ese nombre!", "Personaje existente", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						ControllerTES.getInstance(this.rmi).guardarPersonaje(nomUsuario, textField.getText(), fechaCreacion, fechaCreacion, 
								progressBar_vida.getValue(), progressBar_velocidad.getValue(), progressBar_armadura.getValue(), 
								progressBar_discrecion.getValue(), progressBar_ataque.getValue(), 
								progressBar_defensa.getValue(), tipo, crearLista());
						JOptionPane.showMessageDialog(this, "¡Acabas de editar a tu personaje!", "Personaje editado", JOptionPane.INFORMATION_MESSAGE);
						Frame.nombreLogin = this.nomUsuario;
						Frame.elegirPantalla = "MenuPersonaje";
						this.dispose();
						Frame f = new Frame(this.rmi);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Debes rellenar todos los campos", "Personaje incompleto", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == btn_retroceso) {
			if(nombreClase.equals("PantallaLogin")) {
				Frame.nombreLogin = this.nomUsuario;
				Frame.elegirPantalla = "MenuPersonaje";
				this.dispose();
				Frame f = new Frame(this.rmi);
			} else {
				Frame.nombreLogin = this.nomUsuario;
				Frame.elegirPantalla = "PantallaTablaPersonaje";
				this.dispose();
				Frame f = new Frame(this.rmi);
			}
		} else if(e.getSource() == comboBox_Tipo) {
			reiniciarProgressBar();
			actualizarImagenPersonaje();
			actualizarProgressBar("comboBox_Tipo");
		} else if(e.getSource() == comboBox_Yelmo) {
			reiniciarProgressBar();
			actualizarImagenPersonaje();
			actualizarProgressBar("comboBox_Yelmo");
		}else if(e.getSource() == comboBox_Armadura) {
			reiniciarProgressBar();
			actualizarImagenPersonaje();
			actualizarProgressBar("comboBox_Armadura");
		}else if(e.getSource() == comboBox_Brazalete) {
			reiniciarProgressBar();
			actualizarImagenPersonaje();
			actualizarProgressBar("comboBox_Brazalete");
		}else if(e.getSource() == comboBox_Botas) {
			reiniciarProgressBar();
			actualizarImagenPersonaje();
			actualizarProgressBar("comboBox_Botas");
		} else if(e.getSource() == btnCalcularHabilidades) {
			reiniciarProgressBar();
			calcularProgressBar();
		} else if(e.getSource() == btnBasura) {
			reiniciarProgressBar();
			reiniciarComboBoxes();
			actualizarImagenPersonaje();
		}
	}
	
	private void actualizarImagenPersonaje() {
		if(comboBox_Tipo.getSelectedItem().toString().equals("Elfo")) {
			ImageIcon elfoI = new ImageIcon("target\\classes\\img\\Elfo.png");
			elfo.setBounds(415, 21, 199, 609);
			elfo.setIcon(elfoI);
			
			//Yelmo
			if(comboBox_Yelmo.getSelectedItem().toString().equals("Yelmo Elfico")) {
				elfoI = new ImageIcon("target\\classes\\img\\ElfoC.png");
				elfo.setIcon(elfoI);
				ImageIcon cascoElfoI = new ImageIcon("target\\classes\\img\\casco_Elfo.png");
				cascoElfoElfo.setBounds(472, 18, 78, 119);
				cascoElfoElfo.setIcon(cascoElfoI);
			} else if(comboBox_Yelmo.getSelectedItem().toString().equals("Yelmo Daedrico")) {
				elfoI = new ImageIcon("target\\classes\\img\\ElfoC.png");
				elfo.setIcon(elfoI);
				ImageIcon cascoOrcoI = new ImageIcon("target\\classes\\img\\casco_Orco.png");
				cascoElfoElfo.setBounds(455, -14, 108, 187);
				cascoElfoElfo.setIcon(cascoOrcoI);
			}else if(comboBox_Yelmo.getSelectedItem().toString().equals("")) {
				elfoI = new ImageIcon("target\\classes\\img\\Elfo.png");
				elfo.setIcon(elfoI);
				reiniciarProgressBar();
				cascoElfoElfo.setIcon(new ImageIcon());
			}
			
			//Armadura
			if(comboBox_Armadura.getSelectedItem().toString().equals("Armadura Elfica")) {
				reiniciarProgressBar();
				ImageIcon coraElfoI = new ImageIcon("target\\classes\\img\\coraza_Elfo.png");
				coraElfoElfo.setBounds(409, 110, 209, 486);
				coraElfoElfo.setIcon(coraElfoI);
			} else if(comboBox_Armadura.getSelectedItem().toString().equals("Armadura Daedrica")) {
				reiniciarProgressBar();
				ImageIcon coraElfoI = new ImageIcon("target\\classes\\img\\coraza_Orco_Elfo.png");
				coraElfoElfo.setBounds(380, 66, 264, 533);
				coraElfoElfo.setIcon(coraElfoI);
			}else if(comboBox_Armadura.getSelectedItem().toString().equals("")) {
				coraElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}
			
			//Brazaletes
			if(comboBox_Brazalete.getSelectedItem().toString().equals("Brazaletes Elficos")) {
				reiniciarProgressBar();
				ImageIcon brazDerElfoI = new ImageIcon("target\\classes\\img\\brazoDer_Elfo.png");
				brazDerElfoElfo.setBounds(400, 241, 58, 163);
				brazDerElfoElfo.setIcon(brazDerElfoI);
				ImageIcon brazIzqElfoI = new ImageIcon("target\\classes\\img\\brazoIzq_Elfo.png");
				brazIzqElfoElfo.setBounds(568, 236, 59, 159);
				brazIzqElfoElfo.setIcon(brazIzqElfoI);
			} else if(comboBox_Brazalete.getSelectedItem().toString().equals("Brazaletes Daedricos")) {
				reiniciarProgressBar();
				ImageIcon brazDerElfoI = new ImageIcon("target\\classes\\img\\brazoDer_Orco.png");
				brazDerElfoElfo.setBounds(385, 242, 71, 166);
				brazDerElfoElfo.setIcon(brazDerElfoI);
				ImageIcon brazIzqElfoI = new ImageIcon("target\\classes\\img\\brazoIzq_Orco.png");
				brazIzqElfoElfo.setBounds(569, 235, 83, 172);
				brazIzqElfoElfo.setIcon(brazIzqElfoI);
			}else if(comboBox_Brazalete.getSelectedItem().toString().equals("")) {
				brazIzqElfoElfo.setIcon(new ImageIcon());
				brazDerElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}
			
			//Botas
			if(comboBox_Botas.getSelectedItem().toString().equals("Botas Elficas")) {
				ImageIcon botaDerElfoI = new ImageIcon("target\\classes\\img\\botaDer_Elfo.png");
		        botaDerElfoElfo.setBounds(400, 464, 95, 174);
		        botaDerElfoElfo.setIcon(botaDerElfoI);
		        ImageIcon botaIzqElfoI = new ImageIcon("target\\classes\\img\\botaIzq_Elfo.png");
		        botaIzqElfoElfo.setBounds(531, 468, 71, 164);
		        botaIzqElfoElfo.setIcon(botaIzqElfoI);
			} else if(comboBox_Botas.getSelectedItem().toString().equals("Botas Daedricas")) {
				ImageIcon botaDerElfoI = new ImageIcon("target\\classes\\img\\botaDer_Orco.png");
		        botaDerElfoElfo.setBounds(412, 475, 81, 167);
		        botaDerElfoElfo.setIcon(botaDerElfoI);
		        ImageIcon botaIzqElfoI = new ImageIcon("target\\classes\\img\\botaIzq_Orco.png");
		        botaIzqElfoElfo.setBounds(540, 490, 67, 149);
		        botaIzqElfoElfo.setIcon(botaIzqElfoI);
			}else if(comboBox_Botas.getSelectedItem().toString().equals("")) {
				botaIzqElfoElfo.setIcon(new ImageIcon());
				botaDerElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}
		} else if(comboBox_Tipo.getSelectedItem().toString().equals("Orco")) {
			ImageIcon orco = new ImageIcon("target\\classes\\img\\Orco.png");
			elfo.setBounds(397, 44, 227, 579);
			elfo.setIcon(orco);
			
			//Yelmo
			if(comboBox_Yelmo.getSelectedItem().toString().equals("Yelmo Elfico")) {
				ImageIcon cascoElfoI = new ImageIcon("target\\classes\\img\\casco_Elfo.png");
				cascoElfoElfo.setBounds(471, 13, 78, 119);
				cascoElfoElfo.setIcon(cascoElfoI);
			} else if(comboBox_Yelmo.getSelectedItem().toString().equals("Yelmo Daedrico")) {
				ImageIcon cascoOrcoI = new ImageIcon("target\\classes\\img\\casco_Orco.png");
				cascoElfoElfo.setBounds(455, -25, 108, 187);
				cascoElfoElfo.setIcon(cascoOrcoI);
			}else if(comboBox_Yelmo.getSelectedItem().toString().equals("")) {
				reiniciarProgressBar();
				cascoElfoElfo.setIcon(new ImageIcon());
			}

			//Armadura
			if(comboBox_Armadura.getSelectedItem().toString().equals("Armadura Elfica")) {
				reiniciarProgressBar();
				ImageIcon coraElfoI = new ImageIcon("target\\classes\\img\\coraza_Elfo_Orco.png");
				coraElfoElfo.setBounds(375, 89, 254, 484);
				coraElfoElfo.setIcon(coraElfoI);
			} else if(comboBox_Armadura.getSelectedItem().toString().equals("Armadura Daedrica")) {
				reiniciarProgressBar();
				ImageIcon coraElfoI = new ImageIcon("target\\classes\\img\\coraza_Orco.png");
				coraElfoElfo.setBounds(379, 51, 258, 535);
				coraElfoElfo.setIcon(coraElfoI);
			}else if(comboBox_Armadura.getSelectedItem().toString().equals("")) {
				coraElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}
		
			//Brazaletes
			if(comboBox_Brazalete.getSelectedItem().toString().equals("Brazaletes Elficos")) {
				reiniciarProgressBar();
				ImageIcon brazDerElfoI = new ImageIcon("target\\classes\\img\\brazoDer_Elfo.png");
				brazDerElfoElfo.setBounds(385, 227, 58, 163);
				brazDerElfoElfo.setIcon(brazDerElfoI);
				ImageIcon brazIzqElfoI = new ImageIcon("target\\classes\\img\\brazoIzq_Elfo.png");
				brazIzqElfoElfo.setBounds(577, 227, 59, 159);
				brazIzqElfoElfo.setIcon(brazIzqElfoI);
			} else if(comboBox_Brazalete.getSelectedItem().toString().equals("Brazaletes Daedricos")) {
				reiniciarProgressBar();
				ImageIcon brazDerElfoI = new ImageIcon("target\\classes\\img\\brazoDer_Orco.png");
				brazDerElfoElfo.setBounds(374, 232, 71, 166);
				brazDerElfoElfo.setIcon(brazDerElfoI);
				ImageIcon brazIzqElfoI = new ImageIcon("target\\classes\\img\\brazoIzq_Orco.png");
				brazIzqElfoElfo.setBounds(575, 225, 83, 172);
				brazIzqElfoElfo.setIcon(brazIzqElfoI);
			}else if(comboBox_Brazalete.getSelectedItem().toString().equals("")) {
				brazIzqElfoElfo.setIcon(new ImageIcon());
				brazDerElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}

			//Botas
			if(comboBox_Botas.getSelectedItem().toString().equals("Botas Elficas")) {
				ImageIcon botaDerElfoI = new ImageIcon("target\\classes\\img\\botaDer_Elfo.png");
		        botaDerElfoElfo.setBounds(404, 463, 95, 174);
		        botaDerElfoElfo.setIcon(botaDerElfoI);
		        ImageIcon botaIzqElfoI = new ImageIcon("target\\classes\\img\\botaIzq_Elfo.png");
		        botaIzqElfoElfo.setBounds(535, 464, 71, 164);
		        botaIzqElfoElfo.setIcon(botaIzqElfoI);
			} else if(comboBox_Botas.getSelectedItem().toString().equals("Botas Daedricas")) {
				ImageIcon botaDerElfoI = new ImageIcon("target\\classes\\img\\botaDer_Orco.png");
		        botaDerElfoElfo.setBounds(415, 463, 81, 167);
		        botaDerElfoElfo.setIcon(botaDerElfoI);
		        ImageIcon botaIzqElfoI = new ImageIcon("target\\classes\\img\\botaIzq_Orco.png");
		        botaIzqElfoElfo.setBounds(544, 476,67, 149);
		        botaIzqElfoElfo.setIcon(botaIzqElfoI);
			}else if(comboBox_Botas.getSelectedItem().toString().equals("")) {
				botaIzqElfoElfo.setIcon(new ImageIcon());
				botaDerElfoElfo.setIcon(new ImageIcon());
				reiniciarProgressBar();
			}
		}
	}
}