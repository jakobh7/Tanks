package sprites;

import javafx.scene.image.Image;
import main.ImageManager;
import main.Tanks;

public class Enemy extends Tank {
	private static final int RELOADTIME = 100;
	private int firingCycle;
	public boolean shouldFire(){
		return firingCycle == RELOADTIME;
	}
	public Enemy(double initx, double inity, double initdx, double initdy, int initImage){
		super(initx, inity, initdx, initdy, initImage);
		firingCycle = (int)(200*Math.random());
	}
	@Override
	protected Image getImage() {
		if(state==0)return ImageManager.getEnemy(imageNum);
		else return ImageManager.getExplosion(explosionNum);
	}

	@Override
	protected int getMovement() {
		return 0;
	}
	
	public void update(){
		faceTowardPlayer();
		if(firingCycle > RELOADTIME)firingCycle = 0;
		if(firingCycle <= RELOADTIME)firingCycle++;
		super.update();
	}
	private void faceTowardPlayer() {
		double xdelta = this.x - Tanks.getPlayerX();
		double ydelta = this.y - Tanks.getPlayerY();
		if(Math.abs(xdelta)>Math.abs(ydelta)&&xdelta<=0){
			imageNum=3;
		}
		if(Math.abs(xdelta)>Math.abs(ydelta)&&xdelta>=0){
			imageNum=1;
		}
		if(Math.abs(xdelta)<Math.abs(ydelta)&&ydelta<=0){
			imageNum=2;
		}
		if(Math.abs(xdelta)<Math.abs(ydelta)&&ydelta>=0){
			imageNum=0;
		}
	}
	public Bullet shootBullet() {
		return super.shootBullet(Tanks.getPlayerX(), Tanks.getPlayerY());
	}

}
