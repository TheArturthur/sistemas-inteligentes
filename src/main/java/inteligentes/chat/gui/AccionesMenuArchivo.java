package inteligentes.chat.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import inteligentes.chat.interfaces.ArchivoListener;


public class AccionesMenuArchivo implements ActionListener {
    protected ArchivoListener archivoListener;
    
    AccionesMenuArchivo(ArchivoListener archivoListener) {
        this.archivoListener=archivoListener;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (e.getSource() instanceof JMenuItem || e.getSource() instanceof JButton)
        {
            switch(e.getActionCommand().charAt(0))
            {
                case 'E': salir();
                    break;
                   
            }
        }
    }
    
    
    public void salir()
    {
        System.exit(0);
    }
}