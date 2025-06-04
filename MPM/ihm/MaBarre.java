package MPM.ihm;

import MPM.Controleur;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;



public class MaBarre extends JMenuBar implements ActionListener
{
	private JMenuItem     menuiImporter;
	private JMenuItem     menuiQuitter ;
	private JMenuItem     menuiNouveau ;
	

	private FrameMPM      frame;
	private Controleur    ctrl;

	private JFileChooser fileChooser = new JFileChooser();


	public MaBarre(FrameMPM frame, Controleur ctrl)
	{
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.frame = frame;
		this.ctrl  = ctrl;

		this.fileChooser.setCurrentDirectory(new File(this.fileChooser.getCurrentDirectory() + "/Documents/Exo_SAE2.1/class/MPM/donnee") );


		// un element de la barre de menu
		JMenu menuFichier = new JMenu("Fichier"  );

		// les items du menu FichierAnnuler
		this.menuiNouveau  = new JMenuItem ("Nouveau Projet");
		this.menuiImporter = new JMenuItem ("Importer"      );
		this.menuiQuitter  = new JMenuItem ("Quitter"       );
		
		// Raccourci 
		menuFichier       .setMnemonic('F');
		this.menuiImporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , InputEvent.CTRL_DOWN_MASK) );
		this.menuiQuitter .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK ) );
	
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		// menu Fichier
		menuFichier.add( this.menuiNouveau );
		menuFichier.add( this.menuiImporter );
		menuFichier.addSeparator();
		menuFichier.add( this.menuiQuitter );

		// ajout du menu 'Fichier' a la barre de menu

		this.add( menuFichier );

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiImporter .addActionListener(this);
		this.menuiQuitter.addActionListener(this);		

	}

	public void actionPerformed ( ActionEvent e )
	{
		if(e.getSource() == this.menuiImporter)
		{
			this.frame.reinitialiser();
			this.fileChooser.setCurrentDirectory(this.fileChooser.getCurrentDirectory() );

			int retour = fileChooser.showOpenDialog(this.frame); 
			if(retour == JFileChooser.APPROVE_OPTION)
			{
				File fichier  = fileChooser.getSelectedFile();

				System.out.print("Nom du fichier : " + fichier.getName() + "\n");
				
				
				String path   = fichier.getPath(); 

				String ext = fichier.getName().substring(fichier.getName().lastIndexOf(".")); 
			
				// Vérification de l'extension du fichier
				// Si le fichier n'a pas l'extension .txt, on affiche un message d'erreur
				if (!ext.equals(".txt") )
				{
					JOptionPane.showMessageDialog(this.frame, "Le fichier doit avoir l'extension .txt", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else // Sinon on lit le fichier
				{
					this.ctrl.lireFichier(path);
					this.frame.activerBoutons();
					this.frame.majList();
				}
				

				//System.out.println("Path : " + path);
				
				//System.out.println("lecture du fichier...");
				//System.out.println("Affichage du projet : \n" + this.ctrl.afficherProjet());
				
			} 
		}

		if(e.getSource() == this.menuiQuitter) { System.exit(0); }

	}
}

