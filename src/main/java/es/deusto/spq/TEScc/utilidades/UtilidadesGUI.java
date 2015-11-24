package es.deusto.spq.TEScc.utilidades;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class UtilidadesGUI {

	public static Container getContenedorPrincipal(Component c) {
		if (c == null)
			return null;
		Container ret = c.getParent();
		while (ret != null) {
			c = ret;
			ret = ret.getParent();
		}
		return (Container) c;
	}
}