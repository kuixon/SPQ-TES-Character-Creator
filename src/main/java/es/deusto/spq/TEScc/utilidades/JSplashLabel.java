package es.deusto.spq.TEScc.utilidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public final class JSplashLabel extends JLabel
{
  private static final long serialVersionUID = -774117609679903588L;
  private String m_text = null;

  private Font m_font = null;

  private Color m_color = null;
  
  final Logger logger = LoggerFactory.getLogger(JSplashLabel.class);

  public JSplashLabel(String path, String s, Font f, Color c)
  {
    ImageIcon icon = new ImageIcon(path);
    if (icon.getImageLoadStatus() != 8) {
      logger.error("Cannot load splash screen: " + path);
      setText("Cannot load splash screen: " + path);
    } else {
      setIcon(icon);
      this.m_text = s;
      this.m_font = f;
      this.m_color = c;

      if (this.m_font != null)
        setFont(this.m_font);
    }
  }

  public JSplashLabel(URL url, String s)
  {
    ImageIcon icon = new ImageIcon(url);
    if (icon.getImageLoadStatus() != 8) {
      logger.error("Cannot load splash screen: " + url);
      setText("Cannot load splash screen: " + url);
    } else {
      setIcon(icon);
      this.m_text = s;
      if (this.m_font != null)
        setFont(this.m_font);
    }
  }

  public void paint(Graphics g)
  {
    super.paint(g);

    if (this.m_text != null) {
      if (this.m_color != null) {
        g.setColor(this.m_color);
      }

      FontMetrics fm = g.getFontMetrics();
      int width = fm.stringWidth(this.m_text) + 20;
      int height = fm.getHeight();

      g.drawString(this.m_text, getWidth() - width, getHeight() - height);
    }
  }
}