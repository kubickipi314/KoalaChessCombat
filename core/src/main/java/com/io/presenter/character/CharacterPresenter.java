package com.io.presenter.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.characters.CharacterViewType;
import com.io.view.characters.CharacterView;

import java.util.Random;

public abstract class CharacterPresenter implements CharacterPresenterInterface {
    protected final SoundManager sm;
    protected final CoordinatesManager cm;
    protected CharacterView characterView;
    protected BoardPosition boardPosition;
    protected boolean isMoving;
    protected boolean isAttacking;
    protected float movementTime = 0;
    protected float attackTime = 0;
    protected int attackNumber = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;

    protected Vector2 attackerPosition;
    protected Vector2 attackPosition;

    protected int state = 0;
    protected float stateTime = 0;
    protected float stateInterval;

    protected CharacterViewType characterType;

    public CharacterPresenter(SoundManager sm, CoordinatesManager cm, BoardPosition boardPosition, CharacterViewType characterType) {
        this.sm = sm;
        this.cm = cm;

        isMoving = false;
        isAttacking = false;
        this.boardPosition = boardPosition;
        this.characterType = characterType;

        stateInterval = randomFloat(6);
    }

    public void update() {
        updateState();
        if (isMoving) updateMove();
        if (isAttacking) updateAttack();
    }

    public void startMove(BoardPosition position) {
        if (position == boardPosition) return;
        isMoving = true;
        movementTime = 0;

        characterView.setTexture(2);
        sm.playMoveSound();

        startPosition = cm.calculatePosition(boardPosition);
        targetPosition = cm.calculatePosition(position);
        boardPosition = position;
    }

    public void updateMove() {
        movementTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, movementTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;
        Vector2 currentPosition = new Vector2(currentX, currentY);
        characterView.setPosition(currentPosition);

        if (progress >= 1.0f) {
            isMoving = false;
            characterView.setTexture(state);
        }
    }

    public void startAttack(BoardPosition position) {
        movementTime = 0;
        attackTime = 0;
        sm.playAttackSound(characterType);
        isAttacking = true;
        attackPosition = cm.calculatePosition(position);
        attackerPosition = cm.calculatePosition(boardPosition);
    }

    public void updateAttack() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        movementTime += deltaTime;
        attackTime += deltaTime;
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, movementTime / animationDuration);

        if (attackTime >= 0.2f) {
            attackTime = 0;
            attackNumber = (attackNumber + 1) % 2;
            characterView.setAttackTexture(attackNumber);
        }

        float currentX = attackerPosition.x + (attackPosition.x - attackerPosition.x) * progress;
        float currentY = attackerPosition.y + (attackPosition.y - attackerPosition.y) * progress;
        Vector2 currentPosition = new Vector2(currentX, currentY);
        characterView.setAttackPosition(currentPosition);

        if (progress >= 1.0f) {
            isAttacking = false;
        }
    }

    public void updateState() {
        stateTime += Gdx.graphics.getDeltaTime();
        if (state == 0) {
            if (stateTime >= stateInterval) {
                state = 1;
                characterView.setTexture(state);
                stateTime = 0f;
                stateInterval = randomFloat(5);
                if (characterType == CharacterViewType.FIREFOX) stateInterval = 0.5f;
            }
        } else {
            if (stateTime >= stateInterval) {
                state = 0;
                characterView.setTexture(state);
                stateTime = 0f;
            }
        }
    }

    protected float randomFloat(int max) {
        Random rand = new Random();
        return rand.nextFloat() * (float) max;
    }

    public void render(SpriteBatch batch) {
        characterView.draw(batch);
        if (isAttacking) characterView.drawAttack(batch);
    }

    public boolean isActive() {
        return isMoving || isAttacking;
    }

}
