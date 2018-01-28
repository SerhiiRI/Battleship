package com.company;

import java.util.ArrayList;

/**
 * Class represent some List of Point as ship, with served method of servicing them.
 */
class Ship {
    // Ship are destroy, when all of PointType Ship<Point> changed to @PointType.Shot
    private boolean killed = false;
    public ArrayList<Point> ship = new ArrayList<>();
    // copy ship.
    public Ship(ArrayList<Point> toAdd) {
        ship = toAdd;
    }
    // return True, if one of Point contain equals X, Y, value;
    public boolean contain(int x, int y) {
        for (Point point : ship) {
            if (point.getX() == x && point.getY() == y) {
                return true;
            }
        }
        return false;
    }
    // equal to method @contain, but used external ArrayList of Point
    public static boolean contain(int x, int y, ArrayList<Point> availabelPoint){
        for (Point point : availabelPoint) {
            if (point.getX() == x && point.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> get() {
        return ship;
    }
    // set-method to @this.killed parameter;
    private boolean checkToKilledShip() {
        for (Point value : ship)
            if (value.getType() == PointType.Ship) {
                return false;
            }
        killed = true;
        return true;
    }
    // get-method to @this.killed parameter;
    public boolean isKilled(){
        return killed;
    }
    // Change PointType.Ship to PointType.Shot in searched Point;
    public boolean shot(int x, int y){
        for(Point sh: ship)
            if(sh.getX()== x && sh.getY()==y) sh.setType(PointType.Shot);
        return checkToKilledShip();
    }
}