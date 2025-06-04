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

    private int niveauPrc;
    private int niveauSvt;

    private ArrayList<Tache>    lstTache;
    private ArrayList<BoxShape> lstBoxShape;

    private Controleur ctrl;
    private FrameMPM  frame;

    public PanelBouton(Controleur ctrl, FrameMPM frame)
    {
        this.ctrl  = ctrl;
        this.frame = frame;

        this.setBackground(new Color(133, 129, 129));

        /*--------------------------------------*/
        /*        Création des composants       */
        /*--------------------------------------*/

        this.lstTache = new ArrayList<Tache>();
        this.lstBoxShape = new ArrayList<BoxShape>();
        this.niveauPrc = 0;
        this.niveauSvt = 0;

        this.btnDateMin       = new JButton("-");
        this.btnDateMax       = new JButton("+");
        this.btnAfficherTout  = new JButton("Afficher Tout");
        this.btnAfficherRien  = new JButton("Afficher Rien");

        this.desactiverBouton();

        /*--------------------------------------*/
        /*     Positionnement des composants    */
        /*--------------------------------------*/

        this.add(this.btnDateMin);
        this.add(this.btnDateMax);
        this.add(this.btnAfficherTout);
        this.add(this.btnAfficherRien);

        /*--------------------------------------*/
        /*     Activation des composants        */
        /*--------------------------------------*/

        this.btnDateMin.addActionListener(this);
        this.btnDateMax.addActionListener(this);
        this.btnAfficherTout.addActionListener(this);
        this.btnAfficherRien.addActionListener(this);
    }

    public void desactiverBouton()
    {
        this.btnDateMin.setEnabled(false);
        this.btnDateMax.setEnabled(false);
        this.btnAfficherTout.setEnabled(false);
        this.btnAfficherRien.setEnabled(false);

        this.btnAfficherRien.setBackground(new Color(180, 0, 0 ));
        this.btnAfficherTout.setBackground(new Color(180, 0, 0 ));
        this.btnDateMin.setBackground(new Color(180, 0, 0 ));
        this.btnDateMax.setBackground(new Color(180, 0, 0 ));
    }

    public void activerBouton()
    {
        this.btnDateMin.setEnabled(true);
        this.btnAfficherTout.setEnabled(true);

        this.btnDateMin.setBackground(new Color(101, 180, 0 ));
        this.btnAfficherTout.setBackground(new Color(101, 180, 0 ));

    }

    public void resetNiveau()
    {
        this.niveauPrc = this.ctrl.getNbNiveau() - 1;
        this.niveauSvt = 0;
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
        this.btnAfficherRien.setBackground(new Color(101, 180, 0 ));
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnDateMin)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                if(this.lstBoxShape.get(cpt).getNiveau() == this.niveauSvt)
                {
                    //System.out.println("BoxShape : " + this.lstBoxShape.get(cpt).getDateMin() + " ->  Niveau : " + this.niveauPrc);
                    this.lstBoxShape.get(cpt).setDateMin(" "  + this.lstTache.get(cpt).getDateMin());
                    //System.out.println("BoxShape : " + this.lstBoxShape.get(cpt).getDateMin() + " ->  Niveau : " + this.niveauPrc);

                }
            }
            this.niveauSvt++;

            if(this.niveauSvt >= this.ctrl.getNbNiveau())
            {
                this.btnDateMin.setEnabled(false);
                this.btnDateMin.setBackground(new Color(180, 0, 0 ));

                this.btnDateMax.setEnabled(true);
                this.btnDateMax.setBackground(new Color(101, 180, 0 ));
                
                if(this.niveauPrc < 0 && this.niveauSvt >= this.ctrl.getNbNiveau())
                {
                    this.btnAfficherTout.setEnabled(false);
                    this.btnAfficherTout.setBackground(new Color(180, 0, 0 ));
                    this.activerBtnAfficherRien();
                }
            }

            this.frame.majDessin();
        }

        if(e.getSource() == this.btnDateMax)
        {
            for(int cpt = this.lstBoxShape.size() - 1; cpt >= 0; cpt--)
            {
                if(this.lstBoxShape.get(cpt).getNiveau() == this.niveauPrc)
                {
                    this.lstBoxShape.get(cpt).setDateMax(" " + this.lstTache.get(cpt).getDateMax());
                }
            }
            this.niveauPrc--;

            if(this.niveauPrc < 0)
            {
                this.btnDateMax.setEnabled(false);
                this.btnDateMax.setBackground(new Color(180, 0, 0 ));

                if(this.niveauPrc < 0 && this.niveauSvt >= this.ctrl.getNbNiveau())
                {
                    this.btnAfficherTout.setEnabled(false);
                    this.btnAfficherTout.setBackground(new Color(180, 0, 0 ));
                    this.activerBtnAfficherRien();
                }
            }

            this.frame.majDessin();
        }

        if(e.getSource() == this.btnAfficherTout)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                this.lstBoxShape.get(cpt).setDateMin( " " + this.lstTache.get(cpt).getDateMin());
                this.lstBoxShape.get(cpt).setDateMax( " " +this.lstTache.get(cpt).getDateMax());
            }

            this.frame.majDessin();

            this.desactiverBouton();
            this.activerBtnAfficherRien();
        }

        if(e.getSource() == this.btnAfficherRien)
        {
            for(int cpt = 0; cpt < this.lstBoxShape.size(); cpt++)
            {
                this.lstBoxShape.get(cpt).setDateMin(" ");
                this.lstBoxShape.get(cpt).setDateMax(" ");
            }

            this.activerBouton();
            this.btnAfficherRien.setEnabled(false);
            this.btnAfficherRien.setBackground(new Color(180, 0, 0 ));

            this.frame.majDessin();
            this.resetNiveau();
        }
    }
}
