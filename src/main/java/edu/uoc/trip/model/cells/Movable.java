package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.utils.Coordinate;

public interface Movable {

    /**
     * Moves the object to the new row and column position.
     *
     */
    void move(Coordinate destination) throws Exception;

}
