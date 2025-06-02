/**
 * Cette classe créer les tâches
 * 
 * Groupe : 09
 * 
 * Exercice 2
 * 
 * Participants : 
 * DELPECH Nicolas, 
 * FOYER Emilien, 
 * GUTU Nichita, 
 * KULPA Clément
 * 
 * Date de création : 02/06/2025 16h42
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Tache
{

    private String            nom;
    private int               duree;
    private DateFr dateMin;
    private DateFr dateMax;

    private ArrayList<Tache>  lstPrc;    
    private ArrayList<Tache>  lstSvt;

    public Tache(String nom, int duree)
    {

        this.nom   = nom;
        this.duree = duree;

        this.dateMin = new DateFr();
        this.dateMax = new DateFr();
        this.lstPrc  = new ArrayList<Tache>();
        this.lstSvt  = new ArrayList<Tache>();

    }

    public Tache( Tache t)
    {
        this.nom   = t.nom  ;
        this.duree = t.duree;
  
    }

    public String getNom()   { return this.nom  ;}
    public int    getDuree() { return this.duree;}

    public Tache  getTache(int indice, ArrayList<Tache> lst) { return lst.get(indice); }
    
    public DateFr getDateMin() { return this.dateMin;}
    public DateFr getDateMax() { return this.dateMax;}

    public ArrayList<Tache> getLstPrc() { return this.lstPrc;}
    public ArrayList<Tache> getLstSvt() { return this.lstSvt;}
    
    public void setDateMin(DateFr dateMin) { this.dateMin = dateMin;}
    
    public void setDateMin (int marge) 
    { 
        DateFr tmp = new DateFr(this.dateMin.get(DateFr.DAY_OF_MONTH + marge), 
                                                                   this.dateMin.get(DateFr.MONTH),
                                                                   this.dateMin.get(DateFr.YEAR));

        System.out.println(tmp.compareTo(this.dateMin));

        if(tmp.compareTo(this.dateMin) > 0)
        {
            this.dateMin = tmp;
        }
    
    }

                                                                   
    public void setDateMax(DateFr dateMax) { this.dateMax = dateMax;}

    
    public int getMarge() { return dateMax.get(DateFr.DAY_OF_YEAR) - dateMin.get(DateFr.DAY_OF_YEAR);       }

    public void addPrecedent(Tache t)
    {
        this.lstPrc.add(t);
        t   .lstSvt.add(this);
    }
    
    public void addSuivant(Tache t){ this.lstSvt.add(t); }

    
    public String toString()
    {
        
       //dateMin.set(2025, 06, 02);
       //dateMax.set(2025, 06, 24);

       
        String sRet = "";

        sRet += this.nom + " : ";
        
        sRet += this.duree + " jour";
        if (this.duree > 1) sRet += "s";


        sRet += "\n" + String.format ("%20s", "  date au plus tôt  : " )+  String.format("%02d",this.dateMin.get(DateFr.DAY_OF_MONTH) ) + "/" + 
                                                                                          String.format("%02d",this.dateMin.get (DateFr.MONTH) );

        sRet += "\n" + String.format ("%20s", "  date au plus tard : " )+  String.format("%02d",this.dateMax.get(DateFr.DAY_OF_MONTH) )+ "/" + 
                                                                                          String.format("%02d",this.dateMax.get(DateFr.MONTH));
        
        /*String.format("%02d",this.dateMax.get(DateFr.DAY_OF_MONTH) ) + "/" + 
        String.format("%02d",this.dateMax.get(DateFr.       MONTH) ); */
        sRet += "\n  marge" + String.format("%17s", " : " + this.getMarge() );


        if(this.lstPrc.size() != 0)
        {
            sRet += "\n" + String.format("%34s","liste des tâches précédentes : \n") + "     ";

            for (int cpt =0; cpt < this.lstPrc.size(); cpt++ )
                sRet +=  this.lstPrc.get(cpt).getNom() +(cpt<this.lstPrc.size()-1? ", ":"") ;
        }
        else 
            sRet += "\n" + String.format("%25s", "pas de tâche précédente");


        if(this.lstSvt.size() != 0)
        {
            sRet += "\n" + String.format("%32s","liste des tâches suivantes : \n") + "     ";

            for (int cpt =0; cpt < this.lstSvt.size(); cpt++ )
                sRet +=  this.lstSvt.get(cpt).getNom() +(cpt<this.lstSvt.size()-1? ", ":"") ;
        }
        else sRet += "\n" + String.format("%23s", "pas de tâche suivante");
        
        return sRet; 
        
    }
    
    /*
    public static void main(String[] args) 
    {
        Tache t1 = new Tache("Test",12);

        System.out.println(t1);
    }
    */
}