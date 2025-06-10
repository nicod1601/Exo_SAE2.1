package MPM.ihm;
import MPM.Controleur;
import MPM.metier.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class PanelModifier extends JPanel implements ActionListener
{
    private Controleur    ctrl;

    private FrameMPM      frameMPM;
    private FrameModifier frame   ;

    private JTextField    txtNomTache;
    private JTextField    txtDureeTache;
    private JButton       btnModifier;

    private Tache         tache;



    public PanelModifier(Controleur ctrl, FrameMPM frameMPM, FrameModifier frame)
    {
        this.setLayout(new GridLayout(4, 1));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        JPanel panelNom    = new JPanel(new FlowLayout() );
        JPanel panelDuree  = new JPanel(new FlowLayout() );
        JPanel panelBtn    = new JPanel(new FlowLayout() );

        JLabel lblTitre    = new JLabel("Modifier une tache", JLabel.CENTER);
        JLabel lblNom      = new JLabel("Nom Tache :"  , JLabel.RIGHT);
        JLabel lblDuree    = new JLabel("Duree Tache :", JLabel.RIGHT);

        this.ctrl     = ctrl;
        this.frameMPM = frameMPM;
        this.frame    = frame;

        this.tache = null;

        this.txtNomTache   = new JTextField(10);
        this.txtDureeTache = new JTextField(10);
        this.btnModifier   = new JButton("Modifier");

        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/

        panelNom  .add(lblNom      );
        panelNom  .add(txtNomTache );

        panelDuree.add(lblDuree     );
        panelDuree.add(txtDureeTache);

        panelBtn  .add(btnModifier  );

        this.add(lblTitre  );
        this.add(panelNom  );
        this.add(panelDuree);
        this.add(panelBtn  );

        
        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.btnModifier.addActionListener(this);
    }

    public void setModifTache(Tache tache)
    {
        this.tache = tache;
        this.txtNomTache  .setText(tache.getNom());
        this.txtDureeTache.setText(String.valueOf(tache.getDuree()));
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == this.btnModifier)
        {
            this.ctrl         .modifierTache(this.txtNomTache.getText(), Integer.parseInt(this.txtDureeTache.getText()), this.tache);
            this.frameMPM     .majList();
            this.frame        .dispose();
            this.txtNomTache  .setText("");
            this.txtDureeTache.setText("");
        }
    }
}
