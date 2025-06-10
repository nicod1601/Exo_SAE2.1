package MPM.ihm;
import MPM.Controleur;
import javax.swing.*;

public class FrameNouveau extends JFrame
{
    private Controleur   ctrl;
    private FrameMPM     frame;
    private PanelNouveau panelNouveau;


    public FrameNouveau(FrameMPM frame,Controleur ctrl)
    {
        this.setTitle("Nouvelle Tache");
        this.setExtendedState(JFrame.NORMAL);
        this.setSize(500,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.ctrl  = ctrl;
        this.frame = frame;
        this.panelNouveau = new PanelNouveau(this.frame,this.ctrl,this);

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/

        this.add(this.panelNouveau);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.setVisible(false);
    }

    public void majTache(){this.panelNouveau.majTache();}
}