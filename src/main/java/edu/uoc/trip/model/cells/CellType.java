package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.levels.Direction;

import java.util.EnumSet;

public enum CellType {

    START('S', '^', "start.png", new boolean[]{true, false, false, false}) {
        public CellType next(){
            return null;
        }
    },
    FINISH('F', 'v', "finish.png", new boolean[]{false, false, true, false}) {
        public CellType next(){
            return null;
        }
    },
    MOUNTAINS('M', 'M', "mountains.png", new boolean[]{false, false, false, false}){
        public CellType next(){
            return null;
        }
    },
    RIVER('~', '~', "river.png", new boolean[]{false, false, false, false}){
        public CellType next(){
            return null;
        }
    },
    VERTICAL('V', '\u2551', "road_vertical.png", new boolean[]{true, false, true, false}){
        public CellType next(){
            return HORIZONTAL;
        }
    },
    HORIZONTAL('H', '\u2550', "road_horizontal.png", new boolean[]{false, true, false, true}){
        public CellType next(){
            return VERTICAL;
        }
    },
    BOTTOM_RIGHT('r', '\u2554', "road_bottom_right.png", new boolean[]{true, true, false, false}){
        public CellType next(){
            return BOTTOM_LEFT;
        }
    },
    BOTTOM_LEFT('l', '\u2557', "road_bottom_left.png", new boolean[]{true, false, false, true}){
        public CellType next(){
            return TOP_LEFT;
        }
    },
    TOP_RIGHT('R', '\u255A', "road_top_right.png", new boolean[]{false, true, true, false}){
        public CellType next(){
            return BOTTOM_RIGHT;
        }
    },
    TOP_LEFT('L', '\u255D', "road_top_left.png", new boolean[]{false, false, true, true}){
        public CellType next(){
            return TOP_RIGHT;
        }
    },
        FREE('·', '\u00b7', "free.png", new boolean[]{false, false, false, false}){
        public CellType next(){
            return null;
        }
    },
    ROTATABLE_VERTICAL('G', '\u2503', "road_rotatable_vertical.png", new boolean[]{true, false, true, false}){
        public CellType next(){
            return ROTATABLE_HORIZONTAL;
        }
    },
        ROTATABLE_HORIZONTAL('g', '\u2501', "road_rotatable_horizontal.png", new boolean[]{false, true, false, true}){
        public CellType next(){
            return ROTATABLE_VERTICAL;
        }
    };

    //private static boolean[] connections;
    private char fileSymbol;
    private char unicodeRepresentation;
    private String imageSrc;
    private boolean[] connections;

    CellType(char fileSymbol, char unicodeRepresentation, String imageSrc, boolean[] connections){
        setFileSymbol(fileSymbol);
        setUnicodeRepresentation(unicodeRepresentation);
        setImageSrc(imageSrc);
        setConnections(connections);
    }

    public char getFileSymbol(){
        return this.fileSymbol;
    }

    public char getUnicodeRepresentation(){
        return unicodeRepresentation;
    }

    public String getImageSrc(){
        return imageSrc;
    }

    private void setFileSymbol(char fileSymbol){
        this.fileSymbol = fileSymbol;
    }

    private void setUnicodeRepresentation(char unicodeRepresentation){
        this.unicodeRepresentation = unicodeRepresentation;
    }

    private void setImageSrc(String imageSrc){
        this.imageSrc = imageSrc;
    }

    private void setConnections(boolean[] connections){
        this.connections = connections;
    }


    public static CellType map2CellType(char fileSymbol){
        if(fileSymbol == 'S') {
            return START;
        }else if(fileSymbol == 'F'){
            return FINISH;
        }else if(fileSymbol == '~'){
            return RIVER;
        }else if(fileSymbol == 'M'){
            return MOUNTAINS;
        }else if(fileSymbol == 'V'){
            return VERTICAL;
        }else if(fileSymbol == 'H'){
            return HORIZONTAL;
        }else if(fileSymbol == 'r'){
            return BOTTOM_RIGHT;
        }else if(fileSymbol == 'l'){
            return BOTTOM_LEFT;
        }else if(fileSymbol == 'R'){
            return TOP_RIGHT;
        }else if(fileSymbol == 'L'){
            return TOP_LEFT;
        }else if(fileSymbol == '·'){
            return FREE;
        }else if(fileSymbol == 'G'){
            return ROTATABLE_VERTICAL;
        }else if(fileSymbol == 'g'){
            return ROTATABLE_HORIZONTAL;
        }else{
            return null;
        }
    }

    public EnumSet<Direction> getAvailableConnections() {
        if(fileSymbol == 'S'){                             // Start
            return  EnumSet.of(Direction.UP);
        }else if(fileSymbol == 'F'){                      //  Finnish
             return EnumSet.of(Direction.DOWN);
        }else if(fileSymbol == 'V'){                      // Vertical
            return EnumSet.of(Direction.UP, Direction.DOWN);
        }else if(fileSymbol == '~'){                      // River
            return //null;
                    EnumSet.noneOf(Direction.class);
        }else if(fileSymbol == 'M'){                     // Mountain
            return EnumSet.noneOf(Direction.class);
                 // EnumSet.noneOf(days.class);
        } else if(fileSymbol == 'H'){                   // Horizontal
            return EnumSet.of(Direction.LEFT, Direction.RIGHT);
        }else if(fileSymbol == 'r'){
            return EnumSet.of(Direction.DOWN, Direction.RIGHT);
        }else if(fileSymbol == 'l'){
            return EnumSet.of(Direction.LEFT, Direction.DOWN);
        }else if(fileSymbol == 'R'){
            return EnumSet.of(Direction.RIGHT, Direction.UP);
        }else if(fileSymbol == 'L'){
            return EnumSet.of(Direction.LEFT, Direction.UP);
        }else if(fileSymbol == '.'){
            return //null;
                    EnumSet.noneOf(Direction.class);
        }else if(fileSymbol == 'G'){
            return EnumSet.of(Direction.DOWN, Direction.UP);
        }else if(fileSymbol == 'g'){
            return EnumSet.of(Direction.LEFT, Direction.RIGHT);
        }
        return EnumSet.noneOf(Direction.class);
    }
    public abstract CellType next();


}
