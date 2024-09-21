package com.io.service;

import com.io.core.CharacterType;
import com.io.core.character.CharacterEnum;

import java.util.EnumMap;
import java.util.Map;

public class CharacterTypesMapper {
    private static final Map<CharacterEnum, CharacterType> map = new EnumMap<>(CharacterEnum.class);

    static {
        map.put(CharacterEnum.Player, CharacterType.PLAYER);
        map.put(CharacterEnum.MeleeEnemy, CharacterType.LINUX);
    }
    public static CharacterType getPresenterType(CharacterEnum type) {
        return map.get(type);
    }
}
