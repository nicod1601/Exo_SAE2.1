package MPM.ihm;

import java.awt.*;
import javax.swing.*;
import MPM.Controleur;
import MPM.metier.*;
import java.util.ArrayList;

public class PanelMPM extends JPanel 
{
	private MaBarre maBarre;
	private FrameMPM frame;

	private ArrayList<Tache> listTache;
	private ArrayList<BoxShape> lstBoxShape;

	private Controleur ctrl;


	public PanelMPM(FrameMPM frame, Controleur ctrl) 
	{
		this.setLayout(new BorderLayout());
		
		/*--------------------------------------*/
		/*        Création des composants       */
		/*--------------------------------------*/

		this.frame   = frame;
		this.ctrl    = ctrl;

		this.listTache   = new ArrayList<Tache>();
		this.lstBoxShape = new ArrayList<BoxShape>();

		this.maBarre = new MaBarre(this.frame, this.ctrl);
		


		/*--------------------------------------*/
		/*     Positionnement des composants    */
		/*--------------------------------------*/
		this.add(this.maBarre, BorderLayout.NORTH);
	}

	public void majList()
	{
		this.listTache = this.ctrl.getListeTache();

		System.out.println("Liste de tache : \n" + this.listTache);

		for(Tache t : this.listTache)
		{
			this.lstBoxShape.add(new BoxShape( t.getNom(),   String.valueOf( t.getDateMin()), String.valueOf( t.getDateMax()), t.getNiveau(), this.ctrl ) );
		}
		
		//System.out.println("Liste des BoxShape : \n" + this.lstBoxShape);

		this.repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
		{
			this.lstBoxShape.get(cpt).dessiner((Graphics2D) g, 50, 50);
		}
		
	}
}
