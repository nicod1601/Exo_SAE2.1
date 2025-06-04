package MPM.ihm;

import MPM.Controleur;
import javax.swing.*;

public class FrameMPM extends JFrame
{
    private PanelMPM panelMPM;
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

        this.panelMPM   = new PanelMPM(this, this.ctrl);
        this.scrollPane = new JScrollPane(this.panelMPM);



        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.scrollPane);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/


        this.setVisible(true);
    }

    public void majList(){ this.panelMPM.majList(); }

    public void activerBoutons(){ this.panelMPM.activerBouton(); }

    public void reinitialiser() {this.panelMPM.reinitialiser(); }
}
