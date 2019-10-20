import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FindMaxHeight {
    public static void main(String[] args) {
        boolean[][] landscape = new boolean[][]{
                {true, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, true}
        };

        // Q1.
        ArrayList<int[]> highestLocations = findMaxHeight(landscape); // [[x,y,max],...]
        for (int[] highestLocation : highestLocations) {
            System.out.println("x: " + highestLocation[0] + ", y: " + highestLocation[1] + ", peak: " + highestLocation[2]);
        }

        // Q2.
        int[] location = findTheLocation(highestLocations, landscape); // [x,y,count]
        System.out.println("x: " + location[0] + ", y: " + location[1] + ", count: " + location[2]);
    }

    private static ArrayList<int[]> findMaxHeight(boolean[][] landscape) {
        int[][] optimizedLandscape = new int[landscape.length][landscape[0].length];
        boolean[][] visited = new boolean[landscape.length][landscape[0].length];
        ArrayList<int[]> seaLocations = new ArrayList<>();

        for (int x = 0; x < landscape.length; x++) {
            for (int y = 0; y < landscape[0].length; y++) {
                optimizedLandscape[x][y] = landscape[x][y] ? 0 : 1000;
                if (optimizedLandscape[x][y] == 0) {
                    seaLocations.add(new int[]{x, y});
                }
            }
        }

        for (int[] seaLocation : seaLocations) {
            fillAdjecantAreas(optimizedLandscape, seaLocation); // Fill all adjacent areas of sea location with 1

            visited[seaLocation[0]][seaLocation[1]] = true; // Check house location as visited location
            // When the starting direction is up of house location
            optimizeLandscape(optimizedLandscape, visited, seaLocation[0] + 1, seaLocation[1]);
            visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
            // When the starting direction is down of house location
            optimizeLandscape(optimizedLandscape, visited, seaLocation[0] - 1, seaLocation[1]);
            visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
            // When the starting direction is right of house location
            optimizeLandscape(optimizedLandscape, visited, seaLocation[0], seaLocation[1] + 1);
            visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
            // When the starting direction is left of house location
            optimizeLandscape(optimizedLandscape, visited, seaLocation[0], seaLocation[1] - 1);
            visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
        }

        return findHighestLocations(optimizedLandscape);
    }

    // Fill adjacents of a location with 1
    private static void fillAdjecantAreas(int[][] map, int[] location) {
        int x = location[0];
        int y = location[1];

        // up adjacent of the location
        if (x - 1 >= 0 && map[x - 1][y] != 0) {
            map[x - 1][y] = 1;
        }
        // down adjacent of the location
        if (x + 1 < map.length && map[x + 1][y] != 0) {
            map[x + 1][y] = 1;
        }
        // left adjacent of the location
        if (y - 1 >= 0 && map[x][y - 1] != 0) {
            map[x][y - 1] = 1;
        }
        // right adjacent of the location
        if (y + 1 < map[0].length && map[x][y + 1] != 0) {
            map[x][y + 1] = 1;
        }
    }

    private static void optimizeLandscape(int[][] landscape, boolean[][] visited, int x, int y) {
        if (x < 0 || x > landscape.length - 1 || y < 0 || y > landscape[0].length - 1 || landscape[x][y] == 0 || visited[x][y]) {
            return;
        }

        int minAdjacent = findMinAdjacent(landscape, x, y);
        if (minAdjacent < landscape[x][y]) {
            landscape[x][y] = minAdjacent + 1;
        }

        visited[x][y] = true;
        optimizeLandscape(landscape, visited, x + 1, y);
        optimizeLandscape(landscape, visited, x - 1, y);
        optimizeLandscape(landscape, visited, x, y + 1);
        optimizeLandscape(landscape, visited, x, y - 1);
    }

    private static int findMinAdjacent(int[][] landscape, int x, int y) {
        ArrayList<Integer> list = new ArrayList<>();

        if (x - 1 >= 0 && landscape[x - 1][y] != 0) {
            list.add(landscape[x - 1][y]);
        }
        if (x + 1 <= landscape.length - 1 && landscape[x + 1][y] != 0) {
            list.add(landscape[x + 1][y]);
        }
        if (y - 1 >= 0 && landscape[x][y - 1] != 0) {
            list.add(landscape[x][y - 1]);
        }
        if (y + 1 <= landscape[0].length - 1 && landscape[x][y + 1] != 0) {
            list.add(landscape[x][y + 1]);
        }
        Collections.sort(list);
        return list.size() == 0 ? 1000 : list.get(0);
    }

    private static ArrayList<int[]> findHighestLocations(int[][] landscape) {
        int max = Integer.MIN_VALUE;
        ArrayList<int[]> locations = new ArrayList<>();

        for (int x = 0; x < landscape.length; x++) {
            for (int y = 0; y < landscape[0].length; y++) {
                max = Math.max(landscape[x][y], max);
            }
        }

        for (int x = 0; x < landscape.length; x++) {
            for (int y = 0; y < landscape[0].length; y++) {
                if (landscape[x][y] == max) {
                    locations.add(new int[]{x, y, max});
                }
            }
        }
        return locations;
    }

    private static int[] findTheLocation(ArrayList<int[]> highestLocations, boolean[][] landscape) {
        int maxBeaches = -1;
        int[] location = new int[3];
        for (int[] highestLocation : highestLocations) {
            int numOfBeaches = maximizeBeaches(highestLocation, landscape);
            if (numOfBeaches > maxBeaches) {
                maxBeaches = numOfBeaches;
                location[0] = highestLocation[0];
                location[1] = highestLocation[1];
            }
        }
        location[2] = maxBeaches;

        return location;
    }

    private static int maximizeBeaches(int[] highestLocation, boolean[][] landscape) {
        int[][] optimizedLandscape = new int[landscape.length][landscape[0].length];
        boolean[][] visited = new boolean[landscape.length][landscape[0].length];
        optimizedLandscape[highestLocation[0]][highestLocation[1]] = highestLocation[2];

        fillAdjecantAreasTwo(optimizedLandscape, highestLocation); // Fill all adjacent areas of peak location with peak - 1

        visited[highestLocation[0]][highestLocation[1]] = true; // Check house location as visited location
        // When the starting direction is up of house location
        optimizeLandscapeTwo(optimizedLandscape, visited, highestLocation[0] + 1, highestLocation[1]);
        visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
        // When the starting direction is down of house location
        optimizeLandscapeTwo(optimizedLandscape, visited, highestLocation[0] - 1, highestLocation[1]);
        visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
        // When the starting direction is right of house location
        optimizeLandscapeTwo(optimizedLandscape, visited, highestLocation[0], highestLocation[1] + 1);
        visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];
        // When the starting direction is left of house location
        optimizeLandscapeTwo(optimizedLandscape, visited, highestLocation[0], highestLocation[1] - 1);
        visited = new boolean[optimizedLandscape.length][optimizedLandscape[0].length];

        // Check optimizedLandscape is valid
        for (int x = 0; x < landscape.length; x++) {
            for (int y = 0; y < landscape[0].length; y++) {
                if (landscape[x][y] && optimizedLandscape[x][y] != 0) {
                    return -1;
                }
            }
        }

        // Count the number of zeros
        int count = 0;
        for (int[] row : optimizedLandscape) {
            for (int col : row) {
                if (col == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    // Fill adjacents of a location with 1
    private static void fillAdjecantAreasTwo(int[][] map, int[] location) {
        int x = location[0];
        int y = location[1];

        // up adjacent of the location
        if (x - 1 >= 0) {
            map[x - 1][y] = map[x][y] - 1;
        }
        // down adjacent of the location
        if (x + 1 < map.length) {
            map[x + 1][y] = map[x][y] - 1;
        }
        // left adjacent of the location
        if (y - 1 >= 0) {
            map[x][y - 1] = map[x][y] - 1;
        }
        // right adjacent of the location
        if (y + 1 < map[0].length) {
            map[x][y + 1] = map[x][y] - 1;
        }
    }

    private static void optimizeLandscapeTwo(int[][] landscape, boolean[][] visited, int x, int y) {
        if (x < 0 || x > landscape.length - 1 || y < 0 || y > landscape[0].length - 1 || visited[x][y]) {
            return;
        }

        int maxAdjacent = findMaxAdjacent(landscape, x, y);
        if (maxAdjacent == 0) {
            return;
        }

        if (maxAdjacent > landscape[x][y]) {
            landscape[x][y] = maxAdjacent - 1;
        }

        visited[x][y] = true;
        optimizeLandscapeTwo(landscape, visited, x + 1, y);
        optimizeLandscapeTwo(landscape, visited, x - 1, y);
        optimizeLandscapeTwo(landscape, visited, x, y + 1);
        optimizeLandscapeTwo(landscape, visited, x, y - 1);
    }

    private static int findMaxAdjacent(int[][] landscape, int x, int y) {
        ArrayList<Integer> list = new ArrayList<>();

        if (x - 1 >= 0) {
            list.add(landscape[x - 1][y]);
        }
        if (x + 1 <= landscape.length - 1) {
            list.add(landscape[x + 1][y]);
        }
        if (y - 1 >= 0) {
            list.add(landscape[x][y - 1]);
        }
        if (y + 1 <= landscape[0].length - 1) {
            list.add(landscape[x][y + 1]);
        }
        Collections.sort(list);
        return list.get(list.size() - 1);
    }
}
