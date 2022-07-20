import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(in);
        out.println("Please enter in the range 3 to 7 included");
        byte gridSize = sc.nextByte();

        while(gridSize < 3 || gridSize > 7){
            out.println("Please enter in the range 3 to 7 included");
            gridSize = sc.nextByte();
        }

        Character[][] gameBoard = new Character[gridSize][gridSize];
        for (byte i = 0; i < gridSize; i++) {
            for (byte j = 0; j < gridSize; j++) {
                gameBoard[i][j] = '.';
            }
        }

        out.println("Let's start the game!");
        out.println("---------------------------------------");

        Random rd = new Random();

        int thePlayerNum = rd.nextInt(2);
        String thePlayer = thePlayerNum == 1 ? "RealPlayer" : "Computer";

        byte realPlayerPoints = 0;
        byte computerPoints = 0;

        byte matchFlag = 0;

        List<PositionPair> positionList = new LinkedList<>();
        Integer[][] positionArr = new Integer[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                positionList.add(new PositionPair(i, j));
            }
        }

        while (Arrays.stream(gameBoard)
                .flatMap(Stream::of)
                .anyMatch(cell -> cell == '.')) {

            out.println("The Real Player's current points: " + realPlayerPoints);
            out.println("The Computer's current points: " + computerPoints);

            out.print("  ");
            IntStream.range(1, gridSize + 1).forEach(k -> out.print(" " + k));
            out.println();
            for (byte i = 0; i < gridSize; i++) {
                out.print((i + 1) + "  ");
                for (byte j = 0; j < gridSize; j++) {
                    out.print(gameBoard[i][j] + " ");
                }
                out.println();
            }

            out.println();
            out.println("---------------------------------------");

            int theLetterNum = rd.nextInt(2);
            char theLetter = theLetterNum == 1 ? 'S' : 'O';


            if (thePlayer.equals("RealPlayer")) {

                out.println("The player is the real player and its letter is " + theLetter);
                out.println("Please choose where to place your letter!");

                byte column;
                byte row;

                while (true) {

                    out.println();
                    out.println("Please enter the column num: ");
                    column = (byte) (sc.nextByte() - 1);
                    while (column > gridSize || column < -1) {
                        out.println("Please enter a valid column num!");
                        column = (byte) (sc.nextByte() - 1);
                    }

                    out.println("Please enter the row num: ");
                    row = (byte) (sc.nextByte() - 1);
                    while (row > gridSize || row < -1) {
                        out.println("Please enter a valid row num!");
                        row = (byte) (sc.nextByte() - 1);
                    }

                    if (gameBoard[row][column].equals('.')) {
                        break;
                    }

                    out.println();
                    out.println("Please place the letter in a blank space!");
                }

                positionList.remove(new PositionPair(row, column));
                gameBoard[row][column] = theLetter;

                byte allSosMatchesCount = traverseAndFindSosMatches(gameBoard);
                if(matchFlag != allSosMatchesCount){
                    realPlayerPoints += (allSosMatchesCount - matchFlag);
                    out.println("The Real Player's got " +
                            (((allSosMatchesCount - matchFlag) == 1) ? "1 point!" :
                                    (allSosMatchesCount - matchFlag) +  " points!"));
                    matchFlag = allSosMatchesCount;
                    out.println();
                }


                thePlayer = "Computer";
                continue;

            }

            if (thePlayer.equals("Computer")) {

                out.println("The player is the computer and its letter is " + theLetter);

                Thread.sleep(2500);

                int randomRowColumnIndex = rd.nextInt(positionList.size());
                PositionPair positionPair = positionList.remove(randomRowColumnIndex);

                gameBoard[positionPair.x][positionPair.y] = theLetter;

                byte allSosMatchesCount = traverseAndFindSosMatches(gameBoard);
                if(matchFlag != allSosMatchesCount){
                    computerPoints += (allSosMatchesCount - matchFlag);
                    out.println("The Computer's got " +
                            (((allSosMatchesCount - matchFlag) == 1) ? " 1 point!" :
                                    (allSosMatchesCount - matchFlag) +  " points!"));
                    matchFlag = allSosMatchesCount;
                    out.println();
                }

                thePlayer = "RealPlayer";

            }

        }

        out.print("  ");
        IntStream.range(1, gridSize + 1).forEach(k -> out.print(" " + k));
        out.println();
        for (byte i = 0; i < gridSize; i++) {
            out.print((i + 1) + "  ");
            for (byte j = 0; j < gridSize; j++) {
                out.print(gameBoard[i][j] + " ");
            }
            out.println();
        }

        out.println();
        out.println("---------------------------------------");
        out.println();

        if(realPlayerPoints > computerPoints)
            out.println("The Real Player won the game!");
        if(realPlayerPoints < computerPoints)
            out.println("The Computer won the game!");
        if(realPlayerPoints == computerPoints)
            out.println("Draw!");


        out.println("The Real Player's total points: " + realPlayerPoints);
        out.println("The Computer's total points: " + computerPoints);

    }


    static byte traverseAndFindSosMatches(Character[][] gameBoard){

        byte numOfSosMatches = 0;

        //column traversal
        for (int i = 0; i < gameBoard.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < gameBoard.length; j++) {
                stringBuilder.append(gameBoard[i][j]);
            }
            if (stringBuilder.toString().contains("SOS")) {
                numOfSosMatches++;
            }
        }

        //row traversal
        for (int i = 0; i < gameBoard.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < gameBoard.length; j++) {
                stringBuilder.append(gameBoard[j][i]);
            }
            if (stringBuilder.toString().contains("SOS")) {
                numOfSosMatches++;
            }
        }

        //diagonal traversal
        for (int k = 0; k <= 2 * (gameBoard.length - 1); ++k) {
            int yMin = Math.max(0, k - gameBoard.length + 1);
            int yMax = Math.min(gameBoard.length - 1, k);
            StringBuilder stringBuilder = new StringBuilder();
            for (int y = yMin; y <= yMax; ++y) {
                int x = k - y;
                stringBuilder.append(gameBoard[y][x]);
            }
            if (stringBuilder.toString().contains("SOS")) {
                numOfSosMatches++;
            }
        }

        //reverse-diagonal traversal
        for (int i = 0, j = gameBoard.length - 1; i < gameBoard.length && j >= 0; i++, j--) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int k = 0, l = j; k <= i && l < gameBoard.length; k++, l++){
                stringBuilder.append(gameBoard[k][l]);
            }
            if (stringBuilder.toString().contains("SOS")) {
                numOfSosMatches++;
            }
        }
        for (int i = 1, j = gameBoard.length - 2; i < gameBoard.length && j >= 0; i++, j--) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int k = i, l = 0; k < gameBoard.length && l <= j; k++, l++){
                stringBuilder.append(gameBoard[k][l]);
            }
            if (stringBuilder.toString().contains("SOS")) {
                numOfSosMatches++;
            }
        }

        return numOfSosMatches;
    }



}
