package MPM.metier;

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
