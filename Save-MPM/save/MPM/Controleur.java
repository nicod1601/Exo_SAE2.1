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


    /*-------------------------------*/
    /*         Accesseurs            */
    /*-------------------------------*/


    public String            afficherProjet()                       { return this.projet.toString()             ;}
    public ArrayList<Tache>  getListeTache ()                       { return this.projet.getLstTache()          ;}
    public int               getNbNiveau()                          { return this.projet.getNbNiveau()          ;}
    public int               getTailleNivMax()                      { return this.projet.getTailleNivMax()      ;}
    public int[]             getNbParNiveau (int niv, String nom)   { return this.projet.getNbParNiveau(niv,nom);}
    public ArrayList<Erreur> getErreur()                            {return this.projet.getErreur()             ;}


    /*-------------------------------*/
    /*         Autre méthodes        */
    /*-------------------------------*/

    /*public void sauvegarderTaches(ArrayList<Tache> lstTaches, String lien)
    {
        this.projet.sauvegarderTaches(lstTaches, lien);
    }*/




    public void addPrecedent(Tache tache, Tache precedent)
    {
        this.projet.addPrecedent(tache,precedent);
        this.frame.majList();
    }

    public void majDate() {this.projet.majDate();}

    public void ajouterTache(Tache tache)
    {
        this.projet.ajouterTache(tache);
        this.frame.majList();

    }

     public void lireFichier(String chemin)
     {
        this.projet.lireFichier(chemin);
        this.frame.majTxt();
    }


    public void supprimerTache(Tache tache)
    {
        this.projet.supprimerTache(tache);
        this.frame.majList();
    }

    public String getFichier(String chemin)
    {
        if(this.projet == null) return "";
        return this.projet.getFichier(chemin);
    }

    public void enregistrer(String lien, ArrayList<Tache> lstTaches)
    {
        this.projet.enregistrer(lien, lstTaches);
        this.frame.majTxt();
    }

    public void enregistrerSous(String lien, ArrayList<Tache> lstTaches)
    {
        this.projet.enregistrerSous(lien, lstTaches);
        this.frame.majTxt();
    }

    public void modifierTache(String nom, int duree, Tache tache)
    {
        this.projet.modifierTache(nom, duree, tache);
        //this.frame.majList();
    }

    public static void main(String[] args)
    {
        new Controleur();
    }


    public ArrayList<CheminCritique> getCheminCritique()
    {
        return this.projet.getCheminCritique();
    }
}