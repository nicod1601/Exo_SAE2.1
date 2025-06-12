package MPM.metier;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;


public class DateFr extends GregorianCalendar
{

    public DateFr() 
    {
        super(); 
    }
    
    public DateFr ( int jour, int mois, int annee )
    {
        super( annee, mois, jour,0,0,0);
    }
    public DateFr ( int jour, int mois, int annee, int heure, int minute )
    {
        super(annee, mois-1, jour, heure, minute,0);
    } 	
    public DateFr ( int jour, int mois, int annee, int heure, int minute, int seconde )
    {
        super(annee,mois-1,jour,heure,minute,seconde);
    }	    
    public DateFr ( String date ) 
    {
        super();
        int jour, mois, annee;
        jour  = Integer.parseInt(date.substring(0, 2));
        mois  = Integer.parseInt(date.substring(3, 5));
        annee = Integer.parseInt(date.substring(6));
        
        this.set(DAY_OF_MONTH, jour);
        this.set(MONTH, mois -1);
        this.set(YEAR, annee);
    }	
    public DateFr ( DateFr autreDate)
    {
        this(autreDate.get(DateFr.DAY_OF_WEEK), autreDate.get(DateFr.MONTH),autreDate.get(DateFr.YEAR));
        System.out.println(DateFr.DAY_OF_MONTH);
    } 

    public int get(int field)
    {
        int nb = -1;
        switch (field)
        {
            case YEAR, MINUTE, SECOND, DAY_OF_MONTH, DAY_OF_YEAR -> nb = super.get(field);
            case DAY_OF_WEEK -> {nb = super.get(field);
                                 if (nb < 0) nb = 7; }

            case MONTH -> nb = super.get(field) + 1;
            case HOUR  -> nb = super.get(field);
        }
        return nb;
    }

    public String toString()
    {
        return  String.format("%02d", this.get(DateFr.DAY_OF_WEEK))  + "/"      + 
                String.format("%02d", this.get(DateFr.MONTH))        + "/"      + 
                String.format("%02d", this.get(DateFr.YEAR))         + " "      + 
                String.format("%02d", this.get(DateFr.HOUR))         + ":"      + 
                String.format("%02d", this.get(DateFr.MINUTE))       + ":"      + 
                String.format("%02d", this.get(DateFr.SECOND))       + "DoY : " + 
                String.format("%02d", this.get(DateFr.DAY_OF_YEAR) );
    }
    
    public boolean estFerie() 
    {
        int jour = get(DAY_OF_MONTH);
        int mois = get(MONTH);

        // Jours fixes
        if ((jour == 1 && mois == 1)  ||  // Jour de l'an
            (jour == 1 && mois == 5)  ||  // Fête du travail
            (jour == 8 && mois == 5)  ||  // Victoire 1945
            (jour == 14 && mois == 7) ||  // Fête Nationale
            (jour == 11 && mois == 11) )  // Armistice 1918
            return true;

        return false;
    }


    public static void main(String[]args)
    {
        DateFr d1 = new DateFr();
        DateFr d2 = new DateFr(1,4,2025,3,45);
        DateFr d3 = new DateFr(25,12,2024, 23,59,59);
        DateFr d4 = new DateFr(d3);

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
    }

    
}
