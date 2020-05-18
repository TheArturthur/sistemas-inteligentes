package inteligentes.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import inteligentes.chat.interfaces.*;


public class JPanelConversacion extends JPanel implements KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JScrollPane jScrollPanelHistorico;
	protected JEditorPane jEditorPaneHistorico;
	protected JTextArea jTextAreaMensaje;
	protected JSplitPane jSplitPane;
	protected String mensajes;
	protected SendMessageListener sendMessageListener;
	protected String nombre;
	protected String amigo;
	
	public JPanelConversacion(String nombre, String amigo, SendMessageListener sendMessageListener) {
		this.sendMessageListener=sendMessageListener;
		this.nombre=nombre;
		this.amigo=amigo;
		
		jEditorPaneHistorico=new JEditorPane();
		jEditorPaneHistorico.setContentType("text/html");
		jEditorPaneHistorico.setBackground(Color.gray);
		jEditorPaneHistorico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jEditorPaneHistorico.setEditable(false);
		jScrollPanelHistorico=new JScrollPane(jEditorPaneHistorico);
		jTextAreaMensaje=new JTextArea(3, 0);
		jTextAreaMensaje.setLineWrap(true);
		jTextAreaMensaje.addKeyListener(this);
		jTextAreaMensaje.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jTextAreaMensaje.setFocusable(true);

		
		jSplitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jScrollPanelHistorico, jTextAreaMensaje);
		jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerLocation(1.0);
        jSplitPane.setResizeWeight(1.0); //el espacio extra para el jscrollpane
        
        setLayout(new BorderLayout());
        add(jSplitPane, BorderLayout.CENTER);
        
        mensajes="";
	}
	
	public void addMensaje(String persona, String mensaje) {
		if(mensaje==null)
			return;
		mensajes=mensajes+"<b>"+persona+"</b>"+": "+mensaje+"<br/>";
		jEditorPaneHistorico.setText("<html><body>"+mensajes+"</body></html>");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
			addMensaje(nombre, jTextAreaMensaje.getText());
			sendMessageListener.enviarMensaje(amigo, jTextAreaMensaje.getText());
			jTextAreaMensaje.setText("");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

