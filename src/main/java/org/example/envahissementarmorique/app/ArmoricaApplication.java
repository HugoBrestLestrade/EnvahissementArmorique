package org.example.envahissementarmorique.app;

import org.example.envahissementarmorique.model.character.base.General;

public class ArmoricaApplication {
    public static void main(String[] args) {
        General ma = new General("Mohamed-Amine", "Homme", "Romain", 180, 20, 15, 20, 110, 100, 0, 0);
        General willem = new General("willem", "Homme", "Gaulois", 181, 19, 17, 17, 100, 100, 0, 0);
        willem.battre(ma);
    }
}
