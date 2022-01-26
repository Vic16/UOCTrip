package edu.uoc.trip.model.cells;

public class RotatableCell extends Cell implements Rotatable{

    public RotatableCell(int row, int column, CellType cellType) {
        super(row, column, cellType);
    }

    /**public CellType next() {
        String type = String.valueOf(this.getType());
        if(type.equals("ROTATABLE_VERTICAL")){
            return CellType.ROTATABLE_VERTICAL.next();
        }
        return CellType.ROTATABLE_HORIZONTAL.next();
    }**/

    @Override
    public boolean isMovable(){
        return false;
    }

    @Override
    public boolean isRotatable(){
        return true;
    }

    @Override
    public void rotate(){
        CellType cellType = getType();
        setType(cellType.next());

    }


}
