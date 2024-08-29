package com.io.viewmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.io.view.*;


public class GameViewModel {

    private int actualMana = 5;
    private final int MAX_MANA = 12;
    private int actualHealth = 8;
    private final int MAX_HEALTH = 10;


    private final SpriteBatch batch;
    private final BoardTileView[][] board;
    private final ChessTileView[] chessBoard;
    private PlayerView player;

    private HealthBarView healthBar;
    private ManaBarView manaBar;
    private TourButton tourButton;

    private final int ROWS = 5;
    private final int COLS = 5;
    private final int NUMBER_OF_CHESS = 5;
    private float WINDOW_WIDTH;
    private float WINDOW_HEIGHT;
    private float BOARD_SIZE;
    private float BOARD_X;
    private float BOARD_Y;
    private float CHESS_BOARD_X;
    private float CHESS_BOARD_Y;
    private float TILE_SIZE;

    private final TextureManager tm;
    private final SoundManager sm;


    private int playerX;
    private int playerY;
    private boolean isMoving = false;
    private float elapsedTime = 0;
    private Vector2 startPosition;
    private Vector2 targetPosition;


    public GameViewModel() {

        batch = new SpriteBatch();

        tm = new TextureManager();
        sm = new SoundManager();


        board = new BoardTileView[ROWS][COLS];
        chessBoard = new ChessTileView[5];

        playerX = 2;
        playerY = 0;

        initializeBoardTiles();
        initializeChessTiles();
        initializeTopElements();
    }

