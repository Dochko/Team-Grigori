# Zombie - Land
Top-down zombie shooter game for SoftUni team project by Team "Grigori"

Zombie-Land is a 2D shooter game. In this game is used class JFrame which is an extended version of java.awt.Frame that adds support for 
the JFC/Swing component architecture. It’s used also Java 2D API for drawing a 2D graphics objects. 

Brief description of the game: The game represents one player who must fight against zombies in three difficulty levels. The game has a 
few packages: Engine, GameState, Models and Utilities. 

Engine package contain the classes: GameScreen – it contains JFrame and GamePanel constructors, the initialization 
of  the game, sound effects and checking high scores of the players, creating and drawing the objects, like player, enemies and bullets,
methods that update every object in it,  checking for collisions. 
S
econd class  is a  StartScreen. In it are buttons for start, help, high score and exit.In GameStatePackage stay GameStateManager class, 
one for the background and classes for the levels type. 

Models Package has classes Enemy, Player, Projectiles. 

Enemy Class contains types of enemies – normal zombie, zombie dog, advanced zombie and zombie boss, who comes from different sides in
different waves. 

In Player Class is represents the movements of the player, drawing and sound effects. 

In package Utilities are the classes Animator, BackgroundImageComponent, FrameRateCounter, HighScoreTemplate, ImageHandler and Sound.
