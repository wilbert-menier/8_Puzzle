import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    private int moves;
    private LinkedStack<Board> solution;

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board initial){
        if(initial == null)
            throw new NullPointerException("board is null");
        else if(!initial.isSolvable())
            throw new IllegalArgumentException("board is unsolvable");
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode b = new SearchNode(initial, 0, null);
        pq.insert(b);
        SearchNode node;
        solution = new LinkedStack<>();
        while (!pq.isEmpty()){
            if(initial.isGoal()){
                solution.push(initial);
                break;
            }
            node = pq.delMin();
            if(node.board.isGoal()){
                moves = node.moves;
                solution.push(node.board);
                SearchNode newprev = node.previous;
                while (newprev.previous != null){
                    solution.push(newprev.board);
                    newprev = newprev.previous;


                }
                break;
            }
            else{
                SearchNode prev = new SearchNode(node.board, node.moves, node.previous); // this line is redundant but it helps with tracing.
                for (Board neighbor : node.board.neighbors()){
                    if (!neighbor.equals(prev.board)){
                        pq.insert(new SearchNode(neighbor, prev.moves + 1, prev));
                    }
                }
            }
        }
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        return solution;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;

        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            int sum1 = board.manhattan() + moves;
            int sum2 = other.board.manhattan() + other.moves;
            return sum1 - sum2;
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
