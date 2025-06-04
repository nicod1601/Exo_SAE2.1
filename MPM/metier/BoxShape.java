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
    private int niveau;

    private String txtNom;
    private String txtDateMin;
    private String txtDateMax;

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
        this.largeur = 140;
        this.hauteur = 140;
        this.hauteurCaseSuperieure = 70;
        this.txtNom = "A";
        this.txtDateMin = "0";
        this.txtDateMax = "0";
        this.niveau = 0;
    }

    public BoxShape(String txtNom, String txtDateMin, String txtDateMax, int niveau, Controleur ctrl) 
    {
        this.ctrl                  = ctrl;
        
        this.txtNom                = txtNom;
        this.txtDateMin            = txtDateMin;
        this.txtDateMax            = txtDateMax;
        this.largeur               = 140+ (int)((20*txtNom.length())*0.8);;
        this.hauteur               = 140;
        this.hauteurCaseSuperieure = 70;
        this.niveau                = niveau;
    }
    
    // Getters
    public int getLargeur()                 { return largeur;                         }
    public int getHauteur()                 { return hauteur;                         }
    public int getHauteurCaseSuperieure()   { return hauteurCaseSuperieure;           }
    public int getHauteurCasesInferieures() { return hauteur - hauteurCaseSuperieure; }
    public int getLargeurCaseInferieure()   { return largeur / 2;                     }

    public String getNom()                  { return txtNom;                          }
    public String getDateMin()              { return txtDateMin;                      }
    public String getDateMax()              { return txtDateMax;                      }
    public int getNiveau()                  { return niveau;                          }

    
    
    // Setters avec validation
    public void setLargeur(int largeur) { this.largeur = largeur;}
    public void setHauteur(int hauteur) {this.hauteur = hauteur;}
    
    public void setHauteurCaseSuperieure(int hauteurCaseSuperieure) {this.hauteurCaseSuperieure = hauteurCaseSuperieure;}

    public void setNom(String txtNom)         { this.txtNom = txtNom;         }
    public void setDateMin(String txtDateMin)
    { 
        this.txtDateMin = txtDateMin; 
    }
    public void setDateMax(String txtDateMax) { this.txtDateMax = txtDateMax; }

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

        int xb;
        int yb;

        int[] nbParNiveau  = this.ctrl.getNbParNiveau(this.niveau,this.txtNom);

        // Calcul de la largeur et de la hauteur
        xb = 140 + ( (2*140)*this.niveau );
        yb = ((int)(1.5*this.hauteur)*nbParNiveau[1]) ;

        // Ajustement de la position en fonction du nom
        if(this.txtNom.equals("Début") || this.txtNom.equals("Fin"))
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
    
    /**
     * Méthode privée pour dessiner le texte dans les cases
     */
    private void dessinerTexte(Graphics2D g2d, int x, int y, int hauteurInferieure, int largeurCase) 
    {
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        
        FontMetrics fm = g2d.getFontMetrics();
        
        // Texte case supérieure
        if (!txtNom.isEmpty()) 
        {
            int textWidth = fm.stringWidth(txtNom);
            int textHeight = fm.getAscent();
            int textX = x + (largeur - textWidth) / 2;
            int textY = y + (hauteurCaseSuperieure + textHeight) / 2;
            g2d.drawString(txtNom, textX, textY);
        }
        
        // Texte case inférieure gauche
        if (!txtDateMin.isEmpty())
        {
            int textWidth = fm.stringWidth(txtDateMin);
            int textHeight = fm.getAscent();
            int textX = x + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(txtDateMin, textX, textY);

        }
        
        // Texte case inférieure droite
        if (!txtDateMax.isEmpty())
        {
            int textWidth = fm.stringWidth(txtDateMax);
            int textHeight = fm.getAscent();
            int textX = x + largeurCase + (largeurCase - textWidth) / 2;
            int textY = y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(txtDateMax, textX, textY);


        }
    }
    
    /**
     * Crée un JPanel contenant cette forme
     * @return JPanel avec la forme dessinée
     */
    public JPanel creerPanel()
    {
        return new JPanel()
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                dessiner((Graphics2D) g);
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
               "Nom : "                       + this.txtNom                + "\n"  + 
               "DateMin : "                   + this.txtDateMin            + "\n"  + 
               "DateMax : "                   + this.txtDateMax            + "\n" +
               "Niveau : "                    + this.niveau                + "\n\n";
    }


}