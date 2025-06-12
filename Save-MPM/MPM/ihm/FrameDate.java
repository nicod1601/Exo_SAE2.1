package MPM.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameDate extends JFrame implements ActionListener
{

    private JTextField txtDebut;
    private JButton    btnAnnuler;
    private JButton    btnConfirmer;

    private PanelBouton panelBouton;
    private PanelMPM panelMPM;


    public FrameDate(PanelBouton panelBouton, PanelMPM panelMPM)
    {
        this.setTitle("Changement en dates");
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setSize(350,150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridLayout(3,1) );

        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                FrameDate.this.dispose();
                FrameDate.this.panelBouton.getRbDuree().setSelected(true);
                
            }
        });

        //Création

        this.panelBouton = panelBouton;
        this.panelMPM    = panelMPM;

        JPanel panelTexte = new JPanel();
        JPanel panelBtn   = new JPanel();

        this.txtDebut     = new JTextField(10);

        this.btnAnnuler   = new JButton("Annuler" );
        this.btnConfirmer = new JButton("Confirmer");

        //Positionnement

        panelTexte.add(this.txtDebut );
        panelBtn  .add(this.btnConfirmer);
        panelBtn  .add(this.btnAnnuler);

        this.add( new JLabel("Veuillez indiquer une date de début pour votre projet", SwingConstants.CENTER));

        this.add(panelTexte);
        this.add(panelBtn);

        //Activation

        this.btnAnnuler  .addActionListener(this);
        this.btnConfirmer.addActionListener(this);

        this.setVisible(true);


    }

    public void changerAffichage()
    {

    }
    

    public void actionPerformed(ActionEvent e)
    { 
        if (e.getSource() == this.btnAnnuler)
        {
            this.dispose();
            this.panelBouton.getRbDuree().setSelected(true);
            
        } 

        if (e.getSource() == this.btnConfirmer) this.changerAffichage();

        
    }
}