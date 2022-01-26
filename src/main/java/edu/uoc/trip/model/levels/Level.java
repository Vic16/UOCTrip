package edu.uoc.trip.model.levels;

//import java.lang.reflect.Array;
import java.util.*;

import edu.uoc.trip.model.cells.*;
import edu.uoc.trip.model.utils.Coordinate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Class that represents each level of the game.
 *
 * @author David García-Solórzano
 * @version 1.0
 */
public class Level {

    /**
     * Size of the board, i.e. size x size.
     */
    private int size;

    /**
     * Difficulty of the level
     */
    private LevelDifficulty difficulty;

    /**
     * Representation of the board.
     */
    private Cell[][] board;

    /**
     * Number of moves that the player has made so far.
     */
    private int numMoves = 0;

    /**
     * Minimum value that must be assigned to the attribute "size".
     */
    private static final int MINIMUM_BOARD_SIZE = 3;

    /**
     * Constructor
     *
     * @param fileName Name of the file that contains level's data.
     * @throws LevelException When there is any error while parsing the file.
     */
    public Level(String fileName) throws LevelException {
        setNumMoves(0);
        parse(fileName);
    }

    /**
     * Parses/Reads level's data from the given file.<br/>
     * It also checks which the board's requirements are met.
     *
     * @param fileName Name of the file that contains level's data.
     * @throws LevelException When there is any error while parsing the file
     * or some board's requirement is not satisfied.
     */
    private void parse(String fileName) throws LevelException{
        boolean isStarting = false;
        boolean isFinish = false;
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(fileName));

