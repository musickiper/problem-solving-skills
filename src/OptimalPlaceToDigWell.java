import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class OptimalPlaceToDigWell {
    public static void main(String[] args) {
        // [X][Y]
//        int[][] map = new int[][]{
//                {0, 1000, -1, 1000},
//                {1000, 1000, 1000, 1000},
//                {1000, 0, 0, 1000},
//                {1000, -1, 0, 1000},
//                {0, 0, 1000, -1}
//        };
        int[][] map = new int[][]{
                {1000,1000,1000,1000,1000,1000},
                {1000,1000,1000,1000,-1,1000},
                {1000,-1,1000,1000,1000,1000},
                {1000,1000,1000,1000,1000,1000},
                {1000,1000,1000,1000,-1,1000},
                {1000,1000,1000,1000,1000,1000},
        };

        System.out.println(Arrays.toString(findOptimalPlace(map)));
    }


    private static int[] findOptimalPlace(int[][] map) {
        ArrayList<int[]> houseLocations = findHouseLocations(map); // Find houses' locations
        int[][] optimizedMapForHouses = new int[map.length][map[0].length]; // Each house's optimized map
        int[][] optimizedMapForAHouse = new int[map.length][map[0].length]; // All houses' optimized map
        copyTwoDiArray(map, optimizedMapForAHouse); // Copy original map to optimizedMapForAHouse
        boolean[][] visited = new boolean[map.length][map[0].length];


        for (int[] houseLocation : houseLocations) {
            fillAdjecantAreas(optimizedMapForAHouse, houseLocation); // Fill all adjacent areas of house location with 1
            visited[houseLocation[0]][houseLocation[1]] = true; // Check house location as visited location
            // When the starting direction is up of house location
            optimizeMapForAHouse(optimizedMapForAHouse, visited, houseLocation[0] + 1, houseLocation[1]);
            visited = new boolean[optimizedMapForAHouse.length][optimizedMapForAHouse[0].length];
            // When the starting direction is down of house location
            optimizeMapForAHouse(optimizedMapForAHouse, visited, houseLocation[0] - 1, houseLocation[1]);
            visited = new boolean[optimizedMapForAHouse.length][optimizedMapForAHouse[0].length];
            // When the starting direction is right of house location
            optimizeMapForAHouse(optimizedMapForAHouse, visited, houseLocation[0], houseLocation[1] + 1);
            visited = new boolean[optimizedMapForAHouse.length][optimizedMapForAHouse[0].length];
            // When the starting direction is left of house location
            optimizeMapForAHouse(optimizedMapForAHouse, visited, houseLocation[0], houseLocation[1] - 1);
            visited = new boolean[optimizedMapForAHouse.length][optimizedMapForAHouse[0].length];
            System.out.println(Arrays.deepToString(optimizedMapForAHouse));

            // Accumulate each optimizedMapForAHouse to optimizedMapForHouses
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map[0].length; y++) {
                    optimizedMapForHouses[x][y] += optimizedMapForAHouse[x][y];
                }
            }
            // Reset optimizedMapForAHouse to use it for the next house
            copyTwoDiArray(map, optimizedMapForAHouse);
        }

        // Find OptimalPlace from optimizedMapForHouses
        return findOptimalPlaceFromTwoDiArray(optimizedMapForHouses);
    }

    // Find location (x,y) of houses in map
    private static ArrayList<int[]> findHouseLocations(int[][] map) {
        ArrayList<int[]> locations = new ArrayList<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == -1) {
                    locations.add(new int[]{x, y});
                }
            }
        }
        return locations;
    }

    // Copy two di array from A to B
    private static void copyTwoDiArray(int[][] from, int[][] to) {
        for (int x = 0; x < from.length; x++) {
            for (int y = 0; y < from[0].length; y++) {
                to[x][y] = from[x][y];
            }
        }
    }

    // Fill adjacents of a location with 1
    private static void fillAdjecantAreas(int[][] map, int[] location) {
        int x = location[0];
        int y = location[1];

        // up adjacent of the location
        if (x - 1 >= 0 && map[x - 1][y] != 0 && map[x - 1][y] != -1) {
            map[x - 1][y] = 1;
        }
        // down adjacent of the location
        if (x + 1 < map.length && map[x + 1][y] != 0 && map[x + 1][y] != -1) {
            map[x + 1][y] = 1;
        }
        // left adjacent of the location
        if (y - 1 >= 0 && map[x][y - 1] != 0 && map[x][y - 1] != -1) {
            map[x][y - 1] = 1;
        }
        // right adjacent of the location
        if (y + 1 < map[0].length && map[x][y + 1] != 0 && map[x][y + 1] != -1) {
            map[x][y + 1] = 1;
        }
    }

    // Optimize original map for a house
    private static void optimizeMapForAHouse(int[][] map, boolean[][] visited, int x, int y) {
        // Base case
        if (x < 0 || x > map.length - 1 || y < 0 || y > map[0].length - 1 || map[x][y] == 0 || map[x][y] == -1 || visited[x][y]) {
            return;
        }

        visited[x][y] = true;

        // Find the minimum adjacent value
        int minAdjacent = findMinAdjacent(map, x, y);
        if (minAdjacent < map[x][y]) {
            map[x][y] = minAdjacent + 1;
        }

        optimizeMapForAHouse(map, visited, x + 1, y);
        optimizeMapForAHouse(map, visited, x - 1, y);
        optimizeMapForAHouse(map, visited, x, y + 1);
        optimizeMapForAHouse(map, visited, x, y - 1);
    }

    // Find Minimum adjacent value of the location
    private static int findMinAdjacent(int[][] map, int x, int y) {
        ArrayList<Integer> list = new ArrayList<>();
        // Check up
        if (x - 1 >= 0 && map[x - 1][y] != -1 && map[x - 1][y] != 0) {
            list.add(map[x - 1][y]);
        }
        // Check down
        if (x + 1 <= map.length - 1 && map[x + 1][y] != -1 && map[x + 1][y] != 0) {
            list.add(map[x + 1][y]);
        }
        // Check left
        if (y - 1 >= 0 && map[x][y - 1] != -1 && map[x][y - 1] != 0) {
            list.add(map[x][y - 1]);
        }
        // Check right
        if (y + 1 <= map[0].length - 1 && map[x][y + 1] != -1 && map[x][y + 1] != 0) {
            list.add(map[x][y + 1]);
        }
        Collections.sort(list);
        return list.size() == 0 ? 1000 : list.get(0);
    }

    private static int[] findOptimalPlaceFromTwoDiArray(int[][] map) {
        int x = -1;
        int y = -1;
        int v = Integer.MAX_VALUE;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0 && map[i][j] < v) {
                    v = map[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        return new int[]{x, y};
    }
}
