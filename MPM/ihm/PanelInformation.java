package MPM.ihm;
import MPM.Controleur;
import java.awt.*;
import javax.swing.*;

public class PanelInformation extends JPanel
{
    private JPanel panelInfoTache;

    private JLabel lblNomTache;
    private JLabel lblDureeTache;
    private JLabel lblMargeTache;
    private JLabel lblDateTot;
    private JLabel lblDateTard;
    private JLabel lblPrc;
    private JLabel lblSvt;

    private Controleur ctrl;

    public PanelInformation(Controleur ctrl)
    {
        /*----------------------------------------*/
        /* Création des composants PanelInfoTache */
        /*----------------------------------------*/
        this.ctrl = ctrl;

        this.panelInfoTache = new JPanel(new GridLayout(4, 1, 5, 10));
        this.panelInfoTache.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel du haut - Informations de base
        JPanel panelHaut = new JPanel(new GridLayout(3, 2, 5, 8));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.lblNomTache   = new JLabel("", JLabel.LEFT);
        this.lblDureeTache = new JLabel("", JLabel.LEFT);
        this.lblMargeTache = new JLabel("", JLabel.LEFT);

        // Style des labels d'étiquettes
        JLabel lblNomEtiquette = new JLabel("Nom Tache :", JLabel.RIGHT);
        JLabel lblDureeEtiquette = new JLabel("Duree :", JLabel.RIGHT);
        JLabel lblMargeEtiquette = new JLabel("Marge :", JLabel.RIGHT);
        
        lblNomEtiquette.setFont(new Font("Arial", Font.BOLD, 12));
        lblDureeEtiquette.setFont(new Font("Arial", Font.BOLD, 12));
        lblMargeEtiquette.setFont(new Font("Arial", Font.BOLD, 12));

        panelHaut.add(lblNomEtiquette);
        panelHaut.add(this.lblNomTache);
        panelHaut.add(lblDureeEtiquette);
        panelHaut.add(this.lblDureeTache);
        panelHaut.add(lblMargeEtiquette);
        panelHaut.add(this.lblMargeTache);

        // Panel du centre - Dates
        JPanel panelCentre = new JPanel(new GridLayout(2, 2, 10, 8));
        panelCentre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.lblDateTot  = new JLabel("", JLabel.CENTER);
        this.lblDateTard = new JLabel("", JLabel.CENTER);

        // Style des en-têtes de dates
        JLabel lblDateTotEtiquette = new JLabel("Date au Plus Tot", JLabel.CENTER);
        JLabel lblDateTardEtiquette = new JLabel("Date au Plus Tard", JLabel.CENTER);
        
        lblDateTotEtiquette.setFont(new Font("Arial", Font.BOLD, 11));
        lblDateTardEtiquette.setFont(new Font("Arial", Font.BOLD, 11));
        lblDateTotEtiquette.setForeground(new Color(0, 100, 0));
        lblDateTardEtiquette.setForeground(new Color(150, 0, 0));

        panelCentre.add(lblDateTotEtiquette);
        panelCentre.add(lblDateTardEtiquette);
        panelCentre.add(this.lblDateTot);
        panelCentre.add(this.lblDateTard);

        // Panel du bas - Listes
        JPanel panelBas = new JPanel(new GridLayout(2, 2, 5, 8));
        panelBas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.lblPrc = new JLabel("", JLabel.LEFT);
        this.lblSvt = new JLabel("", JLabel.LEFT);

        JLabel lblPrcEtiquette = new JLabel("Liste PRC :", JLabel.RIGHT);
        JLabel lblSvtEtiquette = new JLabel("Liste SVT :", JLabel.RIGHT);
        
        lblPrcEtiquette.setFont(new Font("Arial", Font.BOLD, 12));
        lblSvtEtiquette.setFont(new Font("Arial", Font.BOLD, 12));

        panelBas.add(lblPrcEtiquette);
        panelBas.add(this.lblPrc);
        panelBas.add(lblSvtEtiquette);
        panelBas.add(this.lblSvt);

        /*----------------------------------------*/
        /* Ajout des composants PanelInfoTache    */
        /*----------------------------------------*/

        // Titre principal
        JLabel titreLabel = new JLabel("Information Tache", JLabel.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titreLabel.setForeground(new Color(0, 51, 102));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        this.panelInfoTache.add(titreLabel);
        this.panelInfoTache.add(panelHaut);
        this.panelInfoTache.add(panelCentre);
        this.panelInfoTache.add(panelBas);

        // Style des labels de données
        styleDataLabels();

        // Ajout du panel principal
        this.add(this.panelInfoTache);
        
        // Style du panel principal
        this.setBackground(new Color(245, 245, 245));
        this.panelInfoTache.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void styleDataLabels()
    {
        Font dataFont = new Font("Arial", Font.PLAIN, 12);
        Color dataColor = new Color(51, 51, 51);

        // Style pour les données principales
        this.lblNomTache.setFont(new Font("Arial", Font.BOLD, 12));
        this.lblNomTache.setForeground(new Color(0, 102, 204));
        
        this.lblDureeTache.setFont(dataFont);
        this.lblDureeTache.setForeground(dataColor);
        
        this.lblMargeTache.setFont(dataFont);
        this.lblMargeTache.setForeground(dataColor);

        // Style pour les dates avec bordures
        this.lblDateTot.setFont(dataFont);
        this.lblDateTot.setForeground(new Color(0, 100, 0));
        this.lblDateTot.setBorder(BorderFactory.createLoweredBevelBorder());
        this.lblDateTot.setOpaque(true);
        this.lblDateTot.setBackground(new Color(240, 255, 240));
        
        this.lblDateTard.setFont(dataFont);
        this.lblDateTard.setForeground(new Color(150, 0, 0));
        this.lblDateTard.setBorder(BorderFactory.createLoweredBevelBorder());
        this.lblDateTard.setOpaque(true);
        this.lblDateTard.setBackground(new Color(255, 240, 240));

        // Style pour les listes
        this.lblPrc.setFont(dataFont);
        this.lblPrc.setForeground(dataColor);
        
        this.lblSvt.setFont(dataFont);
        this.lblSvt.setForeground(dataColor);
    }
}