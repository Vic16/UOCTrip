package edu.uoc.trip.model.cells;
import edu.uoc.trip.model.utils.Coordinate;

public class Cell {
    private CellType cellType;
    private Coordinate coordinate;

    public Cell(int row, int column, CellType cellType){
        this.coordinate = new Coordinate(row, column);
        this.cellType = cellType;
    }

    public CellType getType() {
        return cellType;
    }

    protected void setType(CellType cellType) {
        this.cellType = cellType;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    protected void setCoordinate(int row, int column){
        this.coordinate = new Coordinate(row, column);
    }

    public boolean isMovable(){
        if(String.valueOf(cellType.getFileSymbol()).equals("V") ||
                String.valueOf(cellType.getFileSymbol()).equals("H") ||
                String.valueOf(cellType.getFileSymbol()).equals("L") ||
                String.valueOf(cellType.getFileSymbol()).equals("R") ||
                String.valueOf(cellType.getFileSymbol()).equals("l") ||
                String.valueOf(cellType.getFileSymbol()).equals("r")){
            return true;
        }
        return false;
    }

    public boolean isRotatable(){
        if(String.valueOf(cellType.getFileSymbol()).equals("G") ||
                String.valueOf(cellType.getFileSymbol()).equals("g")){
            return true;
        }
        return false;
    }

    @Override
    /**
     *
     * @return the character of the symbol.
     */
    public String toString() {
        return String.valueOf(cellType.getUnicodeRepresentation());
    }
}
