package MPM.ihm;

import javax.swing.*;

public class FrameMPM extends JFrame
{
    private PanelMPM panelMPM;
    
    public FrameMPM()
    {
        this.setTitle("MPM");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.panelMPM = new PanelMPM(this);


        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.panelMPM);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/


        this.setVisible(true);
    }
}
