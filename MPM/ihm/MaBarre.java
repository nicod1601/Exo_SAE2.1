package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;



public class MaBarre extends JMenuBar implements ActionListener
{
	private JMenuItem     menuiImporter   ;
	private JMenuItem     menuiRefresh    ;
	private JMenuItem     menuiQuitter    ;
	private JMenuItem     menuiNouveau    ;
	private JMenuItem     menuiSaveAs     ;
	private JMenuItem     menuiOption     ;
	private JMenuItem     menuiSave       ;
	

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


		// les items du menu Fichier
		this.menuiNouveau         = new JMenuItem ("Ajouter Tache"    );
		this.menuiRefresh         = new JMenuItem ("Actualiser"       );
		this.menuiImporter        = new JMenuItem ("Importer"         );
		this.menuiSaveAs          = new JMenuItem ("Enregistrer sous" );
		this.menuiSave            = new JMenuItem ("Enregistrer"      );
		this.menuiQuitter         = new JMenuItem ("Quitter"          );
		this.menuiOption          = new JMenuItem ("Options"          );

		
		// Raccourci 
		menuFichier        .setMnemonic('F');
		this.menuiNouveau  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A , InputEvent.CTRL_DOWN_MASK) );
		this.menuiImporter .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I , InputEvent.CTRL_DOWN_MASK) );
		this.menuiQuitter  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK ) );
		this.menuiRefresh  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R , InputEvent.CTRL_DOWN_MASK) );
		this.menuiSaveAs   .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , InputEvent.CTRL_DOWN_MASK) );
		this.menuiSave     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , InputEvent.CTRL_DOWN_MASK) );


		if(this.cheminFichier.equals("") || this.cheminFichier == null)
		{
			this.menuiSave.setEnabled(false);
			this.menuiSaveAs.setEnabled(false);
			this.menuiOption.setEnabled(false);
		}
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		// menu Fichier
		menuFichier.add( this.menuiNouveau );
		menuFichier.add( this.menuiRefresh );
		menuFichier.addSeparator();
		menuFichier.add( this.menuiImporter );
		menuFichier.add( this.menuiSaveAs );
		menuFichier.add( this.menuiSave );
		menuFichier.add( this.menuiOption);
		menuFichier.addSeparator();
		menuFichier.add( this.menuiQuitter );

		this.add( menuFichier );

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiImporter .addActionListener(this);
		this.menuiQuitter  .addActionListener(this);	
		this.menuiNouveau  .addActionListener(this);	
		this.menuiRefresh  .addActionListener(this);
		this.menuiSaveAs.addActionListener(this);
		this.menuiOption.addActionListener(this);
		this.menuiSave.addActionListener(this);

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

	public void activerLesOption()
	{
		this.menuiSaveAs.setEnabled(true);
		this.menuiSave.setEnabled(true);
		this.menuiOption.setEnabled(true);
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
					this.ctrl.getErreur().clear(); // On vide la liste des erreurs
					if(!fichier.exists())
					{
						this.ctrl.getErreur().add(new Erreur(7));
						this.verification();
					}
					else
					{

						this.frame.setLien(this.cheminFichier);
						this.ctrl.lireFichier(this.cheminFichier);
						this.verification();
						this.frame.majList();
						this.frame.activerBoutons();
						this.activerLesOption();
					}
					
				}
				
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

		if(e.getSource() == this.menuiOption)
		{
			this.frame.setVisibleFrameOption();
		}

		if(e.getSource() == this.menuiSave)
		{
			File fichier  = this.fileChooser.getSelectedFile();
			String path   = fichier.getPath();
				

			try 
			{
				this.ctrl.enregistrer(this.cheminFichier, this.ctrl.getListeTache());
				this.frame.setLien(this.cheminFichier);
				this.ctrl.lireFichier(this.cheminFichier);
				this.verification();
				this.frame.majList();
				this.frame.activerBoutons();
				JOptionPane.showMessageDialog(this.frame, "Projet enregistré  " + path, "Enregistrement", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (Exception e1) 
			{
				JOptionPane.showMessageDialog(this.frame, "Problème lors de l'enregistrement", "Enregistrement", JOptionPane.ERROR_MESSAGE);
			}
		}

		if(e.getSource() == this.menuiSaveAs)
		{
			this.fileChooser.setCurrentDirectory(this.fileChooser.getCurrentDirectory() );
			fileChooser.setDialogTitle("Enregistrer sous");
			this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
			int retour = this.fileChooser.showSaveDialog(this.frame); 


			if(retour == JFileChooser.APPROVE_OPTION)
			{
				File fichier  = this.fileChooser.getSelectedFile();
				String path   = fichier.getPath();

				this.cheminFichier = path;
				

				try 
				{
					this.ctrl.enregistrerSous(this.cheminFichier, this.ctrl.getListeTache());
					this.frame.setLien(this.cheminFichier);
					this.ctrl.lireFichier(this.cheminFichier);
					this.verification();
					this.frame.majList();
					this.frame.activerBoutons();
					JOptionPane.showMessageDialog(this.frame, "Projet enregistré sous " + path, "Enregistrement", JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(this.frame, "Problème lors de l'enregistrement", "Enregistrement", JOptionPane.ERROR_MESSAGE);
				}
				
			
			}
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
			for (int code = 0; code <= 10; code++)
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
					JOptionPane.showMessageDialog(this.frame, message, "Code erreur " + code, JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}

