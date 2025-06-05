package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*;

public class FrameMPM extends JFrame
{
    
    private MaBarre menu;
    private PanelMPM panelMPM;
    private PanelBouton panelBouton;

    private FrameNouveau frameNouveau;
    private FrameTheme frameTheme;
    private FrameEnregistrerSous frameEnregistrerSous;

    private Controleur ctrl;

    private JScrollPane scrollPane;
    
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

        this.frameNouveau         = new FrameNouveau(this.ctrl);
        this.frameTheme           = new FrameTheme();
        this.frameEnregistrerSous = new FrameEnregistrerSous(this.ctrl);

        this.menu        = new MaBarre(this, this.ctrl);
        this.panelMPM    = new PanelMPM(this, this.ctrl);
        this.panelBouton = new PanelBouton(this.ctrl, this);
        this.scrollPane  = new JScrollPane(this.panelMPM);


        // utilisation des flèches sur la clavier
        this.panelMPM.setFocusable(true);
        this.panelMPM.requestFocusInWindow();

        this.scrollPane.getVerticalScrollBar().setUnitIncrement(50);
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

    public void setVisibleFrameNouveau(){this.frameNouveau.setVisible(true);}


    /**
     * Méthode permettant de mettre à jour la liste des tâches
     * dans le panel de gestion des tâches.
     */
    public void majList()          { this.panelMPM.majList();             }

    /**
     * Méthode permettant d'activer
     * les boutons du panel de gestion des tâches.'
     */
    public void activerBoutons()   { this.panelBouton.activerBouton();    }

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
    public void majTacheBox(ArrayList<Tache> t, ArrayList<BoxShape> b)
    { 
        this.panelBouton.majTacheBox(t,b); 
    }

    /**
     * Méthode permettant de mettre à jour le dessin
     * dans le panel de gestion des tâches.
     */
    public void majDessin() {this.panelMPM.majDessin();}

    /**
     * Méthode permettant de réinitialiser le panel de gestion des tâches.
     */
    public void reinitialiser() {this.panelMPM.reinitialiser(); }

    public void setVisibleFrameTheme(){this.frameTheme.setVisible(true);}

    public void setVisibleFrameEnregistrerSous(){this.frameEnregistrerSous.setVisible(true);}
}
