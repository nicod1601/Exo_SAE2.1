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
    private int largeur;
    private int hauteur;
    private int hauteurCaseSuperieure;
    private String dateMax;
    private String dateMin;


    private Tache  tache;

    private int x;
    private int y;

    private Controleur ctrl;

    
    
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
        this.ctrl  = ctrl;
        this.largeur = 140;
        this.hauteur = 140;
        this.hauteurCaseSuperieure = 70;
        this.tache = new Tache();
        this.dateMax = " ";
        this.dateMin = " ";
    }

    public BoxShape(Tache t, Controleur ctrl)
    {
        this.ctrl                  = ctrl;
        this.tache                 = t;
        this.largeur               = 140+ (int)((20*this.tache.getNom().length())*0.8);
        this.hauteur               = 140;
        this.hauteurCaseSuperieure = 70;
        this.dateMax = " " + t.getDateMax();
        this.dateMin = " " + t.getDateMin();
    }
    
    // Getters
    public int getLargeur()                 { return largeur;                         }
    public int getHauteur()                 { return hauteur;                         }
    public int getHauteurCaseSuperieure()   { return hauteurCaseSuperieure;           }
    public int getHauteurCasesInferieures() { return hauteur - hauteurCaseSuperieure; }
    public int getLargeurCaseInferieure()   { return largeur / 2;                     }
    public Rectangle getBounds() { return new Rectangle(this.x, this.y, this.largeur, this.hauteur);}

    public String getNom()    { return tache.getNom();           }
    public String getDateMin(){ return this.dateMin;  }
    public String getDateMax(){ return this.dateMax;  }
    public int getNiveau()    { return tache.getNiveau();        }

    
    
    // Setters avec validation
    public void setLargeur(int largeur) { this.largeur = largeur;}
    public void setHauteur(int hauteur) {this.hauteur = hauteur;}
    
    public void setHauteurCaseSuperieure(int hauteurCaseSuperieure) {this.hauteurCaseSuperieure = hauteurCaseSuperieure;}

    public void setNom    (String nom       ) { this.tache.setNom(nom) ;}
    public void setDateMin(String txtDateMin) { this.dateMin = txtDateMin;}
    public void setDateMax(String txtDateMax) { this.dateMax = txtDateMax; }

    public int getX() { return x; }
    public int getY() { return y; }
    
    /**
     * Méthode pour dessiner la forme sur un Graphics2D
     * @param g2d le contexte graphique
     * @param x position x de départ
     * @param y position y de départ
     */
    public void dessiner(Graphics2D g2d) 
    {
        g2d.setColor(Color.BLACK);
        int xb;
        int yb;

        int[] nbParNiveau  = this.ctrl.getNbParNiveau(this.tache.getNiveau(),this.tache.getNom());

        // Calcul de la largeur et de la hauteur
        xb = 140 + ( (2*140)*this.tache.getNiveau() );
        yb = ((int)(1.5*this.hauteur)*nbParNiveau[1]) ;

        // Ajustement de la position en fonction du nom
        if(this.tache.getNom().equals("Début") || this.tache.getNom().equals("Fin"))
        {
            yb = (  (  (int)(1.5 * this.hauteur) * this.ctrl.getTailleNivMax() ) + (  (int)(1.5*this.hauteur)) )    /2 ;
        }


        // Positionnement de la forme
        this.x = xb;
        this.y = yb;

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

    private int getLargeurMaxPrc()
    {
        int max = 0;
        for(Tache t : this.tache.getLstPrc())
            
        return max;
    }
    
    /**
     * Méthode privée pour dessiner le texte dans les cases
     */
    private void dessinerTexte(Graphics2D g2d, int x, int y, int hauteurInferieure, int largeurCase) 
    {
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        
        FontMetrics fm = g2d.getFontMetrics();
        
        // Texte case supérieure
        if (!this.tache.getNom().isEmpty()) 
        {
            g2d.setColor(Color.BLACK);
            int textWidth = fm.stringWidth(this.tache.getNom());
            int textHeight = fm.getAscent();
            int textX = x + (largeur - textWidth) / 2;
            int textY = y + (hauteurCaseSuperieure + textHeight) / 2;
            g2d.drawString(this.tache.getNom(), textX, textY);
        }
        
        // Texte case inférieure gauche
        if (!this.dateMin.isEmpty())
        {
             g2d.setColor(new Color(59, 185, 28 ));
            int textWidth = fm.stringWidth(this.dateMin);
            int textHeight = fm.getAscent();
            int textX = x + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMin, textX, textY);

        }
        
        // Texte case inférieure droite
        if (!this.dateMax.isEmpty())
        {
            g2d.setColor(Color.RED);
            int textWidth = fm.stringWidth(this.dateMax);
            int textHeight = fm.getAscent();
            int textX = x + largeurCase + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMax, textX, textY);


        }
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
                box.dessiner((Graphics2D) g);
            }
            
            public Dimension getPreferredSize() {return new Dimension(largeur + 20, hauteur + 20); }
        };
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
    
    public String toString() 
    {
        return "Largeur: "                    + this.largeur               + "\n"  + 
               "Hauteur: "                    + this.hauteur               + "\n " + 
               "HauteurCaseSuperieure : "     + this.hauteurCaseSuperieure + "\n"  +
               "Nom : "                       + this.tache.getNom()        + "\n"  + 
               "DateMin : "                   + this.tache.getDateMin()    + "\n"  + 
               "DateMax : "                   + this.tache.getDateMax()    + "\n" +
               "Niveau : "                    + this.tache.getNiveau()     + "\n\n";
    }


}