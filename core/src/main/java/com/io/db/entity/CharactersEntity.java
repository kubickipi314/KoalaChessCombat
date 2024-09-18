package com.io.db.entity;

import com.io.core.snapshot.CharacterSnapshot;
import com.j256.ormlite.field.DatabaseField;

public class CharactersEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private long snapshotId;

    @DatabaseField()
    private int positionX;

    @DatabaseField()
    private int positionY;

    @DatabaseField()
    private String characterName;

    @DatabaseField()
    private int characterHealth;

    public CharactersEntity() {

    }

    public void init(long snapshotId, CharacterSnapshot characterSnapshot) {
        this.snapshotId = snapshotId;
        this.positionX = characterSnapshot.x();
        this.positionY = characterSnapshot.y();
        this.characterName = characterSnapshot.characterName();
        this.characterHealth = characterSnapshot.health();
    }
}
