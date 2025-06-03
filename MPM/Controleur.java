package MPM;

import MPM.ihm.*;
import MPM.metier.*;

public class Controleur
{
    private FrameMPM frame;
    private Projet projet;

    public Controleur()
    {
        this.frame = new FrameMPM();
        this.projet = new Projet();
    }

    public static void main(String[] args)
    {
        new Controleur();
    }
}