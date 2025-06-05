package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelNouveau extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JTextField txtNom;
    private JTextField txtDuree;
    private JButton btnCreerTache;

    private JPanel panelBox;
    private JPanel panelChoixPrc; 

    private BoxShape boxShape;
    private ArrayList<Tache> lstTache;

    private ArrayList<JCheckBox> tabPrc;
    private FrameMPM frameMPM;

    public PanelNouveau(FrameMPM frameMPM,Controleur ctrl)
    {
        this.setLayout(new GridLayout(1, 3));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;
        this.frameMPM = frameMPM;

        JPanel panelInformation = new JPanel(new GridLayout(3, 1));

        JLabel lblNom   = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree = new JLabel("Duree Tache :", JLabel.RIGHT);

        JPanel panelNom      = new JPanel(new GridLayout(1,2));
        JPanel panelDuree    = new JPanel(new GridLayout(1,2));
        JPanel panelAction   = new JPanel();
        
        this.panelChoixPrc = new JPanel();
        this.panelChoixPrc.setLayout(new BoxLayout(this.panelChoixPrc, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPaneCheckbox = new JScrollPane(this.panelChoixPrc);
        scrollPaneCheckbox.setPreferredSize(new Dimension(200, 300));
        scrollPaneCheckbox.setBorder(BorderFactory.createTitledBorder("Prédécesseurs"));

        this.txtNom        = new JTextField(10);
        this.txtDuree      = new JTextField(3);
        this.btnCreerTache = new JButton("Creer Tache");
        this.btnCreerTache.setEnabled(false);

        this.boxShape = new BoxShape(this.ctrl);
        this.panelBox = this.boxShape.creerPanel(this.boxShape);
        this.lstTache = new ArrayList<Tache>();

        this.tabPrc = new ArrayList<JCheckBox>();

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
        
        this.add(scrollPaneCheckbox);
        this.add(panelInformation);
        this.add(this.panelBox);

        

        /*--------------------------------------*/
        /*         Ajout des listeners          */
        /*--------------------------------------*/
        this.txtNom.addActionListener(this);
        this.txtDuree.addActionListener(this);
        this.btnCreerTache.addActionListener(this);
    }

    public void majTache()
    {
        // Nettoyer les anciens checkboxes
        this.panelChoixPrc.removeAll();
        this.tabPrc.clear();

        if(this.ctrl.getListeTache() != null)
        {
            this.lstTache = this.ctrl.getListeTache();

            for(int cpt =0; cpt < this.lstTache.size() ; cpt++)
            {
                System.out.println("Nom : " + this.lstTache.get(cpt).getNom());
                if(! this.lstTache.get(cpt).getNom().equals("Début") && ! this.lstTache.get(cpt).getNom().equals("Fin"))
                {
                    System.out.println("Ajout checkbox : " + this.lstTache.get(cpt).getNom());
                    JCheckBox checkbox = new JCheckBox(this.lstTache.get(cpt).getNom());
                    this.tabPrc.add(checkbox);
                    this.panelChoixPrc.add(checkbox);
                }
            }
        }
        
        // Rafraîchir l'affichage
        this.panelChoixPrc.revalidate();
        this.panelChoixPrc.repaint();

        this.verifLien();
    }

    public void majPanelBoxShape()
    {
         this.panelBox.repaint();
    }

    public void verifLien()
    {
        String lien = this.frameMPM.getLien();

        if(lien.equals("") || lien == null || lien.trim().equals(""))
        {
            this.btnCreerTache.setEnabled(false);
        }
        else
        {
            this.btnCreerTache.setEnabled(true);
        }
        
    }

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource() == this.txtNom)
        {
            this.boxShape.setNom(this.txtNom.getText());
            System.out.println("Lien : " + this.frameMPM.getLien());
            this.majPanelBoxShape();
        }

        if(!this.txtDuree.getText().equals("") && !this.txtNom.getText().equals(""))
        {

            if(e.getSource() == this.btnCreerTache)
            {
                try 
                {
                    Tache nouvelleTache = new Tache(this.txtNom.getText(), Integer.parseInt(this.txtDuree.getText()));
                    this.lstTache.add(nouvelleTache);
                    
                    BoxShape boxShape = new BoxShape(nouvelleTache, this.ctrl);
                    this.ctrl.ajouterTache(nouvelleTache);
                    
                    for(int i = 0; i < this.tabPrc.size(); i++)
                    {
                        JCheckBox cb = this.tabPrc.get(i);
                        if(cb.isSelected())
                        {
                            nouvelleTache.addPrecedent(this.lstTache.get(i + 1));
                            System.out.println("Ajout prédécesseur : " + this.lstTache.get(i + 1).getNom());
                            
                        }
                    }

                    this.boxShape.setNom(this.txtNom.getText());
                    this.majPanelBoxShape();

                    //this.ctrl.sauvegarderTaches(this.lstTache, this.frameMPM.getLien());

                    this.txtNom.setText("");
                    this.txtDuree.setText("");
                    
                    for(JCheckBox cb : this.tabPrc)
                    {
                        cb.setSelected(false);
                    }
                    
                    System.out.println("Tâche créée : " + nouvelleTache.getNom());

                    this.majTache();
                    this.frameMPM.refresh(this.frameMPM.getLien());

                    
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "La durée doit être un nombre entier", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}