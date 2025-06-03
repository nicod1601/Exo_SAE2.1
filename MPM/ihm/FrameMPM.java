package MPM.ihm;

import javax.swing.*;
import MPM.Controleur;

public class FrameMPM extends JFrame
{
    private PanelMPM panelMPM;
    private Controleur ctrl;
    
    public FrameMPM(Controleur ctrl)
    {
        this.setTitle("MPM");
        this.setSize(800, 600);
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
}
