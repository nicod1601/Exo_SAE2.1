package MPM.ihm;

import MPM.Controleur;
import javax.swing.*;

public class FrameMPM extends JFrame
{
    private PanelMPM panelMPM;
    private Controleur ctrl;
    
    public FrameMPM(Controleur ctrl)
    {
        this.setTitle("MPM");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.ctrl = ctrl;

        this.panelMPM = new PanelMPM(this, this.ctrl);


        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.panelMPM);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/


        this.setVisible(true);
    }

    public void majList()
    {
        this.panelMPM.majList();
    }

    public void reinitialiser()
    {
        this.panelMPM.reinitialiser();
    }
}
