/**
 * brainstorm
 */


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ALP_GRUDER_S018769_1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter lookahead:");
        int depth = sc.nextInt();

        // create board
        String[][] board = new String[6][7];
        // 7 = column
        // 6 = row
        // fill board with empty sign and print empty board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = "#";
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("game starts");

        int number_of_turns = 0; // number of turns/2 aynı zamanda vertixlerin sayısı. 2 tane matrix oluşturucam,
                                 // oyuncular her oynadığında vertexler güncellens
        boolean isPlayerTurn = true;
        boolean did_someone_win = false;
        // game is played here
        while (number_of_turns < 42) {
            // print board before each turn
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
            if (isPlayerTurn) {
                // player's turn
                System.out.println("select column between 1 and 7");
                int input = sc.nextInt();
                if (input < 1 || input > 7) {
                    System.out.println("illegal move, computer wins");
                    break;

                }
                if (!board[0][input-1].equals("#")) {
                    System.out.println("illegal move, computer wins");
                    break;
                }
                board = put_disc(board, input, "X");
                did_someone_win = check(board, "X");
                if (did_someone_win) {
                    System.out.println("player won");
                    break;
                }

                isPlayerTurn = false;
                number_of_turns++;
                System.out.println("player played");

            } else {
                // computer's turn
                ArrayList<Vertex> graph = createGraph(board, depth);
                int computer_column = traverse(graph.get(0));
                board = put_disc(board, computer_column, "O");
                System.out.println("computer played");
                did_someone_win = check(board, "O");
                if (did_someone_win) {
                    System.out.println("computer won");
                    break;
                }
                isPlayerTurn = true;
                number_of_turns++;
            }
            if (number_of_turns == 42) {
                System.out.println("Draw");
            }

        }

        sc.close();
    }

    static int traverse(Vertex root) {
        int max_score = 0;

        int column = 3; // puts the disk in the middle if no move is optimal
        Stack<Vertex> stack = new Stack<>();
        root.setVisited();
        stack.add(root);

        while (!stack.isEmpty()) {
            Vertex actualVertex = stack.pop();
            // System.out.println(actualVertex);

            for (Vertex vertex : actualVertex.getAdjList()) {
                // System.out.println("visted:" + vertex.getName());
                if (!vertex.visited) {
                    vertex.setVisited();
                    stack.add(vertex);
                    int score = 0;
                    score = evaluate(vertex);

                    if (score >= max_score) {
                        max_score = score;
                        column = vertex.getColumn();
                        // System.out.println("column: " + column);
                        // System.out.println(score);
                    }
                }
            }
        }
        return column;
    }

    static boolean check(String[][] board, String player) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final String EMPTY_SLOT = "#"; // bunu methodu çağırırken almam lazım
        for (int r = 0; r < HEIGHT; r++) { // iterate rows, bottom to top
            for (int c = 0; c < WIDTH; c++) { // iterate columns, left to right
                if (player.equals(EMPTY_SLOT))
                    continue; // don't check empty slots

                if (c + 3 < WIDTH &&
                        player == board[r][c + 1] && // look right
                        player == board[r][c + 2] &&
                        player == board[r][c + 3] &&
                        player == board[r][c])
                    return true;
                if (r + 3 < HEIGHT) {
                    if (player == board[r + 1][c] && // look up
                            player == board[r + 2][c] &&
                            player == board[r + 3][c] &&
                            player == board[r][c])
                        return true;
                    if (c + 3 < WIDTH &&
                            player == board[r + 1][c + 1] && // look up & right
                            player == board[r + 2][c + 2] &&
                            player == board[r + 3][c + 3] &&
                            player == board[r][c])
                        return true;
                    if (c - 3 >= 0 &&
                            player == board[r + 1][c - 1] && // look up & left
                            player == board[r + 2][c - 2] &&
                            player == board[r + 3][c - 3] &&
                            player == board[r][c])
                        return true;
                }
            }
        }
        return false; // no winner found
    }

    static boolean check3(String[][] board, String player) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final String EMPTY_SLOT = "#"; // bunu methodu çağırırken almam lazım
        for (int r = 0; r < HEIGHT; r++) { // iterate rows, bottom to top
            for (int c = 0; c < WIDTH; c++) { // iterate columns, left to right
                if (player.equals(EMPTY_SLOT))
                    continue; // don't check empty slots

                if (c + 3 < WIDTH &&
                        player == board[r][c + 1] && // look right
                        player == board[r][c + 2] &&
                        player == board[r][c])
                    return true;
                if (r + 3 < HEIGHT) {
                    if (player == board[r + 1][c] && // look up
                            player == board[r + 2][c] &&
                            player == board[r][c])
                        return true;
                    if (c + 3 < WIDTH &&
                            player == board[r + 1][c + 1] && // look up & right
                            player == board[r + 2][c + 2] &&
                            player == board[r][c])
                        return true;
                    if (c - 3 >= 0 &&
                            player == board[r + 1][c - 1] && // look up & left
                            player == board[r + 2][c - 2] &&
                            player == board[r][c])
                        return true;
                }
            }
        }
        return false; // no winner found
    }

    static boolean check2(String[][] board, String player) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final String EMPTY_SLOT = "#"; // bunu methodu çağırırken almam lazım
        for (int r = 0; r < HEIGHT; r++) { // iterate rows, bottom to top
            for (int c = 0; c < WIDTH; c++) { // iterate columns, left to right
                if (player.equals(EMPTY_SLOT))
                    continue; // don't check empty slots

                if (c + 3 < WIDTH &&
                        player == board[r][c + 1] && // look right
                        player == board[r][c])
                    return true;
                if (r + 3 < HEIGHT) {
                    if (player == board[r + 1][c] && // look up
                            player == board[r][c])
                        return true;
                    if (c + 3 < WIDTH &&
                            player == board[r + 1][c + 1] && // look up & right
                            player == board[r][c])
                        return true;
                    if (c - 3 >= 0 &&
                            player == board[r + 1][c - 1] && // look up & left
                            player == board[r][c])
                        return true;
                }
            }
        }
        return false; // no winner found
    }

    static int evaluate(Vertex vertex) {
        int score = 0;
        boolean win = check(vertex.getCurrentState(), "0");
        boolean lose = check(vertex.getCurrentState(), "X");
        if (win && !lose) {
            return 1000;
        } else if (lose && !win) {
            return -1000;
        }else if (lose){
            vertex.tString();
            return 1000;}

        boolean win3 = check3(vertex.getCurrentState(), "O");
        boolean lose3 = check3(vertex.getCurrentState(), "X");

        if (win3) {
            score += 50;
        } else if (lose3) {
            score += 60;
        }

        boolean win2 = check2(vertex.getCurrentState(), "O");
        boolean lose2 = check2(vertex.getCurrentState(), "X");

        if (win2) {
            score += 20;
        } else if (lose2) {
            score -= 5;
        }
        return score;
    }

    static String[][] put_disc(String[][] bboard, int input, String player) {
        int row_count = 5;
        while (row_count >= 0) {
            if (bboard[row_count][input - 1].equals("#")) {
                bboard[row_count][input - 1] = player;
                break;
            } else
                row_count--;
        }

        return bboard;
    }

    static class Vertex {
        int ID;
        int layer;
        int column;
        boolean visited;
        ArrayList<Vertex> adjList = new ArrayList<Vertex>();
        String[][] currentState = new String[6][7];

        public Vertex(int ID, int layer, String[][] board) {
            this.ID = ID;
            this.layer = layer;
            visited = false;

            for (int a = 0; a < board.length; a++) {
                for (int b = 0; b < board[a].length; b++) {
                    currentState[a][b] = board[a][b];
                }
            }

        }

        public int getLayer() {
            return layer;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String[][] getCurrentState() {
            return currentState;
        }
        public void addNeighbour(Vertex vertex) {
            adjList.add(vertex);
        }

        public ArrayList<Vertex> getAdjList() {
            return adjList;
        }

        public void setVisited() {
            visited = true;
        }
        public void tString() {
            for (int i = 0; i < currentState.length; i++) {
                for (int j = 0; j < currentState[i].length; j++) {
                    System.out.print(currentState[i][j]);}
                }
        }
    }

    static ArrayList<Vertex> createGraph(String[][] board, int lookahead) {
        ArrayList<Vertex> Graph = new ArrayList<Vertex>();
        int counter = 0;
        int column_count = 1;

        Vertex initial = new Vertex(0, 0, board);
        Graph.add(initial);

        for (int i = 1; i <= lookahead; i++) {
            for (int j = 0; j < Math.pow(7, i); j++) {
                System.out.println(column_count);
                counter++;
                int index = (counter - 1) / 7;
                String[][] tempBoard = new String[6][7];

                for (int a = 0; a < board.length; a++) {
                    for (int b = 0; b < board[a].length; b++) {
                        tempBoard[a][b] = board[a][b];
                    }
                }

                if (tempBoard[0][column_count-1].equals("#")) {
                    
                    if (i % 2 == 0)
                        tempBoard = put_disc(tempBoard, column_count, "X");
                    else
                        tempBoard = put_disc(tempBoard, column_count, "O");

                    column_count++;
                    if (column_count ==  8) {
                        column_count = 1;
                    }

                    Vertex a = new Vertex(j, i, tempBoard);
                    Graph.add(a);

                    Graph.get(index).addNeighbour(a);

                    int column = counter;
                    while (column > 7) {
                        column = (column - 1) / 7;
                    }
                    a.setColumn(column);
                }
                else
                    column_count++;
                    if (column_count ==  8) {
                        column_count = 1;
                    }
            }
        }

        return Graph;
    }

}
