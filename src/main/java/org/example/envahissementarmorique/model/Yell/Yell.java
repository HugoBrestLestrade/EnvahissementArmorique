package org.example.envahissementarmorique.model.Yell;

import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.YellType;

import java.time.Year;

public class Yell {
    public Lycanthropes emetteur;
    public  YellType type;
    public  String message;
    private boolean dejaRepete = false;

    public Yell(Lycanthropes e, YellType t, String m) {
        this.emetteur = e;
        this.type = t;
        this.message = m;
    }

    public void afficher() {
        System.out.printf("[%s] %s hurle: %s (%s)%n",
                emetteur.getIdentifiant(), emetteur.getRang(), type, message);
    }

    public boolean estDejaRepete() { return dejaRepete; }
    public void marquerRepete() { dejaRepete = true; }
}

