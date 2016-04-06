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
		numCols = Tanks.NUMCOLS;
		numRows = Tanks.NUMROWS;
		cellArray = new PlayCell[numRows][numCols];
		initializeCellsForLevel(levelNum);
	}

	private void initializeCellsForLevel(int levelNum) {
		for(int row = 0; row < numRows; row++){
			for(int col = 0; col<numCols; col++){
				cellArray[row][col] = new PlayCell(col*Tanks.CELLSIZE, row*Tanks.CELLSIZE, LevelManager.getWalled(row,col,levelNum), this);
			}
		}
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
