package main;

import settings.Config;

public class Coordonnees {

    int line;
    int column;
    

    Coordonnees(int numLine, int numCol){
        line = numLine;
        column = numCol;
    }

    boolean isInGrid(){
        return 0 <= line && line < Config.nbRow 
        && 0 <= column && column < Config.nbCol;

    }
}
