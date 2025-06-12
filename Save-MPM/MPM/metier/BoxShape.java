package MPM.metier;
import MPM.Controleur;
import java.awt.*;
import javax.swing.*;

/**
 * Classe métier représentant une forme de boîte avec une case supérieure
 * et deux cases inférieures
 */
public class BoxShape
{
    private int    largeur;
    private int    hauteur;
    private int    hauteurCaseSuperieure;
    private String dateMax;
    private String dateMin;
    private Color  couleur;


    private Tache  tache;

    private int posX;
    private int posY;

    private int x;
    private int y;

    private Controleur ctrl;

    private boolean positionManuelle = false;

    
    
    /**
     * Constructeur principal avec tous les paramètres
     * @param largeur largeur totale de la boîte
     * @param hauteur hauteur totale de la boîte
     * @param hauteurCaseSuperieure hauteur de la case du haut
     * @param couleurBordure couleur des bordures
     * @param epaisseurBordure épaisseur des bordures
     */
    public BoxShape(Controleur ctrl)
    {
        this.ctrl    = ctrl;
        this.largeur = 60;
        this.hauteur = 60;
        this.tache   = new Tache();
        this.dateMax = " ";
        this.dateMin = " ";
        this.couleur = Color.BLACK;
        this.hauteurCaseSuperieure = 30;


    }

    public BoxShape(Tache t, Controleur ctrl)
    {
        this.ctrl                  = ctrl;
        this.tache                 = t;
        this.largeur               = 60+ (int)((20*this.tache.getNom().length())*0.8);
        this.hauteur               = 60;
        this.hauteurCaseSuperieure = 30;
        this.dateMax               = " " + t.getDateMax();
        this.dateMin               = " " + t.getDateMin();
        this.couleur               = Color.BLACK;
    }

    public Tache getTache() { return this.tache; }
    
    // Getters
    public int       getLargeur()                 { return this.largeur;                         }
    public int       getHauteur()                 { return this.hauteur;                         }
    public int       getHauteurCaseSuperieure()   { return this.hauteurCaseSuperieure;           }
    public int       getHauteurCasesInferieures() { return this.hauteur - hauteurCaseSuperieure; }
    public int       getLargeurCaseInferieure()   { return this.largeur / 2;                     }

    public Rectangle getBounds() { return new Rectangle(this.x, this.y, this.largeur, this.hauteur);}

    public String    getNom    ()              { return tache.getNom();           }
    public String    getDateMin()              { return this.dateMin;  }
    public String    getDateMax()              { return this.dateMax;  }
    public int       getNiveau ()              { return tache.getNiveau();        }
    public Color     getCouleur()              { return this.couleur; }
    public void      setTaille (int taille)    { this.largeur = taille; this.hauteur = taille; }
    public void      setCouleur(Color couleur) { this.couleur = couleur; }

    
    // Setters avec validation
    public void setLargeur(int largeur)
    { 
        if(largeur > 60)
            this.largeur = largeur;
        else
            this.largeur = 60;
    }
    public void setHauteur(int hauteur)
    { 
        if(hauteur > 60)
            this.hauteur = hauteur;
        else
            this.hauteur = 60;
    }
    
    public void setHauteurCaseSuperieure(int hauteurCaseSuperieure) {this.hauteurCaseSuperieure = hauteurCaseSuperieure;}

    public void setNom    (String nom       ) { this.tache.setNom(nom) ;   }
    public void setDateMin(String txtDateMin) { this.dateMin = txtDateMin; }
    public void setDateMax(String txtDateMax) { this.dateMax = txtDateMax; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPos()
    {
        this.posX = this.getX();
        this.posY = this.getY();
    }
    
    /**
     * Méthode pour dessiner la forme sur un Graphics2D
     * @param g2d le contexte graphique
     * @param x position x de départ
     * @param y position y de départ
     */
    public void dessiner(Graphics2D g2d) 
    {
        g2d.setColor(this.couleur);
        int xb;
        int yb; 
        

        // Si la position n'est pas définie manuellement, calculer automatiquement
        if (!this.positionManuelle) 
        {
            int[] nbParNiveau = this.ctrl.getNbParNiveau(this.tache.getNiveau(), this.tache.getNom());

            // Calcul de la largeur et de la hauteur
            xb = this.largeur + ((2 * 125) * this.tache.getNiveau());
            yb = ((int) (1.5 * this.hauteur) * nbParNiveau[1]);

            // Ajustement de la position en fonction du nom
            if (this.tache.getNom().equals("Début") || this.tache.getNom().equals("Fin")) 
            {
                yb = (((int) (1.5 * this.hauteur) * this.ctrl.getTailleNivMax()) + ((int) (1.5 * this.hauteur))) / 2;
            }

            // Positionnement de la forme
            this.x = xb;
            this.y = yb;
        } 
        else 
        {
            // Utiliser la position manuelle
            xb = this.x;
            yb = this.y;
        }

        // Configuration du style pour les bordures
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING     , RenderingHints.VALUE_ANTIALIAS_ON     );
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Case supérieure
        g2d.drawRect(xb, yb, largeur, hauteurCaseSuperieure);
        
        // Cases inférieures
        int hauteurInferieure = hauteur - hauteurCaseSuperieure;
        int largeurCase = largeur / 2;
        
        // Case inférieure gauche
        g2d.drawRect(xb, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Case inférieure droite
        g2d.drawRect(xb + largeurCase, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Dessin du texte
        dessinerTexte(g2d, xb, yb, hauteurInferieure, largeurCase);
    }

    private void dessinerTexte(Graphics2D g2d, int x, int y, int hauteurInferieure, int largeurCase) 
    {
       
        
        FontMetrics fm = g2d.getFontMetrics();
        
        // Texte case supérieure
        if (!this.tache.getNom().isEmpty()) 
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.setColor(Color.BLACK);
            int textWidth  = fm.stringWidth(this.tache.getNom());
            int textHeight = fm.getAscent();
            int textX = x + (largeur - textWidth) / 2;
            int textY = y + (hauteurCaseSuperieure + textHeight) / 2;
            g2d.drawString(this.tache.getNom() , textX, textY);
        }
        
        // Texte case inférieure gauche
        if (!this.dateMin.isEmpty() )
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.setColor(new Color(59, 185, 28 ));
            int textWidth  = fm.stringWidth(this.dateMin);
            int textHeight = fm.getAscent();

            int textX = x + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMin, textX, textY);

        }
        
