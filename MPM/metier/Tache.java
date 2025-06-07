package MPM.metier;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Représente une tâche dans un projet, avec une durée, une date de début au plus tôt, 
 * une date de fin au plus tard, et des dépendances de tâches (précédentes et suivantes).
 * <p>
 * Cette classe est utilisée dans le cadre de la méthode MPM pour la planification de projet.
 * </p>
 * 
 * Groupe : 09
 * Exercice 2
 * Participants : 
 * DELPECH Nicolas, FOYER Emilien, GUTU Nichita, KULPA Clément
 * Date de création : 02/06/2025 16h42
 */


public class Tache
{

	private String nom;
	private int    duree;
	private int    dateMin;
	private int    dateMax;

	private int niveau = 0;
	private int hauteur = 0;

	private ArrayList<Tache> lstPrc;
	private ArrayList<Tache> lstSvt;

	/**
	 * Crée une nouvelle tâche avec un nom et une durée.
	 * Initialise les dates min et max à des valeurs par défaut,
	 * et les listes de précédents et suivants comme vides et le niveau
	 * qui est initialiser correctement.
	 * 
	 * @param nom   le nom de la tâche
	 * @param duree la durée de la tâche en jours
	 */
	public Tache(String nom, int duree)
	{
		this.nom     = nom;
		this.duree   = duree;
		this.dateMin = -1;
		this.dateMax = -1;
		this.lstPrc  = new ArrayList<Tache>();
		this.lstSvt  = new ArrayList<Tache>();
	}

	public Tache()
	{
		this.nom     = "A";
		this.duree   = 0;
		this.dateMin = -1;
		this.dateMax = -1;
		this.lstPrc  = new ArrayList<Tache>();
		this.lstSvt  = new ArrayList<Tache>();
	}

	/**
	 * Crée une copie d'une tâche existante (copie profonde des dates,
	 * mais copie superficielle des dépendances).
	 * 
	 * @param t la tâche à copier
	 */
	public Tache(Tache t)
	{
		this.nom     = t.nom;
		this.duree   = t.duree;
		this.dateMin = t.dateMin;
		this.dateMax = t.dateMax;
		this.lstPrc  = new ArrayList<>(t.lstPrc);
		this.lstSvt  = new ArrayList<>(t.lstSvt);
	}
	

	/** @return le nom de la tâche */
	public String getNom() { return this.nom; }


	/** @return la durée de la tâche en jours */
	public int getDuree() { return this.duree; }

	/**
	 * Renvoie une tâche à un indice donné dans une liste.
	 * 
	 * @param indice l'indice de la tâche
	 * @param lst    la liste contenant les tâches
	 * @return la tâche à l’indice spécifié
	 */
	public Tache getTache(int indice, ArrayList<Tache> lst) { return lst.get(indice); }


	/** @return la date au plus tôt pour commencer la tâche */
	public int getDateMin() { return this.dateMin; }


	/** @return la date au plus tard pour finir la tâche */
	public int getDateMax() { return this.dateMax; }


	/** @return la liste des tâches précédentes */
	public ArrayList<Tache> getLstPrc() { return this.lstPrc; }


	/** @return la liste des tâches suivantes */
	public ArrayList<Tache> getLstSvt() { return this.lstSvt; }


	/** @return le nombre de tâche précédentes */

	public int getNbPrc()  {return this.lstPrc.size()  ;}


	/** @return le nombre de tâche suivantes */
	public int getNbSvt()  {return this.lstSvt.size()  ;}


	/** @return la tâche suivante à l'indice sélectionné */

	public Tache getSvt(int index){return this.lstSvt.get(index); }


	/** @return la tâche précédente à l'indice sélectionné */

	public Tache getPrc(int index){return this.lstPrc.get(index); }

	/**
	 * Définit la date au plus tôt pour cette tâche.
	 * 
	 * @param dateMin la nouvelle date minimale
	 */
	

	public void setNiveau()
	{
		//System.out.println("coucou");
		
		//System.out.println(! this.lstPrc.isEmpty() );
		
		if (! this.lstPrc.isEmpty())
		{
			int nivTemp =0 ;
			for (Tache t : this.lstPrc)
			{
				if(nivTemp < t.getNiveau()+1)
					nivTemp = t.getNiveau()+1;
			}
				this.niveau = nivTemp;
			
			//System.out.println("il a un precedent");
			
		}
			
	}
	//coucou nicolas devine qui cest ?
	public void setNiveau(int val) { this.niveau = val; }

	public int getNiveau() { return this.niveau;}

	/**
	 * Définit la date au plus tard pour cette tâche.
	 * 
	 * @param dateMax la nouvelle date maximale
	 */
	public void setDateMax(int val) 
	{ 
		if(this.dateMax < 0 || val < dateMax)
		{
			this.dateMax = val;
		}
	}
	public void setDateMin(int val)
	{ 
		if(this.dateMin == -1 || val > dateMin)
		{
			this.dateMin = val;
		}
	}
	public void setNom(String nom)
	{
		this.nom = nom;
	}


	/**
	 * Calcule et retourne la marge de la tâche (différence entre dateMax et dateMin).
	 * 
	 * @return la marge en jours
	 */
	public int getMarge()
	{
		return this.dateMax-this.dateMin;
	}

	/**
	 * Ajoute une tâche comme précédente. Cette tâche devient aussi une suivante
	 * de la tâche ajoutée.
	 * 
	 * @param t la tâche précédente à ajouter
	 */
	public void addPrecedent(Tache t)
	{
		this.lstPrc.add(t);
		t.lstSvt.add(this);
	}

	/**
	 * Retourne une représentation textuelle détaillée de la tâche :
	 * nom, durée, dates au plus tôt et au plus tard, marge, dépendances, niveau.
	 * 
	 * @return la description de la tâche
	 */
	public String toString()
	{
		String sRet = "";

		sRet += this.nom + " : ";
		sRet += this.duree + " jour" + (this.duree > 1 ? "s" : "");

		sRet += "\n" + String.format("%20s", "  date au plus tôt  : ") +
				String.format("%02d", this.dateMin );

		sRet += "\n" + String.format("%20s", "  date au plus tard : ") +
				String.format("%02d", this.dateMax);
		sRet += "\n  marge" + String.format("%16s", " : " + this.getMarge());

		if (this.lstPrc.isEmpty() || this.lstPrc.get(0).nom.equals("Début") )
		{
			sRet += "\n" + String.format("%25s", "pas de tâche précédente");
		} 

		else
		{
			sRet += "\n" + String.format("%34s", "liste des tâches précédentes : \n") + "     ";
			for (int cpt = 0; cpt < this.lstPrc.size(); cpt++)

				sRet += this.lstPrc.get(cpt).getNom() + (cpt < this.lstPrc.size() - 1 ? ", " : "");
		}
			
		if ( this.lstSvt.isEmpty() || this.lstSvt.get(0).nom.equals("Fin") ) 
		{
			sRet += "\n" + String.format("%23s", "pas de tâche suivante");	
		}

		else
		{
			sRet += "\n" + String.format("%32s", "liste des tâches suivantes : \n") + "     ";
			for (int cpt = 0; cpt < this.lstSvt.size(); cpt++)

				sRet += this.lstSvt.get(cpt).getNom() + (cpt < this.lstSvt.size() - 1 ? ", " : "");
		}
		
			
		sRet += "\nNiveau : " + niveau + "\n\n";

		return sRet;
	}
}
