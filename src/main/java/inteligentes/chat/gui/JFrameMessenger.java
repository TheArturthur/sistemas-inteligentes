package inteligentes.chat.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import inteligentes.chat.interfaces.*;



public class JFrameMessenger extends JFrame
{
    //para que no se queje con el warning
    public static final long serialVersionUID = 1L;

    //En el Frame situamos un panel que utilizaremos como contenedor
    protected JPanel jPanel;
    protected SendMessageListener sendMessageListener;
    
    public JFrameMessenger(String nombre, SendMessageListener sendMessageListener) {
    	super();
    	this.sendMessageListener=sendMessageListener;
        jPanel=new JPanelVisualMessenger(nombre, sendMessageListener);
        
        //setBackground(Color.blue);
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });
        
        setContentPane(jPanel);
    }
    
    public void salir() {
    	sendMessageListener.finalizar();
    }
}
