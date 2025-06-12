package MPM.ihm;
import MPM.Controleur;
import javax.swing.*;

public class FrameOption extends JFrame
{
    private PanelOptionParametre panel;
    private FrameMPM frame;
    private Controleur ctrl;

    public FrameOption(FrameMPM frame, Controleur ctrl)
    {
        this.setTitle("Option");
        this.setExtendedState(JFrame.NORMAL);
        this.setSize(800,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.frame = frame;
        this.ctrl  = ctrl;
        this.panel = new PanelOptionParametre(this.frame,this.ctrl);

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/

        this.add(this.panel);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.setVisible(false);
    }

    public void majTxt(){ this.panel.majTxt(); }
}
