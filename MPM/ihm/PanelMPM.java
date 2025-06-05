package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelMPM extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{

	private ArrayList<Tache> listTache;
	private ArrayList<BoxShape> lstBoxShape;
	private int niveauPrc;
	private int niveauSvt;

	private Controleur ctrl;
	private FrameMPM frame;
	private Fleche fleche;
	
	// déplacement
	private BoxShape boxShapeSelectionnee = null;
	private Point pointClique = null;
	private boolean enDeplacement = false;

	// elem de PopMenu
	JPopupMenu popMenu;
	JMenuItem itemAjouter;
	JMenuItem itemSupprimer;
	JMenuItem itemModifier;


	private final int TAILLESCROLL = 600;

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

		this.popMenu = new JPopupMenu();

		this.itemAjouter   = new JMenuItem("Ajouter");
		this.itemSupprimer = new JMenuItem("Supprimer");
		this.itemModifier  = new JMenuItem("Modifier");

		this.itemAjouter.setEnabled(true);
		this.itemSupprimer.setEnabled(false);
		this.itemModifier.setEnabled(false);

		


		/*--------------------------------------*/
		/*     Positionnement des composants    */
		/*--------------------------------------*/
		this.popMenu.add(this.itemAjouter);
		this.popMenu.add(this.itemSupprimer);
		this.popMenu.add(this.itemModifier);

		this.setComponentPopupMenu(this.popMenu);

		/*--------------------------------------*/
		/*       Activation des composants      */
		/*--------------------------------------*/
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.itemAjouter.addActionListener(this);
		this.itemSupprimer.addActionListener(this);
		this.itemModifier.addActionListener(this);
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
		this.setPreferredSize(new Dimension( (TAILLESCROLL * largeur) / 2, (TAILLESCROLL * hauteur) / 2) );
		this.revalidate();
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

		this.revalidate();
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

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.itemAjouter)
		{
			this.frame.setVisibleFrameNouveau();
		}

		if(e.getSource() == this.itemSupprimer)
		{
			this.supprimerTache();
		}
	}

	public void supprimerTache()
	{
		System.out.println("supprimerTache");
		System.out.println("Tache selectionnee : " + this.boxShapeSelectionnee);

		if(this.boxShapeSelectionnee != null)
		{
			System.out.println("Suppression de la tache : " + this.boxShapeSelectionnee.getTache().getNom());
			System.out.println("Tableau des taches : " + this.listTache);
			
			// Supprimer de la liste des tâches
			this.listTache.remove(this.boxShapeSelectionnee.getTache());
			
			// Vider complètement la liste des BoxShape avant de la recréer
			this.lstBoxShape.clear();
			
			// Remettre à null la sélection
			this.boxShapeSelectionnee = null;
			
			// Maintenant on peut appeler majList() en sécurité
			this.majList();
			
			System.out.println("Tableau des taches après suppression : " + this.listTache);
		}
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
		//this.boxShapeSelectionnee = null;
		this.pointClique = null;
		this.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e) 
	{
		
	}

	public void mouseExited(MouseEvent e) 
	{
		
	}

	public void mouseMoved(MouseEvent e)
	{
		Point pointSouris         = e.getPoint();
		this.boxShapeSelectionnee = this.trouverBoxShapeSousSouris(pointSouris);


		if (this.boxShapeSelectionnee != null)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//System.out.println(boxSousSouris.getNom().equals("Début"));

			if(! this.popMenu.isShowing())
			{

				if(this.boxShapeSelectionnee.getNom().equals("Début") || this.boxShapeSelectionnee.getNom().equals("Fin"))
				{
					if(! this.popMenu.isShowing())
					{
						this.itemAjouter.setEnabled(false);
						this.itemModifier.setEnabled(false);
						this.itemSupprimer.setEnabled(false);
					}
				}
				else
				{
					if(! this.popMenu.isShowing())
					{
						this.itemAjouter.setEnabled(false);
						this.itemModifier.setEnabled(true);
						this.itemSupprimer.setEnabled(true);
					}
				}
			}
	
		} 
		else
		{
			this.setCursor(Cursor.getDefaultCursor());
			if(! this.popMenu.isShowing())
			{
				this.itemAjouter.setEnabled(true);
				this.itemModifier.setEnabled(false);
				this.itemSupprimer.setEnabled(false);
			}
		}
	}
}