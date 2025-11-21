package org.example.envahissementarmorique.model.character.interfaces;

import org.example.envahissementarmorique.model.character.base.Character;

public interface TypeCombat {
    void battre(Character ally); // même clan
    void combattre(Character enemy, String location);
}
