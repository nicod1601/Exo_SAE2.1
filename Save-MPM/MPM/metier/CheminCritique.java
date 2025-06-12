package MPM.metier;

/**


 Cette classe créer les chemins critiques

 Groupe : 09Exercice 2Participants :DELPECH Nicolas,FOYER Emilien,GUTU Nichita,KULPA ClémentDate de création : 02/06/2025 16h42*/
import java.util.ArrayList;

public class CheminCritique
{
    private ArrayList<Tache> lstTacheCritique;

    public CheminCritique(ArrayList<Tache> lstTacheCritique)
    {
        this.lstTacheCritique = lstTacheCritique;
    }

    public CheminCritique()
    {
        this.lstTacheCritique = null;
    }

    public ArrayList<Tache> getListeTacheCritique() { return this.lstTacheCritique;}

    public String toString()
    {
        String sRet = "";

        for (Tache tache : lstTacheCritique)
            sRet += tache.getNom() + ", ";

        return sRet;

    }


}