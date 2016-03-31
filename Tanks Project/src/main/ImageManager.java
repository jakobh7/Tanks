package main;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ImageManager {
	private static Image character[] = new Image[4];
	private static Image enemy[] = new Image[4];
	private static Image bullet;
	private static Image explosion[] = new Image[30];
	
	public static Image getCharacter(int state){
		return character[state];
	}
	public static Image getEnemy(int state){
		return enemy[state];
	}
	public static Image getExplosion(int state){
		return explosion[state];
	}
	public static Image getBullet(){
		return bullet;
	}
	
	public static void setImages(){
		Image full = new Image("TankSprite.jpg");
		int buffer = 11;
		int interimBuffer = 32;
		for(int col = 0; col < 4; col++){
			character[col] = (Image) new WritableImage(full.getPixelReader(),buffer+(Tanks.TANKSIZE+interimBuffer)*col, buffer+2, Tanks.TANKSIZE, Tanks.TANKSIZE);
		}
		
		full = new Image("EnemyTankSprite.jpg");
		for(int col = 0; col < 4; col++){
			enemy[col] = (Image) new WritableImage(full.getPixelReader(),buffer+(Tanks.TANKSIZE+interimBuffer)*col, buffer+2, Tanks.TANKSIZE, Tanks.TANKSIZE);
		}
		
		bullet = new Image("shot.png");
		
		full = new Image("explosions.png");
		int count = 0;
		for(int row = 1; row < 5; row++){
			for(int col = 0; col < 6; col++){
				explosion[count] = (Image) new WritableImage(full.getPixelReader(), Tanks.CELLSIZE*col, Tanks.CELLSIZE*row, Tanks.CELLSIZE, Tanks.CELLSIZE);
				count++;
			}
		}
	}
}
