# Koala Chess Combat

This is a team project of game made on the Software Engineering course written in Java with libGDX. It is turn-based strategic game combining chess-like board moves and multiple enemy fight. The player needs to kill all the mobs spendning mana on moves of different attack power. Each turn all the enemies perform  moves based on their strategy.

## Appearance

Each character type has it's appearance and attack motive with simple animations. \
Enemies referes to software-based animals logos. Characters and tiles are 16x16 pixel textures.
![photo-collage png](https://github.com/user-attachments/assets/51189eb6-2184-4051-8e68-2ffea98cb0d4)
![koala_game_0](https://github.com/user-attachments/assets/40778365-61d5-4809-a077-878bc1e2af86)
![koala_game](https://github.com/user-attachments/assets/6f15db89-8bff-436f-8f6e-63f0de711648)


## Classes graph

Project structure is as follows. Arrows means keeping reference, inheriting or implementing an interface. Classes are grouped in components marked with blue frames:
- **core** keeps the current game state and analyze it.
- **services** are responible for controlling all the characters' actions and the database.
- **presenter** manages view, gets player's input and communicates with the game service.
- **view** classes render all textures.
- **database** keeps the leves and unfinished games snapshots.

Dependencies between them are minimized, so they were maximally independent. In particular Presenter and Core don't know anything about themselves and the Presenter-Service communication follows a single interface.

![Screenshot from 2024-09-27 19-07-03](https://github.com/user-attachments/assets/307524c7-4791-464b-b89f-3f83adc4455e)

## Building and running

After cloning the repository \
Build with `./gradlew build` \
Run with `./gradlew run`

---

We used libGDX library for easy rendering:

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3.
