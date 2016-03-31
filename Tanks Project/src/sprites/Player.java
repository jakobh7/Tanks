package sprites;

import javafx.scene.image.Image;
import main.ImageManager;
import main.Tanks;

public class Player extends Tank {
	public Player(double initx, double inity, double initdx, double initdy) {
		super(initx, inity, initdx, initdy, 0);
	}

	@Override
	public Image getImage() {
		if(state==0)return ImageManager.getCharacter(imageNum);
		else return ImageManager.getExplosion(explosionNum);
	}
	@Override
	public int getMovement(){
		return Tanks.getPlayerDir();
	}
}
