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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
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
	private int nbTacheMaxNiveau;

	private ArrayList<CheminCritique> lstCheminCritique;
	private ArrayList<Erreur> erreur;


	/**
	 * Constructeur de la classe Projet.
	 * Initialise un nouveau projet en lisant les données depuis un fichier
	 * et calcule automatiquement les dates de planification.
	 */
	public Projet()
	{
		this.lstTache    = new ArrayList<Tache>();
		this.dureeProjet = 0;
		this.nbTacheMaxNiveau = 0;

		//this.lstCheminCritique = new ArrayList<CheminCritique>(this.lstTache);
	}


	/*public Projet()
	{
		this.lstTache    = new ArrayList<Tache>();
		this.dureeProjet = 0;

		Tache debut = new Tache("Début", 0);
		debut.setDateMin(0);
		this.lstTache.add(debut);

		this.lireFichier();

		Tache fin = new Tache("Fin", 0);
		for(Tache t : this.lstTache) {
			if(t.getNbSvt() == 0 && !t.getNom().equals("Début")) {
				fin.addPrecedent(t);
			}
		}
		this.lstTache.add(fin);

		for(Tache t : this.lstTache) {
			if(t.getNbPrc() == 0 && !t.getNom().equals("Début") && !t.getNom().equals("Fin")) {
				t.addPrecedent(debut);
				
			}
			t.setNiveau();
		}

		this.majDate();
		
		
	}*/

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
	 * Retourne le nombre de tâches dans le projet.
	 * 
	 * @return le nombre de tâches
	 */
	public int getTailleNivMax()
	{
		int nbNiv =0;
		int max   =0;
		for(Tache t : this.lstTache)
		{
			nbNiv = this.getNbParNiveau(t.getNiveau(), t.getNom())[0];
			if(max < nbNiv)
				max = nbNiv;
		}
		return max;
		
	}

	/**
	 * Retourne le nombre de tâches à un niveau donné et le nombre de tâches avec un nom spécifique à ce niveau.
	 * 
	 * @param niv le niveau des tâches
	 * @param nom le nom des tâches à compter
	 * @return un tableau contenant le nombre de tâches au niveau spécifié et le nombre de tâches avec le nom spécifié
	 */
	public int[] getNbParNiveau(int niv,String nom)
	{
		int[] nb = new int[2];
		for (Tache t : this.lstTache)
		{
			if(t.getNiveau() == niv)
				nb[0]++;

			if(nom.equals(t.getNom()))
				nb[1] = nb[0];
			
		}

		return nb;
	}



	/**
	 * Retourne la liste des tâches du projet.
	 * 
	 * @return la liste des tâches
	 */
	public ArrayList<Tache> getLstTache() {return this.lstTache;}

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

	public int getNbNiveau()
	{
		int posDernier = this.lstTache.size()-1;
		return this.lstTache.get(posDernier).getNiveau()+1;
	}


	/**
	 * Lit le fichier de données du projet (mpm.txt) et construit la liste des tâches.
	 * Le fichier doit respecter le format : nom|durée|précédents (séparés par des virgules).
	 * 
	 * @throws Exception si le fichier n'est pas trouvé ou mal formaté
	 */

	/**
	 * Lit un fichier de données du projet et construit la liste des tâches.
	 * Le fichier doit respecter le format : nom|durée|précédents (séparés par des virgules).
	 * 
	 * @param chemin le chemin du fichier à lire
	 */
	public void lireFichier(String chemin) 
	{
		this.erreur = new ArrayList<Erreur>();
		this.lstTache.clear();
		Tache debut = new Tache("Début", 0);
		debut.setDateMin(0);
		this.lstTache.add(debut);

		try
		{
			Scanner sc = new Scanner ( new File ( chemin ), "UTF-8" );
			int numLigne = 0;

			while ( sc.hasNextLine() )
			{
				String ligne    = sc.nextLine();
				numLigne++;
				if (ligne.isEmpty()) continue;
				if (!testSeparateur(ligne, numLigne)) continue; // Vérifie le format de la ligne
				if (!testDureeInt(ligne, numLigne)) continue; // Vérifie que la durée est un entier
				String[] partie = ligne.split("\\|");

				String nom = partie[0];
				if (!testNomDoublon(nom, numLigne, ligne)) continue; // Vérifie les doublons de nom
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
				else
				{
					if(!tmp.getNom().equals("Début"))
						tmp.addPrecedent(this.lstTache.get(0));
				}

				this.lstTache.add(tmp);
				this.nbTache++;
			}

			Tache fin = new Tache("Fin", 0);
			for(Tache t : this.lstTache)
			{
				if(t.getNbSvt() == 0 && !t.getNom().equals("Début")) 
				{
					fin.addPrecedent(t);
				}
			}
			this.lstTache.add(fin);

			for(Tache t : this.lstTache)
			{
				if(t.getNbPrc() == 0 && !t.getNom().equals("Début") && !t.getNom().equals("Fin"))
				{
					t.addPrecedent(debut);
				}
				t.setNiveau();
			}

			this.majDate();
		}
		
		catch ( Exception e )
		{ 
			e.printStackTrace();
			this.erreur.add(new Erreur(e.getMessage()));  
		}
	}

	public ArrayList<Erreur> getErreur()
	{
		return this.erreur;
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
				
				if(tPrc.getDateMax() == -1 )
				{
					tPrc.setDateMax(nouvelleDateMax);
				}

				if(nouvelleDateMax < tPrc.getDateMax())
				{
					//System.out.println("12 > 0 PUTAIN DE MERDE");
					tPrc.setDateMax(nouvelleDateMax);
				}
				
				//System.out.println("tPrc.getDateMax() : " + tPrc.getDateMax() + " > "+ nouvelleDateMax + " : " + (tPrc.getDateMax() > nouvelleDateMax));
			}
		}
	}
	
		/**
	 * Vérifie si une ligne du fichier respecte le format attendu (2 séparateurs '|').
	 * 
	 * @param ligne la ligne à vérifier
	 * @param numLigne le numéro de la ligne dans le fichier
	 * @return true si la ligne est correctement formatée, false sinon
	 */
	private boolean testSeparateur(String ligne, int numLigne)
	{
		int nbSeparateurs = 0;
		for (int i = 0; i < ligne.length(); i++)
		{
			if (ligne.charAt(i) == '|')
			{
				nbSeparateurs++;
			}
		}
		if (nbSeparateurs != 2)
		{
			System.out.println(
					"Erreur de format à la ligne " + numLigne + " : " + ligne + " (doit contenir 2 séparateurs '|')");
			this.erreur.add(new Erreur(ligne, numLigne, 1));
			return false;
		}
		return true;
	}

	/**
	 * Vérifie si la 2e partie de la ligne est bien un nombre.
	 * 
	 * @param ligne
	 *            la ligne à tester
	 * @param numLigne
	 *            le numéro de la ligne
	 * @return true si c'est un nombre, false sinon (et ajoute une erreur)
	 */
	private boolean testDureeInt(String ligne, int numLigne)
	{
		String[] parties = ligne.split("\\|");
		if (parties.length < 2)
		{
			this.erreur.add(new Erreur(ligne, numLigne, 2));
			return false;
		}
		try
		{
			Integer.parseInt(parties[1]);
			return true;
		} catch (NumberFormatException e)
		{
			System.out.println("Erreur de durée à la ligne " + numLigne + " : " + parties[1] + " n'est pas un nombre");
			this.erreur.add(new Erreur(ligne, numLigne, 2));
			return false;
		}
	}

	/**
	 * Vérifie si le nom de la tâche est déjà présent dans la liste des tâches.
	 * 
	 * @param nom
	 *            le nom de la tâche à vérifier
	 * @param numLigne
	 *            le numéro de la ligne dans le fichier
	 * @param ligne
	 *            la ligne complète (pour le message d'erreur)
	 * @return true si le nom n'est pas en double, false sinon (et ajoute une
	 *         erreur)
	 */
	private boolean testNomDoublon(String nom, int numLigne, String ligne)
	{
		for (Tache t : this.lstTache)
		{
			if (t.getNom().equals(nom))
			{
				System.out.println("Erreur de nom en double à la ligne " + numLigne + " : " + nom);
				this.erreur.add(new Erreur(ligne, numLigne, 3)); // code 3 pour doublon
				return false;
			}
		}
		return true;
	}

	public void sauvegarderTaches(ArrayList<Tache> lstTaches)
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("./MPM/donnee/mpmNouveau.txt"), "UTF8"));

			for (Tache tache : lstTaches)
			{
				String predecesseurs = "";
				if (tache.getLstPrc() != null && !tache.getLstPrc().isEmpty())
				{
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < tache.getLstPrc().size(); i++)
					{
						if (i > 0) sb.append(",");
						sb.append(tache.getLstPrc().get(i).getNom());
					}
					predecesseurs = sb.toString();
				}

				pw.println(tache.getNom() + "|" + 
						tache.getDuree() + "|" + 
						predecesseurs);
			}
			
			pw.close();
			System.out.println("Tâches sauvegardées avec succès dans mpmNouveau.txt");
		}
		catch (Exception e)
		{ 
			e.printStackTrace(); 
		}
	}

	public void EnregistrerSous(ArrayList<Tache> lstTaches, String nomFichier) 
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("./MPM/donnee/" + nomFichier + ".txt"), "UTF8"));

			for (int cpt = 1; cpt < lstTaches.size() - 1; cpt++)
			{
				String predecesseurs = "";
				if (lstTaches.get(cpt).getLstPrc() != null && !lstTaches.get(cpt).getLstPrc().isEmpty())
				{
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < lstTaches.get(cpt).getLstPrc().size(); i++)
					{
						if (i > 0) sb.append(",");
						sb.append(lstTaches.get(cpt).getLstPrc().get(i).getNom());
					}
					predecesseurs = sb.toString();
				}

				if(predecesseurs.equals("Début")) 
					predecesseurs = "";

				pw.println(lstTaches.get(cpt).getNom()   + "|" + 
						   lstTaches.get(cpt).getDuree() + "|" + 
						   predecesseurs);
			}
			
			pw.close();
			System.out.println("Tâches sauvegardées avec succès dans "+ nomFichier + ".txt");
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
		}
	}

	
	/**
	 * Retourne une représentation textuelle du projet, incluant toutes les tâches et leurs détails.
	 * 
	 * @return une chaîne de caractères représentant le projet
	 */
	
	public String toString()
	{
		String sRet ="";
        
		for (Tache t : this.lstTache) 
			sRet += t.toString() + "\n\n";
		
		return sRet;
	}
}