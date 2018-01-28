package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

enum PointType {
    Ship,
    Watter,
    Lose,
    ToSelect,
    Buffer,
    Shot;
}

enum Direction {
    Up,
    Down,
    Right,
    Left;
}

enum Status{
    Create,
    Game,
    Finish;
}

class Board {
    // (get|set by otherside class) board for View in User-end side.
    private Point[][] board = null;
    // (get)Name of Gamer, used to printing method;
    private String gamer;
    // (set|init) Enumeration of game-stage (start|game|finish);
    // start - creation of ship map;
    // game - user try to kill other gamer ship;
    // finish - stage of closed anyone of operation on Board
    private Status status;
    // Used in Start stage. Double click to Adding ship, must save first click to invoking method;
    // (get|set)
    private Point bufferClick;
    // Used to view possible place to built a ship. For decision use @BufferClick X and Y method;
    // (init|remove)
    private ArrayList<ArrayList<Point>> AvailablePositionToInsertShip = new ArrayList<>();
    // List of @Ships class. @Ships - is a list of @Point class.
    // (add|remove)
    private ArrayList<Ship> ships = new ArrayList<>();
    // Hash map, which control count of available ship by his length:  < key = length, value = CountOfAvailableShip >;
    // (init|set)
    private Map<Integer, Integer> availableShipsCount = new HashMap<Integer, Integer>() {{
        put(4, 1);
        put(3, 1);
        put(2, 1);
        put(1, 1);
    }};

