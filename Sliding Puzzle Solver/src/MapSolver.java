// IIT Student_ID : 20220253
// UOW ID : w1953179
// Name : Serasingha Kuruppu Arachchige Dinusha Anupama

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class MapSolver {
   private char[][] grid;
    private int numRows;
    private int numCols;
    private int startX;                                                                  // define variables
    private int startY;
    private int finishX;
    private int finishY;
    private List<int[]> rocks;                                                           // List to store rock positions

    public void setCell(int row, int col, char symbol) {
        if (row >= 0  && col >= 0 ) {
            grid[row][col] = symbol;
        }
    }
    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getStartX() {
        return startX+1;
    }

    public int getStartY() {
        return startY+1;
    }

    public int getFinishX() {
        return finishX+1;
    }

    public int getFinishY() {
        return finishY+1;
    }

    public List<int[]> getRocks() {
        return rocks;
    }

    public void parseMapFile(String mapName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(mapName))) {
            String line;
            int row = 0;
            rocks = new ArrayList<>();                                                   // Initialize the list of rocks
            while ((line = reader.readLine()) != null) {
                if (numCols == 0) {
                    numCols = line.length();
                    grid = new char[5000][numCols];                        // Initialize grid with initial capacity 5000
                } else if (line.length() != numCols) {
                    throw new IllegalArgumentException("Inconsistent number of columns in row " + (row + 1) +
                            ": Expected " + numCols + ", Actual " + line.length());
                }
                for (int col = 0; col < numCols; col++) {
                    char symbol = line.charAt(col);
                    setCell(row, col, symbol);                             // Adjust row and col indices to start from 0
                    if (symbol == 'S') {
                        startX = row;
                        startY = col;
                    } else if (symbol == 'F') {
                        finishX = row;
                        finishY = col;
                    } else if (symbol == '0') {
                        rocks.add(new int[]{row+1, col+1});                                             // Add rock position
                    }
                }
                row++;
            }
            numRows = row;
        }
    }

    public char getCell(int row, int col) {
        if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
            return grid[row][col];
        }
        return '.';
    }

    public List<int[]> findShortestPath() {
        boolean[][] visited = new boolean[numRows + 1][numCols + 1];
        int[][] parentX = new int[numRows + 1][numCols + 1];
        int[][] parentY = new int[numRows + 1][numCols + 1];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startX+1, startY+1});
        visited[startX+1][startY+1] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];

            if (x == finishX+1 && y == finishY+1) break;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 1 && nx <= numRows && ny >= 1 && ny <= numCols && !visited[nx][ny] && grid[nx][ny] != '0') {
                    queue.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                    parentX[nx][ny] = x;
                    parentY[nx][ny] = y;
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        int x = finishX+1;
        int y = finishY+1;
        while (x != startX+1 || y != startY+1) {
            path.add(new int[]{x, y});
            int px = parentX[x][y];
            int py = parentY[x][y];
            x = px;
            y = py;
        }
        path.add(new int[]{startX+1, startY+1});
        Collections.reverse(path);
        return path;
    }
}
