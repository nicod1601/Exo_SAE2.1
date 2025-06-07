package MPM.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import MPM.Controleur;

public class PanelTheme extends JPanel implements ActionListener
{
    private JButton[][] tabButtons;
    private Color[][] tabCouleur;

    private JCheckBox cbBoutton;
    private JCheckBox cbBoxShape;
    private JCheckBox cbFleche;



    public PanelTheme()
    {
        
        this.setLayout(new BorderLayout());

        this.tabCouleur = new Color[][] 
        {
            { new Color(255, 0, 0)    , new Color(220, 20, 60)  , new Color(178, 34, 34)   },
            { new Color(255, 165, 0)  , new Color(255, 140, 0)  , new Color(255, 69, 0)    },
            { new Color(255, 255, 0)  , new Color(255, 215, 0)  , new Color(255, 165, 0)   },
            { new Color(0, 255, 0)    , new Color(101, 180, 0)  , new Color(34, 139, 34)   },
            { new Color(0, 255, 255)  , new Color(0, 191, 255)  , new Color(30, 144, 255)  },
            { new Color(0, 0, 255)    , new Color(25, 25, 112)  , new Color(72, 61, 139)   },
            { new Color(128, 0, 128)  , new Color(147, 112, 219), new Color(138, 43, 226)  },
            { new Color(255, 192, 203), new Color(255, 20, 147) , new Color(199, 21, 133)  },
            { new Color(255, 255, 255), new Color(192, 192, 192), new Color(128, 128, 128) },
            { new Color(0, 0, 0)      , new Color(64, 64, 64)   , new Color(105, 105, 105) }
        };

        this.tabButtons      = new JButton[this.tabCouleur.length][this.tabCouleur[0].length];
        JPanel panelCouleur  = new JPanel(new GridLayout(this.tabCouleur.length, this.tabCouleur[0].length,5,5));
        JPanel panelCheckbox = new JPanel(new GridLayout(4,1));

        for(int cpt = 0; cpt < this.tabCouleur.length; cpt++)
        {
            for(int j = 0; j < this.tabCouleur[cpt].length; j++)
            {
                this.tabButtons[cpt][j] = new JButton();
                this.tabButtons[cpt][j].setBackground(this.tabCouleur[cpt][j]);
                this.tabButtons[cpt][j].addActionListener(this);
            }
        }

        this.cbBoutton  = new JCheckBox("Boutton");
        this.cbBoxShape = new JCheckBox("BoxShape");
        this.cbFleche   = new JCheckBox("Fleche");

        



        /*ajout */
        for(int cpt = 0; cpt < this.tabCouleur.length; cpt++)
        {
            for(int j = 0; j < this.tabCouleur[cpt].length; j++)
            {
                panelCouleur.add(this.tabButtons[cpt][j]);
            }
        }
        panelCheckbox.add(this.cbBoutton);
        panelCheckbox.add(this.cbBoxShape);
        panelCheckbox.add(this.cbFleche);

        this.add(panelCheckbox, BorderLayout.WEST);

        this.add(panelCouleur);
    }

	@Override
	public void actionPerformed(ActionEvent e) 
    {
		for(int l =0; l < this.tabButtons.length; l++)
        {
            for(int h = 0; h < this.tabButtons[l].length; h++)
            {
                if(e.getSource() == this.tabButtons[l][h])
                {
                    Color couleur = this.tabButtons[l][h].getBackground(); 

                    
                }
            }
        }
	}
}
