package main;

import java.util.ArrayList;

import sprites.Enemy;

public class LevelManager {
	public static int numLevels = 5;
	private static int levelArray[][][];
	
	public static void setLevels(){
		levelArray = new int[Tanks.NUMROWS][Tanks.NUMCOLS][numLevels];
		setWalls();
		setEnemies();
	}
	
	
	private static void setWalls() {
		for(int level = 0; level < numLevels; level++){
			for(int row = 0; row<Tanks.NUMROWS; row++){
				for(int col = 0; col<Tanks.NUMCOLS; col++){
					if(level == 0) fillLevelOne(row, col);
					if(level == 1) fillLevelTwo(row, col);
					if(level == 2) fillLevelThree(row, col);
					if(level == 3) fillLevelFour(row, col);
					if(level == 4) fillLevelFive(row, col);
					if(row==0||row==Tanks.NUMROWS-1||col==0||col==Tanks.NUMCOLS-1) levelArray[row][col][level] = 1;
				}
			}
		}
	}

	private static void fillLevelOne(int row, int col){
		levelArray[row][col][0] = 0;
	}
	
	private static void fillLevelTwo(int row, int col) {
		// TODO Auto-generated method stub
		if(row+1 == col) levelArray[row][col][1] = 1;
		if(row+10 == col) levelArray[row][col][1] = 1;
		if(row == 3 || row == 9) levelArray[row][col][1] = 0;		
	}
	
	private static void fillLevelThree(int row, int col) {
		if((row*col)%6==0)levelArray[row][col][2] = 1;
		if(row==6||row==12)levelArray[row][col][2] = 0;
		
	}
	private static void fillLevelFour(int row, int col) {
		if((row*col)%5==0)levelArray[row][col][3] = 1;		
		if(col==5||col==10||col==15||col==20)levelArray[row][col][3]=0;
	}
	private static void fillLevelFive(int row, int col) {
		if(row == Tanks.NUMROWS/2)levelArray[row][col][4] = 1;
		if(col == Tanks.NUMCOLS/2)levelArray[row][col][4] = 1;
		if(row == 1 ||col == 1 || row == Tanks.NUMROWS-2 || col == Tanks.NUMCOLS-2) levelArray[row][col][4] = 0;
	}
	
	private static void setEnemies() {
		//level1
		levelArray[4][5][0] = 2;
		//level2
		levelArray[4][4][1] = 2;
		levelArray[6][8][1] = 2;
		//level3
		levelArray[6][9][2] = 2;
		levelArray[8][8][2] = 2;
		levelArray[5][15][2] = 2;
		//level4
		levelArray[7][2][3] = 2;
		levelArray[10][15][3] = 2;
		levelArray[5][16][3] = 2;
		levelArray[6][6][3] = 2;
		levelArray[2][17][3] = 2;
		//level5
		levelArray[4][4][4] = 2;
		levelArray[4][15][4] = 2;
		levelArray[10][4][4] = 2;
		levelArray[10][15][4] = 2;
	}

	public static boolean getWalled(int row, int col,int levelNum){
		if (levelArray[row][col][levelNum-1] == 1) return true;
		else return false;
	}
	
	public static ArrayList<Enemy> GetEnemies(int level){
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(int row = 0; row<Tanks.NUMROWS; row++){
			for(int col = 0; col<Tanks.NUMCOLS; col++){
				if(levelArray[row][col][level]==2){
					enemies.add(new Enemy(col*Tanks.CELLSIZE+5,row*Tanks.CELLSIZE+5,1,0,1));
				}
			}
		}
		return enemies;
	}
}