    Board(String gamer) {
        this.status = Status.Create;
        this.gamer = gamer;
        board = new Point[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Point(i, j);
            }
        }
        __admin__randomGenerate();
    }
    public String getGamer(){
        return gamer;
    }
    //reference to static method in Print class;
    public void print() {
        Print.print(this.board, this.gamer);
    }
    public void printForOthePlayer(){
        Print.printForOthePlayer(this.board, this.gamer);
    }

    /**
     * method @click choose one of action,
     * depending on the previous click( contained in <Point>Buffer);
     * and type of cell PointType;
     */
    public void click(int x, int y) {
        if (status == Status.Create) {
            if (bufferClick == null) {
                // if user not click before;
                switch (board[x][y].getType()) {
                    case Ship:
                        removeShip(x, y);
                        break;
                    case Watter:
                        getArrayVector(x, y, Direction.Down);
                        getArrayVector(x, y, Direction.Up);
                        getArrayVector(x, y, Direction.Left);
                        getArrayVector(x, y, Direction.Right);
                        bufferClick = new Point(x, y, PointType.Buffer);
                        break;
                    default:
                        break;
                }
            } else {
                switch (board[x][y].getType()) {
                    // if user click on one cell of @Board table
                    // buffer != null;
                    case ToSelect:
                        AvailablePositionToInsertShip.forEach(items -> items.forEach(item -> board[item.getX()][item.getY()].setType(PointType.Watter)));
                        ships.add(addShip(x, y, bufferClick.getX(), bufferClick.getY()));
                        AvailablePositionToInsertShip.clear();
                        bufferClick = null;
                        break;
                    case Watter:
                        removeAllAvailableShipPlace();
                        bufferClick = null;
                        getArrayVector(x, y, Direction.Down);
                        getArrayVector(x, y, Direction.Up);
                        getArrayVector(x, y, Direction.Left);
                        getArrayVector(x, y, Direction.Right);
                        bufferClick = new Point(x, y, PointType.Buffer);
                        break;
                    default:
                        removeAllAvailableShipPlace();
                        bufferClick = null;
                        break;
                }
            }
        }

        if (status == Status.Finish){
            //TODO: if finish, then continue game;
        }
    }

    /**
     * Service to shooting;
     * @return true, if OnClick was caused by choosing Watter or Ship cell, in other type cell return false;
     */
    public boolean ShotClick(int x, int y){
        boolean isClick = false;
        if(status == Status.Game){
            switch(board[x][y].getType()){
                case Watter:
                    board[x][y].setType(PointType.Lose);
                    isClick = false;
                    break;
                case Ship:
                    for(Ship sh: ships){
                        if(sh.contain(x, y)){
                            sh.shot(x, y);
                            board[x][y].setType(PointType.Shot);
                            isClick = true;
                            break;
                        }
                    }
                    break;
                default:
                    isClick = true;
                    break;
            }
        }
        printForOthePlayer();
        return isClick;
    }

    /**
     * Test on count of destroyed ship;
     */
    public boolean isFinal(){
        for(Ship ship: ships){
            if(!ship.isKilled()){
                return false;
            }
        }
        return true;
    }

    /**
     * Init new stage of game; (start| game| finish)
     */
    public void changeStatusOfGame(Status newGameStatus){
        this.status = newGameStatus;
    }

    /**
     * Testing HashMap of available ship to insertion into Creation stage of game;
     * @return true, if count of all ships equals 0;
     */
    public boolean isReady(){
        // how to learn English to commit level?
        // ok... this...  function return true if all ships in board;
        boolean result = true;
        for (int i=4; i>=1; i--){
            if(availableShipsCount.get(i) != 0) result = false;
        }
        return result;
    }
    private Ship addShip(int x, int y, int x1, int y1) {
        ArrayList<Point> tempShip = new ArrayList<>();
        for (ArrayList<Point> points : AvailablePositionToInsertShip)
            if (Ship.contain(x, y, points)) {
                Point point = points.get(points.size() - 1);
                x = point.getX();
                y = point.getY();
            }
        int ship_length = -100;
        if(x!=x1 && y1==y) {
            for (int functional : range(x, x1)) {
                board[functional][y] = new Point(functional, y, PointType.Ship);
                tempShip.add(new Point(functional, y, PointType.Ship));
            }
            ship_length = range(x, x1).size();
        }
        if(x==x1 && y1!=y) {
            for (int functional : range(y, y1)) {
                board[x][functional] = new Point(x, functional, PointType.Ship);
                tempShip.add(new Point(x, functional, PointType.Ship));
            }
            ship_length = range(y, y1).size();
        }
        if(x==x1 && y1==y) {
            board[x][y] = new Point(x, y, PointType.Ship);
            tempShip.add(new Point(x, y, PointType.Ship));
            ship_length = 1;
        }
        if(ship_length >0) {
            int ship_count = availableShipsCount.get(ship_length);
            availableShipsCount.put(ship_length, ship_count - 1);
        }
        return new Ship(tempShip);
    }

    /**
     * Method create possible vector of ArrayList<Point>, that used for adding ships in this place;
     * To every of Direction enumeration(left|right|down|up) create new ArrayList<Point>
     * the decision to create a list depends on the coordinates X, Y and pre-define
     * position of ships on board;
     */
    private boolean getArrayVector(int x, int y, Direction direct) {
        int MaxShipLenght = 0;
        for (int i = 4; i > 0; i--) {
            int value = availableShipsCount.get(i);
            if (value > 0) {
                MaxShipLenght = i;
                break;
            }
        }
        if (MaxShipLenght > 0) {
            ArrayList<Point> tempShip = new ArrayList<>();
            switch (direct) {
                case Down:
                    for (int i = y; i > ((y - MaxShipLenght < 0) ? y : y - MaxShipLenght); i--) {
                        tempShip.add(new Point(x, i, PointType.ToSelect));
                    }
                    break;
                case Up:
                    for (int i = y; i < ((y + MaxShipLenght > 9) ? y : y + MaxShipLenght); i++) {
                        tempShip.add(new Point(x, i, PointType.ToSelect));
                    }
                    break;
                case Left:
                    for (int i = x; i > ((x - MaxShipLenght < 0) ? x : x - MaxShipLenght); i--) {
                        tempShip.add(new Point(i, y, PointType.ToSelect));
                    }
                    break;
                case Right:
                    for (int i = x; i < ((x + MaxShipLenght > 9) ? x : x + MaxShipLenght); i++) {
                        tempShip.add(new Point(i, y, PointType.ToSelect));
                    }
                    break;
                default:
                    break;
            }
            if (testAvailabelSpace(tempShip)) {
                AvailablePositionToInsertShip.add(tempShip);
                // I wrote this, because I can...
                AvailablePositionToInsertShip.forEach(items -> items.forEach(item -> board[item.getX()][item.getY()].setType(item.getType())));
                // And now Cthulhu mast wake up.
            }
        }
        return true;
    }

    /**
     * loop for testing free space(no ship, no board) to non create Possible Vector space for adding Ship;
     */
    private boolean testAvailabelSpace(ArrayList<Point> maybeShip){
        boolean result=true;
        for(Point tt: maybeShip){
            int x = tt.getX(); int y = tt.getY();
            for (int i=((x-1 < 0)? x : x-1); i<=((x+1>9)? x: x+1); i++){
                for (int j=((y-1 < 0)? y : y-1); j<=((y+1>9)? y: y+1); j++){
                    if(board[i][j].getType() == PointType.Ship){
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private ArrayList<Integer> range(int a, int b){
        // because i use python, and I`m so lazy. And I know about Guava.
        if (a>b){
            int temp = a;
            a = b;
            b = temp;
        }
        ArrayList<Integer> rang = new ArrayList<>();
        for(int i=a; i<=b; i++) {
            rang.add(i);
        }
        return rang;
    }
    private void removeShip(int x, int y) {
        int indexToRemove = 0;
        int size = -100;
        for (Ship ship : ships) {
            if (ship.contain(x, y)) {
                ship.get().forEach(point -> board[point.getX()][point.getY()].setType(PointType.Watter));
                indexToRemove = ships.indexOf(ship);
                size = ship.get().size();
            }
        }
        if(size>0){
            availableShipsCount.put(size, availableShipsCount.get(size)+1);
        }
        ships.remove(indexToRemove);
    }

    /**
     * fast define ship to TestBoard;
     */
    private void __admin__randomGenerate(){
	    ships.add(addShip(0,0,3,0));
		ships.add(addShip(0, 2, 2, 2));
		ships.add(addShip(0, 4, 1, 4));
		ships.add(addShip(0, 6, 0, 6));
    }

    /**
     * cleaning available Ships Vectors;
     */
    private void removeAllAvailableShipPlace() {
        AvailablePositionToInsertShip.forEach(items ->items.forEach(item ->board[item.getX()][item.getY()].setType(PointType.Watter)));
        AvailablePositionToInsertShip.clear();
    }
}
