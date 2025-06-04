package MPM.ihm;
import MPM.Controleur;
import javax.swing.*;
import java.awt.*;

public class FrameNouveau extends JFrame
{
    private Controleur ctrl;

    private PanelNouveau panelNouveau;


    public FrameNouveau(Controleur ctrl)
    {
        this.setTitle("Nouvelle Tache");
        this.setExtendedState(JFrame.NORMAL);
        this.setSize(600,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;
        this.panelNouveau = new PanelNouveau(this.ctrl);

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/
        this.add(this.panelNouveau);


        this.setVisible(false);
    }



}