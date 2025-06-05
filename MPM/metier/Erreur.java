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
		this.ligne = ligne;
		this.numLigne = numLigne;
		this.codeErreur = codeErreur;
	}

	public Erreur(int codeErreur) 
	{
		this.codeErreur = codeErreur;
		this.ligne = "";
		this.numLigne = 0;
	}

	public Erreur(String message)
	{
		this.ligne = message;
		this.numLigne = 0;
		this.codeErreur = 0;
	}

	public String getMessage() 
	{
		switch (this.codeErreur) 
		{
			case 0:
				return "Erreur inconnue : " + this.ligne;
			case 1:
				return "Erreur de format dans le fichier importé à la ligne " + numLigne + " : " + ligne 
					 + " (doit contenir 2 séparateurs '|')";
			case 2:
				return "La durée indiquée à la ligne "  + numLigne + " n'est pas un entier : "   + ligne;
			case 3:
				return "La nom de la tâche à la ligne " + numLigne + " est déjà utilisé : "      + ligne;
			case 4:
				return "Le prédécesseur à la ligne "    + numLigne + " n'existe pas : "          + ligne;
			case 5:
				return "La tâche à la ligne "           + numLigne + " n'a pas de nom : "        + ligne;
			case 6:
				return "Le ou les prédécesseurs de la tâche à la ligne " + numLigne + " n'ont pas de nom : " + ligne;
			case 7:
				return "Le fichier importé n'existe pas ou n'est pas accessible." ; 
			
			default:
				return "Erreur inconnue : " + this.ligne;
		}
	}
	
	public String getLigne()
	{
		return ligne;
	}



	public int getNumLigne()
	{
		return numLigne;
	}



	public int getCodeErreur()
	{
		return codeErreur;
	}

	public String toString() 
	{
		return "Ligne " + this.numLigne + " : " + getMessage() +  " --> " + this.ligne;
	}


	

	
}
