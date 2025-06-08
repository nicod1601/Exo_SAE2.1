package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PanelOptionParametre extends JPanel implements ActionListener
{
    private JTabbedPane tabbedPane;
    private JPanel panelMPM;
    private JPanel panelFondComposants;
    private JPanel panelFondBox;
    private JButton btnQuitter;
    private JTextPane txtFichier;
    private JTextField txtLargeur;
    private JTextField txtHauteur;
    private JButton[] btnCouleur;
    private Color sauvColor;
    private BoxShape box;
    private JButton btnValider;


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

        /* Ajout des onglets */
        this.tabbedPane.addTab("Informations MPM"              , this.panelMPM           );
        this.tabbedPane.addTab("Changer le fond des composants", this.panelFondComposants);
        this.tabbedPane.addTab("Changer les boxes"             , this.panelFondBox       );

        this.panelMPM           .setLayout(new GridLayout(1,2));
        this.panelFondComposants.setLayout(new BorderLayout());
        this.panelFondBox       .setLayout(new GridLayout(1,2));

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

        //PanelFondBox
        JPanel panelDonne     = new JPanel(new GridLayout(4,1));
        JPanel panelLargeur = new JPanel();
        JPanel panelHauteur = new JPanel();

        this.txtLargeur        = new JTextField(10);
        JLabel lblLarg      = new JLabel("Largeur :", JLabel.RIGHT);

        this.txtHauteur        = new JTextField(10);
        JLabel lblHauteur      = new JLabel("Hauteur :", JLabel.RIGHT);

        this.btnValider       = new JButton("Valider");
        JPanel panelValider   = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelValider.add(this.btnValider);

        panelLargeur.add(lblLarg);
        panelLargeur.add(this.txtLargeur);

        panelHauteur.add(lblHauteur);
        panelHauteur.add(this.txtHauteur);

        //---------

        Fond couleur = new Fond();

        this.btnCouleur = new JButton[couleur.getTailleSimple()];

        JPanel panelCouleur   = new JPanel(new GridLayout(1, this.btnCouleur.length, 2 ,2));
        panelCouleur.setBackground(Color.BLACK);
        JScrollPane scrollPaneCouleur = new JScrollPane(panelCouleur);

        box = new BoxShape(this.ctrl);

        for(int cpt = 0; cpt < this.btnCouleur.length; cpt++)
        {
            this.btnCouleur[cpt] = new JButton();
            this.btnCouleur[cpt].setBackground(couleur.getCouleurSimple(cpt));
            panelCouleur.add(this.btnCouleur[cpt]);
        }

        panelDonne.add(panelLargeur);
        panelDonne.add(panelHauteur);
        panelDonne.add(scrollPaneCouleur);
        panelDonne.add(panelValider);

        JPanel panelBox = new JPanel(new BorderLayout());
        panelBox.setOpaque(true);
        panelBox.add(box.creerPanel(box), BorderLayout.CENTER);

        this.panelFondBox.add(panelDonne);
        this.panelFondBox.add(panelBox);
        




        /*--------------------------*/
        /* Position des composants  */
        /*--------------------------*/

        /* Position des composants */
        this.add(this.tabbedPane     , BorderLayout.CENTER);
        

        /*--------------------------*/
        /* Activation des composants*/
        /*--------------------------*/

        /* Activation des composants */
        this.btnValider.addActionListener(this);
        this.txtLargeur.addActionListener(this);
        this.txtHauteur.addActionListener(this);

        for(int cpt = 0; cpt < this.btnCouleur.length; cpt++)
        {
            this.btnCouleur[cpt].addActionListener(this);
        }


        

    }

    public void majPanel()
    {
        this.panelFondBox.remove(1);
        this.panelFondBox.add(box.creerPanel(box), BorderLayout.CENTER);
        this.panelFondBox.revalidate();
        this.panelFondBox.repaint();
        
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.txtLargeur)
        {
            if(this.estEntier(this.txtLargeur.getText()))
            {
                this.box.setLargeur(Integer.parseInt(this.txtLargeur.getText()));
                this.majPanel();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "La Taille doit etre un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        if(e.getSource() == this.txtHauteur)
        {
            System.out.println(this.txtHauteur.getText());
            if(this.estEntier(this.txtHauteur.getText()))
            {
                this.box.setHauteur(Integer.parseInt(this.txtHauteur.getText()));
                this.majPanel();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "La Taille doit etre un entier", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        for(JButton btn : this.btnCouleur)
        {
            if(e.getSource() == btn)
            {
                System.out.println("Couleur : " + btn.getBackground());
                this.box.setCouleur(btn.getBackground());
                this.sauvColor = btn.getBackground();
                this.majPanel();
            }
        }

        if(e.getSource() == this.btnValider)
        {
            this.frame.setModifBocks(this.box.getLargeur(), this.box.getHauteur(), this.box.getCouleur());
        }
    }

    public boolean estEntier(String entier)
    {
        try
        {
            Integer.parseInt(entier);
            return true;
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
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
        label.setForeground(new Color(97, 175, 239)); // Bleu clair moderne
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void styleButton(JButton button)
    {
        button.setBackground(new Color(60, 63, 65)); // Gris foncé
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Bleu acier
        button.setFocusPainted(false);
    }

    private void styleRadioButton(JRadioButton radio)
    {
        radio.setBackground(new Color(40, 44, 52)); // Fond sombre
        radio.setForeground(Color.LIGHT_GRAY);
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        radio.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        radio.setFocusPainted(false);
        radio.setOpaque(true);
    }

    private void styleTextField(JTextField field)
    {
        field.setBackground(new Color(60, 63, 65));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(97, 175, 239));
        field.setFont(new Font("Consolas", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
    }

    private void stylePanel(JPanel panel)
    {
        panel.setBackground(new Color(40, 44, 52));
    }

    private void styleScrollPane(JScrollPane scrollPane)
    {
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));
        scrollPane.getViewport().setBackground(new Color(60, 63, 65));
    }
}