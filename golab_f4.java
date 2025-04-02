import java.util.Scanner;

public class golab_f4 {

    static String[][] goBoard = new String[9][9];
    static final String EMPTY = "+"; // using "+" for empty spaces b/c -| shifts around too much
    static final String Black = "o";
    static final String White = "x";

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        boolean player1 = true;
        boolean playing = true;
        int moveX, moveY;

        System.out.print("**Welcome to the game of Go!** \nPlease select from the options below: \n");
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

        initializeBoard();

        while (playing) {

            printBoard();

            System.out.println("Enter x coord (0-8):");
            moveX = scn.nextInt();
            System.out.println("Enter y coord (0-8):");
            moveY = scn.nextInt();
            scn.nextLine();

            //check if the move is within bounds
            if (moveX < 0 || moveX >= 9 || moveY < 0 || moveY >= 9) {
                System.out.println("Out of bounds! Try again.");
                break;
            }

            //check if spot is open
            if (!goBoard[moveY][moveX].equals(EMPTY)) {
                System.out.println("That spot is already taken! Try again.");
                continue;
            }

            //placing the piece
            String currentPlayer = player1 ? Black : White;
            goBoard[moveY][moveX] = currentPlayer;

            //check for captures
            checkCapture(moveX, moveY, currentPlayer);

            //switch players
            player1 = !player1;

        }

        scn.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < goBoard.length; i++) {
            for (int j = 0; j < goBoard[i].length; j++) {
                goBoard[i][j] = EMPTY;  //initialize all spaces as "+"
            }
        }
    }

    private static void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        for (int i = 0; i < goBoard.length; i++) {
            System.out.print((i) + " ");
            for (int j = 0; j < goBoard[i].length; j++) {
                System.out.print(goBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void checkCapture(int x, int y, String currentPlayer) {
        String opponent = currentPlayer.equals(Black) ? White : Black; //checks who is the opponent

        //checks in all four directions for captures
        checkGroupLiberties(x + 1, y, opponent);
        checkGroupLiberties(x - 1, y, opponent);
        checkGroupLiberties(x, y + 1, opponent);
        checkGroupLiberties(x, y - 1, opponent);
    }

    private static void checkGroupLiberties(int x, int y, String player) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9 || !goBoard[y][x].equals(player)) {
            return; //out of bounds or not the player's stone
        }

        boolean[][] visited = new boolean[9][9]; 
        if (!hasLiberties(x, y, player, visited)) {
            removeGroup(x, y, player); // removes the group if it has no liberties
        }
    }

    private static boolean hasLiberties(int x, int y, String player, boolean[][] visited) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9 || visited[y][x]) {
            return false; // out of bounds or already checked this spot
        }

        if (goBoard[y][x].equals(EMPTY)) {
            return true; // liberty has been found since spot is empty
        }

        if (!goBoard[y][x].equals(player)) {
            return false; // not the player's stone
        }

        visited[y][x] = true; //marks this stone as visited so it doesn't get checked again

        //checks all four directions using the function again (recursion)
        return hasLiberties(x + 1, y, player, visited) ||
               hasLiberties(x - 1, y, player, visited) ||
               hasLiberties(x, y + 1, player, visited) ||
               hasLiberties(x, y - 1, player, visited);
    }

    private static void removeGroup(int x, int y, String player) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9 || !goBoard[y][x].equals(player)) {
            return; // out of bounds or not the player's stone
        }

        goBoard[y][x] = EMPTY; // removes the stone

        //removes the entire group
        removeGroup(x + 1, y, player);
        removeGroup(x - 1, y, player);
        removeGroup(x, y + 1, player);
        removeGroup(x, y - 1, player);
    }

}