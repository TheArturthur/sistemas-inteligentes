package inteligentes.chat.gui;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import inteligentes.chat.interfaces.*;



public class JPanelVisualMessenger extends JPanel implements ArchivoListener
{	
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
        //BARRA DE MENU
        jMenuBar=new JMenuBar();
        
        JMenu jMenuArchivo=new JMenu("File");
        jMenuArchivo.setMnemonic('F');
        
        //
        //ARCHIVO para escuchar lo que ocurre en la barra de manu
        //
        accionesMenuArchivo=new AccionesMenuArchivo(this);
        
		//A�adimos un tipo de item File al Menu
        JMenuItem jMenuItemSalir=new JMenuItem("Exit");
        //no poner letra minusculas que las prepara 'A' y no 'a'
        jMenuItemSalir.setAccelerator(KeyStroke.getKeyStroke('E', java.awt.Event.CTRL_MASK));
        //jMenuItemSalir.setIcon(new ImageIcon(JPanelVisualMessenger.class.getResource("/es/upm/ejercicioChat/icon/salir.png")));
        jMenuItemSalir.setActionCommand("E");
        jMenuItemSalir.setMnemonic('E');
        jMenuItemSalir.setToolTipText("Exit");
        jMenuItemSalir.addActionListener(accionesMenuArchivo);
        
        jMenuArchivo.add(jMenuItemSalir);
        
        //A�adimos el menu a la barra de menu
        jMenuBar.add(jMenuArchivo);
        
	}
	
	
	public void iniciarToolBar() {	
		jToolBar=new JToolBar();
        
		/*
        JButton jButtonSalir=new JButton();
        jButtonSalir.setToolTipText("Exit");
        jButtonSalir.setActionCommand("E");
        jButtonSalir.addActionListener(accionesMenuArchivo);

        jToolBar.add(jButtonSalir);
        */
	}
	
	public void iniciarPanel(String nombre, SendMessageListener sendMessageListener) {
		jPanelPrincipalMessenger=new JPanelPrincipalMessenger(nombre, sendMessageListener);
        setLayout(new BorderLayout());
        add(jMenuBar, BorderLayout.NORTH);
        add(jPanelPrincipalMessenger, BorderLayout.CENTER);
	}


	@Override
	public void salir() {
	}

}
