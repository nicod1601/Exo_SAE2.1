package MPM.metier;

/**
 * Cette classe créer les projets et gère la planification des tâches selon la méthode MPM (Méthode des Potentiels Métra).
 * Elle permet de calculer les dates au plus tôt et au plus tard de chaque tâche, ainsi que la durée totale du projet.
 * 
 * @version 1.0
 * @since 2025-06-02
 * @author Groupe 09 - DELPECH Nicolas, FOYER Emilien, GUTU Nichita, KULPA Clément
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Projet
{
	/** Liste des tâches composant le projet */
	private ArrayList<Tache> lstTache;
	
	/** Nombre de tâches dans le projet */
	private int nbTache;
	
	/** Durée totale du projet en jours */
	private int dureeProjet; 

	/**
	 * Constructeur de la classe Projet.
	 * Initialise un nouveau projet en lisant les données depuis un fichier
	 * et calcule automatiquement les dates de planification.
	 */
	public Projet()
	{
		this.lstTache    = new ArrayList<Tache>();
		this.dureeProjet = 0;

		Tache debut = new Tache("Debut", 0);
		debut.setDateMin(0);
		this.lstTache.add(debut);

		this.lireFichier();

		Tache fin = new Tache("Fin", 0);
		for(Tache t : this.lstTache) {
			if(t.getNbSvt() == 0 && !t.getNom().equals("Debut")) {
				fin.addPrecedent(t);
			}
		}
		this.lstTache.add(fin);

		for(Tache t : this.lstTache) {
			if(t.getNbPrc() == 0 && !t.getNom().equals("Debut") && !t.getNom().equals("Fin")) {
				t.addPrecedent(debut);
			}
		}




		this.majDate();
		
	}

	/**
	 * Vérifie si le projet est vide (aucune tâche).
	 * 
	 * @return true si le projet ne contient aucune tâche, false sinon
	 */
	public boolean estVide(){return this.lstTache.isEmpty() ;}
	
	/**
	 * Retourne le nombre de tâches dans le projet.
	 * 
	 * @return le nombre de tâches
	 */
	public int getTaille() {return this.lstTache.size();}

	/**
	 * Détermine la durée totale du projet.
	 * 
	 * @return la durée du projet en jours
	 */
	public int determinerDuree() {return this.dureeProjet;}

	/**
	 * Calcule et retourne le chemin critique du projet.
	 * 
	 * @return une chaîne représentant le chemin critique (non implémenté)
	 * @todo Implémenter le calcul du chemin critique
	 */
	public String getCheminCritique() { return " "; }

	/**
	 * Lit le fichier de données du projet (mpm.txt) et construit la liste des tâches.
	 * Le fichier doit respecter le format : nom|durée|précédents (séparés par des virgules).
	 * 
	 * @throws Exception si le fichier n'est pas trouvé ou mal formaté
	 */
	public void lireFichier() 
	{
		try
		{
			Scanner sc = new Scanner ( new File ( "./MPM/donnee/mpm.txt" ), "UTF-8" );

			while ( sc.hasNextLine() )
			{
				String ligne    = sc.nextLine();
				String[] partie = ligne.split("\\|");

				String nom = partie[0];
				int duree  = Integer.parseInt(partie[1]);

				Tache tmp = new Tache(nom, duree);

				if(partie.length > 2 && ! partie[2].isEmpty() )
				{
					String[] prc = partie[2].split(",");

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
			}
		}
		catch ( Exception e ){ e.printStackTrace(); }
	}

	/**
	 * Met à jour les dates de toutes les tâches du projet.
	 * Calcule les dates au plus tôt et au plus tard selon la méthode MPM.
	 */
	private void majDate()
	{
		this.setDateMin();
		this.setDateMax();
	}

	/**
	 * Calcule les dates au plus tôt pour toutes les tâches du projet.
	 * Les tâches sans précédent commencent le 02/06/2025.
	 * Les autres tâches commencent après la fin de leurs prédécesseurs.
	 */
	private void setDateMin()
	{
		Tache tSvt;

		for(Tache t : this.lstTache)
		{
			for(int cpt = 0; cpt < t.getNbSvt(); cpt++)
			{
				tSvt = t.getSvt(cpt); 
				tSvt.setDateMin(t.getDateMin() + t.getDuree());
			}
		}
	}

	/**
	 * Calcule la date minimale de début pour une tâche donnée.
	 * Cette date correspond à la fin la plus tardive de ses tâches précédentes.
	 * 
	 * @param tache la tâche pour laquelle calculer la date minimale
	 * @return la date minimale de début, ou null si les précédents ne sont pas encore calculés
	 */
	private void setDateMax()
	{
		Tache t, tPrc;

		t = this.lstTache.get(this.lstTache.size()-1);
		t.setDateMax(t.getDateMin());

		for(int cptP = this.lstTache.size()-1; cptP > 0; cptP--)
		{
			t = this.lstTache.get(cptP);

			for(int cpt = 0; cpt < t.getNbPrc(); cpt++)
			{
				tPrc = t.getPrc(cpt);

				int nouvelleDateMax = t.getDateMax() - tPrc.getDuree();
				
				if(tPrc.getDateMax() == -1 || nouvelleDateMax < tPrc.getDateMax())
				{
					tPrc.setDateMax(nouvelleDateMax);
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