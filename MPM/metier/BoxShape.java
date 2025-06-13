package MPM.metier;
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
    private int    x;
    private int    y;
    private String dateMin;
    private String dateMax;

    private Color  couleur;

    private Tache  tache;

    private Projet projet;

    private boolean positionManuelle = false;
    private boolean positionCalculee = false;
    
    /**
     * Constructeur principal avec tous les paramètres
     */
    public BoxShape(Projet p)
    {
        this.projet   = p;
        this.largeur = 60;
        this.hauteur = 60;
        this.tache   = new Tache();
        this.dateMax = " ";
        this.dateMin = " ";
        this.couleur = Color.BLACK;
        this.hauteurCaseSuperieure = 30;
        this.x = 0;
        this.y = 0;
    }

    public BoxShape(Tache t, Projet p)
    {
        this.projet                = p;
        this.tache                 = t;
        this.largeur               = 60;
        this.hauteur               = 60;
        this.hauteurCaseSuperieure = 30;
        this.dateMax               = " " + t.getDateMax();
        this.dateMin               = " " + t.getDateMin();
        this.couleur               = Color.BLACK;
        
        // Initialiser la position
        this.calculerPosition();
    }



    // Getters
    public Tache getTache()                { return this.tache;                                                }
    public int getLargeur()                { return this.largeur;                                              }
    public int getHauteur()                { return this.hauteur;                                              }
    public int getHauteurCaseSuperieure()  { return this.hauteurCaseSuperieure;                                }
    public int getHauteurCasesInferieures(){ return this.hauteur - hauteurCaseSuperieure;                      }
    public int getLargeurCaseInferieure()  { return this.largeur / 2;                                          }
    public int getNiveau()                 { return tache.getNiveau();                                         }
    public int getX()                      { return x;                                                         }
    public int getY()                      { return y;                                                         }
    public Rectangle getBounds()           { return new Rectangle(this.x, this.y, this.largeur, this.hauteur); }
    public String getNom()                 { return tache.getNom();                                            }
    public String getDateMin()             { return this.dateMin;                                              }
    public String getDateMax()             { return this.dateMax;                                              }
    public Color  getCouleur()             { return this.couleur;                                              }


    

    // Setters
    public void setTaille(int largeur)        { this.largeur = largeur;                 }
    public void setLargeur(int largeur)       { this.largeur = Math.max(largeur, 60); }
    public void setHauteur(int hauteur)       { this.hauteur = Math.max(hauteur, 60); }
    public void setNom(String nom)            { this.tache.setNom(nom);                 }
    public void setDateMin(String txtDateMin) { this.dateMin = txtDateMin;              }
    public void setDateMax(String txtDateMax) { this.dateMax = txtDateMax;              }
    public void setCouleur(Color couleur)     { this.couleur = couleur;                 }
    
        
    public void setHauteurCaseSuperieure(int hauteurCaseSuperieure) 
    {
        this.hauteurCaseSuperieure = hauteurCaseSuperieure;
    }


    public void setPosition(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.positionManuelle = true;
        this.positionCalculee = true;
    }
    

    public void setX(int x) 
    { 
        this.x = x; 
        this.positionManuelle = true;
    }

    public void setY(int y) 
    { 
        this.y = y; 
        this.positionManuelle = true;
    }

    public void setPositionManuelle(boolean manuel) 
    { 
        this.positionManuelle = manuel;
        
        if (!manuel && !this.positionCalculee)
        {
            this.calculerPositionAutomatique();
        }
    }

    /**
     * Met à jour la position (utile après modification du projet)
     */
    public void mettreAJourPosition()
    {
        if (!this.positionManuelle) 
        {
            this.calculerPositionAutomatique();
        }
        
        this.dateMax = " " + this.tache.getDateMax();
        this.dateMin = " " + this.tache.getDateMin();
    }
    
    /**
     * Méthode principale pour dessiner la forme
     */
    public void dessiner(Graphics2D g2d) 
    {
        // S'assurer que la position est calculée
        if (!this.positionCalculee) { this.calculerPosition(); }

        g2d.setColor(this.couleur);

        // Configuration du style pour les bordures
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON          );
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Case supérieure
        g2d.drawRect(this.x, this.y, largeur, hauteurCaseSuperieure);
        
        // Cases inférieures
        int hauteurInferieure = hauteur - hauteurCaseSuperieure;
        int largeurCase       = largeur / 2;
        
        // Case inférieure gauche
        g2d.drawRect(this.x, this.y + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Case inférieure droite
        g2d.drawRect(this.x + largeurCase, this.y + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Dessin du texte
        dessinerTexte(g2d, hauteurInferieure, largeurCase);
    }

    

    /**
     * Méthode pour dessiner un exemple (pour prévisualisation)
     */
    public void dessinerExemple(Graphics2D g2d) 
    {
        int xb = 100;
        int yb = 20 ;

        g2d.setColor(this.couleur);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING     , RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Cases
        g2d.drawRect(xb, yb, largeur, hauteurCaseSuperieure);

        int hauteurInferieure = hauteur - hauteurCaseSuperieure;
        int largeurCase       = largeur / 2;

        g2d.drawRect(xb, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        g2d.drawRect(xb     + largeurCase, yb + hauteurCaseSuperieure, largeurCase, hauteurInferieure);
        
        // Texte d'exemple
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        
        String nom    = this.tache.getNom().isEmpty() ? "Tâche" : this.tache.getNom();
        int textWidth = fm.stringWidth(nom);
        int textX     = xb + (largeur - textWidth                   ) / 2;
        int textY     = yb + (hauteurCaseSuperieure + fm.getAscent()) / 2;
        g2d.drawString(nom, textX, textY);
    }

    /**
     * Vérifie si un point est contenu dans cette BoxShape
     */
    public boolean contient(Point point) 
    {
        return point.x >= this.x && point.x <= this.x + this.largeur &&
               point.y >= this.y && point.y <= this.y + this.hauteur;
    }

    public void supprimer(Tache tOrg, Tache tDest)
    {
        this.tache.getLstPrc().remove(tOrg );
        this.tache.getLstSvt().remove(tDest);
    }
    
    public JPanel creerPanel(BoxShape box)
    {
        return new JPanel()
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                box.dessinerExemple((Graphics2D) g);
            }
        };
    }
    
    public int calculerAire     () { return     largeur     * hauteur                            ; }
    public int calculerPerimetre() { return 2 * largeur + 4 * hauteur - 2 * hauteurCaseSuperieure; }


    /**
     * Calcule la position de la BoxShape selon la logique automatique ou manuelle
     */
    private void calculerPosition()
    {
        if (this.tache.getPosX() != 0 || this.tache.getPosY() != 0)
        {
            this.x = this.tache.getPosX();
            this.y = this.tache.getPosY();
            this.positionManuelle = true;
        } 
        else
        {
            this.calculerPositionAutomatique();
        }
        this.positionCalculee = true;
    }

    /**
     * Calcule la position automatique basée sur le niveau et la position dans le niveau
     */
    private void calculerPositionAutomatique()
    {
        int[] nbParNiveau = this.projet.getNbParNiveau(this.tache.getNiveau(), this.tache.getNom());
        int espacementHorizontal = 200; // Espacement horizontal entre les niveaux
        int espacementVertical   = 90; // Espacement vertical entre les tâches du même niveau

        // Les Marge de départ
        int margeX = 50;
        int margeY = 50;

        // Position X et Y basée sur le niveau et Y de la hauteur 
        this.x = margeX + (this.tache.getNiveau() * espacementHorizontal);
        this.y = margeY + (nbParNiveau[1] * espacementVertical);

        // Ajustement spécial pour Début et Fin (centrer verticalement)
        if (this.tache.getNom().equals("Début") || this.tache.getNom().equals("Fin")) 
        {
            int centreVertical = margeY + (this.projet.getTailleNivMax() * espacementVertical) / 2;
            this.y = centreVertical;
        }
    }
    
    private void dessinerTexte(Graphics2D g2d, int hauteurInferieure, int largeurCase) 
    {
        String nomTache = ( this.tache.getNom().length() >= 6 ? this.tache.getNom().substring(0, 6) +"...": this.tache.getNom() );


        // Texte case supérieure (nom de la tâche)
        if (!this.tache.getNom().isEmpty()) 
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);
            
            int textWidth = fm.stringWidth(nomTache);
            int textHeight = fm.getAscent();
            int textX = this.x + (largeur - textWidth) / 2;
            int textY = this.y + (hauteurCaseSuperieure + textHeight) / 2;
            g2d.drawString(nomTache, textX, textY);
        }
        
        // Texte case inférieure gauche (date min)
        if (!this.dateMin.trim().isEmpty())
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setColor(new Color(59, 185, 28));
            
            int textWidth = fm.stringWidth(this.dateMin);
            int textHeight = fm.getAscent();
            int textX = this.x + (largeurCase - textWidth) / 2;
            int textY = this.y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMin, textX, textY);
            g2d.drawString(this.dateMin, textX, textY);
        }
        
        // Texte case inférieure droite (date max)
        if (!this.dateMax.trim().isEmpty())
        {
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.setColor(Color.RED);
            
            int textWidth = fm.stringWidth(this.dateMax);
            int textHeight = fm.getAscent();
            int textX = this.x + largeurCase + (largeurCase - textWidth) / 2;
            int textY = this.y + hauteurCaseSuperieure + (hauteurInferieure + textHeight) / 2;
            g2d.drawString(this.dateMax, textX, textY);
        }
    }

    public String toString() 
    {
        return "BoxShape{ "            +
               "nom       = "          + this.tache.getNom()    + '\'' +
               ", x       = "          + this.x                 +
               ", y       = "          + this.y                 +
               ", largeur = "          + this.largeur           +
               ", hauteur = "          + this.hauteur           +
               ", niveau  = "          + this.tache.getNiveau() +
               ", positionManuelle = " + this.positionManuelle  +
               '}';
    }
}