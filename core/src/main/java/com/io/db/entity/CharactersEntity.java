package com.io.db.entity;

import com.io.core.character.CharacterEnum;
import com.io.core.snapshot.CharacterSnapshot;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Characters")
public class CharactersEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private long snapshotId;

    @DatabaseField()
    private CharacterEnum characterEnum;

    @DatabaseField()
    private int positionX;

    @DatabaseField()
    private int positionY;

    @DatabaseField()
    private int currentHealth;

    @DatabaseField()
    private int currentMana;

    @DatabaseField()
    private int team;

    public CharactersEntity() {

    }

    public void init(long snapshotId, CharacterSnapshot characterSnapshot) {
        this.snapshotId = snapshotId;
        this.positionX = characterSnapshot.x();
        this.positionY = characterSnapshot.y();
        this.characterEnum = characterSnapshot.characterEnum();
        this.currentHealth = characterSnapshot.currentHealth();
        this.currentMana = characterSnapshot.currentMana();
        this.team = characterSnapshot.team();
    }

    public CharacterSnapshot export() {
        return new CharacterSnapshot(positionX, positionY, characterEnum, currentHealth, currentMana, team);
    }
}
