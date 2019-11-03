import com.sun.tools.javac.util.Pair;
import java.util.LinkedList;
import java.util.Queue;

public class FindMaxHeight {
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

    int[] peak = findPeak(lands);
    System.out.println("X: " + peak[0] + ", Y: " + peak[1] + ", Height: " + peak[2]);
  }

  private static int[] findPeak(int[][] lands) {
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
    return findOnePeak(computedLands);
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

  // Return one peak of peaks
  private static int[] findOnePeak(int[][] lands) {
    int x = 0;
    int y = 0;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < lands.length; i++) {
      for (int j = 0; j < lands[i].length; j++) {
        if (lands[i][j] > max) {
          x = i;
          y = j;
          max = lands[x][y];
        }
      }
    }
    return new int[] {x, y, max};
  }
}
