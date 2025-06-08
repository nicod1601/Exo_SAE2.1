package MPM.ihm;
import MPM.Controleur;
import MPM.metier.*;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class PanelModifier extends JPanel implements ActionListener
{
    private JTextField txtNomTache;
    private JTextField txtDureeTache;
    private JButton btnModifier;

    private Tache tache;

    private FrameMPM frameMPM;
    private Controleur ctrl;
    private FrameModifier frame;


    public PanelModifier(Controleur ctrl, FrameMPM frameMPM, FrameModifier frame)
    {
        this.setLayout(new GridLayout(4, 1));

        this.ctrl     = ctrl;
        this.frameMPM = frameMPM;
        this.frame    = frame;

        this.tache = null;


        JLabel lblTitre    = new JLabel("Modifier une tache", JLabel.CENTER);
        JPanel panelNom    = new JPanel(new FlowLayout());
        JPanel panelDuree  = new JPanel(new FlowLayout());
        JLabel lblNom      = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree    = new JLabel("Duree Tache :", JLabel.RIGHT);
        this.txtNomTache   = new JTextField(10);
        this.txtDureeTache = new JTextField(10);
        JPanel panelBtn    = new JPanel(new FlowLayout());
        this.btnModifier   = new JButton("Modifier");

        panelNom.add(lblNom);
        panelNom.add(txtNomTache);

        panelDuree.add(lblDuree);
        panelDuree.add(txtDureeTache);

        panelBtn.add(btnModifier);


        this.add(lblTitre);
        this.add(panelNom);
        this.add(panelDuree);
        this.add(panelBtn);

        this.btnModifier.addActionListener(this);
    }

    public void setModifTache(Tache tache)
    {
        this.tache = tache;
        this.txtNomTache.setText(tache.getNom());
        this.txtDureeTache.setText(String.valueOf(tache.getDuree()));
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == this.btnModifier)
        {
            this.ctrl.modifierTache(this.txtNomTache.getText(), Integer.parseInt(this.txtDureeTache.getText()), this.tache);
            this.frameMPM.majList();
            this.frame.dispose();
            this.txtNomTache.setText("");
            this.txtDureeTache.setText("");
        }
    }
}
