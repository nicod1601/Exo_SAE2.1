package MPM.ihm;

import MPM.Controleur;
import java.awt.*;
import javax.swing.*;

public class PanelOptionParametre extends JPanel
{
    private JTabbedPane tabbedPane;
    private JPanel panelMPM;
    private JPanel panelFondComposants;
    private JPanel panelFondBox;
    private JPanel panelVoirLogue;
    private JButton btnQuitter;
    private JTextPane txtFichier;


    private FrameMPM frame;
    private Controleur ctrl;

    public PanelOptionParametre(FrameMPM frame, Controleur ctrl)
    {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(30, 30, 30));

        /*--------------------------*/
        /* Création des composants  */
        /*--------------------------*/
        this.frame = frame;
        this.ctrl  = ctrl;

        // Configuration des Panel et notre TabbedPane

        this.tabbedPane          = new JTabbedPane();
        this.panelMPM            = new JPanel();
        this.panelFondComposants = new JPanel();
        this.panelFondBox        = new JPanel();
        this.panelVoirLogue      = new JPanel();

        /* Ajout des onglets */
        this.tabbedPane.addTab("Informations MPM"              , this.panelMPM           );
        this.tabbedPane.addTab("Changer le fond des composants", this.panelFondComposants);
        this.tabbedPane.addTab("Changer le fond des boxes"     , this.panelFondBox       );
        this.tabbedPane.addTab("Voir le logue"                 , this.panelVoirLogue     );

        this.panelMPM           .setLayout(new GridLayout(1,2));
        this.panelFondComposants.setLayout(new BorderLayout());
        this.panelFondBox       .setLayout(new BorderLayout());
        this.panelVoirLogue     .setLayout(new BorderLayout());

        // PanelMPM
        JPanel panelFichier = new JPanel(new BorderLayout());
        this.txtFichier = new JTextPane();
        
        this.txtFichier.setEditable(false);
        
        panelFichier.add(this.txtFichier, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(this.txtFichier);

        this.panelMPM.add(new JLabel("Informations MPM", JLabel.CENTER));
        this.panelMPM.add(scrollPane);


        //PanelFondComposants
        this.panelFondComposants.add(new JLabel("Changer le fond des composants", JLabel.CENTER));


        /*--------------------------*/
        /* Position des composants  */
        /*--------------------------*/

        /* Position des composants */
        this.add(this.tabbedPane     , BorderLayout.CENTER);

        /*--------------------------*/
        /* Activation des composants*/
        /*--------------------------*/

        // ActionListener
        
        //ItemListener
        

    }

    public void majTxt()
    {
        String lien = this.frame.getLien();
        String infoFichier = this.ctrl.getFichier(lien);
        this.txtFichier.setText(infoFichier);
        this.txtFichier.setFont(new Font("Arial",Font.ROMAN_BASELINE, 15));
        
    }

    

    private void styleLabel(JLabel label) 
    {
        label.setForeground(Color.YELLOW); // Jaune militaire
        label.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void styleButton(JButton button)
    {
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.YELLOW);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        button.setFocusPainted(false);
    }

    private void styleRadioButton(JRadioButton radio)
    {
        radio.setBackground(new Color(50, 50, 50));
        radio.setForeground(Color.YELLOW);
        radio.setFocusPainted(false);
        radio.setFont(new Font("Arial", Font.BOLD, 12));
        radio.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        radio.setOpaque(true);
    }

    private void styleTextField(JTextField field)
    {
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.YELLOW);
        field.setFont(new Font("Arial", Font.BOLD, 12));
        field.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
    }

    private void stylePanel(JPanel panel)
    {
        panel.setBackground(new Color(50, 50, 50));
    }
}