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

import inteligentes.chat.basics.EncodedMessage;
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
		if(mensaje==null) {return;}
		
		
		String[] lineas = mensaje.split("\n");
		StringBuilder msgbuilt = new StringBuilder();
		String toShow = "";

		if (lineas.length > 2) {
			for (int i = 0; i < lineas.length; i++) {
				msgbuilt.append(lineas[i].replace(" ", "&nbsp;") +"<br/>");
			}
			toShow = msgbuilt.toString();
		} else {
			toShow = mensaje;
		}
		
		mensajes=mensajes+"<b>"+persona+"</b>"+": <br/><p>"+toShow+"</p><br/>";
		jEditorPaneHistorico.setText("<html><body>"+mensajes+"</body></html>");
	}


	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	//Cada vez que se envia un mensaje, se envia al manager para que lo procese.
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
			String special = sendMessageListener.esComandoEspecial(jTextAreaMensaje.getText());
			if (special.length() > 0) {
				if (!special .equals("lista")) {
					addMensaje(nombre, special);
				}
				jTextAreaMensaje.setText("");

			} else {
				//Hacer un filtro rapido de emoji cogiendo la funcion de Edison
				String mensaje = sendMessageListener.esComando(jTextAreaMensaje.getText());
				addMensaje(nombre, mensaje);
				EncodedMessage em = new EncodedMessage();
				em.setFrom(nombre);
				em.setSendTo(amigo);
				em.setMessage(jTextAreaMensaje.getText());
				sendMessageListener.sendMsgToManager(em);
				jTextAreaMensaje.setText("");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}

