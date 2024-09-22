package com.io.service.service_utils;

import com.io.view.characters.CharacterViewType;
import com.io.core.character.CharacterEnum;

import java.util.EnumMap;
import java.util.Map;

public abstract class CharacterTypesMapper {
    private static final Map<CharacterEnum, CharacterViewType> map = new EnumMap<>(CharacterEnum.class);

    static {
        map.put(CharacterEnum.Player, CharacterViewType.FIREFOX);
        map.put(CharacterEnum.MeleeEnemy, CharacterViewType.LINUX);
    }

    public static CharacterViewType getPresenterType(CharacterEnum type) {
        return map.get(type);
    }
}
