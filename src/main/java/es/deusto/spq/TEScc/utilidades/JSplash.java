package es.deusto.spq.TEScc.utilidades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public final class JSplash extends JWindow
{
  private static final long serialVersionUID = 5029462743623964649L;
  private final JProgressBar m_progress = new JProgressBar();

  private boolean m_progressBar = false;

  private boolean m_progressBarMessages = false;

  private boolean m_progressBarPercent = false;

  public JSplash(String path, boolean progress, boolean messages, boolean percent, String versionString, Font versionStringFont, Color versionStringColor, Color progressbarColor)
  {
    this.m_progress.setForeground(progressbarColor);
    this.m_progressBar = progress;
    this.m_progressBarMessages = messages;
    this.m_progressBarPercent = percent;

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setContentPane(panel);

    JSplashLabel label = new JSplashLabel(path, versionString, 
      versionStringFont, versionStringColor);

    if (this.m_progressBar) {
      if ((this.m_progressBarMessages) || (this.m_progressBarPercent))
        this.m_progress.setStringPainted(true);
      else {
        this.m_progress.setStringPainted(false);
      }

      if ((this.m_progressBarMessages) && (!(this.m_progressBarPercent))) {
        this.m_progress.setString("");
      }

      this.m_progress.setMaximum(100);
      this.m_progress.setMinimum(0);
      this.m_progress.setValue(0);
    }

    getContentPane().add(label, "Center");

    if (this.m_progressBar) {
      getContentPane().add(this.m_progress, "South");
    }

    pack();

    GuiUtility.centerOnScreen(this);

    setVisible(false);
  }

  public JSplash(URL url, boolean progress, boolean messages, boolean percent, String versionString, Color progressbarColor)
  {
    this.m_progress.setForeground(progressbarColor);
    this.m_progress.setBackground(Color.BLACK);
    this.m_progressBar = progress;
    this.m_progressBarMessages = messages;
    this.m_progressBarPercent = percent;

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setContentPane(panel);

    JSplashLabel label = new JSplashLabel(url, versionString);

    if (this.m_progressBar) {
      if ((this.m_progressBarMessages) || (this.m_progressBarPercent))
        this.m_progress.setStringPainted(true);
      else {
        this.m_progress.setStringPainted(false);
      }

      if ((this.m_progressBarMessages) && (!(this.m_progressBarPercent))) {
        this.m_progress.setString("");
      }

      this.m_progress.setMaximum(100);
      this.m_progress.setMinimum(0);
      this.m_progress.setValue(0);
    }

    getContentPane().add(label, "Center");

    if (this.m_progressBar) {
      getContentPane().add(this.m_progress, "South");
    }

    pack();

    GuiUtility.centerOnScreen(this);

    setVisible(false);
  }

  public JSplash(URL url, boolean progress, boolean messages, boolean percent, String versionString)
  {
    this.m_progressBar = progress;
    this.m_progressBarMessages = messages;
    this.m_progressBarPercent = percent;

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setContentPane(panel);

    JSplashLabel label = new JSplashLabel(url, versionString);

    if (this.m_progressBar) {
      if ((this.m_progressBarMessages) || (this.m_progressBarPercent))
        this.m_progress.setStringPainted(true);
      else {
        this.m_progress.setStringPainted(false);
      }

      if ((this.m_progressBarMessages) && (!(this.m_progressBarPercent))) {
        this.m_progress.setString("");
      }

      this.m_progress.setMaximum(100);
      this.m_progress.setMinimum(0);
      this.m_progress.setValue(0);
    }

    getContentPane().add(label, "Center");

    if (this.m_progressBar) {
      getContentPane().add(this.m_progress, "South");
    }

    pack();

    GuiUtility.centerOnScreen(this);

    setVisible(false);
  }

  public void splashOn()
  {
    setVisible(true);
  }

  public void splashOff()
  {
    setVisible(false);
    dispose();
  }

  public void setProgress(int value)
  {
    if ((this.m_progressBar) && (value >= 0) && (value <= 100))
      this.m_progress.setValue(value);
  }

  public void setProgress(int value, String msg)
  {
    setProgress(value);

    if ((this.m_progressBarMessages) && (!(this.m_progressBarPercent)) && (msg != null))
      this.m_progress.setString(msg);
  }

  public final JProgressBar getProgressBar()
  {
    return this.m_progress;
  }
}