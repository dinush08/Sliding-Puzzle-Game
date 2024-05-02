// IIT Student_ID : 20220253
// UOW ID : w1953179
// Name : Serasingha Kuruppu Arachchige Dinusha Anupama

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter map name with .txt extension:");                                   //getting the input
        String mapName = scanner.nextLine();
        MapSolver map = new MapSolver();                                                             //create map object
        try {
            map.parseMapFile(mapName);
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid map file: " + e.getMessage());
            return;
        }

        int numCols = map.getNumCols();
        int numRows = map.getNumRows();

        System.out.println();
        System.out.println("Map Width: " + numCols);                                     //printing the width and height
        System.out.println("Map Height: " + numRows);
        System.out.println();

        System.out.println("Map:");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char cell = map.getCell(i, j);                                                        //printing the map
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();


        System.out.println("Start position: (" + map.getStartX() + ", " + map.getStartY() + ")");
        System.out.println("Finish position: (" + map.getFinishX() + ", " + map.getFinishY() + ")");
        System.out.println();

        System.out.println("Rocks:");
        for (int[] rock : map.getRocks()) {
            System.out.println("(" + rock[0] + ", " + rock[1] + ")");
        }

        List<int[]> path = map.findShortestPath();
        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("\nShortest Path:");                                         //printing the shortest path
            int step = 1;
            for (int[] position : path) {
                System.out.println(step + ". Move to (" + position[0] + ", " + position[1] + ")");
                step++;
            }
            System.out.println("Done!");
        }
    }
}