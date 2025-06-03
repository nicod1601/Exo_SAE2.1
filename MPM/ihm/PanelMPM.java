package MPM.ihm;

import java.awt.*;
import javax.swing.*;

public class PanelMPM extends JPanel 
{
	private MaBarre maBarre;
	private FrameMPM frame;


	public PanelMPM(FrameMPM frame) 
	{
		this.setLayout(new BorderLayout());
		/*--------------------------------------*/
		/*        Création des composants       */
		/*--------------------------------------*/
		this.frame   = frame;

		this.maBarre = new MaBarre(frame);
		


		/*--------------------------------------*/
		/*     Positionnement des composants    */
		/*--------------------------------------*/
		this.add(this.maBarre, BorderLayout.NORTH);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
