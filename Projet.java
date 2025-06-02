/**
 * Cette classe créer les projets
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

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Projet
{
	private ArrayList<Tache> lstTache;
	private int nbTache;
	private int dureeProjet; 

	public Projet()
	{
		
		this.lstTache    = new ArrayList<Tache>();
		this.dureeProjet = 0;

		this.lireFichier();
		this.majDate();
	}


	public boolean estVide(){return this.lstTache.size() == 0;}
	public int getTaille  (){return this.lstTache.size();}

	public int determinerDuree(){return this.dureeProjet;}

	public String getCheminCritique()
	{
		return " ";
	}


	private void lireFichier()
	{
		try
		{
			Scanner sc = new Scanner ( new File ( "mpm.txt" ), "UTF-8" );

			while ( sc.hasNextLine() )
			{
				String ligne    = sc.nextLine();
				
				String[] partie = ligne.split("\\|");


				String nom = partie[0];

		
				int duree  = Integer.parseInt(partie[1]);


				Tache tmp = new Tache(nom, duree);

				if(partie.length > 2 && ! partie[2].isEmpty() )
				{
					String[] prc    = partie[2].split(",");

					for(int cpt =0; cpt < prc.length; cpt++)
					{
						for (Tache t : this.lstTache)
						{
							if(prc[cpt].equals(t.getNom()))
								tmp.addPrecedent(t);
						}
							
					}
				}

				this.lstTache.add(tmp);
				this.nbTache++;
				this.dureeProjet = this.dureeProjet + duree;
			}

			
		}
		catch ( Exception e ){ e.printStackTrace(); }

	}

	private void majDate()
	{


		for (Tache tache : lstTache)
		{
			tache.setDateMin(12);

            int jourMin = tache.getDateMin().get(DateFr.DAY_OF_YEAR);
			if(tache.getLstPrc().size() == 0)
			{
				tache.setDateMin(new DateFr() );
                //System.out.println(tache.getDateMin() );
            
                /*System.out.println(tache.getMarge() );

                tache.setDateMax(tache.getMarge() );*/
                //System.out.println(tache.getDateMax() );



				
			}
			else
			{
				ArrayList<Tache> tmp = tache.getLstPrc();

				/*for(int cpt =0; cpt < tmp.size(); cpt++)
				{
					if()
					{

					}
				}*/
				for (Tache tPrc : tmp) 
				{
					tache.setDateMin(tache.getMarge() );
				}
       		}
		}
	}

	public String toString()
	{
		String sRet ="";
        
		for (Tache t : this.lstTache) 
			sRet += t.toString() + "\n\n";
		
		return sRet;
	}

}

