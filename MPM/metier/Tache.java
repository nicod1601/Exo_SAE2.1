package MPM.metier;

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

import java.util.ArrayList;

public class Tache
{

	private String nom;
	private int duree;
	private DateFr dateMin;
	private DateFr dateMax;

	private ArrayList<Tache> lstPrc;
	private ArrayList<Tache> lstSvt;

	/**
	 * Crée une nouvelle tâche avec un nom et une durée.
	 * Initialise les dates min et max à des valeurs par défaut,
	 * et les listes de précédents et suivants comme vides.
	 * 
	 * @param nom   le nom de la tâche
	 * @param duree la durée de la tâche en jours
	 */
	public Tache(String nom, int duree)
	{
		this.nom     = nom;
		this.duree   = duree;
		this.dateMin = new DateFr();
		this.dateMax = new DateFr();
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
		this.dateMin = new DateFr(t.dateMin);
		this.dateMax = new DateFr(t.dateMax);
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
	public Tache getTache(int indice, ArrayList<Tache> lst) {
		return lst.get(indice);
	}

	/** @return la date au plus tôt pour commencer la tâche */
	public DateFr getDateMin() { return this.dateMin; }

	/** @return la date au plus tard pour finir la tâche */
	public DateFr getDateMax() { return this.dateMax; }

	/** @return la liste des tâches précédentes */
	public ArrayList<Tache> getLstPrc() { return this.lstPrc; }

	/** @return la liste des tâches suivantes */
	public ArrayList<Tache> getLstSvt() { return this.lstSvt; }

	/**
	 * Définit la date au plus tôt pour cette tâche.
	 * 
	 * @param dateMin la nouvelle date minimale
	 */
	public void setDateMin(DateFr dateMin) { this.dateMin = dateMin; }

	/**
	 * Définit la date au plus tard pour cette tâche.
	 * 
	 * @param dateMax la nouvelle date maximale
	 */
	public void setDateMax(DateFr dateMax) { this.dateMax = dateMax; }

	/**
	 * Calcule et retourne la marge de la tâche (différence entre dateMax et dateMin).
	 * 
	 * @return la marge en jours
	 */
	public int getMarge()
	{
		return dateMax.differenceEnJours(dateMin);
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
	 * Ajoute une tâche comme suivante sans modifier l'autre tâche.
	 * 
	 * @param t la tâche suivante à ajouter
	 */
	public void addSuivant(Tache t)
	{
		this.lstSvt.add(t);
	}

	/**
	 * Retourne une représentation textuelle détaillée de la tâche :
	 * nom, durée, dates au plus tôt et au plus tard, marge, dépendances.
	 * 
	 * @return la description de la tâche
	 */
	public String toString()
	{
		String sRet = "";

		sRet += this.nom + " : ";
		sRet += this.duree + " jour" + (this.duree > 1 ? "s" : "");

		sRet += "\n" + String.format("%20s", "  date au plus tôt  : ") +
				String.format("%02d", this.dateMin.get(DateFr.DAY_OF_MONTH)) + "/" +
				String.format("%02d", this.dateMin.get(DateFr.MONTH));

		sRet += "\n" + String.format("%20s", "  date au plus tard : ") +
				String.format("%02d", this.dateMax.get(DateFr.DAY_OF_MONTH)) + "/" +
				String.format("%02d", this.dateMax.get(DateFr.MONTH));

		sRet += "\n  marge" + String.format("%17s", " : " + this.getMarge());

		if (this.lstPrc.size() != 0)
		{
			sRet += "\n" + String.format("%34s", "liste des tâches précédentes : \n") + "     ";
			for (int cpt = 0; cpt < this.lstPrc.size(); cpt++)
				sRet += this.lstPrc.get(cpt).getNom() + (cpt < this.lstPrc.size() - 1 ? ", " : "");
		} 
		else
		{
			sRet += "\n" + String.format("%25s", "pas de tâche précédente");
		}

		if (this.lstSvt.size() != 0) 
		{
			sRet += "\n" + String.format("%32s", "liste des tâches suivantes : \n") + "     ";
			for (int cpt = 0; cpt < this.lstSvt.size(); cpt++)
				sRet += this.lstSvt.get(cpt).getNom() + (cpt < this.lstSvt.size() - 1 ? ", " : "");
		}
		else
		{
			sRet += "\n" + String.format("%23s", "pas de tâche suivante");
		}

		return sRet;
	}

	/*
	public static void main(String[] args) {
		Tache t1 = new Tache("Test", 12);
		System.out.println(t1);
	}
	*/
}
