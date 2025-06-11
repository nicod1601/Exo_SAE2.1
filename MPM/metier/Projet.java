package MPM.metier;

/**
 * Cette classe créer les projets et gère la planification des tâches selon la méthode MPM (Méthode des Potentiels Métra).
 * Elle permet de calculer les dates au plus tôt et au plus tard de chaque tâche, ainsi que la durée totale du projet.
 * 
 * @version 1.0
 * @since 2025-06-02
 * @author Groupe 09 - DELPECH Nicolas, FOYER Emilien, GUTU Nichita, KULPA Clément
 */
import MPM.metier.CheminCritique;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class Projet
{
	private ArrayList<Erreur> lstErreur;
	private ArrayList<Tache>  lstTache;
	private ArrayList<CheminCritique> lstCheminCritique;

	private int   nbTache;
	private int   nbTacheSurNiveau;
	private int   dureeProjet;
	private int   nbTacheMaxNiveau;
	private int[] ensTacheMaxNiveau;

	



	/**
	 * Constructeur de la classe Projet.
	 * Initialise un nouveau projet en lisant les données depuis un fichier
	 * et calcule automatiquement les dates de planification.
	 */
	public Projet()
	{
		this.dureeProjet 	   = 0;
		this.nbTacheMaxNiveau  = 0;
		this.lstTache    	   = new ArrayList<Tache> ();
		this.lstErreur 		   = new ArrayList<Erreur>();
		this.lstCheminCritique = new ArrayList<CheminCritique>();
	}

	public void addPrecedent(Tache t, Tache precedent) { t.addPrecedent(precedent); }

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
	public int getNbParNiveau(int niv)
	{
		int nb = 0;

		for (Tache t : this.lstTache)
		{
			if(t.getNiveau() == niv)
				nb++;

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
	public ArrayList<CheminCritique> getCheminCritiques() { return this.lstCheminCritique; }

	public int getNbNiveau()
	{
		int posDernier = this.lstTache.size()-1;
		return this.lstTache.get(posDernier).getNiveau()+1;
	}


	/**
	 * Lit un fichier de données du projet et construit la liste des tâches.
	 * Le fichier doit respecter le format : nom|durée|précédents (séparés par des virgules).
	 * 
	 * @param chemin le chemin du fichier à lire
	 * @throws Exception si le fichier n'est pas trouvé ou mal formaté
	 */
	public void lireFichier(String chemin) 
	{
		if (!fichierEstVide(chemin)) 
		{
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
					boolean ligneValide = true;
					
					if (ligne.isEmpty())                                 			 			ligneValide = false; // Ignore les lignes vides
					if (ligneValide && !testSeparateur(ligne, numLigne)) 			 			ligneValide = false; // Vérifie le format de la ligne
					if (ligneValide && !testDureeInt  (ligne, numLigne)) 			 			ligneValide = false; // Vérifie que la durée est un entier
					if (ligneValide && !testNomVide   (ligne, numLigne)) 			 			ligneValide = false; // Vérifie que le nom n'est pas vide
					if (ligneValide && !testPredecesseursMalFormes(ligne, numLigne)) 			ligneValide = false; // Vérifie la liste des prédécesseurs
					if (ligneValide && !testNomDoublon(ligne.split("\\|")[0], numLigne, ligne)) ligneValide = false; // Vérifie les doublons de nom

					if(ligneValide)
					{
						String[] partie = ligne.split("\\|");

						String nom = partie[0];
						
						int duree  = Integer.parseInt(partie[1]);

						Tache tmp  = new Tache(nom, duree);

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


						int posPrcDernier = 0 ;
						int posPrcTmp     = 0 ;
					
						if (!this.lstTache.isEmpty() && !(tmp.getNom().equals("Début") || tmp.getLstPrc().get(0).getNom().equals("Début")))
						{
							posPrcDernier = this.lstTache.indexOf(this.lstTache.get(this.lstTache.size()-1).getLstPrc().get(0));
							posPrcTmp = this.lstTache.indexOf(tmp.getLstPrc().get(0));

						}

						if (posPrcTmp < posPrcDernier )
							this.lstTache.add(posPrcDernier,tmp);

						else
							this.lstTache.add( tmp);

						tmp.setNiveau(tmp.getLstPrc());
						this.nbTache++;
					}
				
				}

				Tache fin = new Tache("Fin", 0);

				for(Tache t : this.lstTache)
				
					if(t.getNbSvt() == 0 && !t.getNom().equals("Début")) 
						fin.addPrecedent(t);
					
				
				this.lstTache.add(fin);

				for(Tache t : this.lstTache)
				{
					if(t.getNbPrc() == 0 && !t.getNom().equals("Début") && !t.getNom().equals("Fin"))
						t.addPrecedent(debut);
					
					t.setNiveau(t.getLstPrc());
				}

				this.ensTacheMaxNiveau = new int[this.getNbNiveau()];

				this.majHauteur();

				

				if(!this.testTropDeNiveaux()) this.lstTache.clear();
				else 						  this.majDate();
				
				this.afficherLstTacheCritique();

				for(CheminCritique c : this.lstCheminCritique)
					System.out.println(c.toString());

				System.out.println(this.lstCheminCritique.size());
				
			}
			
			catch ( Exception e )
			{ 
				e.printStackTrace();
				this.lstErreur.add(new Erreur(e.getMessage()));  
			}

			this.ecrireErreursDansLog();
		}
	}

	// Méthode publique qui lance la détermination des chemins critiques à partir des tâches sans prédécesseurs (racines)
	public void afficherLstTacheCritique() 
	{
		this.lstCheminCritique.clear();
	
		

		for (Tache t : this.lstTache) 
		{
			//System.out.println("afficher dans le for");
			
			//System.out.println(t);
			//System.out.println("" + t.getLstPrc().isEmpty() + " " + (t.getMarge()  == 0 ));

			if (t.getLstPrc().isEmpty() && t.getMarge() == 0) {
				ArrayList<Tache> chemin = new ArrayList<>();
				//System.out.println("afficher dans le if");

				determinerCheminCritique(t, chemin);

			}
		}
	}

	// Méthode privée récursive qui construit les chemins critiques
	private void determinerCheminCritique(Tache tache, ArrayList<Tache> cheminActuel) {
		cheminActuel.add(tache);

		// Trouver les successeurs critiques (ayant la tâche actuelle comme précédent et marge == 0)
		ArrayList<Tache> successeursCritiques = new ArrayList<>();
		for (Tache t : this.lstTache) {
			if (t.getLstPrc().contains(tache) && t.getMarge() == 0) {
				successeursCritiques.add(t);
			}
		}

		
		if (successeursCritiques.isEmpty()) {
			// Pas de successeur critique, c'est une fin de chemin critique
			this.lstCheminCritique.add(new CheminCritique(new ArrayList<>(cheminActuel)));
		}

		else {
			// Pour chaque successeur critique, continuer la récursion
			for (Tache succ : successeursCritiques) {
				determinerCheminCritique(succ, new ArrayList<>(cheminActuel));
			}
		}
	}


	public void majHauteur()
	{
		for(Tache t : this.lstTache)
		{
			if (t.getNiveau() == this.ensTacheMaxNiveau.length /* this.ensTacheMaxNiveau.length == 0*/)
				this.ensTacheMaxNiveau[t.getNiveau()] = 0;
			


			int hauteurTemp = t.setHauteur(this.ensTacheMaxNiveau[t.getNiveau()] );

			if( hauteurTemp> this.ensTacheMaxNiveau[t.getNiveau()])
				this.ensTacheMaxNiveau[t.getNiveau()] =hauteurTemp;
			
			this.ensTacheMaxNiveau[t.getNiveau()]++;
			//System.out.println("" +this.ensTacheMaxNiveau[t.getNiveau()]);
		}

	}


	public String getFichier(String chemin)
	{
		String str = "";
		try
		{
			Scanner sc = new Scanner ( new File ( chemin ), "UTF-8" );
			int numLigne = 0;

			while ( sc.hasNextLine() )
			{
				String ligne    = sc.nextLine();
				str += ligne + "\n";

			}
		}
		catch ( Exception e )
		{ 
			e.printStackTrace();
			this.lstErreur.add(new Erreur(e.getMessage() ) );  
		}

		
		return str;
	}	

	/**
	 * Retourne la liste des erreurs rencontrées lors de la lecture du fichier.
	 * 
	 * @return la liste des erreurs
	 */
	public ArrayList<Erreur> getLstErreur() { return this.lstErreur; }
	

	/* Cette méthode permet de parcourir la liste des tâches
	 * pour recalculer les dates au plus tôt et au plus tard
	 */
	
	public void majDate()
	{
		for(Tache t : this.lstTache)
		{
			if(t.getNom().equals("Début"))
			{
				t.forceSetDateMin(0);
				t.forceSetDateMax(0);
			}
			else
			{
				t.forceSetDateMax(-1);
				t.forceSetDateMin(-1);
			}
		}
		
		this.trierTachesParNiveau();
		this.setDateMin();
		this.setDateMax();

		//for(Tache t : this.lstTache)
		//	System.out.println(t);
		
	}

	public void trierTachesParNiveau() 
	{
		for (int i = 0; i < this.lstTache.size() - 1; i++)
		 {
			for (int j = 0; j < this.lstTache.size() - 1 - i; j++)
			{
				if (this.lstTache.get(j).getNiveau() > this.lstTache.get(j + 1).getNiveau())
				{
					// Échange les deux tâches
					Tache temp = this.lstTache.get(j);
					this.lstTache.set(j, this.lstTache.get(j + 1));
					this.lstTache.set(j + 1, temp);
				}
			}
		}
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
		if(! this.lstTache.isEmpty() ) 
		{
			Tache t, tPrc;
			int nouvelleDateMax;

			t = this.lstTache.get(this.lstTache.size()-1);
			t.setDateMax(t.getDateMin());

			for(int cptP = this.lstTache.size()-1; cptP > 0; cptP--)
			{
				t = this.lstTache.get(cptP);

				for(int cpt = 0; cpt < t.getNbPrc(); cpt++)
				{
					tPrc 			= t.getPrc(cpt);
					nouvelleDateMax = t.getDateMax() - tPrc.getDuree();
					
					if(tPrc.getDateMax() < 0 ) 			   	  tPrc.setDateMax(nouvelleDateMax);
					if(nouvelleDateMax 	 < tPrc.getDateMax()) tPrc.setDateMax(nouvelleDateMax);
				}
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
			if (ligne.charAt(i) == '|') nbSeparateurs++;
		
		if (nbSeparateurs != 2)
		{
			//System.out.println(
			//		"Erreur de format à la ligne " + numLigne + " : " + ligne + " (doit contenir 2 séparateurs '|')");
			this.lstErreur.add(new Erreur(ligne, numLigne, 1));
			return false;
		}
		return true;
	}

	/**
	 * Vérifie si la 2e partie de la ligne est bien un nombre.
	 * 
	 * @param ligne la ligne à tester
	 * @param numLigne le numéro de la ligne
	 * @return true si c'est un nombre, false sinon (et ajoute une erreur)
	 */
	private boolean testDureeInt(String ligne, int numLigne)
	{
		String[] parties = ligne.split("\\|");
		if (parties.length < 2)
		{
			this.lstErreur.add(new Erreur(ligne, numLigne, 2));
			return false;
		}
		try
		{
			Integer.parseInt(parties[1]);
			return true;
		} catch (NumberFormatException e)
		{
			//System.out.println("Erreur de durée à la ligne " + numLigne + " : " + parties[1] + " n'est pas un nombre");
			this.lstErreur.add(new Erreur(ligne, numLigne, 2));
			return false;
		}
	}

	/**
	 * Vérifie si le nom de la tâche est déjà présent dans la liste des tâches.
	 * 
	 * @param nom le nom de la tâche à vérifier
	 * @param numLigne le numéro de la ligne dans le fichier
	 * @param ligne la ligne complète (pour le message d'erreur)
	 * @return true si le nom n'est pas en double, false sinon (et ajoute une
	 *         erreur)
	 */
	private boolean testNomDoublon(String nom, int numLigne, String ligne)
	{
		for (Tache t : this.lstTache)
		{
			if (t.getNom().equals(nom))
			{
				//System.out.println("Erreur de nom en double à la ligne " + numLigne + " : " + nom);
				this.lstErreur.add(new Erreur(ligne, numLigne, 3)); // code 3 pour doublon
				return false;
			}
		}
		return true;
	}

		/**
	 * Vérifie si le nom de la tâche est vide.
	 * 
	 * @param ligne la ligne à tester
	 * @param numLigne le numéro de la ligne
	 * @return true si le nom n'est pas vide, false sinon (et ajoute une erreur)
	 */
	private boolean testNomVide(String ligne, int numLigne) 
	{
		String[] parties = ligne.split("\\|");
		if (parties.length < 1 || parties[0].trim().isEmpty()) //trim permet de retirer les espaces 
		{
			//System.out.println("Erreur : nom de tâche vide à la ligne " + numLigne);
			this.lstErreur.add(new Erreur(ligne, numLigne, 5)); // code 5 pour nom vide
			return false;
		}
		return true;
	}


	/**
	 * Vérifie si la liste des prédécesseurs est bien formée.
	 * 
	 * @param ligne la ligne à tester
	 * @param numLigne le numéro de la ligne
	 * @return true si la liste est correcte, false sinon (et ajoute une erreur)
	 */
	private boolean testPredecesseursMalFormes(String ligne, int numLigne) 
	{
		String[] parties = ligne.split("\\|");
		if (parties.length < 3 || parties[2].isEmpty()) return true; // pas de prédécesseur, donc OK
		
		String[] prc = parties[2].split(",");
		for (String nom : prc) 
		{
			if (nom.trim().isEmpty())
			{
				//System.out.println("Erreur : prédécesseur mal formé à la ligne " + numLigne + " : " + ligne);
				this.lstErreur.add(new Erreur(ligne, numLigne, 6)); 
				return false;
			}
		}
		return true;
	}

	/**
	 * Vérifie si le nombre de niveaux dépasse la limite autorisée (200). Ajoute
	 * une erreur si la limite est dépassée.
	 * 
	 * @return true si le nombre de niveaux est correct, false sinon
	 */
	private boolean testTropDeNiveaux()
	{
		if (this.getNbNiveau() > 202) // 200 niveaux + 2 (Début et Fin)
		{
			//System.out.println("Erreur : trop de niveaux (plus de 200) - Nombre de niveaux : " + this.getNbNiveau());
			this.lstErreur.add(new Erreur( 8));
			return false;
		}
		return true;
	}

	/**
	 * Vérifie si le fichier est vide ou contient uniquement des espaces.
	 * 
	 * @param chemin le chemin du fichier à vérifier
	 * @return true si le fichier est vide, false sinon
	 */
	private boolean fichierEstVide(String chemin)
	{
		File fichier = new File(chemin); 
		if (fichier.exists() && fichier.length() == 0  )
		{
			//System.out.println("Le fichier : " + chemin + "est vide : " );
			this.lstErreur.add(new Erreur(9));
			return true;
		}
		else
		{
			try
			{
				Scanner sc      = new Scanner(new File(chemin), "UTF-8");
				String  contenu = "";

				while (sc.hasNextLine())
					contenu += sc.nextLine(); 
				 
				if (contenu.trim().isEmpty()) // <-- Utilise trim() pour enlever les espaces inutiles
				{
					//System.out.println("Le fichier : " + chemin + " est vide." );
					this.lstErreur.add(new Erreur(9));
					return true;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.lstErreur.add(new Erreur(e.getMessage()));
			}
		}
		
		return false;
	}

	/**
	 * Écrit les erreurs rencontrées dans un fichier de log.
	 * Le fichier est nommé "erreurs_YYYY-MM-DD_HH-mm-ss.log" et stocké dans le dossier "logs".
	 * 
	 */
	private void ecrireErreursDansLog()
	{
		if (!this.lstErreur.isEmpty())
		{
			try
			{
				File dossierLogs = new File("logs");
				
				if (!dossierLogs.exists()) 
					dossierLogs .mkdirs(); // <-- Utilise mkdirs() pour créer tous les dossiers nécessaires
				
				GregorianCalendar cal 	  = new GregorianCalendar();
				String 			  annee   = String.format("%04d", cal.get(Calendar.YEAR			) 	 );
				String 			  mois    = String.format("%02d", cal.get(Calendar.MONTH		) + 1);
				String 			  jour    = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH ) 	 );
				String 			  heure   = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY  ) 	 );
				String 			  minute  = String.format("%02d", cal.get(Calendar.MINUTE		) 	 );
				String 			  seconde = String.format("%02d", cal.get(Calendar.SECOND		) 	 );

				String date = annee + "-" + mois + "-" + jour + "_" + heure + "-" + minute + "-" + seconde;

				String nomFichier = "logs/erreurs_" + date +".log";
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "UTF8"));

				for (Erreur e : this.lstErreur)
					pw.println(e.toString());
				
				pw.close();
				//System.out.println("Erreurs enregistrées dans erreurs.log");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.lstErreur.add(new Erreur(e.getMessage())); 
			}
		}
	}

	public void supprimerTache(Tache tacheASupprimer)
	{
		int niveauTacheSuprimee = tacheASupprimer.getNiveau();
		
		for(int cpt = 0; cpt < tacheASupprimer.getNbPrc(); cpt++)
		{
			tacheASupprimer.getLstPrc().get(cpt).getLstSvt().remove(tacheASupprimer);
		}
		
		for(int cpt = 0; cpt < tacheASupprimer.getNbSvt(); cpt++)
		{
			tacheASupprimer.getLstSvt().get(cpt).getLstPrc().remove(tacheASupprimer);
		}
		
		this.lstTache.remove(tacheASupprimer);
		
		for(Tache t : this.lstTache)
		{
			t.setNiveau(t.getLstPrc());
		}
		
		Tache tacheFin = this.lstTache.get(this.lstTache.size() - 1);
		int niveauMaxActuel = 0;
		
		for(Tache t : this.lstTache) 
		{
			if(!t.equals(tacheFin) && t.getNiveau() > niveauMaxActuel)
			{
				niveauMaxActuel = t.getNiveau();
			}
		}
		
		tacheFin.setNiveau(niveauMaxActuel + 1);
		
		this.majDate();
	}


		

	public void ajouterTache(Tache nouvelleTache)
	{
		int indexFin = this.lstTache.size() - 1;

		this.lstTache.add(indexFin, nouvelleTache);

		if(nouvelleTache.getNbPrc() == 0)
		{
			nouvelleTache.addPrecedent(this.lstTache.get(0));
		}
		else
		{
			for(int cpt = 0; cpt < nouvelleTache.getNbPrc(); cpt++)
				nouvelleTache.getLstPrc().get(cpt).getLstSvt().add(nouvelleTache);
		}
	
		for(Tache t : this.lstTache) 
			t.setNiveau(t.getLstPrc());
	
		Tache tacheFin = this.lstTache.get(this.lstTache.size() - 1);

		if(nouvelleTache.getNiveau() == tacheFin.getNiveau()) 
			tacheFin.setNiveau(tacheFin.getNiveau() + 1);
		
		this.majDate();

	}

	public void enregistrer(String lien, ArrayList<Tache> lstTaches)
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(lien), "UTF8") );

			for (Tache tache : lstTaches)
			{
				// Exclure les tâches "Début" et "Fin"
				if (! tache.getNom().equals("Début") && ! tache.getNom().equals("Fin") )
				{
					String predecesseurs = "";

					if (tache.getLstPrc() != null && !tache.getLstPrc().isEmpty())
					{
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < tache.getLstPrc().size(); i++)
						{
							// Exclure "Début" des prédécesseurs
							if (!tache.getLstPrc().get(i).getNom().equals("Début"))
							{
								if (sb.length() > 0) sb.append(",");
								sb.append(tache.getLstPrc().get(i).getNom());
							}
						}
						predecesseurs = sb.toString();

						pw.println(tache.getNom() + "|" + 
						tache.getDuree() 		  + "|" + 
						predecesseurs);
					}
				}

				
			}
			
			pw.close();
			//System.out.println("Tâches sauvegardées avec succès dans " + lien);
		}
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
			this.lstErreur.add(new Erreur(e.getMessage()));
		}
	}

	public void enregistrerSous(String lien, ArrayList<Tache> lstTaches) 
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(lien), "UTF8"));

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
			//System.out.println();
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
			this.lstErreur.add(new Erreur(e.getMessage())); 
		}
	}

	public void modifierTache(String nomTache, int dureeTache, Tache tache) 
	{
		Tache tmp = null;
		String[] tabNom = new String[this.lstTache.size()];
		for(Tache t : this.lstTache)
		{
			if(t.getNom().equals(tache.getNom()))
			{
				tmp = t;
				t.setNom(nomTache);
				t.setDuree(dureeTache);
			}
			tabNom[this.lstTache.indexOf(t)] = t.getNom();
		}

		// verif des nom en double
		for(int i = 0; i < tabNom.length; i++)
		{
			for(int j = i + 1; j < tabNom.length; j++)
			{
				if(tabNom[i].equals(tabNom[j]))
				{
					this.lstTache.get(i).setNom(tmp.getNom());
					this.lstTache.get(i).setDuree(tmp.getDuree());
				}
			}
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