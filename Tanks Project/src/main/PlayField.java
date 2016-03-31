package main;

import javafx.scene.canvas.GraphicsContext;
import sprites.Character;

public class PlayField {
	private int numRows;
	public int getNumRows(){return numRows;}
	private int numCols;
	public int getNumCols(){return numCols;}
	private int width;
	private int height;
	public PlayCell[][] cellArray;
	
	public PlayField(int width, int height, int levelNum){
		this.width = width;
		this.height = height;
		initializeCellsForLevel(levelNum);
	}

	private void initializeCellsForLevel(int levelNum) {
		numCols = this.width/Tanks.CELLSIZE;
		numRows = this.height/Tanks.CELLSIZE;
		cellArray = new PlayCell[numRows][numCols];
		for(int row = 0; row < numRows; row++){
			for(int col = 0; col<numCols; col++){
				cellArray[row][col] = new PlayCell(col*Tanks.CELLSIZE, row*Tanks.CELLSIZE, getWalled(row,col,levelNum), this);
			}
		}
	}
	private boolean getWalled(int row, int col,int levelNum){
		if (row==0)return true;
		else if (col==0)return true;
		else if (row==numRows-1) return true;
		else if (col==numCols-1) return true;
		//else if (((levelNum-1)*row*col)%4==0) return true;
		else return false;
	}
	
	public void renderEachCell(GraphicsContext gc){
		for(int row = 0; row<numRows; row++){
			for(int col = 0; col<numCols; col++){
				cellArray[row][col].render(gc);
			}
		}
	}
	public void updateEachCell(){
		for(int row = 0; row<numRows; row++){
			for(int col = 0; col<numCols; col++){
				cellArray[row][col].update();
			}
		}
	}

	public void placeCharacter(Character temp) {
		int col = (int)temp.getX()/Tanks.CELLSIZE;
		int row = (int)temp.getY()/Tanks.CELLSIZE;
		cellArray[row][col].addCharacter(temp);		
	}
}
