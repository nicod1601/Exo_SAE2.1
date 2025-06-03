package MPM.metier;

import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;


/**
 * La classe {@code DateFr} étend {@link GregorianCalendar} et offre une
 * gestion de dates adaptée au format français (JJ/MM/AAAA), avec des
 * améliorations pour l'affichage, la gestion des jours fériés fixes,
 * les différences entre dates et l'ajout de jours.
 * 
 * <p>Elle ajuste notamment les mois (base 1 au lieu de 0) et le jour
 * de la semaine (dimanche = 7).</p>
 * 
 * @author Groupe 09
 * @version 1.0
 * @since 2025-06-02
 */

public class DateFr extends GregorianCalendar
{
	/**
	 * Construit une date correspondant à l'instant présent (date et heure actuelles).
	 */
	public DateFr() 
	{
		super(); 
	}
	
	/**
	 * Construit une date avec uniquement le jour, mois et année.
	 *
	 * @param jour  Le jour du mois (1-31)
	 * @param mois  Le mois (1-12)
	 * @param annee L'année
	 */
	public DateFr(int jour, int mois, int annee)
	{
		super(annee, mois-1, jour, 0, 0, 0);
	}
	
	/**
	 * Construit une date à partir d'une chaîne de caractères au format "JJ/MM/AAAA".
	 *
	 * @param date La date au format "JJ/MM/AAAA"
	 */
	public DateFr(String date) 
	{
		super();
		int jour, mois, annee;
		jour  = Integer.parseInt(date.substring(0, 2));
		mois  = Integer.parseInt(date.substring(3, 5));
		annee = Integer.parseInt(date.substring(6));
		
		this.set(DAY_OF_MONTH, jour);
		this.set(MONTH, mois - 1);
		this.set(YEAR, annee);
		this.set(HOUR_OF_DAY, 0);
	}	
	
	/**
	 * Constructeur de copie d'une autre instance de {@code DateFr}.
	 *
	 * @param autreDate Une autre instance {@code DateFr}
	 */
	public DateFr(DateFr autreDate)
	{
		this(autreDate.get(DateFr.DAY_OF_MONTH), 
			 autreDate.get(DateFr.MONTH), 
			 autreDate.get(DateFr.YEAR));
	} 

	/**
	 * Redéfinit la méthode {@code get()} pour :
	 * <ul>
	 *   <li>Retourner le mois en base 1 (janvier = 1)</li>
	 *   <li>Retourner le dimanche comme jour 7 (au lieu de 1)</li>
	 * </ul>
	 *
	 * @param field Le champ du calendrier
	 * @return La valeur du champ, adaptée au format français
	 */
	public int get(int field)
	{
		int nb = -1;
		switch (field)
		{
			case YEAR, MINUTE, SECOND, DAY_OF_MONTH, DAY_OF_YEAR -> nb = super.get(field);
			case DAY_OF_WEEK -> 
			{
				nb = super.get(field);
				if (nb == 1) nb = 7; 
			}
			case MONTH -> nb = super.get(field) + 1;
			case HOUR_OF_DAY -> nb = super.get(field);
		}
		return nb;
	}

	/**
	 * Vérifie si la date actuelle correspond à un jour férié fixe en France :
	 * <ul>
	 *   <li>1er janvier (Jour de l'an)</li>
	 *   <li>1er mai (Fête du travail)</li>
	 *   <li>8 mai (Victoire 1945)</li>
	 *   <li>14 juillet (Fête nationale)</li>
	 *   <li>11 novembre (Armistice 1918)</li>
	 * </ul>
	 *
	 * @return {@code true} si la date est un jour férié fixe, sinon {@code false}
	 */
	
	public boolean estFerie() 
	{
		int jour = get(DAY_OF_MONTH);
		int mois = get(MONTH);

		// Jours fixes
		if ((jour == 1 && mois == 1)  ||  // Jour de l'an
			(jour == 1 && mois == 5)  ||  // Fête du travail
			(jour == 8 && mois == 5)  ||  // Victoire 1945
			(jour == 14 && mois == 7) ||  // Fête Nationale
			(jour == 11 && mois == 11))   // Armistice 1918
			return true;

		return false;
	}

	/**
	 * Ajoute un nombre de jours à la date actuelle.
	 *
	 * @param nbJours Le nombre de jours à ajouter (positif ou négatif)
	 */
	public void addJours(int nbJours) 
	{ 
		this.add(DateFr.DAY_OF_YEAR, nbJours); 
	}

	/**
	 * Calcule la différence en jours entre cette date et une autre date {@code DateFr}.
	 *
	 * @param autre L'autre date
	 * @return Le nombre de jours de différence (positif si plus tard, négatif si plus tôt)
	 */
	public int differenceEnJours(DateFr autre)
	{
		long diffMs = this.getTimeInMillis() - autre.getTimeInMillis();
		return (int) (diffMs / (24 * 60 * 60 * 1000));
	}


	/**
	 * Compare cette date à une autre selon l'ordre chronologique.
	 *
	 * @param autre L'autre date à comparer
	 * @return Un entier négatif, zéro ou positif si cette date est
	 *         respectivement antérieure, égale ou postérieure à {@code autre}
	 */
	public int compareTo(Calendar autre)
	{
		return super.compareTo(autre);
	}

	/**
	 * Retourne une représentation textuelle complète de la date
	 * au format : "JJ/MM/AAAA 
	 *
	 * @return La date sous forme de chaîne formatée
	 */
	public String toString()
	{
		return  String.format("%02d", this.get(DateFr.DAY_OF_MONTH)) + "/" + 
				String.format("%02d", this.get(DateFr.MONTH))        + "/" + 
				String.format("%04d", this.get(DateFr.YEAR));
	}
}