# Dalek’s Game

## Table of Contents
  - [Description](#description)
  - [Technologies](#technologies)
  - [Implemented design patterns](#implemented-design-patterns)
  - [Technologies and design patterns usage examples](#technologies-and-design-patterns-usage-examples)
    - [BehaviorSubject (RXJava)](#behaviorsubject-rxjava)
    - [Visitor](#visitor)
    - [Command](#command)
    - [IoC (Spring)](#ioc-spring)
  - [How to run](#how-to-run)
  - [How to play](#how-to-play)
    - [Game mode](#game-mode)
    - [Doctor movement](#doctor-movement)
    - [Power-ups](#power-ups)

## Description
Dalek’s Game is a Java project for Object-Oriented Technologies classes. The goal of the game is to survive and eliminate Daleks (who are the enemies) from the board. Each time the main character (Doctor) moves, every Dalek follows him. When a Dalek bumps into another Dalek or junk, it becomes junk. When there is no Dalek left on the map, Doctor wins.

## Technologies
* Java 17
* RXJava
* JavaFX
* Spring 
* SQLite
* JUnit

## Implemented design patterns
* Visitor 
* Command

## Technologies and design patterns usage examples
### BehaviorSubject (RXJava)
In our project we use BehaviorSubjects to automate some of the game processes. 

In Engine class subscribe to the method onTeleportDeath.
```Java
this.subjectService.getDeadTeleportSubject().subscribe(this::onTeleportDeath);
```
Every time power-up is picked up, we call our Subject’s onNext and onTeleportDeath method is automatically called.
```Java
@Override
public void onPowerUpPickUp(){
   deadTeleportSubject.onNext(this);
}
```

```Java
private void onTeleportDeath(Teleport teleport){
   onPowerUpDeath(teleport);
   this.board.getDoctor().addTeleport(teleport);
}
```

### Visitor
Visitor pattern is used to solve entities collision problem. 
### Command
PowerUp interface and PowerUpHistory implements Command pattern, which allows us to track the history of used power-ups.

### IoC (Spring)

```Java
@Autowired
public GameInitializer(AppController appController,
                      Engine engine,
                      DoctorMoveController doctorMoveController) {
   this.appController = appController;
   this.engine = engine;
   this.doctorMoveController = doctorMoveController;
}
```

## How to run
Clone the repository from github.
```
$ git clone https://github.com/ajablonsk1/daleks-game.git
```
Open project with IDE and run GameApplication class.

## How to play
### Game mode
At the beginning of the game you can choose game mode:
* Quick game 
* Campaign 

### Doctor movement 
Doctor can move in every direction by one step. You can control him by yourself using keyboards controls: 
* Basic move directions (W,A,S,D)
* Diagonal movement (Q,E,Z,C)
Alternatively Numpad can be used for player control.

### Power-ups
When you collect a power-up, you can use it any time, by clicking:
* T - to use Teleport
* R - to use Time Reverse


