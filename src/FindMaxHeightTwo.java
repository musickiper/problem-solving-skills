import com.sun.tools.javac.util.Pair;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FindMaxHeightTwo {
  public static void main(String[] args) {
    // 0: Water, 1000: Land
    int[][] lands =
        new int[][] {
          {0, 1000, 1000, 1000, 1000, 1000},
          {1000, 1000, 1000, 1000, 1000, 1000},
          {1000, 1000, 1000, 1000, 1000, 1000},
          {1000, 1000, 1000, 1000, 1000, 1000},
          {1000, 1000, 1000, 1000, 1000, 0}
        };

    int count = countZeros(lands);
    System.out.println("The number of 0: " + count);
  }

  private static int countZeros(int[][] lands) {
    int[][] computedLands = new int[lands.length][lands[0].length];
    // Copy all elements from 'lands' to 'computedLands'
    cloneTwoDiArray(lands, computedLands);

    boolean[][] visited = new boolean[lands.length][lands[0].length];

    for (int i = 0; i < lands.length; i++) {
      for (int j = 0; j < lands[0].length; j++) {
        if (lands[i][j] == 0) {
          computeLands(lands, computedLands, visited, i, j);
          visited = new boolean[lands.length][lands[0].length];
        }
      }
    }

    // Get height of peak
    int peakHeight = getPeakHeight(computedLands);

    // Find the closest peak to edges of lands
    int peakX = 0;
    int peakY = 0;
    int peakDistance = Integer.MAX_VALUE;
    for (int i = 0; i < lands.length; i++) {
      for (int j = 0; j < lands[0].length; j++) {
        if (computedLands[i][j] == peakHeight) {
          int distance = calcDistanceFromEdge(computedLands, peakHeight, i, j);
          if (distance < peakDistance) {
            peakDistance = distance;
            peakX = i;
            peakY = j;
          }
        }
      }
    }

    visited = new boolean[lands.length][lands[0].length];
    computeLandsTwo(computedLands, visited, peakX, peakY);

    return countZerosHelper(computedLands);
  }

  // Clone two Di array from src array to dest array
  private static void cloneTwoDiArray(int[][] src, int[][] dest) {
    for (int i = 0; i < src.length; i++) {
      System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
    }
  }

  // Compute lands
  private static void computeLands(
      int[][] lands, int[][] computedLands, boolean[][] visited, int x, int y) {
    Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
    queue.add(new Pair<>(x, y));
    visited[x][y] = true;

    while (queue.size() != 0) {
      Pair<Integer, Integer> current = queue.poll();
      if (checkLocationValid(lands, visited, current.fst - 1, current.snd)
          && computedLands[current.fst][current.snd]
              != computedLands[current.fst - 1][current.snd]) {
        // West location of current
        computedLands[current.fst - 1][current.snd] =
            computedLands[current.fst][current.snd] == 0
                ? 1
                : computedLands[current.fst][current.snd] + 1;
        visited[current.fst - 1][current.snd] = true;
        queue.add(new Pair<>(current.fst - 1, current.snd));
      }
      if (checkLocationValid(lands, visited, current.fst + 1, current.snd)
          && computedLands[current.fst][current.snd]
              != computedLands[current.fst + 1][current.snd]) {
        // East location of current
        computedLands[current.fst + 1][current.snd] =
            computedLands[current.fst][current.snd] == 0
                ? 1
                : computedLands[current.fst][current.snd] + 1;
        visited[current.fst + 1][current.snd] = true;
        queue.add(new Pair<>(current.fst + 1, current.snd));
      }
      if (checkLocationValid(lands, visited, current.fst, current.snd - 1)
          && computedLands[current.fst][current.snd]
              != computedLands[current.fst][current.snd - 1]) {
        // South location of current
        computedLands[current.fst][current.snd - 1] =
            computedLands[current.fst][current.snd] == 0
                ? 1
                : computedLands[current.fst][current.snd] + 1;
        visited[current.fst][current.snd - 1] = true;
        queue.add(new Pair<>(current.fst, current.snd - 1));
      }
      if (checkLocationValid(lands, visited, current.fst, current.snd + 1)
          && computedLands[current.fst][current.snd]
              != computedLands[current.fst][current.snd + 1]) {
        // North location of current
        computedLands[current.fst][current.snd + 1] =
            computedLands[current.fst][current.snd] == 0
                ? 1
                : computedLands[current.fst][current.snd] + 1;
        visited[current.fst][current.snd + 1] = true;
        queue.add(new Pair<>(current.fst, current.snd + 1));
      }
    }
  }

  // Check the location's validation
  private static boolean checkLocationValid(int[][] lands, boolean[][] visited, int x, int y) {
    return x >= 0
        && x <= lands.length - 1
        && y >= 0
        && y <= lands[0].length - 1
        && !visited[x][y]
        && lands[x][y] != 0;
  }

  // Return height of peak
  private static int getPeakHeight(int[][] lands) {
    int max = Integer.MIN_VALUE;
    for (int[] land : lands) {
      for (int i : land) {
        if (i > max) {
          max = i;
        }
      }
    }
    return max;
  }

  // Calculate shortest distance from the location to edges of lands
  private static int calcDistanceFromEdge(int[][] lands, int peakHeight, int x, int y) {
    int leftUp = x + y;
    int leftDown = (lands.length - x - 1) + y;
    int rightUp = x + (lands[0].length - y - 1);
    int rightDown = (lands.length - x - 1) + (lands[0].length - y - 1);
    return Math.min(Math.min(leftUp, leftDown), Math.min(rightUp, rightDown));
  }

  private static void computeLandsTwo(int[][] lands, boolean[][] visited, int x, int y) {
    Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
    queue.add(new Pair<>(x, y));
    visited[x][y] = true;

    while (queue.size() != 0) {
      Pair<Integer, Integer> current = queue.poll();
      if (checkLocationValid(lands, visited, current.fst - 1, current.snd)) {
        // West location of current
        lands[current.fst - 1][current.snd] =
            lands[current.fst][current.snd] == 0 ? 0 : lands[current.fst][current.snd] - 1;
        visited[current.fst - 1][current.snd] = true;
        queue.add(new Pair<>(current.fst - 1, current.snd));
      }
      if (checkLocationValid(lands, visited, current.fst + 1, current.snd)) {
        // East location of current
        lands[current.fst + 1][current.snd] =
            lands[current.fst][current.snd] == 0 ? 0 : lands[current.fst][current.snd] - 1;
        visited[current.fst + 1][current.snd] = true;
        queue.add(new Pair<>(current.fst + 1, current.snd));
      }
      if (checkLocationValid(lands, visited, current.fst, current.snd - 1)) {
        // South location of current
        lands[current.fst][current.snd - 1] =
            lands[current.fst][current.snd] == 0 ? 0 : lands[current.fst][current.snd] - 1;
        visited[current.fst][current.snd - 1] = true;
        queue.add(new Pair<>(current.fst, current.snd - 1));
      }
      if (checkLocationValid(lands, visited, current.fst, current.snd + 1)) {
        // North location of current
        lands[current.fst][current.snd + 1] =
            lands[current.fst][current.snd] == 0 ? 0 : lands[current.fst][current.snd] - 1;
        visited[current.fst][current.snd + 1] = true;
        queue.add(new Pair<>(current.fst, current.snd + 1));
      }
    }
  }

  private static int countZerosHelper(int[][] lands) {
    int count = 0;
    for (int[] row : lands) {
      count += Arrays.stream(row).filter(i -> i == 0).count();
    }
    return count;
  }
}
