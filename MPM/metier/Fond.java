package MPM.metier;

import java.awt.Color;


public class Fond 
{
	private Couleur[][] tabCouleur;
	private Couleur[]   tabColorSimple;

	public Fond()
	{
		this.tabCouleur = new Couleur[][] 
		{
			{ Couleur.ROUGE          , Couleur.VERT           , Couleur.BLEU         , Couleur.JAUNE         },
			{ Couleur.MANGENTA       , Couleur.BLEU_CLAIR     , Couleur.BORDEAUX     , Couleur.VERT_FONCE    },
			{ Couleur.BLEU_MARINE    , Couleur.CORAIL_CLAIR   , Couleur.VIOLET       , Couleur.BLEU_SARCELLE },
			{ Couleur.ROUGE_FONCE    , Couleur.VERT_FEUILLE   , Couleur.CYAN         , Couleur.ORANGE        },
			{ Couleur.ROSE_FONCE     , Couleur.VERT_CITRON    , Couleur.VERT_SARCELLE, Couleur.GRIS          },
			{ Couleur.GRIS_CLAIR     , Couleur.BLEU_OCEAN     , Couleur.VIOLET_ROYAL , Couleur.OCRE          },
			{ Couleur.JAUNE_PALE     , Couleur.JAUNE_OLIVE    , Couleur.VERT_MENTHE  , Couleur.MAGENTA_CLAIR },
			{ Couleur.TURQUOISE_CLAIR, Couleur.TURQUOISE_FONCE, Couleur.MAUVE        , Couleur.ROSE_SAUMON   }

		};

		this.tabColorSimple = new Couleur[] 
		{
			Couleur.ROUGE          , Couleur.VERT           , Couleur.BLEU         , Couleur.JAUNE         ,
			Couleur.MANGENTA       , Couleur.BLEU_CLAIR     , Couleur.BORDEAUX     , Couleur.VERT_FONCE    ,
			Couleur.BLEU_MARINE    , Couleur.CORAIL_CLAIR   , Couleur.VIOLET       , Couleur.BLEU_SARCELLE ,
			Couleur.ROUGE_FONCE    , Couleur.VERT_FEUILLE   , Couleur.CYAN         , Couleur.ORANGE        ,
			Couleur.ROSE_FONCE     , Couleur.VERT_CITRON    , Couleur.VERT_SARCELLE, Couleur.GRIS          ,
			Couleur.GRIS_CLAIR     , Couleur.BLEU_OCEAN     , Couleur.VIOLET_ROYAL , Couleur.OCRE          ,
			Couleur.JAUNE_PALE     , Couleur.JAUNE_OLIVE    , Couleur.VERT_MENTHE  , Couleur.MAGENTA_CLAIR ,
			Couleur.TURQUOISE_CLAIR, Couleur.TURQUOISE_FONCE, Couleur.MAUVE        , Couleur.ROSE_SAUMON   

		};
	}

	public int     getTailleSimple()                       { return this.tabColorSimple.length;     }
	public int     getLigne       ()                       { return this.tabCouleur.length;         }
	public int     getColonne     ()                       { return this.tabCouleur[0].length;      }
	public Couleur getCouleur     (int ligne, int colonne) { return this.tabCouleur[ligne][colonne];}

	public Couleur getCouleur(int indice)
	{
		for(int cpt=0; cpt<this.tabCouleur.length; cpt++)
			for(int cpt2=0; cpt2<this.tabCouleur[cpt].length; cpt2++)

				if(this.tabCouleur[cpt][cpt2].equals(indice))
					return this.tabCouleur[cpt][cpt2];
			
		return null;
	}

	public Couleur getTabCouleur(int ligne, int colonne) { return this.tabCouleur[ligne][colonne]; }

	public Couleur getTabCouleur(Color couleur) 
	{
		for(int cpt=0; cpt<this.tabCouleur.length; cpt++)
			for(int cpt2=0; cpt2<this.tabCouleur[cpt].length; cpt2++)
			
				if(this.tabCouleur[cpt][cpt2].equals(couleur))
					return this.tabCouleur[cpt][cpt2];
		
		return null;
	}

	public Couleur getCouleurSimple(int indice) { return this.tabColorSimple[indice]; }
}