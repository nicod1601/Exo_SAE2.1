package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;



public class MaBarre extends JMenuBar implements ActionListener
{
	private JMenuItem     menuiImporter ;
	private JMenuItem     menuiRefresh  ;
	private JMenuItem     menuiQuitter  ;
	private JMenuItem     menuiNouveau  ;
	private JMenuItem     menuiSupprimer;
	

	private FrameMPM      frame;
	private Controleur    ctrl;

	private JFileChooser fileChooser = new JFileChooser();
	private String cheminFichier;


	public MaBarre(FrameMPM frame, Controleur ctrl)
	{
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.frame = frame;
		this.ctrl  = ctrl;

		this.fileChooser.setCurrentDirectory(new File(this.fileChooser.getCurrentDirectory() + "/Documents/Exo_SAE2.1/class/MPM/donnee") );
		this.cheminFichier = "";


		// un element de la barre de menu
		JMenu menuFichier = new JMenu("Fichier"  );

		// les items du menu FichierAnnuler
		this.menuiNouveau  = new JMenuItem ("Nouveau Projet");
		this.menuiRefresh  = new JMenuItem ("Actualiser"    );
		this.menuiImporter = new JMenuItem ("Importer"      );
		this.menuiSupprimer= new JMenuItem ("Supprimer"     );
		this.menuiQuitter  = new JMenuItem ("Quitter"       );
		
		// Raccourci 
		menuFichier       .setMnemonic('F');
		this.menuiNouveau .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N , InputEvent.CTRL_DOWN_MASK) );
		this.menuiImporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I , InputEvent.CTRL_DOWN_MASK) );
		this.menuiQuitter .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK ) );
		this.menuiRefresh .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R , InputEvent.CTRL_DOWN_MASK) );
	
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		// menu Fichier
		menuFichier.add( this.menuiNouveau );
		menuFichier.add( this.menuiImporter );
		menuFichier.add( this.menuiRefresh );
		menuFichier.add( this.menuiSupprimer );
		menuFichier.addSeparator();
		menuFichier.add( this.menuiQuitter );

		// ajout du menu 'Fichier' a la barre de menu

		this.add( menuFichier );

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiImporter.addActionListener(this);
		this.menuiQuitter .addActionListener(this);	
		this.menuiNouveau .addActionListener(this);	
		this.menuiRefresh .addActionListener(this);

	}

	public void refresh(String lien)
	{
		if(!lien.equals(""))
		{
			this.frame.reinitialiser();
			this.ctrl.lireFichier(lien);
			this.frame.majList();
			this.frame.activerBoutons();
		}

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
					this.cheminFichier = path;
					this.ctrl.lireFichier(path);
					this.frame.majList();
					this.verification(); // Vérification des erreurs dans le projet
					this.frame.activerBoutons();
					
				}
				

				//System.out.println("Path : " + path);
				
				//System.out.println("lecture du fichier...");
				//System.out.println("Affichage du projet : \n" + this.ctrl.afficherProjet());
				
			} 
		}

		if(e.getSource() == this.menuiQuitter) { System.exit(0); }

		if(e.getSource() == this.menuiNouveau)
		{
			this.frame.setVisibleFrameNouveau();
		}

		if(e.getSource() == this.menuiRefresh)
		{
			this.refresh(this.cheminFichier);
		}

	}

	/**
	 * Vérifie s'il y a des erreurs dans le projet et affiche un message d'erreur si nécessaire.
	 */
	private void verification()
	{
		ArrayList<Erreur> erreur = this.ctrl.getErreur();
		if (erreur != null && !erreur.isEmpty())
		{
			for (int code = 0; code <= 4; code++)
			{ // adapte la borne max si besoin
				String message = "";
				for (Erreur err : erreur)
				{
					if (err.getCodeErreur() == code)
					{
						message += err.getMessage() + "\n";
					}
				}
				if (!message.isEmpty())
				{
					int type = (code == 1) ? JOptionPane.WARNING_MESSAGE : JOptionPane.ERROR_MESSAGE;
					JOptionPane.showMessageDialog(this.frame, message, "Code erreur " + code, type);
				}
			}
		}
	}
}

