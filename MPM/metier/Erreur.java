package MPM.metier;

/**
 * Représente une erreur détectée lors de la lecture ou du traitement d'un
 * fichier de tâches.
 *
 * <p>
 * Codes d'erreur utilisés :
 * <ul>
 * <li><b>0</b> : Erreur inconnue</li>
 * <li><b>1</b> : Erreur de format (le nombre de séparateurs '|' n'est pas égal
 * à 2)</li>
 * <li><b>2</b> : La durée n'est pas un entier ou champ durée manquant</li>
 * <li><b>3</b> : Nom de tâche en double</li>
 * <li><b>4</b> : Prédécesseur inexistant ou durée négative/nulle</li>
 * </ul>
 * </p>
 */

public class Erreur 
{
	private String ligne;
	private int numLigne; 
	private int codeErreur;
	
	public Erreur(String ligne, int numLigne, int codeErreur) 
	{
		this.ligne      = ligne;
		this.numLigne   = numLigne;
		this.codeErreur = codeErreur;
	}

	public Erreur(int codeErreur) 
	{
        this ("", 0, codeErreur);
	}

	public Erreur(String message)
	{
        this (message, 0, 0);
	}

	public String getMessage() 
	{
		return switch (this.codeErreur) 
		{
			
			case 1  -> "Erreur de format dans le fichier importé à la ligne " + this.numLigne + " : "                       + this.ligne + " (doit contenir 2 séparateurs '|')";
			case 2  -> "La durée indiquée à la ligne "  				      + this.numLigne + " n'est pas un entier : "   + this.ligne;
			case 3  -> "La nom de la tâche à la ligne " 				      + this.numLigne + " est déjà utilisé : "      + this.ligne;
			case 4  -> "Le prédécesseur à la ligne "    				      + this.numLigne + " n'existe pas : "          + this.ligne;
			case 5  -> "La tâche à la ligne "           				      + this.numLigne + " n'a pas de nom : "        + this.ligne;
			case 6  -> "Le ou les prédécesseurs de la tâche à la ligne " 	  + this.numLigne + " n'ont pas de nom : " 	    + this.ligne;
			case 7  -> "Le fichier importé n'existe pas ou n'est pas accessible."; 
			case 8  -> "Le nombre de niveaux dépasse la limite autorisée (200 niveaux)"; 
			case 9  -> "Le fichier importé est vide.";
			
			default -> "Erreur inconnue : " + this.ligne;
		};
	}
	
	public String getLigne	 	() { return this.ligne;     }
	public int 	  getNumLigne	() { return this.numLigne;  }
	public int 	  getCodeErreur () { return this.codeErreur;}

	public String toString() { return "Ligne " + this.numLigne + " : " + getMessage() +  " --> " + this.ligne; }
    
}
