package sprites;

import javafx.scene.image.Image;
import main.Tanks;

public abstract class Tank extends Character {
	protected int imageNum;
	public Tank(double initx, double inity, double initdx, double initdy, int initImage){
		super(initx, inity, initdx, initdy);
		imageNum = initImage;
		height = Tanks.TANKSIZE;
		width = Tanks.TANKSIZE;
	}
	protected abstract Image getImage();
	
	public void update(){
		dx = 0;
		dy = 0;
		switch(getMovement()){
			case 1:
				imageNum = 1;
				dx = -5;
				break;
			case 2:
				imageNum = 3;
				dx = 5;
				break;
			case 3:
				imageNum = 0;
				dy = -5;
				break;
			case 4:
				imageNum = 2;
				dy = 5;
				break;
			default:
				break;
		}
		super.update();
	}
	protected abstract int getMovement();
	
	public Bullet shootBullet(double dirx, double diry) {
		double bulxdir = getCannonX()-dirx;
		double bulydir = getCannonY()-diry;
		double angle = Math.atan2(bulydir, bulxdir);
		double bulxvel = 4 * -Math.cos(angle);
		double bulyvel = 4 * -Math.sin(angle);
		return new Bullet(getCannonX(),getCannonY(),bulxvel,bulyvel);
	}
	
	protected double getCannonX(){
		double cannonX;
		switch(imageNum){
			case 0:
			case 2:
				cannonX = x + Tanks.TANKSIZE/2-Tanks.BULLETSIZE/2;
				break;
			case 1:
				cannonX = x - Tanks.BULLETSIZE;
				break;
			case 3:
				cannonX = x + Tanks.TANKSIZE;
				break;
			default:
				cannonX = x;
				break;				
		}
		return cannonX;
	}
	protected double getCannonY(){
		double cannonY;
		switch(imageNum){
			case 1:
			case 3:
				cannonY = y + Tanks.TANKSIZE/2-Tanks.BULLETSIZE/2;
				break;
			case 0:
				cannonY = y - Tanks.BULLETSIZE;
				break;
			case 2: 
				cannonY = y + Tanks.TANKSIZE;
				break;
			default:
				cannonY = y;
				break;
		}
		return cannonY;
	}
}
