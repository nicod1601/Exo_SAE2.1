package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class FrameMPM extends JFrame
{
    private Controleur    ctrl;

    private FrameNouveau  frameNouveau;
    private FrameOption   frameOption;
    private FrameModifier frameModifier;

    private PanelMPM      panelMPM;
    private PanelBouton   panelBouton;

    private MaBarre       menu;
    private JScrollPane   scrollPane;
    private String        lien;
    
    public FrameMPM(Controleur ctrl)
    {
        this.setTitle("MPM");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.ctrl = ctrl;
        this.lien = " ";

        this.frameNouveau  = new FrameNouveau (this, this.ctrl);
        this.frameOption   = new FrameOption  (this, this.ctrl);
        this.frameModifier = new FrameModifier(this, this.ctrl);

        this.panelMPM      = new PanelMPM     (this, this.ctrl);
        this.panelBouton   = new PanelBouton  (this, this.ctrl);

        this.menu          = new MaBarre      (this, this.ctrl);
        this.scrollPane    = new JScrollPane  (this.panelMPM  );


        // utilisation des flèches sur la clavier
        this.panelMPM  .setFocusable(true);
        this.panelMPM  .requestFocusInWindow();

        this.scrollPane.getVerticalScrollBar  ().setUnitIncrement(50);
        this.scrollPane.getHorizontalScrollBar().setUnitIncrement(50);

        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.menu       , BorderLayout.NORTH);
        this.add(this.scrollPane ,BorderLayout.CENTER);
        this.add(this.panelBouton, BorderLayout.SOUTH);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.setVisible(true);
    }

    //public void refresh(String lien) {this.menu.refresh(lien);}
    public String getLien(){ return this.lien;}

    public void setVisibleFrameNouveau()
    {
        this.frameNouveau.majTache();
        this.frameNouveau.setVisible(true);
    }

    public void setLien              (String lien)                              { this.lien = lien;                                       }
    public void setVisibleFrameOption()                                         { this.frameOption.setVisible(true);                      }
    public void setModifBocks        (int largeur, int hauteur, Color couleur)  { this.panelMPM.setModifBocks(largeur, hauteur, couleur); }
    public void setVisibleFrameModif ()                                         { this.frameModifier.setVisible(true);                    }
    public void setPosition          (int x, int y)                             { this.frameModifier.setPosition(x, y);                   }
    public void setModifTache        (Tache tache)                              { this.frameModifier.setModifTache(tache);                }


    /**
     * Méthode permettant de mettre à jour le texte
     * dans le panel de gestion des tâches.
     */
    public void majTxt () { this.frameOption.majTxt(); }

    /**
     * Méthode permettant de mettre à jour la liste des tâches
     * dans le panel de gestion des tâches.
     */
    public void majList() { this.panelMPM.majList(); }

    /**
     * Méthode permettant d'activer
     * les boutons du panel de gestion des tâches.'
     */
    public void activerBoutons() { this.panelBouton.activerBouton(); }

    /**
     * Méthode permettant de désactiver
     * les boutons du panel de gestion des tâches.
     */
    public void desactiverBoutons(){ this.panelBouton.desactiverBouton(); }

    /**
     * Méthode permettant de mettre à jour la liste des tâches
     * dans le panel de gestion des tâches.
     * @param t Liste des tâches
     * @param b Liste des BoxShape
     */
    public void majTacheBox(ArrayList<Tache> t, ArrayList<BoxShape> b) { this.panelBouton.majTacheBox(t,b); }

    /**
     * Méthode permettant de mettre à jour le dessin
     * dans le panel de gestion des tâches.
     */
    public void majDessin() { this.panelMPM.majDessin(); }

    /**
     * Méthode permettant de réinitialiser le panel de gestion des tâches.
     */
    public void reinitialiser () { this.panelMPM.reinitialiser();   }
    public void boutonDeBase  () { this.panelBouton.boutonDeBase(); }

    /**
     * Méthode permettant de mettre à jour le panel de gestion des tâches
     * avec les chemins critiques.
     * @param chemin Liste des chemins critiques
     */
    public void setCheminCritiques(ArrayList<CheminCritique> chemin) { this.panelMPM.setCheminCritiques(chemin); }

    public void resetDefaut() { this.panelMPM.resetDefaut(); }

}