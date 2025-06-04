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
    public int getTailleNivMax()
    {
        return this.projet.getTailleNivMax();

    }

    /**
     * a l'indice 0 se trouve ne nombre de taches sur le même niveau que la tache données
     * à l'indice 1 se trouve la place de la tache donnée parmis les tâches du même niveau
     */ 
    public int[]            getNbParNiveau (int niv, String nom)   { return this.projet.getNbParNiveau(niv,nom);}

    public static void main(String[] args)
    {
        new Controleur();
    }
}