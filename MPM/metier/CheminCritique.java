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
    private ArrayList<Tache> lstCheminCrit;
    private ArrayList<Tache> lstTache;

    public CheminCritique(ArrayList<Tache> lstTache) 
    {
        this.lstTache = lstTache; 
        this.lstCheminCrit = new ArrayList<Tache>();
    }

    public ArrayList<Tache> getLstCheminCrit() { return this.lstCheminCrit; }

    // je repère toutes les taches qui on une date de debut et de fin egale
    public void CheminCritique()
    {
        // parcour crittique
        for(Tache t : this.lstTache)
        {
            if(t.getDateMax() == t.getDateMin())
            {
                this.lstCheminCrit.add(t);
            }
            
        }

        this.trieCheminCritique();
    }

    // je permet de faire tirer mon chemin critique le premier et ensuite si il en na d'autre je stocke dans une autre liste
    public void trieCheminCritique()
    {
        int niveau = -1 ;
        Tache t = new Tache();
        ArrayList<Tache> tmp = new ArrayList<Tache>();
        tmp.add(this.lstCheminCrit.get(0));

        for(int cpt = 0; cpt < this.lstCheminCrit.size(); cpt++)
        {
            if(niveau < this.lstTache.get(cpt).getNiveau())
            {
                niveau = this.lstTache.get(cpt).getNiveau();
                t = this.lstTache.get(cpt);
                this.lstCheminCrit.add(t);
            }
            else
            {
                tmp.add(this.lstCheminCrit.get(cpt));
                this.lstCheminCrit.remove(t);
            }
        }

        tmp.add(this.lstCheminCrit.get(this.lstCheminCrit.size() - 1));
        
    }

    public static void main(String[] args) {
        Projet p = new Projet();

        p.lireFichier("/Documents/Exo_SAE2.1/class/MPM/donnee/testCr.txt");

        CheminCritique c = new CheminCritique(p.getLstTache());

        c.CheminCritique();
    }


}