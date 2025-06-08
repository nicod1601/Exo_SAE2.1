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
    private FrameNouveau frame;

    public PanelNouveau(FrameMPM frameMPM,Controleur ctrl, FrameNouveau frame)
    {
        this.setLayout(new GridLayout(1, 2));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;
        this.frameMPM = frameMPM;
        this.frame = frame;

        JPanel panelInformation = new JPanel(new GridLayout(3, 1));

        JLabel lblNom   = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree = new JLabel("Duree Tache :", JLabel.RIGHT);

        JPanel panelNom      = new JPanel(new FlowLayout());
        JPanel panelDuree    = new JPanel(new FlowLayout());
        JPanel panelAction   = new JPanel();
        
        this.panelChoixPrc = new JPanel();
        this.panelChoixPrc.setLayout(new BoxLayout(this.panelChoixPrc, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPaneCheckbox = new JScrollPane(this.panelChoixPrc);
        scrollPaneCheckbox.setPreferredSize(new Dimension(200, 300));
        scrollPaneCheckbox.setBorder(BorderFactory.createTitledBorder("Prédécesseurs"));

        this.txtNom        = new JTextField(10);
        this.txtDuree      = new JTextField(10);
        this.btnCreerTache = new JButton("Creer Tache");
        this.btnCreerTache.setEnabled(false);

        this.lstTache = new ArrayList<Tache>();

        this.tabPrc = new ArrayList<JCheckBox>();

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/;

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
                if(! this.lstTache.get(cpt).getNom().equals("Début") && ! this.lstTache.get(cpt).getNom().equals("Fin"))
                {
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
                    
                    ArrayList<Tache> predecesseursSelectionnes = new ArrayList<>();
                    
                    for(int i = 0; i < this.tabPrc.size(); i++) 
                    {
                        JCheckBox cb = this.tabPrc.get(i);

                        if(cb.isSelected()) 
                        {
                            String nomPredecesseur = cb.getText();
                            
                            for(Tache tachePrecedente : this.lstTache)
                            {
                                if(tachePrecedente.getNom().equals(nomPredecesseur))
                                {
                                    predecesseursSelectionnes.add(tachePrecedente);
                                    break;
                                }
                            }
                        }
                    }
                    
                    for(Tache predecesseur : predecesseursSelectionnes)
                    {
                        ArrayList<Tache> successeursATransferer = new ArrayList<>(predecesseur.getLstSvt());
                        
                        for(Tache successeur : successeursATransferer) 
                        {
                            predecesseur.getLstSvt().remove(successeur);
                            successeur.getLstPrc().remove(predecesseur);

                        }
                        
                        nouvelleTache.addPrecedent(predecesseur);
                    }

                    this.ctrl.ajouterTache(nouvelleTache);

                    boolean tacheAjoutee = false;

                    for(Tache t : this.ctrl.getListeTache()) 
                    {
                        if(t.getNom().equals(nouvelleTache.getNom())) 
                        {
                            tacheAjoutee = true;
                            break;
                        }
                    }
                    
                    this.frameMPM.majList();

                    this.majTache();

                    this.frame.setVisible(false);
                    
                    
                    
                } 
                catch(NumberFormatException ex) 
                {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer une durée valide (nombre entier)", 
                                                "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                } 
                catch(Exception ex) 
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la création de la tâche: " + ex.getMessage(), 
                                                "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}