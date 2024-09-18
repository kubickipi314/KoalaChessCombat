package com.io.core.snapshot;

import com.io.core.character.CharacterEnum;

public record CharacterSnapshot(int x, int y,
                                CharacterEnum characterEnum,
                                int currentHealth, int currentMana,
                                int team) {
}
