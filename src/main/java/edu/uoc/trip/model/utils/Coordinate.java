package edu.uoc.trip.model.utils;

import java.util.Objects;

public class Coordinate {

    private int row;
    private int column;

    public Coordinate(int row, int column){
        setColumn(column);
        setRow(row);
    }

    public int getRow(){
        return row;

    }

    public int getColumn(){
        return column;

    }

    private void setRow(int row){
        this.row = row;

    }

    private void setColumn(int column){
        this.column = column;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString(){
        return "(" + getRow() + "," + getColumn() + ")";

    }
}