        try(InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)){

            line = getFirstNonEmptyLine(reader);

            if (line  != null) {
                setSize(Integer.parseInt(line));
            }

            line = getFirstNonEmptyLine(reader);

            if (line != null) {
                setDifficulty(LevelDifficulty.valueOf(line));
            }

            board = new Cell[getSize()][getSize()];

            for (int row = 0; row < getSize(); row++) {
                char[] rowChar = Objects.requireNonNull(getFirstNonEmptyLine(reader)).toCharArray();
                for (int column = 0; column < getSize(); column++) {
                    board[row][column] = CellFactory.getCellInstance(row, column,
                            Objects.requireNonNull(CellType.map2CellType(rowChar[column])));
                }
            }

        }catch (IllegalArgumentException | IOException e){
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        //Check if there is one starting cell, one finish cell and, at least, any other type of cell.
        for(var j =0; j<getSize(); j++){

            if(getCell(new Coordinate(getSize()-1,j)).getType() == CellType.START){
                isStarting = true;
            }

            if(getCell(new Coordinate(0,j)).getType() == CellType.FINISH){
                isFinish = true;
            }
        }

        //Checks if there are more than one starting cell
        if(Stream.of(board).flatMap(Arrays::stream).filter(x -> x.getType() == CellType.START).count()>1){
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        //Checks if there are more than one finish cell
        if(Stream.of(board).flatMap(Arrays::stream).filter(x -> x.getType() == CellType.FINISH).count()>1){
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        if(!isStarting){
            throw new LevelException(LevelException.ERROR_NO_STARTING);
        }

        if(!isFinish){
            throw new LevelException(LevelException.ERROR_NO_FINISH);
        }

        //Checks if there is one road (i.e. movable or rotatable cell) at least.
        if(Stream.of(board).flatMap(Arrays::stream).noneMatch(x -> x.isMovable() || x.isRotatable())){
            throw new LevelException(LevelException.ERROR_NO_ROAD);
        }

    }

    /**
     * This a helper method for {@link #parse(String fileName)} which returns
     * the first non-empty and non-comment line from the reader.
     *
     * @param br BufferedReader object to read from.
     * @return First line that is a parsable line, or {@code null} there are no lines to read.
     * @throws IOException if the reader fails to read a line.
     */
    private String getFirstNonEmptyLine(final BufferedReader br) throws IOException {
        do {

            String s = br.readLine();

            if (s == null) {
                return null;
            }
            if (s.isBlank() || s.startsWith("#")) {
                continue;
            }

            return s;
        } while (true);
    }

    ////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////

    public int getSize(){
        return size;
    }

    private void setSize(int size) throws LevelException{
        if(size < 3){
            throw new LevelException(LevelException.ERROR_BOARD_SIZE);
        }this.size = size;
    }

    public LevelDifficulty getDifficulty() {
        return difficulty;
    }
    private void setDifficulty(LevelDifficulty difficulty){
        this.difficulty = difficulty;
    }

    public int getNumMoves() {
        return numMoves;
    }

    private void setNumMoves(int numMoves) {
        this.numMoves = numMoves;
    }

    private boolean validatePosition(Coordinate coordinate){
        int col = coordinate.getColumn();
        int row = coordinate.getRow();
        int size = getSize();

        if((col >=0 && col < size) && (row >=0 && row < size)){
            return true;
        }
        return false;

    }

    private void setCell(Coordinate coordinate, Cell cell) throws LevelException{
        if(validatePosition(coordinate)){
            int col = coordinate.getColumn();
            int row = coordinate.getRow();
            this.board[row][col] = cell;

        }else if (cell == null){
            throw new LevelException(LevelException.ERROR_COORDINATE);
        }

    }

    public Cell getCell(Coordinate coordinate) throws LevelException{
        if(validatePosition(coordinate)){
            int col = coordinate.getColumn();
            int row = coordinate.getRow();
            return board[row][col];

        }
        throw new LevelException(LevelException.ERROR_COORDINATE);

    }

    public void swapCells(Coordinate firstCoord, Coordinate secondCoord) throws LevelException{

        boolean isMov1 = getCell(firstCoord).isMovable();
        boolean isMov2 = getCell(secondCoord).isMovable();

        if(!isMov1 || !isMov2){
            throw new LevelException(LevelException.ERROR_NO_MOVABLE_CELL);
        }else if(isMov1 && isMov2){
            MovableCell cell1 = (MovableCell) getCell(firstCoord);
            MovableCell cell2 = (MovableCell) getCell(secondCoord);
            cell1.move(secondCoord);
            cell2.move(firstCoord);
            setCell(secondCoord, cell1);
            setCell(firstCoord, cell2);
            //this.board[secondCoord.getRow()][secondCoord.getColumn()] = cell_1;
            //this.board[firstCoord.getRow()][firstCoord.getColumn()] = cell_2;

            this.numMoves+=1;
        }



            //setCell(secondCoord, cell_1);
            //setCell(firstCoord, cell_2);
            //this.board[secondCoord.getRow()][secondCoord.getColumn()] = cell_1;
            //this.board[firstCoord.getRow()][firstCoord.getColumn()] = cell_2;

            //setNumMoves(numMoves + 1);

        //throw new LevelException(LevelException.ERROR_NO_MOVABLE_CELL);

       /**if(!isMov_1 || !isMov_2){
            throw new LevelException(LevelException.ERROR_NO_MOVABLE_CELL);
        }else if(isMov_1 && isMov_2){
           setCell(secondCoord, cell_1);
           setCell(firstCoord, cell_2);
           this.board[secondCoord.getRow()][secondCoord.getColumn()] = cell_1;
           this.board[firstCoord.getRow()][firstCoord.getColumn()] = cell_2;
           this.numMoves+=1;
        }
       throw new LevelException(LevelException.ERROR_NO_MOVABLE_CELL);**/


    }


    public void rotateCell(Coordinate coordinate) throws LevelException {

        /**Cell cell = getCell(coordinate);
        boolean checking = cell.isRotatable();
        if(checking){
            cell.getType().next();
            this.numMoves +=1;
            //setNumMoves(numMoves  + 1);
        }else {
            throw new LevelException(LevelException.ERROR_NO_ROTATABLE_CELL);
        }
        throw new LevelException(LevelException.ERROR_NO_ROTATABLE_CELL);**/
        ////////////////
        boolean cheking = getCell(coordinate).isRotatable();
        if(!cheking){
            throw new LevelException(LevelException.ERROR_NO_ROTATABLE_CELL);
        }else if(cheking){
            RotatableCell cell = (RotatableCell) getCell(coordinate);
            cell.rotate();
            setCell(coordinate, cell);
            this.numMoves += 1;
        }


        /**if(chekinhg){
            RotatableCell cell = (RotatableCell) getCell(coordinate);
            cell.rotate();
            setCell(coordinate, cell);
            this.numMoves += 1;
        }

        throw new LevelException(LevelException.ERROR_NO_ROTATABLE_CELL);**/


    }

    public boolean isSolved() throws LevelException{
        int size = getSize();
        //Coordinate coordinateStart = new Coordinate(0,0);

        for(int row = 0; row<size; row++) {
            for(int column= 0; column<size; column++) {
                Coordinate coord = new Coordinate(row, column);
                CellType cellType = getCell(coord).getType();
                if(cellType == CellType.START){
                    Coordinate coordinateStart = new Coordinate(coord.getRow() + 1, coord.getColumn());
                    getCell(coordinateStart).getType();
                }else if(cellType == CellType.FINISH){
                    Coordinate coordinateFinish = new Coordinate(row, column);
                }

            }
        }


        return true;
    }

    @Override
    public String toString(){

        StringBuilder str = new StringBuilder();
        List<Integer> range = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());
        String rangeString = range.toString();
        rangeString = rangeString.replaceAll(",","");
        rangeString = rangeString.replace("[","");
        rangeString = rangeString.replace("]","");
        rangeString = rangeString.replace(" ","");
        char[] alphabet = new char[]{'a', 'b', 'c','d','e','f','g','h','i','j','k','l','m',
                'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y', 'z'};

        str.append(rangeString + System.lineSeparator());

        for(int row = 0; row<size; row++) {
            char letter = alphabet[row];
            str.append(letter + "|");
            for(int column= 0; column<size; column++) {
                str.append(board[row][column]);
            }
            str.append(System.lineSeparator());
        }
        return str.toString();

    }
}
