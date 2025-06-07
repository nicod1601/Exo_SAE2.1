package MPM.ihm;

import javax.swing.*;

public class FrameTheme extends JFrame
{
    private PanelTheme panelTheme;
   

    public FrameTheme()
    {
        this.setTitle("Theme");
        this.setExtendedState(JFrame.NORMAL);
        this.setSize(800,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        


        this.panelTheme = new PanelTheme();


        this.add(this.panelTheme);


        this.setVisible(false);
    }
}
