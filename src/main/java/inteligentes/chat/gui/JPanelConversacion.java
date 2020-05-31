package inteligentes.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import inteligentes.chat.gui.*;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.interfaces.*;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;


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
	//
	private JButton button;
	private final Action action = new SwingAction();
	private JFrameMessenger jf;
	private JLabel jl;
	private JButton button2;
	private final Action action2 = new SwingAction();
	
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
		
		button = new JButton("ReportUser");
		button.setAction(action);
		jScrollPanelHistorico.setColumnHeaderView(button);
		jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerLocation(1.0);
        jSplitPane.setResizeWeight(1.0); //el espacio extra para el jscrollpane
        
        setLayout(new BorderLayout());
        add(jSplitPane, BorderLayout.CENTER);
        
        mensajes="";
        //button
        //button.setBounds(12, 444, 143, 33);
	}

	public void addMensaje(String persona, String mensaje) {
		if(mensaje==null)
			return;
		mensajes=mensajes+"<b>"+persona+"</b>"+": "+mensaje+"<br/>";
		jEditorPaneHistorico.setText("<html><body>"+mensajes+"</body></html>");
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	//Cada vez que se envia un mensaje, se envia al manager para que lo procese.
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
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

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "ReportUser");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		       JFrame jFrame =new JFrameMessenger("ReportUser", sendMessageListener);
		        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		       // GraphicsDevice gd=ge.getDefaultScreenDevice();
		       // GraphicsConfiguration gc = gd.getDefaultConfiguration();
		        jFrame.setSize(20, 20);
		        
		        //lo situamos a 1/5 parte de la parte superior izquierda y 
		        //con un ancho de la 1/3 y alto de la 1/3 parte
		        jFrame.setTitle("ReportUser");
		        jFrame.setVisible(true);
		        JLabel jl = new JLabel();
		        jl.setText("Seguro que quieres reportar?");
		        jFrame.add(jl);
		        button2 = new JButton("yes");
				button2.setAction(action2);
		        
		        
		}
	}
}
