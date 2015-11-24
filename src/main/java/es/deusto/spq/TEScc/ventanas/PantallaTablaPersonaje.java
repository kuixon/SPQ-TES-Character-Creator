package es.deusto.spq.TEScc.ventanas;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.servidor.DAODatabase;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.utilidades.JSplashLabel;
import es.deusto.spq.TEScc.utilidades.UtilidadesGUI;

public class PantallaTablaPersonaje extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JButton btnEliminar;
	 private JButton btnEditar;
	 private JButton b_retroceso;
	 private JLabel lblFondo;
	 private JTable table;
	 private DefaultTableModel dtm;
	 private JScrollPane scrollPane;
	 private RMIServiceLocator rmi;
	 private String nombre;
	 private JFrame actual;
	 private JPanel estePanel = this;
	 private String nomPersonaje = "";
	 
	 final Logger logger = LoggerFactory.getLogger(PantallaTablaPersonaje.class);

	public PantallaTablaPersonaje(final String nombre, final RMIServiceLocator rmi) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setLayout(null);
		
		this.rmi = rmi;
		this.nombre = nombre;
		
		btnEliminar = new JButton("Eliminar");
		b_retroceso = new JButton();
		lblFondo = new JLabel();
		
		btnEliminar.setBounds(147, 293, 89, 23);
		add(btnEliminar);
		btnEliminar.setEnabled(false);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(400, 293, 89, 23);
		add(btnEditar);
		btnEditar.setEnabled(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 69, 536, 182);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
			{
				int fila = table.rowAtPoint(e.getPoint());
				if (fila > -1) {
					nomPersonaje = (String) dtm.getValueAt(fila, 0);
					logger.info("Personaje actual: " + nomPersonaje);
					btnEliminar.setEnabled(true);
					btnEditar.setEnabled(true);
				}		         
			}
		});
		
		add(b_retroceso);
        b_retroceso.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\retroceso.png"));
        b_retroceso.setBounds(10, 10, 40, 40);
		
		lblFondo.setIcon(new javax.swing.ImageIcon("target\\classes\\img\\fondo.jpg"));
        add(lblFondo);
        lblFondo.setBounds(0, 0, 629, 359);
		
		//Añadir funcionalidad a los botones.
        b_retroceso.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnEditar.addActionListener(this);
        
        //Para que se vea el panel.
	    this.setVisible(true);
      		
	    //Para que se habra con este tama�o como m�nimo.
	    this.setMinimumSize(new Dimension(629, 359));
	    
	    cargarTabla(this.nombre);
	}
	
	public void cargarTabla(String nombre){
		 
	    dtm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	   
		dtm.addColumn("Nombre");
		dtm.addColumn("Ataque");
		dtm.addColumn("Defensa");
		dtm.addColumn("Fecha Modificacion");

	 
	    Set<PersonajeDTO> arrayPers = null;
		try {
			arrayPers = ControllerTES.getInstance(rmi).getPersonajesPorUsuario(nombre);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	    for(Iterator<PersonajeDTO> i = arrayPers.iterator(); i.hasNext();) {
			
	    	PersonajeDTO per = i.next();
			
			Object[] visualizar = new Object[4];
			
			visualizar[0]=per.getApodo();
			visualizar[1]=per.getAtaque();
			visualizar[2]=per.getDefensa();
			visualizar[3]=per.getFechaUltMod();
			
			dtm.addRow(visualizar);
		}
	    
	    
	    table.setModel(dtm);
	    
	    scrollPane.setVisible(true);
	    table.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == b_retroceso) {
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.getContentPane().remove(0);
			actual.getContentPane().add(new PantallaLogin(nombre, this.rmi));
			actual.setPreferredSize(new Dimension(629, 359));
			actual.pack();
			actual.repaint();
			actual.setLocationRelativeTo(null);
		} else if(e.getSource() == btnEditar) {
			actual = (JFrame) UtilidadesGUI.getContenedorPrincipal(estePanel);
			actual.dispose();
			MenuPersonaje mp = new MenuPersonaje("PantallaTablaPersonaje", nombre, nomPersonaje, this.rmi);
		}else if(e.getSource() == btnEliminar) {
			try {
				ControllerTES.getInstance(rmi).deletePersonaje(nomPersonaje);
				JOptionPane.showMessageDialog(this, "¡"+nomPersonaje+" ha sido eliminado!", "Personaje eliminado", JOptionPane.INFORMATION_MESSAGE);
				cargarTabla(nombre);
				btnEliminar.setEnabled(false);
				btnEditar.setEnabled(false);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
