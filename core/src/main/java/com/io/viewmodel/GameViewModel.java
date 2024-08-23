package com.io.viewmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.io.view.GameView;
import com.io.view.TextureManager;

public class GameViewModel {

    //private GameModel gameModel;

    private final SpriteBatch batch;
    private BoardTile[][] board;
    private ChessTile[] chessBoard;

    private static int ROWS = 5;
    private static int COLS = 5;
    private float boardSize;
    private float tileSize;


    private TextureManager tm;

    private OrthographicCamera camera;
    private Viewport viewport;

    private PlayerViewModel player;


//    private boolean isMoving = false;
//    private float elapsedTime = 0;
//    private float startPosX, startPosY;
//    private float targetPosX, targetPosY;

    public GameViewModel(GameView gameView) {

        batch = new SpriteBatch();

        tm = new TextureManager();
        tm.load();

        camera = new OrthographicCamera();
        viewport = new FitViewport(COLS, ROWS, camera); // Initial dimensions of the viewport

        board = new BoardTile[ROWS][COLS];

        chessBoard = new ChessTile[5];

        initializeTiles();
    }

    private void initializeTiles() {
        float windowWidth = viewport.getWorldWidth();
        float windowHeight = viewport.getWorldHeight();
        boardSize = Math.min(windowWidth, windowHeight) * 0.8f;
        tileSize = boardSize / Math.max(ROWS,COLS);

        float boardX = (windowWidth - boardSize) / 2;
        float boardY = (windowHeight - boardSize) / 2;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < ROWS; col++) {
                float x = boardX + col * tileSize;
                float y = boardY + row * tileSize;
                Vector2 position = new Vector2(x,y);
                board[row][col] = new BoardTile(tm, position, tileSize);
            }
        }

        float x = boardX + 2 * tileSize;
        float y = boardY + 0 * tileSize;
        Vector2 position = new Vector2(x,y);
        player = new PlayerViewModel(tm, position, tileSize);

        System.out.println("initialized");
    }

    public void update(){

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Vector2 mouseWorldCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mouseWorldCoords);

        float mouseX = mouseWorldCoords.x;
        float mouseY = mouseWorldCoords.y;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col].update(new Vector2(mouseX,mouseY));
            }
        }

    }

    public void render() {
        batch.begin();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col].render(batch);
            }
        }

        player.draw(batch);

        batch.end();
    }
}
