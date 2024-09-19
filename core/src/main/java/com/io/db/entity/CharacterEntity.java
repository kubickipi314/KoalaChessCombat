package com.io.db.entity;

import com.io.core.board.BoardPosition;
import com.io.core.character.CharacterEnum;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Characters")
public class CharacterEntity {

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

    public CharacterEntity() {
    }

    public CharacterEntity(CharacterEnum characterEnum, int positionX, int positionY, int currentHealth, int currentMana, int team) {
        this.characterEnum = characterEnum;
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentHealth = currentHealth;
        this.currentMana = currentMana;
        this.team = team;
    }

    public void setSnapshotId(long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public CharacterEnum getCharacterEnum() {
        return characterEnum;
    }

    public BoardPosition getPosition() {
        return new BoardPosition(positionX, positionY);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getTeam() {
        return team;
    }
}
