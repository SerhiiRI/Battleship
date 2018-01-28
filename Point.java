package com.company;

/**
 * Class define X, Y param location on board, and one of enumeration PointType;
 */
class Point{
    private PointType type = PointType.Watter;
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = PointType.Watter;
    }
    Point(int x, int y, PointType typ){
        this.x = x;
        this.y = y;
        this.type = typ;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public PointType getType() {
        return type;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setType(PointType type) {
        this.type = type;
    }
}