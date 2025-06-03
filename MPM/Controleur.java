package MPM;

import MPM.ihm.*;
import MPM.metier.*;
import java.util.ArrayList;

public class Controleur
{
    private FrameMPM frame;
    private Projet projet;

    public Controleur()
    {
        this.frame  = new FrameMPM(this);
        this.projet = new Projet();
    }

    public void             lireFichier(String chemin){ this.projet.lireFichier(chemin);}
    public String           afficherProjet(){ return this.projet.toString();}
    public ArrayList<Tache> getListeTache (){ return this.projet.getLstTache(); }
    public int[]            getNbParNiveau (int niv, String nom)   { return this.projet.getNbParNiveau(niv,nom);}

    public static void main(String[] args)
    {
        new Controleur();
    }
}