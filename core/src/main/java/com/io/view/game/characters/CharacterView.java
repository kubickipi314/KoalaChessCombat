package com.io.view.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.managers.game.TextureManager;

import java.util.List;

public abstract class CharacterView {
    protected final Sprite characterSprite;
    protected final Sprite attackSprite;
    protected final List<Texture> characterTextures;
    protected final List<Texture> attackTextures;
    protected final float size;

    public CharacterView(TextureManager tm, Vector2 position, float size, CharacterViewType type) {
        this.characterTextures = tm.getCharacter(type);
        this.attackTextures = tm.getAttack(type);
        this.size = size;

        characterSprite = new Sprite(characterTextures.get(0));
        characterSprite.setPosition(position.x, position.y);
        characterSprite.setSize(size, size);

        attackSprite = new Sprite(attackTextures.get(0));
        attackSprite.setPosition(position.x, position.y);
        attackSprite.setSize(size, size);
    }

    public abstract void setPosition(Vector2 newPosition);

    public abstract void draw(SpriteBatch batch);

    public void setTexture(int stateNumber) {
        characterSprite.setTexture(characterTextures.get(stateNumber));
    }

    public void setAttackTexture(int attackNumber) {
        attackSprite.setTexture(attackTextures.get(attackNumber));
    }

    public void drawAttack(SpriteBatch batch) {
        attackSprite.draw(batch);
    }

    public void setAttackPosition(Vector2 position) {
        attackSprite.setPosition(position.x, position.y);
    }
}
