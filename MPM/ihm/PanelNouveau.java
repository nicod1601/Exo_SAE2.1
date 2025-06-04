package MPM.ihm;

import java.awt.*;
import javax.swing.*;
import MPM.Controleur;
import MPM.metier.*;
import java.awt.event.*;
public class PanelNouveau extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JTextField txtNom;
    private JTextField txtDuree;
    private JButton btnCreerTache;

    private JPanel panelBox;

    private BoxShape boxShape;
    private Projet projet;


    public PanelNouveau(Controleur ctrl)
    {
        this.setLayout(new GridLayout(1, 2));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;

        JPanel panelInformation = new JPanel(new GridLayout(3, 1) );

        JLabel lblNom   = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree = new JLabel("Duree Tache :", JLabel.RIGHT);

        JPanel panelNom    = new JPanel(new GridLayout(1,2));
        JPanel panelDuree  = new JPanel(new GridLayout(1,2));
        JPanel panelAction = new JPanel();


        this.txtNom        = new JTextField(10);
        this.txtDuree      = new JTextField(3);
        this.btnCreerTache = new JButton("Creer Tache");

        this.boxShape = new BoxShape(this.ctrl);
        this.panelBox = this.boxShape.creerPanel(this.boxShape);
        this.projet   = new Projet();

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/
        panelNom.add(lblNom);
        panelNom.add(this.txtNom);

        panelDuree.add(lblDuree);
        panelDuree.add(this.txtDuree);

        panelAction.add(this.btnCreerTache);
        
        panelInformation.add(panelNom);
        panelInformation.add(panelDuree);
        panelInformation.add(panelAction);
        
        this.add(panelInformation);
        this.add(this.panelBox);


        /*--------------------------------------*/
        /*         Ajout des listeners          */
        /*--------------------------------------*/
        this.txtNom.addActionListener(this);
        this.txtDuree.addActionListener(this);
        this.btnCreerTache.addActionListener(this);
    }

    public void majPanelBoxShape()
    {
         this.panelBox.repaint();
    }

    public void actionPerformed ( ActionEvent e )
    {
        if(e.getSource() == this.txtNom)
        {
            this.boxShape.setNom(this.txtNom.getText());
            this.majPanelBoxShape();
        }

        if(! this.txtDuree.getText().equals("") && ! this.txtNom.getText().equals(""))
        {
            if(e.getSource() == this.btnCreerTache)
            {
                System.out.println("Nom : " + this.txtNom.getText());
                System.out.println("Duree : " + this.txtDuree.getText());
                this.boxShape.setNom(this.txtNom.getText());
                this.majPanelBoxShape();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

