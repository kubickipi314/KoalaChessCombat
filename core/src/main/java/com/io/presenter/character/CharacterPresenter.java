package com.io.presenter.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.core.board.BoardPosition;
import com.io.presenter.CoordinatesManager;
import com.io.view.assets_managers.SoundManager;
import com.io.view.assets_managers.TextureManager;
import com.io.view.characters.CharacterView;

public abstract class CharacterPresenter implements CharacterInterface{
    protected final SoundManager sm;
    private final CoordinatesManager cm;
    protected CharacterView characterView;
    protected BoardPosition boardPosition;
    protected boolean isMoving;
    private boolean isAttacking;
    private float movementTime = 0;
    private float attackTime = 0;
    private int attackNumber = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;

    private Vector2 attackerPosition;
    private Vector2 attackPosition;

    public CharacterPresenter(TextureManager tm, SoundManager sm, CoordinatesManager cm, BoardPosition startBoardPosition) {
        this.sm = sm;
        this.cm = cm;

        isMoving = false;
        isAttacking = false;
        boardPosition = startBoardPosition;
    }

    public void update() {
        if (isMoving) updateMove();
        if (isAttacking) updateAttack();
    }

    public void startMove(BoardPosition position) {
        if (position == boardPosition) return;
        isMoving = true;
        movementTime = 0;

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
        }
    }

    public void startAttack(BoardPosition position){
        movementTime = 0;
        attackTime = 0;
        sm.playRoarSound();
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

    public void render(SpriteBatch batch) {
        characterView.draw(batch);
        if (isAttacking) characterView.drawAttack(batch);
    }

    public boolean isActive() {
        return isMoving || isAttacking;
    }

//    public void setHealth(int value) {
//        if (value < health)
//            sm.playRoarSound();
//        health = value;
//        characterView.changeHealth((float) health / maxHealth);
//    }
}
