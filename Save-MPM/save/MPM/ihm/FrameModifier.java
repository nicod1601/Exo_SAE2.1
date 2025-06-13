package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class FrameModifier extends JFrame
{
    private PanelModifier panelModifier;

    private FrameMPM frame;
    private Controleur ctrl;
    
    public FrameModifier(FrameMPM frame,Controleur ctrl)
    {
        this.setTitle("Modifer Tache");
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.ctrl = ctrl;
        this.frame = frame;

        this.panelModifier = new PanelModifier(this.ctrl, this.frame, this);

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/
        this.add(this.panelModifier);

        this.setVisible(false);
    }

    public void setPosition(int x, int y)
    {
        this.setLocation(x, y + 120);
    }

    public void setModifTache(Tache tache)
    {
        this.panelModifier.setModifTache(tache);
    }


}