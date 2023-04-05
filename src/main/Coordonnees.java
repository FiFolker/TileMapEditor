package main;

import settings.Config;

public class Coordonnees {

	public int line;
	public int column;
	

	Coordonnees(int numLine, int numCol){
		line = numLine;
		column = numCol;
	}

	boolean isInGrid(){
		return line >= 0 && line < Config.nbRow 
		&& column >= 0 && column < Config.nbCol;
	}
}
