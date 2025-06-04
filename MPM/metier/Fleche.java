package MPM.metier;


import java.awt.*;
import java.awt.geom.*;

public class Fleche {
    private BoxShape origine;
    private BoxShape destination;
    private float epaisseur;
    private int tailleTete;
    private String etiquette;

    public Fleche(BoxShape bOrig, BoxShape bDest) {
        this.origine = bOrig;
        this.destination = bDest;
        this.epaisseur = 2.0f;
        this.tailleTete = 8;
        this.etiquette = "";
    }

    public void setEpaisseur(float epaisseur) {
        this.epaisseur = epaisseur;
    }

    public void setTailleTete(int taille) {
        this.tailleTete = taille;
    }

    public void setEtiquette(String texte) {
        this.etiquette = texte;
    }

    public void dessiner(Graphics2D g2) {
        Point centreOrigine = getCentre(origine);
        Point centreDestination = getCentre(destination);

        // Calculer les points d'ancrage sur les côtés
        Point p1 = calculerPointCotePlusProche(origine, centreDestination);
        Point p2 = calculerPointCotePlusProche(destination, centreOrigine);

        // Définir l'épaisseur du trait
        Stroke ancienTrait = g2.getStroke();
        g2.setStroke(new BasicStroke(epaisseur));

        // Dessiner la ligne
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);

        // Dessiner la flèche
        dessinerTete(g2, p1, p2);

        // Dessiner l’étiquette
        if (!etiquette.isEmpty()) {
            int texteX = (p1.x + p2.x) / 2;
            int texteY = (p1.y + p2.y) / 2 - 5;
            g2.drawString(etiquette, texteX, texteY);
        }

        // Restaurer l'ancien style
        g2.setStroke(ancienTrait);
    }

    private Point getCentre(BoxShape box) {
        int x = box.getX();
        int y = box.getY();
        int largeur = box.getLargeur();
        int hauteur = box.getHauteur();
        return new Point(x + largeur / 2, y + hauteur / 2);
    }

    private Point calculerPointCotePlusProche(BoxShape box, Point centreAutre) {
        int x = box.getX();
        int y = box.getY();
        int largeur = box.getLargeur();
        int hauteur = box.getHauteur();

        int centreX = x + largeur / 2;
        int centreY = y + hauteur / 2;

        int dx = centreAutre.x - centreX;
        int dy = centreAutre.y - centreY;

        if (Math.abs(dx) > Math.abs(dy)) {
            // Sortie vers la gauche ou la droite
            return dx > 0 ? new Point(x + largeur, centreY) : new Point(x, centreY);
        } else {
            // Sortie vers le haut ou le bas
            return dy > 0 ? new Point(centreX, y + hauteur) : new Point(centreX, y);
        }
    }

    private void dessinerTete(Graphics2D g2, Point from, Point to) {
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double angle = Math.atan2(dy, dx);

        int x2 = to.x;
        int y2 = to.y;

        int x1 = (int) (x2 - tailleTete * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (y2 - tailleTete * Math.sin(angle - Math.PI / 6));
        int x3 = (int) (x2 - tailleTete * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y2 - tailleTete * Math.sin(angle + Math.PI / 6));

        Polygon fleche = new Polygon();
        fleche.addPoint(x2, y2);
        fleche.addPoint(x1, y1);
        fleche.addPoint(x3, y3);

        g2.fill(fleche);
    }
}
