package edu.uoc.trip.model.levels;

public enum Direction {

    UP(-1, 0,2),
    RIGHT(0,1,3),
    DOWN(1, 0,0),
    LEFT(0,-1,1);

    private final int dRow;
    private final int dColumn;
    private final  int opposite;

    Direction(int dRow, int dColumn, int opposite){
        this.dRow = dRow;
        this.dColumn = dColumn;
        this.opposite = opposite;
    }

    public final Direction getOpposite(){
        if(this.dRow == -1 && this.dColumn == 0){
            return DOWN;
        }else if(this.dRow == 0 && this.dColumn == 1){
            return LEFT;
        }else if(this.dRow == 1 && this.dColumn == 0){
            return UP;
        }else{
            return RIGHT;
        }

    }

    public static Direction getValueByIndex(int index){
        Direction[] values = Direction.values();
        return values[index];

    }

    public final int getDRow() {
        return dRow;
    }

    public final int getDColumn() {
        return dColumn;
    }
}
