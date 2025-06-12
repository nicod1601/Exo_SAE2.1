package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelOptionParametre extends JPanel implements ActionListener
{
	private JTabbedPane tabbedPane;

	private JPanel      panelMPM;
	private JPanel      panelFondEcran;
	private JPanel      panelFondBox;

	private JButton     btnQuitter;

	private JTextPane   txtFichier;
	private JTextField  txtLargeur;
	private JTextField  txtHauteur;

	private JButton[]   btnCouleur;
	private JButton[][] btnCouleurFond;
	
	private Color       sauvColor;
	private BoxShape    box;
	private JButton     btnValider;
	
	private JLabel      lblMsgLargeur;
	private JLabel      lblMsgHauteur;

	private FrameMPM    frame;
	private Controleur  ctrl;

	public PanelOptionParametre(FrameMPM frame, Controleur ctrl)
	{
		this.setLayout    (new BorderLayout() );
		this.setBackground(new Color(30, 30, 30) );

		/*--------------------------*/
		/* Création des composants  */
		/*--------------------------*/
		this.frame = frame;
		this.ctrl  = ctrl;

		// Configuration des Panel et notre TabbedPane

		this.tabbedPane          = new JTabbedPane();
		this.panelMPM            = new JPanel();
		this.panelFondEcran      = new JPanel();
		this.panelFondBox        = new JPanel();

		/* Ajout des onglets */
		this.tabbedPane.addTab("Informations MPM"              , this.panelMPM           );
		this.tabbedPane.addTab("Changer le fond d'écran"       , this.panelFondEcran     );
		this.tabbedPane.addTab("Changer les boxes"             , this.panelFondBox       );

		this.panelMPM           .setLayout(new GridLayout  (1,2) );
		this.panelFondBox       .setLayout(new GridLayout  (1,2) );
		this.panelFondEcran.setLayout(new BorderLayout()    );

		// PanelMPM
		JPanel panelFichier = new JPanel(new BorderLayout());
		this.txtFichier     = new JTextPane();
		
		this.txtFichier.setEditable(false);
		
		panelFichier.add(this.txtFichier, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(this.txtFichier);

		this.panelMPM.add(new JLabel("Informations MPM", JLabel.CENTER));
		this.panelMPM.add(scrollPane);


		//PanelFondEcrant

		Fond fondCouleur = new Fond();
		this.btnCouleurFond = new JButton[fondCouleur.getLigne()][fondCouleur.getColonne()];

		this.panelFondEcran.setLayout(new GridLayout(this.btnCouleurFond.length, this.btnCouleurFond[0].length));

		for(int cpt = 0; cpt < this.btnCouleurFond.length; cpt ++)
		{
			for(int cpt2 = 0; cpt2 < this.btnCouleurFond[cpt].length; cpt2 ++)
			{
				this.btnCouleurFond[cpt][cpt2] = new JButton();
				this.btnCouleurFond[cpt][cpt2].setBackground(fondCouleur.getCouleur(cpt, cpt2).getCouleur());
				this.panelFondEcran.add(this.btnCouleurFond[cpt][cpt2]);
			}
		}

		//PanelFondBox
		JPanel panelLargeur = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panelHauteur = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panelDonnee  = new JPanel( new GridLayout(4,1));
		JPanel panelValider = new JPanel( new FlowLayout(FlowLayout.CENTER));

		this.txtLargeur = new JTextField(10);
		this.txtHauteur = new JTextField(10);

		this.txtLargeur.setToolTipText("Taille minimale : 60") ;
		this.txtHauteur.setToolTipText("Taille minimale : 60");

		//this.txtHauteur.
		

		this.btnValider = new JButton("Valider");

		this.lblMsgHauteur = new JLabel();
		this.lblMsgLargeur = new JLabel();


		panelValider.add(this.btnValider);
		
		panelLargeur.add(new JLabel("Largeur :", JLabel.RIGHT) );
		panelLargeur.add(this.txtLargeur);
		panelLargeur.add(this.lblMsgLargeur);

		panelHauteur.add(new JLabel("Hauteur :", JLabel.RIGHT) );
		panelHauteur.add(this.txtHauteur);
		panelHauteur.add(this.lblMsgHauteur);

		Fond couleur    = new Fond();
	
		this.btnCouleur     = new JButton[couleur.getTailleSimple()];

		JPanel panelCouleur = new JPanel(new GridLayout(1, this.btnCouleur.length, 2 ,2) );

		panelCouleur.setBackground(Color.BLACK);

		JScrollPane scrollPaneCouleur = new JScrollPane(panelCouleur);

		box = new BoxShape(this.ctrl.getProjet());

		for(int cpt = 0; cpt < this.btnCouleur.length; cpt++)
		{
			this.btnCouleur[cpt] = new JButton();
			this.btnCouleur[cpt].setBackground(couleur.getCouleurSimple(cpt).getCouleur() );
			panelCouleur.add(this.btnCouleur[cpt]);
		}



		panelDonnee.add(panelLargeur);
		panelDonnee.add(panelHauteur);
		panelDonnee.add(scrollPaneCouleur);
		panelDonnee.add(panelValider);

		JPanel panelBox = new JPanel(new BorderLayout());
		panelBox.setOpaque(true);
		panelBox.add(box.creerPanel(box), BorderLayout.CENTER);

		this.panelFondBox.add(panelDonnee);
		this.panelFondBox.add(panelBox);
	
		/*--------------------------*/
		/* Position des composants  */
		/*--------------------------*/



		this.add(this.tabbedPane     , BorderLayout.CENTER);
		
		/*--------------------------*/
		/* Activation des composants*/
		/*--------------------------*/
		this.btnValider.addActionListener(this);
		this.txtLargeur.addActionListener(this);
		this.txtHauteur.addActionListener(this);

		for(int cpt = 0; cpt < this.btnCouleur.length; cpt++)
			this.btnCouleur[cpt].addActionListener(this);

		for(int lig = 0; lig < this.btnCouleurFond.length; lig++)
		{
			for(int col = 0; col < this.btnCouleurFond[lig].length; col++)
			{
				this.btnCouleurFond[lig][col].addActionListener(this);
			}
		}

	}

	public void majPanel()
	{
		this.panelFondBox.remove(1);
		this.panelFondBox.add(box.creerPanel(box), BorderLayout.CENTER);
		this.panelFondBox.revalidate();
		this.panelFondBox.repaint();
		
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.txtLargeur)
		{
			if(Integer.parseInt(txtLargeur.getText()) < 60)
			{
				this.lblMsgLargeur.setText("Valeur trop petite");
				this.lblMsgLargeur.setForeground(Color.RED);
				this.lblMsgHauteur.setText ("");


			}
			else
			{
				this.lblMsgLargeur.setText ("");
				this.lblMsgHauteur.setText ("");

			}

			if(this.estEntier(this.txtLargeur.getText()))
			{
				this.box.setLargeur(Integer.parseInt(this.txtLargeur.getText()));
				this.majPanel();
			}

			

			else
			JOptionPane.showMessageDialog(null, "La Taille doit être un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
			
		}

		if(e.getSource() == this.txtHauteur)
		{
			
			if(Integer.parseInt(txtHauteur.getText()) < 60)
			{
				this.lblMsgHauteur.setText		("Valeur trop petite");
				this.lblMsgHauteur.setForeground(Color.RED);
				this.lblMsgLargeur.setText ("");

				
			}
			else
			{
				this.lblMsgLargeur.setText ("");
				this.lblMsgHauteur.setText ("");

			}
		
		
			if (this.estEntier (this.txtHauteur.getText() ) )
			{
				this.box.setHauteur(Integer.parseInt(this.txtHauteur.getText()));
				this	.majPanel();
			}

			else
				JOptionPane.showMessageDialog(null, "La Taille doit etre un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
			
		}

		for(int lig = 0; lig < this.btnCouleurFond.length; lig++)
		{
			for(int col = 0; col < this.btnCouleurFond[lig].length; col++)
			{
				if(e.getSource() == this.btnCouleurFond[lig][col])
				{
					this.frame.changerFondEcran(this.btnCouleurFond[lig][col].getBackground());
				}
			}
		}

		for(JButton btn : this.btnCouleur)
		{
			if(e.getSource() == btn)
			{
				System.out.println("Couleur : " + btn.getBackground());
				this.box.setCouleur(btn.getBackground());
				this.sauvColor = btn.getBackground();
				this.majPanel();
			}
		}

		int largeurBox = 60+ (int)((20*this.box.getTache().getNom().length())*0.8);
		if(e.getSource() == this.btnValider)
			if (this.box.getLargeur()<largeurBox)
				this.frame.setModifBocks(largeurBox, this.box.getHauteur(), this.box.getCouleur());
			else 
				this.frame.setModifBocks(this.box.getLargeur(), this.box.getHauteur(), this.box.getCouleur());
		
	}

	public boolean estEntier(String entier)
	{
		try
		{
			Integer.parseInt(entier);
			return true;
		}

		catch(NumberFormatException ex) { return false; }
	}

	public void majTxt()
	{
		String lien        = this.frame.getLien   ();
		String infoFichier = this.ctrl .getFichier(lien);

		this.txtFichier.setText(infoFichier);
		this.txtFichier.setFont(new Font("Arial",Font.ROMAN_BASELINE, 15));
		
	}

	

	private void styleLabel(JLabel label)
	{
		label.setForeground(new Color(97, 175, 239) ); // Bleu clair moderne
		label.setFont      (new Font ("Segoe UI", Font.PLAIN, 14) );
	}

	private void styleButton(JButton button)
	{
		button.setBackground  (new Color(60, 63, 65)); // Gris foncé
		button.setForeground  (Color.WHITE);
		button.setFont        (new Font("Segoe UI", Font.BOLD, 14));
		button.setBorder      (BorderFactory.createLineBorder(new Color(70, 130, 180), 2) ); // Bleu acier
		button.setFocusPainted(false);
	}

	private void styleRadioButton(JRadioButton radio)
	{
		radio.setBackground  (new Color(40, 44, 52)); // Fond sombre
		radio.setForeground  (Color.LIGHT_GRAY);
		radio.setFont        (new Font("Segoe UI", Font.PLAIN, 13) );
		radio.setBorder      (BorderFactory.createLineBorder(new Color(70, 130, 180), 1) );
		radio.setFocusPainted(false);
		radio.setOpaque      (true );
	}

	private void styleTextField(JTextField field)
	{
		field.setBackground(new Color(60, 63, 65));
		field.setForeground(Color.WHITE);
		field.setCaretColor(new Color(97, 175, 239));
		field.setFont      (new Font("Consolas", Font.PLAIN, 13));
		field.setBorder    (BorderFactory.createLineBorder(new Color(70, 130, 180), 1) );
		
	}

	private void stylePanel(JPanel panel) { panel.setBackground(new Color(40, 44, 52)); }

	private void styleScrollPane(JScrollPane scrollPane)
	{
		scrollPane.setBorder    (BorderFactory.createLineBorder(new Color(70, 130, 180) ) );
		scrollPane.getViewport().setBackground                 (new Color(60, 63, 65    ) );
	}
}