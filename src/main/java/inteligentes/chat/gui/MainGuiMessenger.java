package inteligentes.chat.gui;

import javax.swing.JFrame;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Dimension;

import inteligentes.chat.interfaces.*;


public class MainGuiMessenger extends Thread 
{
	//Un agente podr�a tener m�s de un interfaz
	//Utilizaremos un interfaz y un string para gestionar la informaci�n del hilo
	SendMessageListener sendMessageListener;
	String titulo;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainGuiMessenger(String titulo, SendMessageListener sendMessageListener) {
		this.titulo=titulo;
		this.sendMessageListener=sendMessageListener;
	}
	
	public void run() {

		//El interfaz se basar� en un contendor de tipo JFrame
		//Lo personalizamos para que pueda gestionar la informaci�n del hilo
        JFrame jFrame;
        jFrame=new JFrameMessenger(titulo, sendMessageListener);

        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd=ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Rectangle rectangle=gc.getBounds();
        
        //lo situamos a 1/5 parte de la parte superior izquierda y 
        //con un ancho de la 1/3 y alto de la 1/3 parte
        jFrame.setLocation((int)rectangle.getWidth()/7,(int)rectangle.getHeight()/7);
        jFrame.setSize(new Dimension((int)(rectangle.getWidth()/1.5f),(int)(rectangle.getHeight()/1.5f)));
        
        jFrame.setTitle(titulo);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
	}
}

