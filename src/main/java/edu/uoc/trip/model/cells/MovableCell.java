package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.utils.Coordinate;

public class MovableCell extends Cell implements Movable{

    public MovableCell(int row, int column, CellType type) {
        super(row, column, type);
    }

    @Override
    public void move(Coordinate destination){
        int col = destination.getColumn();
        int row = destination.getRow();
        setCoordinate(row, col);

    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isRotatable(){
        String type = String.valueOf(this.getType());
        if(type.equals("ROTATABLE_VERTICAL") || type.equals("ROTATABLE_HORIZONTAL")){
            return true;
        }
        return false;
    }


}
