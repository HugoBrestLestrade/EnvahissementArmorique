package org.example.envahissementarmorique.model.character.interfaces;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

public interface TypeCombat {
    void battre(GameCharacter ally); // même clan
    void combattre(GameCharacter enemy, String location);
}
