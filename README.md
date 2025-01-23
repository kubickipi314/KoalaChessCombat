# Koala Chess Combat

This is a team project of game made on the Software Ingineering course written in Java with libGDX. It is turn-based strategic game combining chess-like board moves and multiple enemy fight. The player needs to kill all the mobs spendning mana on moves of different attack power. Each turn all the enemies perform  moves based on their strategy.

## Appearance

Each character type has it's appearance and attack motive with simple animations. \
Enemies referes to software-based animals logos.
![photo-collage png](https://github.com/user-attachments/assets/51189eb6-2184-4051-8e68-2ffea98cb0d4)

## Classes graph

Project structure is as follows. Arrows means keeping reference, inheriting or implementing an interface.

![Screenshot from 2024-09-27 19-07-03](https://github.com/user-attachments/assets/307524c7-4791-464b-b89f-3f83adc4455e)

![Kazam_00022 (online-video-cutter com)](https://github.com/user-attachments/assets/3453a5b5-560c-4a38-945b-0947f7bd61b6)


## Building and running

After cloning the repository \
Build with `./gradlew build` \
Run with `./gradlew run`

---

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).


This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3.
