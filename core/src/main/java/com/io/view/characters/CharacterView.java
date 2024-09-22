package com.io.view.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.assets_managers.TextureManager;

public abstract class CharacterView {
    protected final Sprite characterSprite;
    protected final Sprite attackSprite;
    protected final TextureManager tm;
    protected final float size;
    protected final CharacterViewType type;

    public CharacterView(TextureManager tm, Vector2 position, float size, CharacterViewType type) {
        this.tm = tm;
        this.size = size;
        this.type = type;

        characterSprite = new Sprite(tm.getCharacter(type, 0));
        characterSprite.setPosition(position.x, position.y);
        characterSprite.setSize(size, size);

        attackSprite = new Sprite(tm.getAttack(type, 0));
        attackSprite.setPosition(position.x, position.y);
        attackSprite.setSize(size, size);
    }

    public abstract void setPosition(Vector2 newPosition);

    public abstract void draw(SpriteBatch batch);

    public void setTexture(int stateNumber) {
        characterSprite.setTexture(tm.getCharacter(type, stateNumber));
    }

    public void setAttackTexture(int attackNumber) {
        attackSprite.setTexture(tm.getAttack(type, attackNumber));
    }

    public void drawAttack(SpriteBatch batch) {
        attackSprite.draw(batch);
    }

    public void setAttackPosition(Vector2 position) {
        attackSprite.setPosition(position.x, position.y);
    }
}
