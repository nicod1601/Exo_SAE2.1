package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelMPM extends JPanel
{

	private ArrayList<Tache> listTache;
	private ArrayList<BoxShape> lstBoxShape;
	private int niveauPrc;
	private int niveauSvt;

	private Controleur ctrl;
	private FrameMPM frame;
	private Fleche fleche;


	public PanelMPM(FrameMPM frame, Controleur ctrl) 
	{
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(5000, 5000));
		
		/*--------------------------------------*/
		/*        Création des composants       */
		/*--------------------------------------*/

		this.frame   = frame;
		this.ctrl    = ctrl;

		JPanel panelFonction = new JPanel(new GridLayout(1, 5));

		this.listTache   = new ArrayList<Tache>();
		this.lstBoxShape = new ArrayList<BoxShape>();
		this.niveauPrc   = 0;
		this.niveauSvt   = 0;

		


		/*--------------------------------------*/
		/*     Positionnement des composants    */
		/*--------------------------------------*/

		/*--------------------------------------*/
		/*       Activation des composants      */
		/*--------------------------------------*/
	}

	public void resetNiveau()
	{
		this.niveauPrc = 0;
		this.niveauSvt = 0;
	}

	public void majDessin()
	{
		this.repaint();
	}

	public void majList()
	{

		this.listTache = this.ctrl.getListeTache();

		System.out.println("Liste de tache : \n" + this.listTache);

		for(Tache t : this.listTache)
		{
			this.lstBoxShape.add(new BoxShape( t.getNom(),   String.valueOf( t.getDateMin()), String.valueOf( t.getDateMax()), t.getNiveau(), this.ctrl ) );
		}

		for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
		{
			this.lstBoxShape.get(cpt).setDateMax("");
			this.lstBoxShape.get(cpt).setDateMin("");
		}
		
		//System.out.println("Liste des BoxShape : \n" + this.lstBoxShape);

		this.frame.majTacheBox(this.listTache, this.lstBoxShape);

		this.repaint();
	}

	public void reinitialiser()
	{
		this.listTache.clear();
		this.lstBoxShape.clear();
		this.frame.desactiverBoutons();
		this.repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
		{	
			this.lstBoxShape.get(cpt).dessiner((Graphics2D) g);
		}

		for (int cpt = 0; cpt < this.listTache.size(); cpt++) 
    	{
			BoxShape boxShape = this.lstBoxShape.get(cpt);
			Tache t = this.listTache.get(cpt);

			ArrayList<Tache> tachesSuivantes = t.getLstSvt();
			
			if(tachesSuivantes != null && !tachesSuivantes.isEmpty())
			{
				for(Tache tSvt : tachesSuivantes)
				{
					int indexTacheSuivante = this.listTache.indexOf(tSvt);
					if(indexTacheSuivante != -1) 
					{
						BoxShape boxShapeSvt = this.lstBoxShape.get(indexTacheSuivante);
						
						this.fleche = new Fleche
						(
							boxShape.getX() + boxShape.getLargeur()/2,
							boxShape.getY() + boxShape.getHauteur()/2,
							boxShapeSvt.getX() + boxShapeSvt.getLargeur()/2,
							boxShapeSvt.getY() + boxShapeSvt.getHauteur()/2
						);
						this.fleche.dessiner((Graphics2D) g);
					}
				}
			}
    	}
		
	}
}
