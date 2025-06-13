package MPM.ihm;

import MPM.Controleur;
import MPM.metier.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelBouton extends JPanel implements ActionListener
{
    private JButton btnDateMin;
    private JButton btnDateMax;
    private JButton btnAfficherTout;
    private JButton btnAfficherRien;
    private JButton btnCheminCritique;

    private int niveauPrc;
    private int niveauSvt;

    private ArrayList<Tache>    lstTache;
    private ArrayList<BoxShape> lstBoxShape;

    private Controleur ctrl;
    private FrameMPM   frame;

    public PanelBouton(FrameMPM frame, Controleur ctrl)
    {
        ButtonGroup bgSwitch;

        this.ctrl  = ctrl;
        this.frame = frame;

        this.setBackground(new Color(133, 129, 129));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.lstTache          = new ArrayList<Tache>();
        this.lstBoxShape       = new ArrayList<BoxShape>();
        this.niveauPrc         = 0;
        this.niveauSvt         = 1;
        this.btnDateMin        = new JButton("+ Tôt");
        this.btnDateMax        = new JButton("+ Tard");
        this.btnAfficherTout   = new JButton("Tout Afficher");
        this.btnAfficherRien   = new JButton  ("Tout Masquer");
        this.btnCheminCritique = new JButton("Chemin critique");

        this.btnAfficherRien.setForeground(Color.BLACK);
        this.btnAfficherTout.setForeground(Color.BLACK);
        this.btnDateMax     .setForeground(Color.BLACK);
        this.btnDateMin     .setForeground(Color.BLACK);

        this.desactiverBouton();

        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.btnDateMin     );
        this.add(this.btnDateMax     );
        this.add(this.btnAfficherTout);
        this.add(this.btnAfficherRien);
        this.add(this.btnCheminCritique);

        this.add(new JLabel() );
        this.add(new JLabel() );
        this.add(new JLabel() );

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.btnDateMin       .addActionListener(this);
        this.btnDateMax       .addActionListener(this);
        this.btnAfficherTout  .addActionListener(this);
        this.btnAfficherRien  .addActionListener(this);
        this.btnCheminCritique.addActionListener(this);

    }

    public void desactiverBouton()
    {
        this.btnDateMin       .setEnabled(false);
        this.btnDateMax       .setEnabled(false);
        this.btnAfficherTout  .setEnabled(false);
        this.btnAfficherRien  .setEnabled(false);
        this.btnCheminCritique.setEnabled(false);

        this.btnAfficherRien  .setBackground(new Color(100, 0, 0 ) );
        this.btnAfficherTout  .setBackground(new Color(100, 0, 0 ) );
        this.btnDateMin       .setBackground(new Color(100, 0, 0 ) );
        this.btnDateMax       .setBackground(new Color(100, 0, 0 ) );
        this.btnCheminCritique.setBackground(new Color(100, 0, 0 ) );
    }

    public void activerBouton()
    {
        this.btnDateMin       .setEnabled(true);
        this.btnAfficherTout  .setEnabled(true);
        this.btnCheminCritique.setEnabled(true);

        this.btnDateMin       .setBackground(new Color(100, 180, 100 ) );
        this.btnAfficherTout  .setBackground(new Color(100, 180, 100 ) );
        this.btnCheminCritique.setBackground(new Color(100, 180, 100 ) );

    }

    public void boutonDeBase()
    {

        this.btnAfficherTout.setEnabled(true );
        this.btnDateMin     .setEnabled(true );
        this.btnAfficherRien.setEnabled(false);
        this.btnDateMax     .setEnabled(false);

        this.btnAfficherTout.setBackground(new Color(100, 180, 100) );
        this.btnDateMin     .setBackground(new Color(100, 180, 100) );
        this.btnAfficherRien.setBackground(new Color(100, 0, 0    ) );
        this.btnDateMax     .setBackground(new Color(100, 0, 0    ) );
    }

    public void resetNiveau()
    {
        this.niveauPrc = this.ctrl.getNbNiveau() - 1;
        this.niveauSvt = 1;
    }

    public void majTacheBox(ArrayList<Tache> t, ArrayList<BoxShape> b)
    {
        this.lstTache    = t;
        this.niveauPrc   = this.ctrl.getNbNiveau() - 1;
        this.lstBoxShape = b;
    }

    public void activerBtnAfficherRien()
    {
        this.btnAfficherRien.setEnabled(true);
        this.btnAfficherRien.setBackground(new Color(100, 180, 100 ));
    }


    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnDateMin)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                if( this.lstBoxShape.get(cpt).getNiveau() == this.niveauSvt)
                    this.lstBoxShape.get(cpt).setDateMin(" "  + this.lstTache.get(cpt).getDateMin() );
                        
            }

            this.niveauSvt++;
            

            if(this.niveauSvt >= this.ctrl.getNbNiveau())
            {
                this.btnDateMin.setEnabled(false);
                this.btnDateMax.setEnabled(true );

                this.btnDateMin.setBackground(new Color(100, 0, 0 ) );
                this.btnDateMax.setBackground(new Color(100, 180, 100 ) );
                
                if(this.niveauPrc < 0 && this.niveauSvt >= this.ctrl.getNbNiveau())
                {
                    this.btnAfficherTout.setEnabled(false);
                    this.btnAfficherTout.setBackground(new Color(100, 0, 0 ));
                    this                .activerBtnAfficherRien();
                }
            }

            this.frame.majDessin();
        }

        if(e.getSource() == this.btnDateMax)
        {
            for(int cpt = this.lstBoxShape.size() - 1; cpt >= 0; cpt--)
            {
                if( this.lstBoxShape.get(cpt).getNiveau() == this.niveauPrc)
                    this.lstBoxShape.get(cpt).setDateMax(" " + this.lstTache.get(cpt).getDateMax());
                
                    
            }

            this.niveauPrc--;

            if(this.niveauPrc < 0)
            {
                this.btnDateMax.setEnabled(false);
                this.btnDateMax.setBackground(new Color(100, 0, 0 ));

                if(this.niveauPrc < 0 && this.niveauSvt >= this.ctrl.getNbNiveau())
                {
                    this.btnAfficherTout.setEnabled(false);
                    this.btnAfficherTout.setBackground(new Color(100, 0, 0 ));
                    this                .activerBtnAfficherRien();
                }
            }

            this.frame.majDessin();
        }

        if(e.getSource() == this.btnAfficherTout)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                this.lstBoxShape.get(cpt).setDateMin( " " + this.lstTache.get(cpt).getDateMin() );
                this.lstBoxShape.get(cpt).setDateMax( " " + this.lstTache.get(cpt).getDateMax() );
            }

            this.frame.majDessin();
            this      .desactiverBouton();
            this      .activerBtnAfficherRien();
        }

        if(e.getSource() == this.btnAfficherRien)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                if(this.lstBoxShape.get(cpt).getNiveau() == 0)
                {
                    this.lstBoxShape.get(cpt).setDateMin( " " + this.lstTache.get(cpt).getDateMin());
                    this.lstBoxShape.get(cpt).setDateMax( " ");
                }

                else
                {
                    this.lstBoxShape.get(cpt).setDateMin( " ");
                    this.lstBoxShape.get(cpt).setDateMax( " ");
                }
            }

            this                .activerBouton();
            this.btnAfficherRien.setEnabled(false);
            this.btnAfficherRien.setBackground(new Color(100, 0, 0 ) );
            this.frame          .majDessin  ();
            this                .resetNiveau();
        }

        if(e.getSource() == this.btnCheminCritique)
        {
            ArrayList<CheminCritique> lstCheminCritique = new ArrayList<CheminCritique>(this.ctrl.getCheminCritiques());
            
            frame.setCheminCritiques(lstCheminCritique); 
        }
    }
}
