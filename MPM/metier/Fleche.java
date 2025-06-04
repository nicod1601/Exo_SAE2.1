package MPM.metier;


import java.awt.*;
import java.awt.geom.*;

public class Fleche
{
    private Point origine;
    private Point destination;
    private float epaisseur;
    private int tailleTete;
    private String etiquette;
    
    public Fleche(Point origine, Point destination)
    {
        this.origine = origine;
        this.destination = destination;
        this.epaisseur = 2.0f;
        this.tailleTete = 8;
        this.etiquette = "";
    }
    
    public Fleche(int x1, int y1, int x2, int y2) 
    {
        this(new Point(x1, y1), new Point(x2, y2));
    }
    
    /*public Fleche(Point origine, Point destination, String etiquette)
    {
        this(origine, destination);
        this.etiquette = etiquette;
    }*/
    
    // Méthode pour dessiner la flèche
    public void dessiner(Graphics2D g2d)
    {
        Color couleurOriginale = g2d.getColor();
        Stroke strokeOriginal  = g2d.getStroke();
        
        // Appliquer les paramètres de la flèche
        g2d.setStroke(new BasicStroke(epaisseur));
        
        // Calculer les points ajustés pour éviter que la flèche traverse les rectangles
        Point origineAjustee = calculerPointSortie(origine, destination, 150);
        Point destinationAjustee = calculerPointEntree(destination, origine, 150);
        
        // Dessiner la ligne principale
        g2d.drawLine(origineAjustee.x, origineAjustee.y, destinationAjustee.x, destinationAjustee.y);
        
        // Calculer l'angle de la flèche
        double dx = destinationAjustee.x - origineAjustee.x;
        double dy = destinationAjustee.y - origineAjustee.y;
        double angle = Math.atan2(dy, dx);
        
        // Calculer les points de la tête de flèche
        double angleGauche = angle + Math.PI - Math.PI/6;
        double angleDroit  = angle + Math.PI + Math.PI/6;
        
        int[] xPoints = {
            destinationAjustee.x,
            destinationAjustee.x + (int)(tailleTete * Math.cos(angleGauche)),
            destinationAjustee.x + (int)(tailleTete * Math.cos(angleDroit))
        };
        
        int[] yPoints = {
            destinationAjustee.y,
            destinationAjustee.y + (int)(tailleTete * Math.sin(angleGauche)),
            destinationAjustee.y + (int)(tailleTete * Math.sin(angleDroit))
        };
        
        // Dessiner et remplir la tête de flèche
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Dessiner l'étiquette si elle existe
        if (!etiquette.isEmpty()) {
            dessinerEtiquette(g2d, origineAjustee, destinationAjustee);
        }
        
        // Restaurer les paramètres originaux
        g2d.setColor(couleurOriginale);
        g2d.setStroke(strokeOriginal);
    }
    
    // Calculer le point de sortie d'un rectangle
    private Point calculerPointSortie(Point centre, Point cible, int tailleRect)
    {
        double dx = cible.x - centre.x;
        double dy = cible.y - centre.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance == 0) return centre;
        
        // Normaliser le vecteur et multiplier par la demi-taille du rectangle
        double facteur = (tailleRect / 2.0) / distance;
        return new Point(
            centre.x + (int)(dx * facteur),
            centre.y + (int)(dy * facteur)
        );
    }
    
    // Calculer le point d'entrée dans un rectangle
    private Point calculerPointEntree(Point centre, Point source, int tailleRect)
    {
        double dx = source.x - centre.x;
        double dy = source.y - centre.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance == 0) return centre;
        
        double facteur = (tailleRect / 2.0) / distance;
        return new Point(
            centre.x + (int)(dx * facteur),
            centre.y + (int)(dy * facteur)
        );
    }
    
    private void dessinerEtiquette(Graphics2D g2d, Point p1, Point p2)
    {
        Font fontOriginale = g2d.getFont();
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        
        int millieuX = (p1.x + p2.x) / 2;
        int millieuY = (p1.y + p2.y) / 2;
        
        FontMetrics fm   = g2d.getFontMetrics();
        int largeurTexte = fm.stringWidth(etiquette);
        int hauteurTexte = fm.getHeight();
        
        // Fond blanc pour l'étiquette
        g2d.setColor(Color.WHITE);
        g2d.fillRect(millieuX - largeurTexte/2 - 2, millieuY - hauteurTexte/2 - 1, 
                     largeurTexte + 4, hauteurTexte);
        
        // Bordure de l'étiquette
        g2d.setColor(Color.BLACK);
        g2d.drawRect(millieuX - largeurTexte/2 - 2, millieuY - hauteurTexte/2 - 1, 
                     largeurTexte + 4, hauteurTexte);
        
        // Texte de l'étiquette
        g2d.drawString(etiquette, millieuX - largeurTexte/2, millieuY + hauteurTexte/4);
        
        g2d.setFont(fontOriginale);
    }
    
    // Méthode statique pour créer une flèche courbe (pour éviter les chevauchements)
    public static void dessinerFlecheCourbe(Graphics2D g2d, Point origine, Point destination, 
                                          int courbure, Color couleur) {
        g2d.setColor(couleur);
        g2d.setStroke(new BasicStroke(2.0f));
        
        // Calculer le point de contrôle pour la courbe
        int ctrlX = (origine.x + destination.x) / 2 + courbure;
        int ctrlY = (origine.y + destination.y) / 2 + courbure;
        
        QuadCurve2D curve = new QuadCurve2D.Float(origine.x, origine.y, 
                                                  ctrlX, ctrlY, 
                                                  destination.x, destination.y);
        g2d.draw(curve);
        
        // Ajouter une petite tête de flèche à la destination
        double angle = Math.atan2(destination.y - ctrlY, destination.x - ctrlX);
        int[] xPoints = {
            destination.x,
            destination.x + (int)(8 * Math.cos(angle + Math.PI - Math.PI/6)),
            destination.x + (int)(8 * Math.cos(angle + Math.PI + Math.PI/6))
        };
        
        int[] yPoints = {
            destination.y,
            destination.y + (int)(8 * Math.sin(angle + Math.PI - Math.PI/6)),
            destination.y + (int)(8 * Math.sin(angle + Math.PI + Math.PI/6))
        };
        
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    
    // Getters et Setters
    public Point getOrigine() { return origine; }
    public void setOrigine(Point origine) { this.origine = origine; }
    
    public Point getDestination() { return destination; }
    public void setDestination(Point destination) { this.destination = destination; }
    
    public String getEtiquette() { return etiquette; }
    public void setEtiquette(String etiquette) { this.etiquette = etiquette; }
    
    public void setEpaisseur(float epaisseur) { this.epaisseur = epaisseur; }
    public void setTailleTete(int tailleTete) { this.tailleTete = tailleTete; }
    
    @Override
    public String toString() {
        return String.format("Flèche: %s -> %s%s", 
                           origine, destination, 
                           etiquette.isEmpty() ? "" : " (" + etiquette + ")");
    }
}