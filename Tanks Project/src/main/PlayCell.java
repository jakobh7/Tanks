package main;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sprites.Character;
import sprites.Enemy;
import sprites.Tank;

public class PlayCell {
	private boolean walled;
	public boolean getWalled(){return walled;}
	private int x;
	public int getX(){return x;}
	private int y;
	public int getY(){return y;}
	int col;
	int row;
	public ArrayList<Character> currentActors;
	private ArrayList<Integer> deadActors;
	private PlayField parentField;
	public PlayCell(int startx, int starty, boolean isWalled, PlayField parent){
		x = startx;
		y = starty;
		row = y/Tanks.CELLSIZE;
		col = x/Tanks.CELLSIZE;
		walled = isWalled;
		parentField = parent;
		currentActors = new ArrayList<Character>();
		deadActors = new ArrayList<Integer>();
	}
	
	public void addCharacter(Character newCharacter){
		currentActors.add(newCharacter);
	}
	
	public void render(GraphicsContext gc){
		if(walled){
			gc.setFill(Color.BLACK);
			gc.strokeRect(x, y, Tanks.CELLSIZE, Tanks.CELLSIZE);
			gc.setFill(Color.CHOCOLATE);
			gc.fillRect(x, y, Tanks.CELLSIZE, Tanks.CELLSIZE);
		}
		else{
			for(int actor = 0; actor<currentActors.size(); actor++) currentActors.get(actor).render(gc);
		}
	}
	
	public void update(){
		for(int actor=0; actor<currentActors.size(); actor++){ 
			Character temp = currentActors.get(actor);
			if(temp.isDead()){
				deadActors.add(actor);
			}
			else{
				temp.update();
				if(temp.isAlive()){
					checkCollisions(temp);
					if(temp instanceof Enemy){
						Enemy enemyTemp = (Enemy)temp;
						if(enemyTemp.shouldFire()){
							addCharacter(enemyTemp.shootBullet());
						}
					}
					if(temp.getX()<x||temp.getX()>x+Tanks.CELLSIZE||temp.getY()<y||temp.getY()>y+Tanks.CELLSIZE){
						parentField.placeCharacter(temp);
						currentActors.remove(temp);
					}
				}
			}
		}
		for(int dead = 0; dead<deadActors.size(); dead++){
			currentActors.remove(deadActors.get(dead));
			//System.out.println("num dead: " + deadActors.size());
		}
		deadActors.clear();
	}

	private void checkCollisions(Character actor) {
		for(int tempRow = row-1; tempRow<=row+1; tempRow++){
			for(int tempCol = col-1; tempCol <=col+1; tempCol++){
				if (tempRow>=0 && tempCol>=0 && tempRow<parentField.getNumRows() && tempCol<parentField.getNumCols()){
					PlayCell adjCell = parentField.cellArray[tempRow][tempCol];
					if(adjCell.getWalled()){
						wallCollision(actor, adjCell, tempCol, tempRow);
					}
					else if(actor instanceof Tank){
						tankCollision(actor, adjCell);
					}
				}
			}
		}
	}

	private void tankCollision(Character actor, PlayCell adjCell) {
		for(int i = 0; i<adjCell.currentActors.size(); i++){
			Character tempActor = adjCell.currentActors.get(i);
			if(!tempActor.equals(actor)){
				if(actor.getX()+actor.getWidth()>tempActor.getX()&&actor.getX()<tempActor.getX()+tempActor.getWidth()
						&&actor.getY()+actor.getHeight()>tempActor.getY()&&actor.getY()<tempActor.getY()+tempActor.getHeight()){
					actor.explode();
					tempActor.explode();
				}
			}
		}
	}

	private void wallCollision(Character actor, PlayCell adjCell, int tempCol, int tempRow) {
		if(actor.getX()+actor.getWidth()>adjCell.getX()&&actor.getX()<adjCell.getX()+Tanks.CELLSIZE
				&&actor.getY()+actor.getHeight()>adjCell.getY()&&actor.getY()<adjCell.getY()+Tanks.CELLSIZE){
			if(tempCol!=col)actor.reverseDx();
			if(tempRow!=row)actor.reverseDy();
		}
	}
}
