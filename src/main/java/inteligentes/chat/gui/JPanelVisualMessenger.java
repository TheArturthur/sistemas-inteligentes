package inteligentes.chat.gui;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import inteligentes.chat.interfaces.*;



public class JPanelVisualMessenger extends JPanel implements ArchivoListener{	
	private static final long serialVersionUID = 1L;
	
	
	protected JMenuBar jMenuBar;
	protected JToolBar jToolBar;
	protected JPanel jPanelPrincipal;
	protected AccionesMenuArchivo accionesMenuArchivo;
	protected JPanelPrincipalMessenger jPanelPrincipalMessenger;
	
	
	public JPanelVisualMessenger(String nombre, SendMessageListener sendMessageListener){
		super();
		
		iniciarInterfaz(nombre, sendMessageListener);
	}
	
	/**
	 * Crea la distribuci�n de los elemento en el interfaz gr�fico
	 */
	public void iniciarInterfaz(String nombre, SendMessageListener sendMessageListener) {
		//Utlizamos un layout del tipo BorderLayout
		BorderLayout borderLayout;
		borderLayout=new BorderLayout();
		setLayout(borderLayout);
		
		//A�adimos un contenedor de tipo JPanel 
		jPanelPrincipal=new JPanel();
		jPanelPrincipal.setLayout(new BorderLayout());
		add(jPanelPrincipal, BorderLayout.CENTER);
		
		//A�adimos una Barra de Men�
		iniciarBarraMenu();
        add(jMenuBar, BorderLayout.NORTH);
        
        //A�adimos una Barra de Herramientas
		iniciarToolBar();
		jPanelPrincipal.add(jToolBar, BorderLayout.NORTH);
		
		//Incializamos el contenido del Panel contenedor
		iniciarPanel(nombre, sendMessageListener);
	}
	
	
	public void iniciarBarraMenu() {
        jMenuBar = new JMenuBar();
    
        accionesMenuArchivo = new AccionesMenuArchivo(this);
        
        JMenuItem newfriend = new JMenuItem("Crea un nuevo amigo!");
        newfriend.addActionListener(accionesMenuArchivo);
        
        jMenuBar.add(newfriend);
        
	}
	
	
	public void iniciarToolBar() {	
		jToolBar=new JToolBar();
	}
	
	public void iniciarPanel(String nombre, SendMessageListener sendMessageListener) {
		jPanelPrincipalMessenger=new JPanelPrincipalMessenger(nombre, sendMessageListener);
        setLayout(new BorderLayout());
        add(jMenuBar, BorderLayout.NORTH);
        add(jPanelPrincipalMessenger, BorderLayout.CENTER);
	}


}
