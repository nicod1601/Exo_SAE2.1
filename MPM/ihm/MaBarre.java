package MPM.ihm;

import java.awt.event.*;
import javax.swing.*;


public class MaBarre extends JMenuBar implements ActionListener
{
	private JMenuItem     menuiOuvrir ;
	private JMenuItem     menuiQuitter;

	private FrameMPM      frame;


	public MaBarre(FrameMPM frame)
	{
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.frame = frame;

		// un element de la barre de menu
		JMenu menuFichier   = new JMenu("Fichier"  );

		// les items du menu FichierAnnuler
		this.menuiOuvrir          = new JMenuItem ("Ouvrir"          );
		this.menuiQuitter         = new JMenuItem ("Quitter"         );



		// Raccourci 
		menuFichier              .setMnemonic('F');
		this.menuiOuvrir         .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		this.menuiQuitter        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));


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
		this.menuiOuvrir.addActionListener          ( this );

		this.menuiQuitter.addActionListener         ( this );

	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.menuiOuvrir )
		{
			System.out.println("Ouvrir");
		}
		
		if ( e.getSource() == this.menuiQuitter )
		{
			System.out.println("Quitter");
		}
		
	}
}

