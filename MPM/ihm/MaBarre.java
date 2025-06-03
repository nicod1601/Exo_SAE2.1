package MPM.ihm;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;

import MPM.Controleur;
import MPM.metier.*;



public class MaBarre extends JMenuBar implements ActionListener
{
	private JMenuItem     menuiOuvrir ;
	private JMenuItem     menuiQuitter;
	

	private FrameMPM      frame;
	private Controleur    ctrl;


	public MaBarre(FrameMPM frame, Controleur ctrl)
	{
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.frame = frame;
		this.ctrl  = ctrl;

		// un element de la barre de menu
		JMenu menuFichier = new JMenu("Fichier"  );

		// les items du menu FichierAnnuler
		this.menuiOuvrir  = new JMenuItem ("Ouvrir" );
		this.menuiQuitter = new JMenuItem ("Quitter");
		
		// Raccourci 
		menuFichier       .setMnemonic('F');
		this.menuiOuvrir  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , InputEvent.CTRL_DOWN_MASK) );
		this.menuiQuitter .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK ) );
	
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		// menu Fichier
		menuFichier.add( this.menuiOuvrir );
		menuFichier.add( this.menuiQuitter );

		// ajout du menu 'Fichier' a la barre de menu

		this.add( menuFichier );

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiOuvrir .addActionListener(this);
		this.menuiQuitter.addActionListener(this);		

	}

	public void actionPerformed ( ActionEvent e )
	{
		if(e.getSource() == this.menuiOuvrir)
		{
			JFileChooser fileChooser = new JFileChooser();
			int retour = fileChooser.showOpenDialog(this.frame); 
			if(retour == JFileChooser.APPROVE_OPTION)
			{
				File fichier  = fileChooser.getSelectedFile();
				
				String path   = fichier.getPath(); 

				//System.out.println("Path : " + path);
				this.ctrl.lireFichier(path);
				//System.out.println("lecture du fichier...");
				//System.out.println("Affichage du projet : \n" + this.ctrl.afficherProjet());
				
				this.frame.majList();
			} 
		}

		if(e.getSource() == this.menuiQuitter) { System.exit(0); }

	}
}

