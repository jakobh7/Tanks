package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.*;
import sprites.Character;

/*
 * Tanks game. Drive a tank around the map
 * shoot bad guys. Win the game.
 * 
 * Author: Jakob Horner
 * Version: March 1, 2016
 */
public class Tanks extends Application {
	final String appName = "Tanks";
	final int FPS = 15; // frames per second
	public final static int WIDTH = 1300;
	public final static int HEIGHT = 700;
	public final static int TANKSIZE = 40;
	public final static int BULLETSIZE = 10;
	public final static int CELLSIZE = 50;
		
	private static int gamestate;
	private static int numEnemies;
	private static int playerDir;
	private static Player playerChar;
	public static double getPlayerX(){return playerChar.getX();}
	public static double getPlayerY(){return playerChar.getY();}
	public static int getPlayerDir(){return playerDir;}
	private PlayField field;
		
	public static void main(String[] args) 
    {
        launch(args);
    }
	
	@Override
	public void start(Stage theStage){
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		setHandlers(theScene);
        
        Canvas canvas = new Canvas( WIDTH, HEIGHT );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        initialize();
        KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
    			new EventHandler<ActionEvent>() {
    				@Override
    				public void handle(ActionEvent event) {
    					// update position
    					update();
    					// draw frame
    					render(gc);
    				}
    		});
    	Timeline mainLoop = new Timeline(kf);
    	mainLoop.setCycleCount(Animation.INDEFINITE);
    	mainLoop.play();
        
        theStage.show();
	}
	
	private void initialize(){
		ImageManager.setImages();
		gamestate = 0;
	}
	
	private void setupLevel(int level){
		numEnemies = 0;
		field = new PlayField(WIDTH, HEIGHT, level);
		playerChar = new Player(50,50,0,0);
		addActor(playerChar);
		//TODO: Add level Manager
		addEnemies(level);
	}
	private void addEnemies(int level) {
		for(int i=0; i<level; i++){
			addActor(new Enemy(100+level*CELLSIZE, 300+level*CELLSIZE,0,0,0));
			numEnemies++;
		}
	}	
	public static void enemyKilled(){
		numEnemies--;
	}
	private void addActor(Character actor) {
		field.placeCharacter(actor);		
	}


	protected void render(GraphicsContext gc) {
		if(gamestate==0){
			gc.setFill(Color.PERU);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			gc.setFill(Color.DIMGRAY);
			gc.setStroke(Color.DIMGRAY);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFont(Font.font(32));
			gc.fillText("Welcome to Tanks! (Beta - only level 1 implemented)", WIDTH/2, 50);
			gc.strokeText("Welcome to Tanks! (Beta - only level 1 implemented)", WIDTH/2, 50);
			gc.fillText("Press space to start the game.", WIDTH/2, 600);
			gc.strokeText("Press space to start the game.", WIDTH/2, 600);
			
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText("Drive with WSAD keys or Arrow Keys", 200, 350);
			gc.fillText("Fire bullets with mouse clicks", 200, 400);
			gc.fillText("Kill all the Enemies to win", 200, 150);
		}
    	if(gamestate==1){
    		// Clear background
    		gc.setFill( Color.GRAY );
    		gc.fillRect( 0, 0, WIDTH, HEIGHT);	
        
    		field.renderEachCell(gc);
    	}
    	if(gamestate==2){
    		gc.setFill(Color.DARKGREEN);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			gc.setFill(Color.SILVER);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFont(Font.font(72));
			gc.fillText("You Win!", WIDTH/2, HEIGHT/2);
			
			gc.setFont(Font.font(24));
			gc.fillText("Press Space to play again", WIDTH/2, 600);
    	}
    	if(gamestate==3){
    		gc.setFill(Color.DARKRED);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFont(Font.font(72));
			gc.fillText("You Lose", WIDTH/2, HEIGHT/2);
			
			gc.setFont(Font.font(24));
			gc.fillText("Press Space to play again", WIDTH/2, 600);
    	}
	}


	protected void update() {
		if(gamestate==1){
			field.updateEachCell();
			if(numEnemies==0&&playerChar.isAlive()) gamestate = 2;
			if(playerChar.isDead())gamestate = 3;
		}
	}


	private void setHandlers(Scene scene) {
		//key input code borrowed and adapted from Mike Slattery
		scene.setOnKeyPressed(
					e->{
						if(gamestate == 0||gamestate==2||gamestate==3){
							switch (e.getCode()){
							case SPACE:
								gamestate=1;
								setupLevel(1);
								break;
							default:
								break;
							}
						}
						if(gamestate == 1){
							switch (e.getCode())
							{
								case A:
								case LEFT:
									playerDir=1; // A:Left
									break;
								case D:
								case RIGHT:
									playerDir=2; // D:Right
									break;
								case W:
								case UP:
									playerDir=3; // W:Up
									break;
								case S:
								case DOWN:
									playerDir=4; // S:Down
									break;
								default:
									break;
							}
						}
					}
				);
		scene.setOnKeyReleased(
					e->{
						if(gamestate == 1)	playerDir = 0;
					}
				);
		scene.setOnMouseClicked(
					e->{
						if(gamestate ==1)fireBullet(e.getX(), e.getY());
					}
				);		
	}
	
	public void fireBullet(double dirx, double diry){
		addActor(playerChar.shootBullet(dirx, diry));
	}

}
