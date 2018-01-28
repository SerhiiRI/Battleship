package com.company;

import java.util.*;


public class Main {
    /**
     * Procedural method to put ships on the board.
     * Method wrote to simplification reading @main function.
     * @param gboard is a first or second Gamer board.
     */
    private static void gamerFillSelfBoard(Board gboard) {
        Scanner in = new Scanner(System.in);
        String ask = "y";
        //do{
            while(!gboard.isReady()){
                int x = 0, y = 0;
                do {
                    gboard.print();
                    System.out.print("Enter X >> ");
                    y = in.nextInt();
                    System.out.print("Enter Y >> ");
                    x = in.nextInt();
                } while ((x < 0) || (x > 9) || (y < 0) || (y > 9));
                gboard.click(x, y);
            }
            System.out.println("All ships are config.,\n Want to exit? y(yes) or n(no)");
//            try{
//                ask = in.next();
//            }catch (Exception e){
                //ask = "y";
//            }
        //}while(ask.equals("n") || ask.equals("no"));
    }

    /**
     * Serve OnClick function on selected board, in "Game" Status, mode.
     * @param board is a first or second Gamer board.
     */
    private static void shoting(Board board) {
        Scanner input = new Scanner(System.in);
        int x, y;
        do {
            System.out.println("Input to shot: ");
            do {
                System.out.print("Enter X >>");
                y = input.nextInt();
            } while (y > 9 || y < 0);
            do {
                System.out.print("Enter Y >>");
                x = input.nextInt();
            } while (x > 9 || x < 0);
        } while (board.ShotClick(x, y) && !board.isFinal());
    }


    public static void main(String[] args) {
        Board firstGamerBoard = new Board("Gamer 1");
        Board secondGamerBoard = new Board("Gamer 2");
        firstGamerBoard.print();
        secondGamerBoard.print();
        gamerFillSelfBoard(firstGamerBoard);
        gamerFillSelfBoard(secondGamerBoard);
        if (firstGamerBoard.isReady()) firstGamerBoard.changeStatusOfGame(Status.Game);
        if (secondGamerBoard.isReady()) secondGamerBoard.changeStatusOfGame(Status.Game);

        /*firstGamerBoard.ShotClick(0, 0);
        firstGamerBoard.ShotClick(1, 0);
        firstGamerBoard.ShotClick(2, 0);
        firstGamerBoard.ShotClick(3, 0);
        firstGamerBoard.ShotClick(0, 2);
        firstGamerBoard.ShotClick(1, 2);
        firstGamerBoard.ShotClick(2, 2);
        firstGamerBoard.ShotClick(0, 4);
        firstGamerBoard.ShotClick(1, 4);

        secondGamerBoard.ShotClick(0, 0);
        secondGamerBoard.ShotClick(1, 0);
        secondGamerBoard.ShotClick(2, 0);
        secondGamerBoard.ShotClick(3, 0);
        secondGamerBoard.ShotClick(0, 2);
        secondGamerBoard.ShotClick(1, 2);
        secondGamerBoard.ShotClick(2, 2);
        secondGamerBoard.ShotClick(0, 4);
        secondGamerBoard.ShotClick(1, 4);*/

        int selector = 0;
        while (!firstGamerBoard.isFinal() && !secondGamerBoard.isFinal()) {
            if (selector % 2 == 0) {
                System.out.println(firstGamerBoard.getGamer() + " controller");
                shoting(secondGamerBoard);
                selector = 1;
            } else {
                System.out.println(secondGamerBoard.getGamer() + " controller");
                shoting(firstGamerBoard);
                selector = 2;
            }
        }
        firstGamerBoard.print();
        secondGamerBoard.print();
        if (selector == 1) {
            System.out.println("First gamer win!!!");
        } else {
            System.out.println("Second gamer win!!!");
        }
    }
}