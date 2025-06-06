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

	//stocke
	private BoxShape recupBox;

	// elem de PopMenu
	private JPopupMenu popMenu;
	private JMenuItem itemAjouter;
	private JMenuItem itemSupprimer;
	private JMenuItem itemModifier;

	// sous menu AjouterPrc
	private JMenu menuAjoutePrc;
	private JMenuItem itemAjoutePrc;

	



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

		this.menuAjoutePrc = new JMenu("Ajouter Prc");
		this.itemAjoutePrc = new JMenuItem("");

		this.recupBox = this.boxShapeSelectionnee;

		this.ajouterPrc();

		

		this.itemAjouter.setEnabled(true);
		this.itemSupprimer.setEnabled(false);
		this.itemModifier.setEnabled(false);

		


		/*--------------------------------------*/
		/*     Positionnement des composants    */
		/*--------------------------------------*/
		this.popMenu.add(this.itemAjouter);
		this.popMenu.add(this.itemSupprimer);
		this.popMenu.add(this.itemModifier);
		this.popMenu.add(this.menuAjoutePrc);

		this.setComponentPopupMenu(this.popMenu);

		/*--------------------------------------*/
		/*       Activation des composants      */
		/*--------------------------------------*/
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.itemAjouter.addActionListener(this);
		this.itemSupprimer.addActionListener(this);
		this.itemModifier.addActionListener(this);
		this.itemAjoutePrc.addActionListener(this);
	}

	public void ajouterPrc()
	{
		this.menuAjoutePrc.removeAll();

		if(this.boxShapeSelectionnee == null) 
		{
			this.menuAjoutePrc.setEnabled(false);
    	}
		else
		{
			this.menuAjoutePrc.setEnabled(true);
			Tache tacheSelectionnee = this.boxShapeSelectionnee.getTache();

			if(tacheSelectionnee.getNom().equals("Début"))
			{
				this.menuAjoutePrc.setEnabled(false);
			}
			else
			{
				this.menuAjoutePrc.setEnabled(true);
			}

		
			for(Tache t : this.ctrl.getListeTache())
			{
				if(!t.equals(tacheSelectionnee) && !t.getNom().equals("Fin") && !( t.getNiveau() >= tacheSelectionnee.getNiveau() ) ) 
				{
					
					JMenuItem item = new JMenuItem(t.getNom());
					item.addActionListener(this);
					
					this.menuAjoutePrc.add(item);
				}
			}
		}

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
		
		this.lstBoxShape.clear();
		
		this.majScroll(this.ctrl.getNbNiveau(), this.ctrl.getTailleNivMax());
		
		for(Tache t : this.listTache)
		{
			BoxShape box = new BoxShape(t, this.ctrl);
			this.lstBoxShape.add(box);
			System.out.println("BoxShape créée pour: " + t.getNom() + " au niveau: " + t.getNiveau());
		}
		
		
		// Configuration des dates pour l'affichage
		for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
		{
			if(this.lstBoxShape.get(cpt).getNiveau() == 0)
			{
				this.lstBoxShape.get(cpt).setDateMin(" " + this.listTache.get(cpt).getDateMin());
				this.lstBoxShape.get(cpt).setDateMax(" ");
			}
			else
			{
				this.lstBoxShape.get(cpt).setDateMax(" ");
				this.lstBoxShape.get(cpt).setDateMin(" ");
			}
		}
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

		if(e.getSource() == this.itemModifier)
		{
			//this.frame.setVisibleFrameModifier();
		}

		for(int cpt = 0; cpt < this.menuAjoutePrc.getItemCount(); cpt++)
		{
			if(e.getSource() == this.menuAjoutePrc.getItem(cpt))
			{
				for(Tache t : this.ctrl.getListeTache())
				{
					if(t.getNom().equals(this.menuAjoutePrc.getItem(cpt).getText()))
					{
						this.ctrl.addPrecedent(this.recupBox.getTache(), t);
					}
				}
			}
		}
	}

	public void supprimerTache()
	{
		System.out.println("supprimerTache");
		System.out.println("Tache selectionnee : " + this.boxShapeSelectionnee);

		if(this.boxShapeSelectionnee != null)
		{
			
			// Supprimer de la liste des tâches
			this.listTache.remove(this.boxShapeSelectionnee.getTache());
			
			// Vider complètement la liste des BoxShape avant de la recréer
			this.lstBoxShape.clear();
			
			// Remettre à null la sélection
			this.boxShapeSelectionnee = null;
			
			// Maintenant on peut appeler majList() en sécurité
			this.majList();

			this.ajouterPrc();
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
						this.fleche.setEtiquette(String.valueOf(t.getDuree()));
						
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
		this.recupBox = this.boxShapeSelectionnee;
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
				this.recupBox = this.boxShapeSelectionnee;

				if(this.boxShapeSelectionnee.getNom().equals("Début") || this.boxShapeSelectionnee.getNom().equals("Fin"))
				{
					if(! this.popMenu.isShowing())
					{
						this.itemAjouter.setEnabled(false);
						this.itemModifier.setEnabled(false);
						this.itemSupprimer.setEnabled(false);
						this.ajouterPrc();

						this.recupBox = this.boxShapeSelectionnee;
					}
				}
				else
				{
					if(! this.popMenu.isShowing())
					{
						this.itemAjouter.setEnabled(false);
						this.itemModifier.setEnabled(true);
						this.itemSupprimer.setEnabled(true);
						this.ajouterPrc();
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
				this.ajouterPrc();
			}
		}
	}
}