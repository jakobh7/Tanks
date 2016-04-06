package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.SoundManager;

public abstract class Character {
	protected double x, y;
	public double getX(){ return x;	}
	public double getY(){ return y; }
	protected double dx, dy;
	protected Image characterImage;
	protected int width, height;
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	protected boolean alive;
	public boolean isAlive(){return alive;}
	private boolean dead;
	public boolean isDead(){return dead;}
	protected int state;
	protected int explosionNum;
	
	public Character(double initx, double inity, double initdx, double initdy){
		x=initx;
		y=inity;
		dx = initdx;
		dy = initdy;
		alive = true;
		dead = false;
		characterImage = getImage();
		state = 0;
	}
	
	protected abstract Image getImage();

	public void update(){
		if(state==0){
			x = x + dx;
			y = y + dy;
		}
		else if (state == 1){
			if(explosionNum<29){
				explosionNum++;
			}
			else{
				dead = true;
			}
		}
		characterImage = getImage();
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(characterImage, x, y);
	}
	
	public void reverseDx() {
		dx = -dx;
		x = x+dx;
	}
	public void reverseDy(){
		dy = -dy;
		y = y+dy;
	}
	
	public void explode(){
		alive = false;
		state=1;
		explosionNum=0;
		dx = 0;
		dy=0;
		SoundManager.playExplosionSound();
	}
}
