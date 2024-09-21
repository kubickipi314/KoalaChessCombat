package com.io.db.entity;

import com.io.core.board.BoardPosition;
import com.io.core.character.CharacterEnum;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Character")
public class CharacterEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long snapshotId;

    @DatabaseField
    private int positionX;

    @DatabaseField
    private int positionY;

    @DatabaseField
    private CharacterEnum characterEnum;

    @DatabaseField
    private Integer currentHealth;

    @DatabaseField
    private Integer currentMana;

    @DatabaseField
    private int team;

    private CharacterEntity() {
    }

    public CharacterEntity(int positionX, int positionY, CharacterEnum characterEnum, int team) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.characterEnum = characterEnum;
        this.team = team;
    }

    public CharacterEntity(int positionX, int positionY, CharacterEnum characterEnum, int team, int currentHealth, int currentMana) {
        this(positionX, positionY, characterEnum, team);
        this.currentHealth = currentHealth;
        this.currentMana = currentMana;
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

    public int getTeam() {
        return team;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public Integer getCurrentMana() {
        return currentMana;
    }
}
