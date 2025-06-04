package MPM.ihm;

import java.awt.*;
import javax.swing.*;
import MPM.Controleur;
import MPM.metier.*;
import java.awt.event.*;
import java.util.ArrayList;
public class PanelNouveau extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JTextField txtNom;
    private JTextField txtDuree;
    private JButton btnCreerTache;

    private JList<String> lstChoix;
    private DefaultListModel<String> listModel;

    private JPanel panelBox;

    private BoxShape boxShape;
    private ArrayList<Tache> lstTache;


    public PanelNouveau(Controleur ctrl)
    {
        this.setLayout(new GridLayout(1, 3));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;

        JPanel panelInformation = new JPanel(new GridLayout(3, 1) );

        JLabel lblNom   = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree = new JLabel("Duree Tache :", JLabel.RIGHT);

        JPanel panelNom      = new JPanel(new GridLayout(1,2));
        JPanel panelDuree    = new JPanel(new GridLayout(1,2));
        JPanel panelAction   = new JPanel();
        JPanel panelChoixPrc = new JPanel();


        this.txtNom        = new JTextField(10);
        this.txtDuree      = new JTextField(3);
        this.btnCreerTache = new JButton("Creer Tache");

        this.boxShape = new BoxShape(this.ctrl);
        this.panelBox = this.boxShape.creerPanel(this.boxShape);
        this.lstTache = new ArrayList<Tache>();

        //Liste des Prc
        this.listModel = new DefaultListModel<String>();
        this.lstChoix = new JList<String>(this.listModel);
        JScrollPane scrollPane = new JScrollPane(this.lstChoix);

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
        
        this.add(scrollPane);
        this.add(panelInformation);
        this.add(this.panelBox);


        /*--------------------------------------*/
        /*         Ajout des listeners          */
        /*--------------------------------------*/
        this.txtNom.addActionListener(this);
        this.txtDuree.addActionListener(this);
        this.btnCreerTache.addActionListener(this);

        /*-------------------------------------- */
        /*          WindowListener              */
        /*--------------------------------------*/
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
        actionMap.put("clickButton", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                btnCreerTache.doClick();
            }
        });
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

                this.lstTache.add(new Tache(this.txtNom.getText(), Integer.parseInt(this.txtDuree.getText())));
                this.boxShape.setNom(this.txtNom.getText());
                this.majPanelBoxShape();

                int[] selectedIndices = this.lstChoix.getSelectedIndices();
                if(selectedIndices.length > 0) 
                {
                    Tache nouvelleTache = this.lstTache.get(this.lstTache.size()-1);
                
                    for(int index : selectedIndices)
                    {
                        nouvelleTache.addPrecedent(this.lstTache.get(index));
                        System.out.println("Ajout prédécesseur : " + this.lstTache.get(index).getNom());
                    }
                }


                this.listModel.addElement(this.txtNom.getText());
                this.lstChoix.setModel(this.listModel);

                this.ctrl.sauvegarderTaches(this.lstTache);

                this.txtNom.setText("");
                this.txtDuree.setText("");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

