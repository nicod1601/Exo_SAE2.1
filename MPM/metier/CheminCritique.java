package MPM.metier;

/**
 * Cette classe créer les chemins critiques
 * 
 * Groupe : 09
 * 
 * Exercice 2
 * 
 * Participants : 
 * DELPECH Nicolas, 
 * FOYER Emilien, 
 * GUTU Nichita, 
 * KULPA Clément
 * 
 * Date de création : 02/06/2025 16h42
 */

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CheminCritique
{
    private ArrayList<ArrayList<Tache>> lstCheminCrit;
    private ArrayList<Tache>            lstCritique;
    private ArrayList<Tache>            lstTache;

    public CheminCritique(ArrayList<Tache> lstTache) 
    {
        this.lstTache      = lstTache; 
        this.lstCritique   = new ArrayList<Tache>();
        this.lstCheminCrit = new ArrayList<ArrayList<Tache>>();
    }

    public void cheminCritique()
    {
        for(Tache t : this.lstTache)
        {
            if(t.getDateMax() == t.getDateMin()) 
                this.lstCritique.add(t);
        }

        this.trieCheminCritique();
    }

    public void trieCheminCritique()
    {
        int niveau = -1;
        ArrayList<Tache> copieLstCritique = new ArrayList<Tache>(this.lstCritique);

        ArrayList<Tache> chemin = null;

        for(Tache t : copieLstCritique)
        {
            if(t.getNiveau() != niveau)
            {
                if(chemin != null && ! chemin.isEmpty())
                {
                    this.lstCheminCrit.add(chemin);
                }
                chemin = new ArrayList<Tache>();
                niveau = t.getNiveau();
            }
            chemin.add(t);
        }

        if(chemin != null && ! chemin.isEmpty())
        {
            this.lstCheminCrit.add(chemin);
        }
    }
    
    public String toString()
    {
        String sRet = "";
        
        for (ArrayList<Tache> chemin : this.lstCheminCrit) 
            sRet += chemin.toString() + "\n\n";
        
        return sRet;
    }

    public void affichage()
    {
        System.out.println("====== Tache critique ======");
        System.out.println("Nombre de taches critique : " + this.lstCritique.size());

        for(Tache t : this.lstCritique)
        {
            System.out.println(" - " + t.getNom());
        }
        
        System.out.println("====== Chemin critique ======");
        System.out.println("Nombre de groupe de niveaux : " + this.lstCheminCrit.size());

        for(int cpt = 0; cpt < this.lstCheminCrit.size(); cpt++)
        {
            System.out.println("Groupe " + (cpt + 1) + " : ");
            for(Tache t : this.lstCheminCrit.get(cpt))
            {
                System.out.println(" - " + t.getNom());
            }
        }
    }


    public static void main(String[] args)
    {
        Projet p = new Projet();

        p.lireFichier("/home/nicod16/Documents/Exo_SAE2.1/class/MPM/donnee/testCR.txt");

        CheminCritique c = new CheminCritique(p.getLstTache());

        c.cheminCritique();

        c.affichage();
    }
}