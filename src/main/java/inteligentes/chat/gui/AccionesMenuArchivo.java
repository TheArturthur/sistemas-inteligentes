package inteligentes.chat.gui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.auxiliar.SetEnvironment;
import inteligentes.chat.interfaces.ArchivoListener;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class AccionesMenuArchivo implements ActionListener {
	protected ArchivoListener archivoListener;

	AccionesMenuArchivo(ArchivoListener archivoListener) {
		this.archivoListener=archivoListener;
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
		String name = newAgentPopUp();
		if (!name.equals("superamigo27")) {
			try {
		    	
				AgentController ac;
				ac = SetEnvironment.cc.createNewAgent(name, AgenteCorreo.class.getName(), new Object[] { });
		    	ac.start();
			} catch (StaleProxyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private String newAgentPopUp() {
		return JOptionPane.showInputDialog(new JOptionPane(), "¿Como se va a llamar?", "superamigo27");
	}
}