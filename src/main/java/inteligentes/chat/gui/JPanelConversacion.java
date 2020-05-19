package inteligentes.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
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
	private String coord;
	
	public JPanelConversacion(String nombre, String amigo, SendMessageListener sendMessageListener) {
		this.sendMessageListener=sendMessageListener;
		this.nombre=nombre;
		this.amigo=amigo;
		//TODO
		this.coord = "manager";
		
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
	
	public boolean offensiveMessagePopUp() {
	      Object[] options = {"Yes, im sure.",
          "Not send the message"};
	      int seleccion = JOptionPane.showOptionDialog(
	    		   this,
	    		   "Seleccione opcion", 
	    		   "Mensaje de alerta",
	    		   JOptionPane.YES_NO_CANCEL_OPTION,
	    		   JOptionPane.QUESTION_MESSAGE,
	    		   null,    // null para icono por defecto.
	    		   options,   // null para YES, NO y CANCEL
	    		   "opcion 1");
    
    return true;

	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	//Cada vez que se envia un mensaje, se envia al coordinador para que lo desvie y se procese.
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
			addMensaje(nombre, jTextAreaMensaje.getText());
			EncodedMessage em = new EncodedMessage();
			em.setFrom(nombre);
			em.setSendTo(amigo);
			em.setMessage(jTextAreaMensaje.getText());
			em.setMessageListener(sendMessageListener);
			em.setConver(this);
			sendMessageListener.enviarEncodedMensaje(coord, em);
			jTextAreaMensaje.setText("");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}

