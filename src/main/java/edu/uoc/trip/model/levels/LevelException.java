package edu.uoc.trip.model.levels;

public class LevelException extends Exception{

    public final static String ERROR_PARSING_LEVEL_FILE = "[ERROR] There was an error while loading the current level file!!";
    public final static String ERROR_BOARD_SIZE = "[ERROR] Board's size must be greater than 2!!";
    public final static String ERROR_COORDINATE = "[ERROR] This coordinate is incorrect!!";
    public final static String ERROR_NO_STARTING = "[ERROR] This level does not have any starting cell!!";
    public final static String ERROR_NO_FINISH = "[ERROR] This level does not have any finish cell!!";
    public final static String ERROR_NO_ROAD = "[ERROR] This level does not have any road!!";
    public final static String ERROR_NO_MOVABLE_CELL = "[ERROR] You have chosen a static cell!!";
    public final static String ERROR_NO_ROTATABLE_CELL = "[ERROR] You have chosen a non-rotatable cell!!";

    public LevelException(String msg) {
        super(msg);
    }

}
