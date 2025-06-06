package MPM.metier;

import java.awt.*;

public class Fleche 
{
    private BoxShape origine;
    private BoxShape destination;
    private float    epaisseur = 2.0f;
    private int      tailleTete = 8;
    private String   etiquette = ""  ;
    private Color    couleurFleche   ;
    private Color    couleurEtiquette;

    public Fleche(BoxShape bOrig, BoxShape bDest) 
    {
        this.origine     = bOrig;
        this.destination = bDest;
        this.couleurFleche     = Color.BLUE; // Couleur par défaut
    }

    public void setEtiquette(String etiquette) 
    {
        this.etiquette = etiquette;
        this.couleurEtiquette = Color.RED; // Couleur par défaut pour l'étiquette
        
    }

    public BoxShape getOrigine() 
    {
        return this.origine;
    }

    public BoxShape getDestination() 
    {
        return this.destination;
    }

    public void supprimer(Tache tOrig, Tache tDest)
    {
        this.origine.getTache().getLstSvt().remove(tDest);
        this.destination.getTache().getLstPrc().remove(tOrig);

    }




    public void dessiner(Graphics2D g2) 
    {
        g2.setColor(this.couleurFleche);
        Point pOrig = getMilieuCoteDroit(origine);
        Point pDest = getMilieuCoteGauche(destination);

        // Ligne
        Stroke strokeAncien = g2.getStroke();
        g2.setStroke(new BasicStroke(epaisseur));
        g2.drawLine(pOrig.x, pOrig.y, pDest.x, pDest.y);
        g2.setStroke(strokeAncien);

        // Tête de flèche
        dessinerTete(g2, pOrig, pDest);

        // Etiquette
        if (!etiquette.isEmpty()) {
            int xText = (pOrig.x + pDest.x) / 2;
            int yText = (pOrig.y + pDest.y) / 2 - 5;
            g2.setColor(this.couleurEtiquette);
            g2.drawString(etiquette, xText, yText);
        }
    }

    public void setCouleurFleche(Color couleur) {
        this.couleurFleche = couleur;
    }
    public void setCouleurEtiquette(Color couleur) {
        this.couleurEtiquette = couleur;
    }

    private Point getMilieuCoteDroit(BoxShape box) {
        Rectangle r = box.getBounds();
        return new Point(r.x + r.width, r.y + r.height / 2);
    }

    private Point getMilieuCoteGauche(BoxShape box) {
        Rectangle r = box.getBounds();
        return new Point(r.x, r.y + r.height / 2);
    }

    private void dessinerTete(Graphics2D g2, Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
        int x = p2.x;
        int y = p2.y;

        int x1 = (int) (x - tailleTete * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (y - tailleTete * Math.sin(angle - Math.PI / 6));
        int x2 = (int) (x - tailleTete * Math.cos(angle + Math.PI / 6));
        int y2 = (int) (y - tailleTete * Math.sin(angle + Math.PI / 6));

        Polygon fleche = new Polygon();
        fleche.addPoint(x, y);
        fleche.addPoint(x1, y1);
        fleche.addPoint(x2, y2);

        g2.fillPolygon(fleche);
    }
}
