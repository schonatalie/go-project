import java.util.Scanner;

public class golab {

    static String[][] goBoard = new String[9][9];

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        boolean player1 = true;
        boolean playing = true;
        int moveX, moveY;

        System.out.print("Welcome to the game of Go! \nPlease select from the options below: \n");
        System.out.println("Type 'P' to Play");
        System.out.println("Type 'Q' to Quit");

        String input = scn.nextLine().toUpperCase();

        switch (input) {
            case "P":
                System.out.println("Have fun!");
                break;
            case "Q":
                System.out.println("Exiting... :(");
                playing = false;
                break;
            default:
                System.out.println("Not an option...");
                playing = false;
        }

        while (playing) {

            System.out.println("  0 1 2 3 4 5 6 7 8");
            for (int i = 0; i < goBoard[0].length; i++) {
                System.out.print((i) + " ");
                for (int j = 0; j < goBoard.length; j++) {
                    if (goBoard[i][j] == null) {
                        System.out.print((j == 0) ? "|" : "-|");
                    } else {
                        System.out.print(goBoard[i][j]);
                    }
                }
                System.out.println();
            }

            System.out.println("Enter x coord (0-8):");
            moveX = scn.nextInt();
            System.out.println("Enter y coord (0-8):");
            moveY = scn.nextInt();
            scn.nextLine();


            //check if the move is within bounds
            if (moveX < 0 || moveX >= 9 || moveY < 0 || moveY >= 9) {
                System.out.println("Invalid move, try again.");
                continue;
            }

            //check if spot is actually free and playable
            if (goBoard[moveY][moveX] != null && !goBoard[moveY][moveX].equals("-|")) {
                System.out.println("That spot is already taken! Try again.");
                continue;
            }

            //placing the piece
            goBoard[moveY][moveX] = (player1) ? "o" : "x";
            player1 = !player1;
        }

        scn.close(); 
    }
}
