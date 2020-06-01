package inteligentes.chat.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import inteligentes.chat.interfaces.*;


public class JPanelPrincipalMessenger extends JPanel implements MouseListener, MostrarMensajesListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JSplitPane jSplitPane;
	protected JList<String> jList;
	protected JTabbedPane jTabbedPane;
	protected Hashtable<String, JPanelConversacion> hashTable;
	protected SendMessageListener sendMessageListener;
	protected String nombre;
	
	public JPanelPrincipalMessenger(String nombre, SendMessageListener sendMessageListener) {
//Problema en Swing al parsear el panel
		//Avisamos de que estamos a�adiendo un nuevo listener para detectar cuando hay que mostrar mensajes
		sendMessageListener.avisarEventos(this);
		
		this.sendMessageListener=sendMessageListener;
		this.nombre=nombre;
		jList=new JList<String>();
		jList.addMouseListener(this);
		jTabbedPane=new JTabbedPane();
		jSplitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jTabbedPane, jList);
		jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerLocation(1.0);
        jSplitPane.setResizeWeight(1.0); //el espacio extra para el jscrollpane
        
        setLayout(new BorderLayout());
        add(jSplitPane, BorderLayout.CENTER);
        
        //listaUsuarios(new String[]{"User1", "User2"});
        
        //Creamos una tabla Hash para llevar el control de las conversaciones (pesta�as)
        hashTable=new Hashtable<String, JPanelConversacion>();
	}

	//El mensaje se muestra en el panel de visualizaci�n
	//En caso de que no hubiese una pesta�a creada todav�a, la creo 
	public void addMensaje(String amigo, String mensaje) {
		//Comprobamos si la pesta�a ya estaba creada, en caso contrario habr� que crearla
		if(hashTable.get(amigo)!=null && jTabbedPane.indexOfComponent(hashTable.get(amigo))!=-1)
			hashTable.get(amigo).addMensaje(amigo, mensaje);
		else
			addPestana(amigo, mensaje);;
	}
	
	//Se crea una nueva pesta�a de conversaci�n 
	//Al crear la pesta�a mostramos un mensaje
	public void addPestana(String remitente, String mensaje) {
		JPanelConversacion jPanelConversacion;
		
		//En caso de que no existiese una pesta�a para el remitente, se crea una nueva pesta�a
		//En caso de que ya existiese una pesta�a de conversaci�n con el remitente, se recupera
		if(hashTable.get(remitente)==null)
			jPanelConversacion=new JPanelConversacion(nombre, remitente, sendMessageListener);
		else
			jPanelConversacion=hashTable.get(remitente);
		
		//Se muestra la pesta�a como pesta�a activa y se muestra el mensaje recibido
    	jTabbedPane.add(jPanelConversacion);
    	jTabbedPane.setSelectedComponent(jPanelConversacion);
    	jTabbedPane.setTabComponentAt(jTabbedPane.indexOfComponent(jPanelConversacion), new ButtonTabComponent(remitente, jTabbedPane));
    	jPanelConversacion.addMensaje(remitente, mensaje);
    	
    	hashTable.put(remitente, jPanelConversacion);

	}
	
	public void listaUsuarios(String[] usuarios) {
		jList.setListData(usuarios);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2)
		{  
			addMensaje(jList.getSelectedValue(), null);
		}  
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usuariosListener(String[] usuarios) 
	{
		// TODO Auto-generated method stub
		//System.out.println("Me han llegado nuevos usuarios");
		jList.setListData(usuarios);
	}

	@Override
	public void nuevoMensaje(String remitente, String mensaje) {
		// TODO Auto-generated method stub
		addMensaje(remitente, mensaje);
	}
}