        // Texte case inférieure droite
        if (!this.dateMax.isEmpty())
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.setColor(Color.RED);
            int textWidth  = fm.stringWidth(this.dateMax);
            int textHeight = fm.getAscent();
            
            int textX = x + largeurCase + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMax, textX, textY);


        }
    }


    public void supprimer(Tache tOrg, Tache tDest)
    {
        this.tache.getLstPrc().remove(tOrg);
        this.tache.getLstSvt().remove(tDest);
    }
    
    /**
     * Crée un JPanel contenant cette forme
     * @return JPanel avec la forme dessinée
     */
    public JPanel creerPanel(BoxShape box)
    {
        return new JPanel()
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                box.dessinerExemple((Graphics2D) g);
            }
            
            //public Dimension getPreferredSize() {return new Dimension(largeur, hauteur); }
        };
    }

     public void dessinerExemple(Graphics2D g2d) 
    {
        g2d.setColor(this.couleur);
        int xb;
        int yb;

        // Si la position n'est pas définie manuellement, calculer automatiquement
        if (!this.positionManuelle) 
        {

            // Calcul de la largeur et de la hauteur
            xb = 100;
            yb = 20;

            // Ajustement de la position en fonction du nom
            if (this.tache.getNom().equals("Début") || this.tache.getNom().equals("Fin")) 
                yb = (((int) (1.5 * this.hauteur) * this.ctrl.getTailleNivMax()) + ((int) (1.5 * this.hauteur))) / 2;
            
        } 
        else 
        {
            // Utiliser la position manuelle
            xb = this.x;
            yb = this.y;
        }

        // Configuration du style pour les bordures
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Case supérieure
        g2d.drawRect(xb, yb, largeur, hauteurCaseSuperieure);
        
        // Cases inférieures
        int hauteurInferieure = hauteur - hauteurCaseSuperieure;
        int largeurCase = largeur / 2;
        
        // Case inférieure gauche
        g2d.drawRect(xb, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Case inférieure droite
        g2d.drawRect(xb + largeurCase, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Dessin du texte
        dessinerTexte(g2d, xb, yb, hauteurInferieure, largeurCase);
    }
    
    /**
     * Calcule l'aire totale de la forme
     * @return aire en pixels carrés
     */
    public int calculerAire() { return largeur * hauteur;}
    
    /**
     * Calcule le périmètre de la forme
     * @return périmètre en pixels
     */
    public int calculerPerimetre() {return 2 * largeur + 4 * hauteur - 2 * hauteurCaseSuperieure;}

    /**
     * Vérifie si un point est contenu dans cette BoxShape
     * @param point Le point à vérifier
     * @return true si le point est dans la BoxShape, false sinon
     */
    public boolean contient(Point point) 
    {
        return point.x >= this.x && point.x <= this.x + this.largeur &&
               point.y >= this.y && point.y <= this.y + this.hauteur;
    }

    /**
     * Définit la position de la BoxShape (remplace le calcul automatique)
     * @param x nouvelle coordonnée X
     * @param y nouvelle coordonnée Y
     */

    public void setPosition(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Définit la coordonnée X de la BoxShape
     * @param x nouvelle coordonnée X
     */
    public void setX(int x) { this.x = x; }

    /**
     * Définit la coordonnée Y de la BoxShape
     * @param y nouvelle coordonnée Y
     */

    public void setY(int y) { this.y = y; }
    
    /**
     * Active/désactive le positionnement manuel
     * @param manuel true pour utiliser les coordonnées définies manuellement
     */
    public void setPositionManuelle(boolean manuel) { this.positionManuelle = manuel; }

    public String toString() 
    {
        return "Largeur: "                    + this.largeur               + "\n"  + 
               "Hauteur: "                    + this.hauteur               + "\n " + 
               "HauteurCaseSuperieure : "     + this.hauteurCaseSuperieure + "\n"  +
               "Nom : "                       + this.tache.getNom()        + "\n"  + 
               "DateMin : "                   + this.tache.getDateMin()    + "\n"  + 
               "DateMax : "                   + this.tache.getDateMax()    + "\n"  +
               "Pos X : "                     + this.getX()                + "\n"  + 
               "Pos Y : "                     + this.getY()                + "\n\n";    
    }
}