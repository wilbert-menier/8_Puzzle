import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    private int[][] tiles;
    private int n;
    private int hamming;
    private int manhattan;
    private int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
        int p = tiles[0].length, k, h;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < p; j++) {
                if (tiles[i][j] == 0)
                    blankPos = (p * i) + (j + 1);
                else {
                    k = tiles[i][j] - 1;
                    manhattan += (Math.abs((k / p) - i) + Math.abs((k % p) - j));
                    h = p * i + (j + 1); // row-major order of i
                    if (tiles[i][j] != h)
                        hamming += 1;
                }
            }
        }
        
    }

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return  manhattan == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int[] a = new int[n * n - 1];
        int k, p = tiles[0].length, v = 0;
        long inv;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < p; j++){
                k = p * i + j;
                if (tiles[i][j] == 0){
                    v = 1;
                }
                else if (v > 0)
                    a[k - 1] = tiles[i][j];
                else
                    a[k] = tiles[i][j];}
        }
        inv = Inversions.count(a);
        int i = (blankPos - 1) / p;
        long sum = i + inv;
        if (tiles.length % 2 != 0)
            return inv % 2 == 0;
        else{
            return sum % 2 != 0;
        }

    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<>();
        int[][] a;
        int n = tiles[0].length, o = tiles.length;
        int k = blankPos - 1, i = k / n, j = k % n; // gets i and j index of blankPos
        if (i != o - 1){
            a = cloneTiles();
            int temp = a[i][j];
            a[i][j] = a[i + 1][j];
            a[i + 1][j] = temp;
            Board b = new Board(a);
            q.enqueue(b);
        }
        if (i != 0){
            a = cloneTiles();
            int temp = a[i][j];
            a[i][j] = a[i - 1][j];
            a[i - 1][j] = temp;
            Board b = new Board(a);
            q.enqueue(b);
        }
        if (j != n - 1){
            a = cloneTiles();
            int temp = a[i][j];
            a[i][j] = a[i][j + 1];
            a[i][j + 1] = temp;
            Board b = new Board(a);
            q.enqueue(b);
        }

        if (j != 0){
            a = cloneTiles();
            int temp = a[i][j];
            a[i][j] = a[i][j - 1];
            a[i][j - 1] = temp;
            Board b = new Board(a);
            q.enqueue(b);
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.tiles == ((Board) other).tiles;
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