    private void initializeBoardTiles() {

        WINDOW_WIDTH = Gdx.graphics.getWidth();
        WINDOW_HEIGHT = Gdx.graphics.getHeight();
        BOARD_SIZE = Math.min(WINDOW_WIDTH, WINDOW_HEIGHT) * 0.5f;
        TILE_SIZE = BOARD_SIZE / Math.max(ROWS, COLS);

        BOARD_X = (WINDOW_WIDTH - BOARD_SIZE) / 2;
        BOARD_Y = (WINDOW_HEIGHT - BOARD_SIZE) / 2;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < ROWS; col++) {
                float x = BOARD_X + col * TILE_SIZE;
                float y = BOARD_Y + row * TILE_SIZE;
                Vector2 position = new Vector2(x, y);
                board[row][col] = new BoardTileView(tm, position, TILE_SIZE);
            }
        }

        float x = BOARD_X + playerX * TILE_SIZE;
        float y = BOARD_Y + playerY * TILE_SIZE;
        Vector2 position = new Vector2(x, y);
        player = new PlayerView(tm, position, TILE_SIZE);

    }

    private void initializeChessTiles() {

        CHESS_BOARD_X = BOARD_X;
        CHESS_BOARD_Y = BOARD_Y - 2 * TILE_SIZE;

        Texture[] chessPieces = tm.getChessArray();
        Texture[] selectedPieces = tm.getSelectedPieces();

        for (int number = 0; number < NUMBER_OF_CHESS; number++) {
            float x = CHESS_BOARD_X + number * TILE_SIZE;
            Vector2 position = new Vector2(x, CHESS_BOARD_Y);
            chessBoard[number] = new ChessTileView(tm.getChessTile(), chessPieces[number], selectedPieces[number], position, TILE_SIZE);
        }
    }

    public void initializeTopElements() {
        float heartBarY = BOARD_Y + (ROWS + 0.9f) * TILE_SIZE;
        Vector2 heartPosition = new Vector2(BOARD_X, heartBarY);
        float barHeight = TILE_SIZE * 9 / 16;
        healthBar = new HealthBarView(tm, heartPosition, barHeight);

        healthBar.setHealth(actualHealth);

        float manaBarY = BOARD_Y + (ROWS + 0.2f) * TILE_SIZE;
        Vector2 manaPosition = new Vector2(BOARD_X, manaBarY);
        manaBar = new ManaBarView(tm, manaPosition, barHeight);

        manaBar.setMana(actualMana);

        float tourButtonX = BOARD_X + (COLS - 1) * TILE_SIZE;
        Vector2 tourButtonPosition = new Vector2(tourButtonX, manaBarY);
        tourButton = new TourButton(tm, tourButtonPosition, TILE_SIZE);
    }

    public void update() {

        if (isMoving) {
            updatePlayerPosition();
        } else {

            Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());

            float mouseX = mouseWorldCoords.x / 2;
            float mouseY = (WINDOW_HEIGHT - mouseWorldCoords.y / 2);
            Vector2 mousePosition = new Vector2(mouseX, mouseY);

            if (isMouseInBoard(mouseX, mouseY)) {
                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLS; col++) {
                        boolean markedState = board[row][col].contains(mousePosition);
                        board[row][col].setMarked(markedState);

                        if (markedState) {
                            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                                //board[row][col].setAvailable(true);
                                board[row][col].switchAvailable();
                            }

                            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                                startMoveAnimation(col, row);
                            }
                        }
                    }
                }
            } else if (isMouseInChessBoard(mouseX, mouseY)) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    for (int i = 0; i < NUMBER_OF_CHESS; i++) {
                        if (chessBoard[i].contains(mouseX, mouseY)) {
                            for (int j = 0; j < NUMBER_OF_CHESS; j++) {
                                chessBoard[j].unselect();
                            }
                            chessBoard[i].select();
                            sm.playSelectSound();
                            break;
                        }
                    }
                }
            } else if (tourButton.contains(mouseX, mouseY)) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    actualMana = Math.min(actualMana + 2, MAX_MANA);
                    manaBar.setMana(actualMana);

                    actualHealth = Math.max(actualHealth - 1, 0);
                    healthBar.setHealth(actualHealth);

                    sm.playSwordSound();
                }
            }
        }
    }

    public void render() {
        batch.begin();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col].draw(batch);
            }
        }

        player.draw(batch);

        for (int number = 0; number < NUMBER_OF_CHESS; number++) {
            chessBoard[number].draw(batch);

        }

        healthBar.draw(batch);
        manaBar.draw(batch);
        tourButton.draw(batch);

        batch.end();
    }

    private boolean isMouseInBoard(float mouseX, float mouseY) {
        return mouseX >= BOARD_X && mouseY >= BOARD_Y && mouseX <= BOARD_X + BOARD_SIZE && mouseY <= BOARD_Y + BOARD_SIZE;

    }

    private boolean isMouseInChessBoard(float mouseX, float mouseY) {
        return mouseX >= CHESS_BOARD_X && mouseY >= CHESS_BOARD_Y && mouseX <= CHESS_BOARD_X + BOARD_SIZE && mouseY <= CHESS_BOARD_Y + 2 * TILE_SIZE;
    }

    private void startMoveAnimation(int targetCol, int targetRow) {
        isMoving = true;
        elapsedTime = 0;

        sm.playMoveSound();

        float startX = BOARD_X + playerX * TILE_SIZE;
        float startY = BOARD_Y + playerY * TILE_SIZE;

        float targetX = BOARD_X + targetCol * TILE_SIZE;
        float targetY = BOARD_Y + targetRow * TILE_SIZE;

        startPosition = new Vector2(startX, startY);
        targetPosition = new Vector2(targetX, targetY);

        playerX = targetCol;
        playerY = targetRow;
    }

    private void updatePlayerPosition() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        float animationDuration = 0.5f;
        float progress = Math.min(1.0f, elapsedTime / animationDuration);

        float currentX = startPosition.x + (targetPosition.x - startPosition.x) * progress;
        float currentY = startPosition.y + (targetPosition.y - startPosition.y) * progress;

        Vector2 currentPosition = new Vector2(currentX, currentY);
        player.changePosition(currentPosition);

        if (progress >= 1.0f) {
            isMoving = false;
        }
    }

}
