package MPM.ihm;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

import MPM.Controleur;

public class FrameEnregistrerSous extends JFrame implements ActionListener
{
    private JTextField txtNomFichier;
    private Controleur ctrl;


    public FrameEnregistrerSous(Controleur ctrl)
    {
        this.setTitle("Enregistrer sous");
        this.setExtendedState(JFrame.NORMAL);
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new FlowLayout());


        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/
        this.ctrl = ctrl;
        this.txtNomFichier = new JTextField(20);
        /*--------------------------------------*/
        /*          Ajout des composants        */
        /*--------------------------------------*/
        this.add(new JLabel("Nom du fichier : "));
        this.add(this.txtNomFichier);

        /*--------------------------------------*/
        /*         Ajout des listeners          */
        /*--------------------------------------*/
        this.txtNomFichier.addActionListener(this);
        
        
        
        this.setVisible(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(! this.txtNomFichier.getText().equals(""))
        {
            this.ctrl.enregistrerSous(this.ctrl.getListeTache(), this.txtNomFichier.getText());
            this.txtNomFichier.setText("");
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Nom de fichier non renseigné");
        }
    }
}
