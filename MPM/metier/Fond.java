package MPM.metier;

import java.awt.Color;

public class Fond 
{
    private Color[][] tabCouleur;
    private Color[]   tabColorSimple;

    public Fond()
    {
        this.tabCouleur = new Color[][] {
            { new Color(255, 0, 0, 255)    , new Color(0, 255, 0, 255)  , new Color(0, 0, 255, 255)    , new Color(255, 255, 0, 255)  }, 
            { new Color(255, 0, 255, 255)  , new Color(0, 255, 255, 255), new Color(128, 0, 0, 255)    , new Color(0, 128, 0, 255)    },
            { new Color(0, 0, 128, 255)    , new Color(128, 128, 0, 255), new Color(128, 0, 128, 255)  , new Color(0, 128, 128, 255)  }, 
            { new Color(192, 0, 0, 255)    , new Color(0, 192, 0, 255)  , new Color(0, 0, 192, 255)    , new Color(255, 128, 0, 255)  },
            { new Color(255, 0, 128, 255)  , new Color(128, 255, 0, 255), new Color(0, 255, 128, 255)  , new Color(128, 128, 128, 255)},
            { new Color(192, 192, 192, 255), new Color(0, 128, 192, 255), new Color(128, 0, 192, 255)  , new Color(192, 128, 0, 255)  },
            { new Color(255, 255, 128, 255), new Color(0, 192, 128, 255), new Color(128, 192, 128, 255), new Color(255, 128, 255, 255)}, 
            {new Color(128, 255, 255, 255) , new Color(192, 192, 128, 255), new Color(192, 128, 192, 255), new Color(255, 128, 128, 255)} 
        };

        this.tabColorSimple = new Color[] {
             new Color(255, 0, 0, 255)    , new Color(0, 255, 0, 255)  , new Color(0, 0, 255, 255)    , new Color(255, 255, 0, 255),
             new Color(255, 0, 255, 255)  , new Color(0, 255, 255, 255), new Color(128, 0, 0, 255)    , new Color(0, 128, 0, 255)    ,
             new Color(0, 0, 128, 255)    , new Color(128, 128, 0, 255), new Color(128, 0, 128, 255)  , new Color(0, 128, 128, 255)  ,
             new Color(192, 0, 0, 255)    , new Color(0, 192, 0, 255)  , new Color(0, 0, 192, 255)    , new Color(255, 128, 0, 255)  ,
             new Color(255, 0, 128, 255)  , new Color(128, 255, 0, 255), new Color(0, 255, 128, 255)  , new Color(128, 128, 128, 255),
             new Color(192, 192, 192, 255), new Color(0, 128, 192, 255), new Color(128, 0, 192, 255)  , new Color(192, 128, 0, 255)  ,
             new Color(255, 255, 128, 255), new Color(0, 192, 128, 255), new Color(128, 192, 128, 255), new Color(255, 128, 255, 255),
             new Color(128, 255, 255, 255) , new Color(192, 192, 128, 255), new Color(192, 128, 192, 255), new Color(255, 128, 128, 255)
            };
    }

    public int getTailleSimple()
    {
        return this.tabColorSimple.length;
    }

    public int getLigne()
    {
        return this.tabCouleur.length;
    }

    public int getColonne()
    {
        return this.tabCouleur[0].length;
    }

    public Color getCouleur(int ligne, int colonne)
    {
        return this.tabCouleur[ligne][colonne];
    }

    public Color getCouleur(int indice)
    {
        for(int cpt=0; cpt<this.tabCouleur.length; cpt++)
        {
            for(int cpt2=0; cpt2<this.tabCouleur[cpt].length; cpt2++)
            {
                if(this.tabCouleur[cpt][cpt2].equals(indice))
                    return this.tabCouleur[cpt][cpt2];
            }
        }
        return null;
    }

    public Color getTabCouleur(int ligne, int colonne)
    {
        return this.tabCouleur[ligne][colonne];
    }

    public Color getTabCouleur(Color couleur) 
    {
        for(int cpt=0; cpt<this.tabCouleur.length; cpt++)
        {
            for(int cpt2=0; cpt2<this.tabCouleur[cpt].length; cpt2++)
            {
                if(this.tabCouleur[cpt][cpt2].equals(couleur))
                    return this.tabCouleur[cpt][cpt2];
            }
        }
        return null;
    }

    public Color getCouleurSimple(int indice)
    {
        return this.tabColorSimple[indice];
    }
}