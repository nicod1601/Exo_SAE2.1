/**
 * Cette classe créer les projets et gère la planification des tâches selon la méthode MPM (Méthode des Potentiels Métra).
 * Elle permet de calculer les dates au plus tôt et au plus tard de chaque tâche, ainsi que la durée totale du projet.
 * 
 * @version 1.0
 * @since 2025-06-02
 * @author Groupe 09 - DELPECH Nicolas, FOYER Emilien, GUTU Nichita, KULPA Clément
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

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

		this.lireFichier();
		this.majDate();
	}

	/**
	 * Vérifie si le projet est vide (aucune tâche).
	 * 
	 * @return true si le projet ne contient aucune tâche, false sinon
	 */
	public boolean estVide(){return this.lstTache.size() == 0;}
	
	/**
	 * Retourne le nombre de tâches dans le projet.
	 * 
	 * @return le nombre de tâches
	 */
	public int getTaille(){return this.lstTache.size();}

	/**
	 * Détermine la durée totale du projet.
	 * 
	 * @return la durée du projet en jours
	 */
	public int determinerDuree(){return this.dureeProjet;}

	/**
	 * Calcule et retourne le chemin critique du projet.
	 * 
	 * @return une chaîne représentant le chemin critique (non implémenté)
	 * @todo Implémenter le calcul du chemin critique
	 */
	public String getCheminCritique()
	{
		return " ";
	}

	/**
	 * Lit le fichier de données du projet (mpm.txt) et construit la liste des tâches.
	 * Le fichier doit respecter le format : nom|durée|précédents (séparés par des virgules).
	 * 
	 * @throws Exception si le fichier n'est pas trouvé ou mal formaté
	 */
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
		for (Tache tache : lstTache)
		{
			tache.setDateMin(null);
			tache.setDateMax(null);
		}
		this.calculerDatesAuPlusTot();
		this.calculerDatesAuPlusTard();
	}

	/**
	 * Calcule les dates au plus tôt pour toutes les tâches du projet.
	 * Les tâches sans précédent commencent le 02/06/2025.
	 * Les autres tâches commencent après la fin de leurs prédécesseurs.
	 */
	private void calculerDatesAuPlusTot()
	{
		for (Tache tache : lstTache)
		{
			if(tache.getLstPrc().size() == 0)
			{
				tache.setDateMin(new DateFr(2, 6, 2025));
			}
		}
		
		boolean changed = true;
		while(changed)
		{
			changed = false;
			for (Tache tache : lstTache)
			{
				if(tache.getLstPrc().size() > 0 && tache.getDateMin() == null)
				{
					DateFr nouvelleDateMin = calculerDateMinimale(tache);
					if(nouvelleDateMin != null)
					{
						tache.setDateMin(nouvelleDateMin);
						changed = true;
					}
				}
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
	private DateFr calculerDateMinimale(Tache tache)
	{
		DateFr datePlusTot = new DateFr(2, 6, 2025);
		
		for (Tache precedent : tache.getLstPrc())
		{
			if(precedent.getDateMin() == null)
			{
				return null;
			}
			
			DateFr finPrecedent = new DateFr(precedent.getDateMin());
			finPrecedent.addJours(precedent.getDuree());
			
			if(finPrecedent.compareTo(datePlusTot) > 0)
			{
				datePlusTot = finPrecedent;
			}
		}
		
		return datePlusTot;
	}

	/**
	 * Calcule les dates au plus tard pour toutes les tâches du projet.
	 * Commence par déterminer la fin du projet, puis propage vers l'arrière.
	 */
	private void calculerDatesAuPlusTard()
	{
		DateFr finProjet = determinerFinProjet();
		this.dureeProjet = calculerDureeProjet(finProjet);
		
		initialiserTachesFinales(finProjet);
		propagerDatesAuPlusTard();
	}

	/**
	 * Détermine la date de fin du projet.
	 * Correspond à la date de fin la plus tardive parmi toutes les tâches.
	 * 
	 * @return la date de fin du projet
	 */
	private DateFr determinerFinProjet()
	{
		DateFr finProjet = new DateFr(2, 6, 2025);
		
		for (Tache tache : lstTache)
		{
			if(tache.getDateMin() != null)
			{
				DateFr finTache = new DateFr(tache.getDateMin());
				finTache.addJours(tache.getDuree());
				
				if(finTache.compareTo(finProjet) > 0)
				{
					finProjet = finTache;
				}
			}
		}
		
		return finProjet;
	}

	/**
	 * Calcule la durée totale du projet en jours.
	 * La durée correspond au nombre de jours entre le début du projet et sa fin.
	 * 
	 * @param finProjet la date de fin du projet
	 * @return la durée du projet en jours depuis le 02/06/2025
	 */
	private int calculerDureeProjet(DateFr finProjet)
	{
		DateFr dateDebut = new DateFr(2, 6, 2025);
		return finProjet.differenceEnJours(dateDebut);
	}

	/**
	 * Initialise les dates au plus tard pour les tâches finales.
	 * Les tâches finales (sans successeur) doivent finir à la date de fin du projet.
	 * 
	 * @param finProjet la date de fin du projet
	 */
	private void initialiserTachesFinales(DateFr finProjet)
	{
		for (Tache tache : lstTache)
		{
			if(tache.getLstSvt().size() == 0)
			{
				DateFr dateMax = new DateFr(finProjet);
				dateMax.addJours(-tache.getDuree());
				tache.setDateMax(dateMax);
			}
		}
	}

	/**
	 * Propage les dates au plus tard vers l'arrière dans le réseau de tâches.
	 * Chaque tâche doit finir avant que ses successeurs ne commencent.
	 */
	private void propagerDatesAuPlusTard()
	{
		boolean changed = true;
		while(changed)
		{
			changed = false;
			for (Tache tache : lstTache)
			{
				if(tache.getLstSvt().size() > 0 && tache.getDateMax() == null)
				{
					DateFr nouvelleDateMax = calculerDateMaximale(tache);
					if(nouvelleDateMax != null)
					{
						tache.setDateMax(nouvelleDateMax);
						changed = true;
					}
				}
			}
		}
	}

	/**
	 * Calcule la date maximale de début pour une tâche donnée.
	 * Cette date correspond au début le plus précoce de ses tâches suivantes,
	 * moins la durée de la tâche courante.
	 * 
	 * @param tache la tâche pour laquelle calculer la date maximale
	 * @return la date maximale de début, ou null si les successeurs ne sont pas encore calculés
	 */
	private DateFr calculerDateMaximale(Tache tache)
	{
		DateFr datePlusTard = null;
		
		for (Tache suivant : tache.getLstSvt())
		{
			if(suivant.getDateMax() == null)
			{
				return null; 
			}
			
			DateFr debutSuivant = new DateFr(suivant.getDateMax());
			
			if(datePlusTard == null || debutSuivant.compareTo(datePlusTard) < 0)
			{
				datePlusTard = debutSuivant;
			}
		}
		
		if(datePlusTard != null)
		{
			datePlusTard.addJours(-tache.getDuree());
		}
		
		return datePlusTard;
	}

	/**
	 * Retourne une représentation textuelle du projet.
	 * Affiche toutes les tâches avec leurs informations de planification.
	 * 
	 * @return une chaîne contenant la description de toutes les tâches
	 */
	public String toString()
	{
		String sRet ="";
        
		for (Tache t : this.lstTache) 
			sRet += t.toString() + "\n\n";
		
		return sRet;
	}
}