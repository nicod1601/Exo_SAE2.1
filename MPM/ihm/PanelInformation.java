package MPM.ihm;
import MPM.Controleur;
import MPM.metier.BoxShape;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelInformation extends JPanel implements ActionListener
{
    
    private Controleur ctrl;
    private FrameMPM frame;

    private JButton btnQuitter;
    
    private JLabel lblNomTache;
    private JLabel lblDureeTache;
    private JLabel lblMargeTache;
    private JLabel lblDateTot;
    private JLabel lblDateTard;


    public PanelInformation(FrameMPM frame, Controleur ctrl)
    {
        /*----------------------------------------*/
        /* Création des composants PanelInfoTache */
        /*----------------------------------------*/

        JPanel panelInfoTache;
        JPanel panelQuitter;
        JPanel panelHaut;
        JPanel panelCentre;
        

        JLabel lblNomEtiquette;
        JLabel lblDureeEtiquette;
        JLabel lblMargeEtiquette;
        JLabel lblTitre;
    
        this.ctrl  = ctrl;
        this.frame = frame;

        panelInfoTache  = new JPanel(new GridLayout(5, 1, 5, 10));
        panelInfoTache.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelQuitter    = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.btnQuitter = new JButton("X");

        panelQuitter.add(this.btnQuitter);
        
        // Panel du haut - Informations de base

        panelHaut = new JPanel(new GridLayout(3, 2, 5, 8));
        panelHaut.setBorder   (BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.lblNomTache   = new JLabel("", JLabel.LEFT);
        this.lblDureeTache = new JLabel("", JLabel.LEFT);
        this.lblMargeTache = new JLabel("", JLabel.LEFT);

        // Style des labels d'étiquettes

        lblNomEtiquette   = new JLabel("Nom Tache :", JLabel.RIGHT);
        lblDureeEtiquette = new JLabel("Duree :"    , JLabel.RIGHT);
        lblMargeEtiquette = new JLabel("Marge :"    , JLabel.RIGHT);
        
        lblNomEtiquette  .setFont(new Font("Arial", Font.BOLD, 12) );
        lblDureeEtiquette.setFont(new Font("Arial", Font.BOLD, 12) );
        lblMargeEtiquette.setFont(new Font("Arial", Font.BOLD, 12) );

        panelHaut.add(lblNomEtiquette   );
        panelHaut.add(this.lblNomTache  );
        panelHaut.add(lblDureeEtiquette );
        panelHaut.add(this.lblDureeTache);
        panelHaut.add(lblMargeEtiquette );
        panelHaut.add(this.lblMargeTache);

        // Panel du centre - Dates
        panelCentre = new JPanel(new GridLayout(2, 2, 10, 8));
        panelCentre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.lblDateTot  = new JLabel("", JLabel.CENTER);
        this.lblDateTard = new JLabel("", JLabel.CENTER);

        // Style des en-têtes de dates
        JLabel lblDateTotEtiquette  = new JLabel("Date au Plus Tot" , JLabel.CENTER);
        JLabel lblDateTardEtiquette = new JLabel("Date au Plus Tard", JLabel.CENTER);
        
        lblDateTotEtiquette .setFont      (new Font("Arial", Font.BOLD, 11));
        lblDateTardEtiquette.setFont      (new Font("Arial", Font.BOLD, 11));
        lblDateTotEtiquette .setForeground(new Color(0, 100, 0));
        lblDateTardEtiquette.setForeground(new Color(150, 0, 0));

        panelCentre.add(lblDateTotEtiquette);
        panelCentre.add(lblDateTardEtiquette);
        panelCentre.add(this.lblDateTot);
        panelCentre.add(this.lblDateTard);

        // Panel du bas - Listes

        JPanel panelBas = new JPanel(new GridLayout(6, 1, 5, 5) );

        panelBas.add(new JLabel("Nom tâche             : Le nom de la tâche"                                                               ) ) ;
        panelBas.add(new JLabel("Durée                     : Combien de temps il faut pour finaliser la tâche"                                 ) ) ;
        panelBas.add(new JLabel("Marge                     : La différence de jour qu'il y a entre la date au plus tard et la date au plus tôt") ) ;
        panelBas.add(new JLabel("Date au plus tôt     : La date à laquelle peut être commencé la tâche"                                    ) ) ;
        panelBas.add(new JLabel("Date au plus tard  : La date maximale à laquelle il faut finir la tâche"                               ) ) ;
        panelBas.add(new JLabel("Chemin Critique   : Indique les tâches qui ne possèdent aucune marge"                                 ) ) ;
        
        /*----------------------------------------*/
        /* Ajout des composants PanelInfoTache    */
        /*----------------------------------------*/

        // Titre principal
        
        lblTitre = new JLabel("Information Tache", JLabel.CENTER);

        lblTitre.setFont      (new Font("Arial", Font.BOLD, 16));
        lblTitre.setForeground(new Color(0, 51, 102));
        lblTitre.setBorder    (BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panelInfoTache.add(panelQuitter);
        panelInfoTache.add(lblTitre    );
        panelInfoTache.add(panelHaut   );
        panelInfoTache.add(panelCentre );
        panelInfoTache.add(panelBas    );

        // Style des labels de données

        this.styleDataLabels();

        // Ajout du panel principal
        this.add(panelInfoTache);
        
        // Style du panel principal
        this          .setBackground(new Color(245, 245, 245));
        this          .setBorder    (BorderFactory.createRaisedBevelBorder());
        panelInfoTache.setBackground(Color.WHITE);


        /*---------------------------------------------*/
        /* Activation des composants PanelInfoTache    */
        /*---------------------------------------------*/
        this.btnQuitter.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnQuitter)
        {
            this.frame.effacerPanelInfo();
            System.out.println("Quitter");
        }
    }

    public void setDonneeInfoTache(BoxShape box)
    {
        this.lblNomTache  .setText(     box.getNom() );
        this.lblDureeTache.setText("" + box.getTache().getDuree()   );
        this.lblMargeTache.setText("" + box.getTache().getMarge()   );
        this.lblDateTot   .setText("" + box.getTache().getDateMin() );
        this.lblDateTard  .setText("" + box.getTache().getDateMax() );
    }

    private void styleDataLabels()
    {
        Font dataFont   = new Font ("Arial", Font.PLAIN, 15);
        Color dataColor = new Color(51, 51, 51);

        // Style pour les données principales

        this.lblNomTache.setFont(new Font("Arial", Font.BOLD, 20) );
        this.lblNomTache.setForeground(new Color(0, 102, 204) );
        
        this.lblDureeTache.setFont      (dataFont );
        this.lblDureeTache.setForeground(dataColor);
        
        this.lblMargeTache.setFont      (dataFont );
        this.lblMargeTache.setForeground(dataColor);

        // Style pour les dates avec bordures

        this.lblDateTot.setFont      (dataFont);
        this.lblDateTot.setForeground(new Color(0, 100, 0) );
        this.lblDateTot.setBorder    (BorderFactory.createLoweredBevelBorder() );
        this.lblDateTot.setOpaque    (true);
        this.lblDateTot.setBackground(new Color(240, 255, 240) );
        
        this.lblDateTard.setFont      (dataFont);
        this.lblDateTard.setForeground(new Color(150, 0, 0) );
        this.lblDateTard.setBorder    (BorderFactory.createLoweredBevelBorder() );
        this.lblDateTard.setOpaque    (true);
        this.lblDateTard.setBackground(new Color(255, 240, 240) );

    }
}