package sprites;

import javafx.scene.image.Image;
import main.ImageManager;
import main.Tanks;

public class Bullet extends Character {
	private int bounces;
	public Bullet(double initx, double inity, double initdx, double initdy){
		super(initx, inity, initdx, initdy);
		height = Tanks.BULLETSIZE;
		width = Tanks.BULLETSIZE;
		bounces = 0;
	}
	@Override
	protected Image getImage() {
		if(state == 0){return ImageManager.getBullet();}
		else
			return ImageManager.getExplosion(explosionNum);
	}
	
	public void reverseDx(){
		super.reverseDx();
		bounces++;
	}
	public void reverseDy(){
		super.reverseDy();
		bounces++;
	}
	
	public void update(){
		super.update();
		if(state == 0){
			if(bounces > 1){
				this.explode();
			}
		}
	}

}
