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
    private JButton    btnCreerTache;
    private JCheckBox  chkInserer;  // Nouveau checkbox pour l'insertion

    private JPanel     panelBox;
    private JPanel     panelChoixPrc; 

    private BoxShape         boxShape;
    private JPanel panelSuivant;
    private ArrayList<JCheckBox> tabSvt;
    private ArrayList<Tache> lstSvt;
    private ArrayList<Tache> lstTache;
    private JScrollPane scrollPaneCheckboxSuivant;

    private ArrayList<JCheckBox> tabPrc;
    private FrameMPM             frameMPM;
    private FrameNouveau         frame;

    public PanelNouveau(FrameMPM frameMPM,Controleur ctrl, FrameNouveau frame)
    {
        this.setLayout(new GridLayout(1, 3));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl     = ctrl;
        this.frameMPM = frameMPM;
        this.frame    = frame;

        JPanel panelAction      = new JPanel();
        JPanel panelNom         = new JPanel(new FlowLayout() );
        JPanel panelDuree       = new JPanel(new FlowLayout() );
        JPanel panelInserer     = new JPanel(new FlowLayout() );  // Nouveau panel pour le checkbox
        JPanel panelInformation = new JPanel(new GridLayout(4, 1) );  // Modifié de 3 à 4 lignes

        JLabel lblNom   = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree = new JLabel("Duree Tache :", JLabel.RIGHT);

        this.panelChoixPrc = new JPanel();
        this.panelChoixPrc.setLayout(new BoxLayout(this.panelChoixPrc, BoxLayout.Y_AXIS) );
        
        JScrollPane scrollPaneCheckbox = new JScrollPane(this.panelChoixPrc);

        scrollPaneCheckbox.setPreferredSize(new Dimension(200, 300) );
        scrollPaneCheckbox.setBorder       (BorderFactory.createTitledBorder("Prédécesseurs") );

        this.txtNom        = new JTextField(10);
        this.txtDuree      = new JTextField(10);
        this.btnCreerTache = new JButton("Créer Tache");
        this.chkInserer    = new JCheckBox("Insérer");

        this.panelSuivant = new JPanel();
        this.panelSuivant.setLayout(new BoxLayout(this.panelSuivant, BoxLayout.Y_AXIS)); // Ajout du layout
        this.scrollPaneCheckboxSuivant = new JScrollPane(this.panelSuivant);

        this.scrollPaneCheckboxSuivant.setPreferredSize(new Dimension(200, 300) );
        this.scrollPaneCheckboxSuivant.setBorder       (BorderFactory.createTitledBorder("Suivant") );
        

        this.lstTache = new ArrayList<Tache>    ();
        this.lstSvt   = new ArrayList<Tache>    ();
        this.tabPrc   = new ArrayList<JCheckBox>();
        this.tabSvt   = new ArrayList<JCheckBox>();

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/;

        panelNom.add(lblNom     );
        panelNom.add(this.txtNom);

        panelDuree.add(lblDuree     );
        panelDuree.add(this.txtDuree);

        panelInserer.add(this.chkInserer);

        panelAction.add(this.btnCreerTache);
        
        panelInformation.add(panelNom   );
        panelInformation.add(panelDuree );
        panelInformation.add(panelInserer);
        panelInformation.add(panelAction);
        
        this.add(scrollPaneCheckbox);
        this.add(panelInformation  );
        /*--------------------------------------*/
        /*         Ajout des listeners          */
        /*--------------------------------------*/

        this.txtNom       .addActionListener(this);
        this.txtDuree     .addActionListener(this);
        this.btnCreerTache.addActionListener(this);
        this.chkInserer   .addActionListener(this);
    }

    public void ajouterPanelLstSuivant()
    {
        this.add(this.scrollPaneCheckboxSuivant);

        this.revalidate();
        this.repaint();
    }

    public void retirerPanelLstSuivant()
    {
        this.remove(this.scrollPaneCheckboxSuivant);

        this.revalidate();
        this.repaint();
    }

    public void majTacheSuivant(Tache tPrc)
    {
        System.out.println("majTacheSuivant appelé pour la tâche: " + tPrc.getNom());
        System.out.println("Nombre de tâches suivantes: " + tPrc.getLstSvt().size());
        
        this.panelSuivant.removeAll();
        this.lstSvt.clear();
        this.tabSvt.clear();

        for(int cpt = 0; cpt < tPrc.getLstSvt().size(); cpt++)
        {
            this.lstSvt.add(tPrc.getLstSvt().get(cpt));
            JCheckBox checkbox = new JCheckBox(this.lstSvt.get(cpt).getNom());
            
            // AJOUT : Ajouter un ActionListener à chaque checkbox suivant
            checkbox.addActionListener(this);
            
            System.out.println("Création du checkbox pour: " + this.lstSvt.get(cpt).getNom());

            this.tabSvt.add(checkbox);
            this.panelSuivant.add(checkbox);
        }

        this.panelSuivant.revalidate();
        this.panelSuivant.repaint   ();
        
        System.out.println("Nombre total de checkboxes suivants créés: " + this.tabSvt.size());
    }

    public void majTache()
    {
        // Nettoyer les anciens checkboxes prc
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

                    checkbox.addActionListener(this);

                    this.tabPrc       .add(checkbox);
                    this.panelChoixPrc.add(checkbox);
                }
            }
        }

        // Rafraîchir l'affichage
        this.panelChoixPrc.revalidate();
        this.panelChoixPrc.repaint   ();

        this.verifLien();
    }

    public void verifLien()
    {
        String lien = this.frameMPM.getLien();

        if(lien.equals("") || lien == null || lien.trim().equals("")) 
            this.btnCreerTache.setEnabled(false);
        else 
            this.btnCreerTache.setEnabled(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        // AJOUT : Vérifier si le clic provient d'un checkbox suivant
        if(this.tabSvt.contains(e.getSource()))
        {
            JCheckBox checkboxClique = (JCheckBox) e.getSource();
            System.out.println("Clic détecté sur le suivant: " + checkboxClique.getText());
            System.out.println("État: " + (checkboxClique.isSelected() ? "Sélectionné" : "Désélectionné"));
            
            // Ici vous pouvez ajouter votre logique métier
            // Par exemple :
            if(checkboxClique.isSelected())
            {
                System.out.println("Tâche suivante sélectionnée: " + checkboxClique.getText());
                // Faire quelque chose quand la tâche est sélectionnée
            }
            else
            {
                System.out.println("Tâche suivante désélectionnée: " + checkboxClique.getText());
                // Faire quelque chose quand la tâche est désélectionnée
            }
            
            return; // Sortir pour éviter de traiter les autres conditions
        }
        
        // Gestion des changements d'état des checkboxes prédécesseurs et du checkbox insérer
        if(e.getSource() == this.chkInserer || this.tabPrc.contains(e.getSource()))
        {
            if(!this.txtDuree.getText().equals("") && !this.txtNom.getText().equals(""))
            {
                boolean coche = false;
                Tache tmp = null;

                // Vérifier si on est en mode insertion et qu'un prédécesseur est sélectionné
                if(this.chkInserer.isSelected())
                {
                    for(JCheckBox cb : this.tabPrc)
                    {
                        if(cb.isSelected())
                        {
                            coche = true;
                            // Trouver la tâche correspondante
                            for(int cpt = 0; cpt < this.lstTache.size(); cpt++)
                            {
                                if(this.lstTache.get(cpt).getNom().equals(cb.getText()))
                                {
                                    tmp = this.lstTache.get(cpt);
                                    break; // Sortir de la boucle une fois trouvé
                                }
                            }
                            break; // Prendre seulement le premier sélectionné pour l'exemple
                        }
                    }
                }

                if(coche && tmp != null)
                {
                    this.majTacheSuivant(tmp);
                    this.ajouterPanelLstSuivant();
                }
                else
                {
                    this.retirerPanelLstSuivant();
                }
            }
        }
        // Gestion du bouton créer tâche
        else if(e.getSource() == this.btnCreerTache)
        {
            if(!this.txtDuree.getText().equals("") && !this.txtNom.getText().equals(""))
            {
                try 
                {
                    Tache           nouvelleTache              = new Tache(this.txtNom.getText(), Integer.parseInt(this.txtDuree.getText() ) );
                    ArrayList<Tache> predecesseursSelectionnes = new ArrayList<>();
                    boolean         inserer                    = this.chkInserer.isSelected();  // Récupération de l'état du checkbox
                    
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
                        nouvelleTache.addPrecedent(predecesseur);
                    }

                    // Si on est en mode insertion, gérer les tâches suivantes sélectionnées
                    for(int i = 0; i < this.tabSvt.size(); i++)
                    {
                        JCheckBox cbSvt = this.tabSvt.get(i);
                        System.out.println("Checkbox " + i + ": " + cbSvt.getText() + " - Sélectionné: " + cbSvt.isSelected());
                        if(cbSvt.isSelected())
                        {
                            System.out.println("Tâche suivante sélectionnée: " + cbSvt.getText());
                            // Ajouter la logique pour gérer les tâches suivantes sélectionnées
                            // selon votre besoin métier
                        }
                    }

                    this.ctrl.ajouterTache(nouvelleTache, inserer);

                    boolean tacheAjoutee = false;

                    for(Tache t : this.ctrl.getListeTache()) 
                    {
                        if(t.getNom().equals(nouvelleTache.getNom())) 
                        {
                            tacheAjoutee = true;
                            break;
                        }
                    }
                    
                    this.frameMPM.majList   ();
                    this         .majTache  ();
                    this.frame   .setVisible(false);
                    
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
            else
            {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}