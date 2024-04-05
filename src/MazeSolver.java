/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;


public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */

    public ArrayList<MazeCell> getSolution() {
        ArrayList<MazeCell> path = new ArrayList<>();
        MazeCell current = maze.getEndCell();

        while (current != null) {
            // Insert at the beginning of the list
            path.add(0, current);
            current = current.getParent();
        }

        return path;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        maze.reset(); // reset maze

        Stack<MazeCell> stack = new Stack<>();
        stack.push(maze.getStartCell()); // Initiate DFS with start cell.
        maze.getStartCell().setExplored(true); // Mark start as explored to prevent revisiting.

        while (!stack.isEmpty()) {
            MazeCell current = stack.pop(); // Remove and explore the top cell on the stack.

            if (current.equals(maze.getEndCell())) {
                return getSolution(); // End cell reached; backtrack to construct path.
            }

            // Iterate over unvisited, accessible neighbors.
            for (MazeCell neighbor : maze.getNeighbors(current)) {
                if (maze.isValidCell(neighbor.getRow(), neighbor.getCol())) { // Check if neighbor is a viable next step.
                    neighbor.setExplored(true); // Mark as explored to avoid cycles.
                    neighbor.setParent(current);
                    stack.push(neighbor); // Add to stack for next exploration.
                }
            }
        }

        return new ArrayList<>(); // Return an empty list if no path to end cell is found.


    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Reset maze for BFS
        maze.reset();

        Queue<MazeCell> queue = new LinkedList<>();
        maze.getStartCell().setExplored(true); // Mark start cell as explored
        queue.add(maze.getStartCell());

        while (!queue.isEmpty()) {
            MazeCell current = queue.remove(); // Explore cells in FIFO order.

            if (current == maze.getEndCell()) {
                return getSolution(); // Found the end; build and return the path.
            }

            // Check each accessible, unvisited neighbor of the current cell.
            for (MazeCell neighbor : maze.getNeighbors(current)) {
                if (maze.isValidCell(neighbor.getRow(), neighbor.getCol()) && !neighbor.isExplored()) { // Validate neighbor for exploration.
                    neighbor.setExplored(true); // Mark neighbor as explored to prevent cycles.
                    neighbor.setParent(current);
                    queue.add(neighbor); // add neighbor for future exploration.
                }
            }
        }

        return new ArrayList<>(); // Return an empty list if no path

    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
