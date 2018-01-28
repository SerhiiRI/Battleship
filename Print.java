package com.company;

/**
 * Class Print, use to view gamers board. Class had a two public method, for different stage of game.
 */
public class Print {
    /**
     * Method to console write.
     * print - used in @creating stage of BattleShip;
     * printForOthePlayer - used in @gaming stage of BattleShip;
     * @param board selected gamer board;
     * @param gamer name;
     */
    public static void print2(Point[][] board, String gamer){
    //public void print() {
        System.out.print("------------{ " + gamer + " }------------\n");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|" + (i) + "\t");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(toChar(board[i][j].getType()) + "  ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.print("--------------{ end }--------------\n");
    }
    public static void print(Point[][] board, String gamer){
    //public void print() {
        String vl = "";
        String temp = "";
        int length = 0;
        for (int i = 0; i < board.length; i++) {
            vl = vl +  "|" + (i) + "\t";
            for (int j = 0; j < board[i].length; j++) {
                vl += toChar(board[i][j].getType()) + "  ";
            }
            vl += "|";
            if (i==0){
                length = vl.length();
            }
            vl += "\n";
        }
        if(length != 0){
            vl = createDownLine('-', length, gamer) + vl + createDownLine('-', length, "end");
        }
        System.out.println(vl);
    }
    public static void printForOthePlayer(Point[][] board, String gamer){
        System.out.print("<<<<<<<<<<<<{ "+gamer+" }>>>>>>>>>>>>\n");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|" + (i) + "\t");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(toCharGame(board[i][j].getType()) + "  ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.print("<<<<<<<<<<<<<<{ end }>>>>>>>>>>>>>>\n");
    }
    private static String createDownLine(char symbol, int length, String text){
        int tempLength = length - text.length();
        String finalLine="";
        tempLength = (int) tempLength / 2;
        if(2*tempLength < length) tempLength += 1;
        tempLength -= 2; // <{<space>>
        for(int i =0; i<tempLength; i++){
            finalLine += symbol;
        }
        finalLine += "{ ";
        finalLine += text;
        finalLine += " }";
        for(int i =0; i<tempLength; i++){
            finalLine += symbol;
        }
        finalLine += (finalLine.length() < length)? symbol : "";
        return finalLine+"\n";
    }

    /**
     * @param oneCell is one of PointType, witch
     *                would convert to char symbol;
     * @return view symbol for console application
     * depended to PointType
     */
    private static char toChar(PointType oneCell) {
        char symbol = '.';
        switch (oneCell) {
            case Ship:
                symbol = 'S';
                break;
            case Lose:
                symbol = '=';
                break;
            case Shot:
                symbol = '*';
                break;
            case ToSelect:
                symbol = 'O';
                break;
            default:
                symbol = '.';
        }
        return symbol;
    }
    private static char toCharGame(PointType oneCell) {
        char symbol;
        switch (oneCell) {
            case Ship:
                symbol = '.';
                break;
            case Lose:
                symbol = '=';
                break;
            case Shot:
                symbol = 'X';
                break;
            default:
                symbol = '.';
        }
        return symbol;
    }
}
