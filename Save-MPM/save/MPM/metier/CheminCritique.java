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
    private ArrayList<Tache> lstCritique;
    private ArrayList<Tache> lstTache;

    public CheminCritique(ArrayList<Tache> lstTache) 
    {
        this.lstTache = lstTache; 
        this.lstCritique = new ArrayList<Tache>();
        this.lstCheminCrit = new ArrayList<ArrayList<Tache>>();
    }

    public void cheminCritique()
    {
        // parcour critique
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



    public static void main(String[] args) {
        Projet p = new Projet();

        p.lireFichier("/Documents/Exo_SAE2.1/class/MPM/donnee/testCr.txt");

        CheminCritique c = new CheminCritique(p.getLstTache());

        c.cheminCritique();

        System.out.println(c);
    }


}