package MPM.metier;

import java.awt.*;

public class Fleche 
{
	private BoxShape origine;
	private BoxShape destination;

	private Color    couleurFleche   ;
	private Color    couleurEtiquette;
	private int      tailleTrou   = 50;   
	private int      tailleTete   = 8; 
	private float    epaisseur    = 2.0f;
	private String   etiquette    = ""  ;

	public Fleche(BoxShape bOrig, BoxShape bDest) 
	{
		this.origine       = bOrig;
		this.destination   = bDest;
		this.couleurFleche = Color.BLUE;
	}

	public Fleche(BoxShape bOrig, BoxShape bDest, Color couleur) 
	{
		this.origine       = bOrig;
		this.destination   = bDest;
		this.couleurFleche = couleur;
	}

	public BoxShape getOrigine      () { return this.origine      ;}
	public BoxShape getDestination  () { return this.destination  ;}
	public Color 	getCouleurFleche() { return this.couleurFleche;}

	public void setEtiquette(String etiquette) 
	{
		this.etiquette        = etiquette;
		this.couleurEtiquette = Color.RED;
	}

	public void setTailleTrou (int taille) { this.tailleTrou = taille; }

	public void supprimer(Tache tOrig, Tache tDest) 
	{
		this.origine    .getTache().getLstSvt().remove(tDest);
		this.destination.getTache().getLstPrc().remove(tOrig);
	}

	public void dessiner(Graphics2D g2) 
	{
		// 1. Trouver les points de départ et d'arrivée
		Point pointDepart  = getMilieuCoteDroit (origine);
		Point pointArrivee = getMilieuCoteGauche(destination);

		// 2. Dessiner la ligne avec le trou
		dessinerLigneAvecTrou(g2, pointDepart, pointArrivee);

		// 3. Dessiner la pointe de la flèche à la fin
		dessinerTete(g2, pointDepart, pointArrivee);

		// 4. Dessiner le texte au milieu (dans le trou)
		dessinerEtiquette(g2, pointDepart, pointArrivee);
	}

	/*------------------*/
	/* MÉTHODES PRIVÉES */
	/*------------------*/

	private Point getMilieuCoteGauche(BoxShape box) 
	{
		Rectangle r = box.getBounds();
		return new Point(r.x, r.y + r.height / 2);
	}

	private Point getMilieuCoteDroit(BoxShape box) 
	{
		Rectangle r = box.getBounds();
		return new Point(r.x + r.width, r.y + r.height / 2);
	}

	public void setCouleurFleche   (Color couleur) { this.couleurFleche    = couleur;}
	public void setCouleurEtiquette(Color couleur) { this.couleurEtiquette = couleur;}


	/**
	 * Dessine une ligne en deux parties avec un trou au milieu
	 */
	private void dessinerLigneAvecTrou(Graphics2D g2, Point debut, Point fin) 
	{
		// Configurer l'apparence de la ligne
		g2.setColor(this.couleurFleche);
		g2.setStroke(new BasicStroke(epaisseur));
		
		// Étape 1: Calculer le point milieu
		int milieuX = (debut.x + fin.x) / 2;
		int milieuY = (debut.y + fin.y) / 2;
		
		// Étape 2: Calculer où commence et finit le trou
		Point debutTrou = calculerPointAvantTrou(debut, fin, milieuX, milieuY);
		Point finTrou   = calculerPointApresTrou(debut, fin, milieuX, milieuY);
		
		// Étape 3: Dessiner les deux parties de la ligne
		g2.drawLine(debut.x  , debut.y  , debutTrou.x, debutTrou.y); // Première partie
		g2.drawLine(finTrou.x, finTrou.y, fin.x      , fin.y      ); // Deuxième partie
	}

	/**
	 * Calcule le point où la ligne s'arrête avant le trou
	 */
	private Point calculerPointAvantTrou(Point debut, Point fin, int milieuX, int milieuY) 
	{
		// On recule de la moitié du trou depuis le milieu
		int distanceAReculer = (tailleTrou / 2) - 15;
		
		// Calculer dans quelle direction reculer
		int distanceX = fin.x - debut.x;
		int distanceY = fin.y - debut.y;
		int longueurTotale = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
		
		// Éviter la division par zéro
		if (longueurTotale == 0) return new Point(milieuX, milieuY);
		
		// Calculer le point
		int nouveauX = milieuX - (distanceX * distanceAReculer) / longueurTotale;
		int nouveauY = milieuY - (distanceY * distanceAReculer) / longueurTotale;
		
		return new Point(nouveauX, nouveauY);
	}

	/**
	 * Calcule le point où la ligne reprend après le trou
	 */
	private Point calculerPointApresTrou(Point debut, Point fin, int milieuX, int milieuY) 
	{
		// On avance de la moitié du trou depuis le milieu
		int distanceAAvancer = (tailleTrou / 2) - 10;
		
		// Calculer dans quelle direction avancer
		int distanceX = fin.x - debut.x;
		int distanceY = fin.y - debut.y;
		int longueurTotale = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
		
		// Éviter la division par zéro
		if (longueurTotale == 0) return new Point(milieuX, milieuY);
		
		// Calculer le point
		int nouveauX = milieuX + (distanceX * distanceAAvancer) / longueurTotale;
		int nouveauY = milieuY + (distanceY * distanceAAvancer) / longueurTotale;
		
		return new Point(nouveauX, nouveauY);
	}

	/**
	 * Dessine le texte au milieu de la flèche (dans le trou)
	 */
	private void dessinerEtiquette(Graphics2D g2, Point debut, Point fin) 
	{
		if (!etiquette.isEmpty())
		{
			g2.setFont(new Font("Arial", Font.BOLD, 15));
			g2.setColor(this.couleurEtiquette);
			int xTexte = (debut.x + fin.x) / 2;
			int yTexte = (debut.y + fin.y) / 2 + 5; // Centré dans le trou
			g2.drawString(etiquette, xTexte, yTexte);
		}
	}

	/**
	 * Dessine la tête de flèche à la destination
	 */
	private void dessinerTete(Graphics2D g2, Point p1, Point p2)
	{
		g2.setColor(this.couleurFleche);
		
		double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
		int x = p2.x;
		int y = p2.y;

		int x1 = (int) (x - tailleTete * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (y - tailleTete * Math.sin(angle - Math.PI / 6));
		int x2 = (int) (x - tailleTete * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (y - tailleTete * Math.sin(angle + Math.PI / 6));

		Polygon fleche = new Polygon();
		fleche.addPoint(x - 2, y - 2);
		fleche.addPoint(x1, y1);
		fleche.addPoint(x2, y2);

		g2.fillPolygon(fleche);
	}
}