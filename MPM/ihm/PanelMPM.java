package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelMPM extends JPanel implements MouseListener, MouseMotionListener
{

	private ArrayList<Tache> listTache;
	private ArrayList<BoxShape> lstBoxShape;
	private int niveauPrc;
	private int niveauSvt;

	private Controleur ctrl;
	private FrameMPM frame;
	private Fleche fleche;
	
	// Variables pour le déplacement
	private BoxShape boxShapeSelectionnee = null;
	private Point pointClique = null;
	private boolean enDeplacement = false;

	private final int TAILLESCROLL = 1000;

	public PanelMPM(FrameMPM frame, Controleur ctrl) 
	{
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(TAILLESCROLL, TAILLESCROLL));
		this.setBackground(new Color(214, 208, 207));
		this.setBorder(BorderFactory.createLineBorder(Color.black) );
		
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
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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

	public void majScroll(int largeur, int hauteur)
	{
		this.setPreferredSize(new Dimension( (TAILLESCROLL * largeur), (TAILLESCROLL * hauteur) / 2) );
	}

	public void majList()
	{
		this.listTache = this.ctrl.getListeTache();
		this.majScroll(this.ctrl.getNbNiveau(), this.ctrl.getTailleNivMax()  );

		//System.out.println("NB niveau total : " +this.ctrl.getNbNiveau());
		System.err.println("Taille ScrollPane : " + this.getPreferredSize() );
		//System.out.println("Liste de tache : \n" + this.listTache);

		for(Tache t : this.listTache)
		{
			this.lstBoxShape.add(new BoxShape( t, this.ctrl ) );
		}

		for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
		{
			if(this.lstBoxShape.get(cpt).getNiveau() == 0)
			{
				this.lstBoxShape.get(cpt).setDateMin(" "  + this.listTache.get(cpt).getDateMin());
				this.lstBoxShape.get(cpt).setDateMax(" ");
			}
			else
			{
				this.lstBoxShape.get(cpt).setDateMax(" ");
				this.lstBoxShape.get(cpt).setDateMin(" ");
			}
			
		}
		
		//System.out.println("Liste des BoxShape : \n" + this.lstBoxShape);

		this.frame.majTacheBox(this.listTache, this.lstBoxShape);

		this.repaint();
	}

	public void reinitialiser()
	{
		this.listTache.clear();
		this.lstBoxShape.clear();
		this.boxShapeSelectionnee = null;
		this.enDeplacement = false;
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
						
						this.fleche = new Fleche(boxShape,boxShapeSvt);
						this.fleche.dessiner((Graphics2D) g);
					}
				}
			}
    	}
	}

	private BoxShape trouverBoxShapeSousSouris(Point point) 
	{
		for (BoxShape box : this.lstBoxShape)
		{
			if (box.contient(point)) 
			{
				return box;
			}
		}
		return null;
	}

	public void mousePressed(MouseEvent e) 
	{
		Point pointSouris         = e.getPoint();
		this.boxShapeSelectionnee = this.trouverBoxShapeSousSouris(pointSouris);
		
		if (this.boxShapeSelectionnee != null)
		{
			this.pointClique = new Point(
                                          pointSouris.x - this.boxShapeSelectionnee.getX(),
				                          pointSouris.y - this.boxShapeSelectionnee.getY()
										);
			this.enDeplacement = true;

			this.boxShapeSelectionnee.setPositionManuelle(true);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
	}

	public void mouseDragged(MouseEvent e) 
	{
		if (this.enDeplacement && this.boxShapeSelectionnee != null && this.pointClique != null)
		{
			Point nouvellePosition = new Point(
				                                e.getX() - this.pointClique.x,
				                                e.getY() - this.pointClique.y
                                              );
			
			this.boxShapeSelectionnee.setPosition(nouvellePosition.x, nouvellePosition.y);
			
			this.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		this.enDeplacement = false;
		this.boxShapeSelectionnee = null;
		this.pointClique = null;
		this.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e)
	{
		BoxShape boxSousSouris = trouverBoxShapeSousSouris(e.getPoint());
		if (boxSousSouris != null)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} 
		else
		{
			this.setCursor(Cursor.getDefaultCursor());
		}
	}
}