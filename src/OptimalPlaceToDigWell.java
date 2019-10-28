import com.sun.tools.javac.util.Pair;
import java.util.LinkedList;
import java.util.Queue;

public class OptimalPlaceToDigWell {
  public static void main(String[] args) {
    // [X][Y]
    int[][] map =
        new int[][] {
          {-2, 1000, -1, 1000},
          {1000, 1000, 1000, 1000},
          {1000, -2, -2, 1000},
          {1000, -1, -2, 1000},
          {-2, -2, 1000, -1}
        };

    // Find the optimal place to dig a well
    Pair<Integer, Integer> place = findOptimalPlace(map);
    System.out.println("X: " + place.fst + ", Y: " + place.snd);
  }

  private static Pair<Integer, Integer> findOptimalPlace(int[][] graph) {
    // Computed a graph for an house
    int[][] computedGraph = new int[graph.length][graph[0].length];
    copyTwoDiArray(graph, computedGraph);
    // Computed a graph for houses
    int[][] computedGraphsSum = new int[graph.length][graph[0].length];
    boolean[][] visited = new boolean[graph.length][graph[0].length];

    // Find the location of house making a computed graph
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[0].length; j++) {
        if (graph[i][j] == -1) {
          computeGraph(graph, computedGraph, visited, i, j);
          // Reset the visited array for the next house
          visited = new boolean[graph.length][graph[0].length];
          // Add the computed graph for a house to computed graph for houses
          sumTwoDiArrays(computedGraphsSum, computedGraph);
        }
      }
    }

    return findOptimalPlaceHelper(computedGraphsSum);
  }

  // Copy two dimensional array "from" to "to"
  private static void copyTwoDiArray(int[][] from, int[][] to) {
    for (int i = 0; i < from.length; i++) {
      System.arraycopy(from[i], 0, to[i], 0, from[0].length);
    }
  }

  // Add arr2 to array1
  private static void sumTwoDiArrays(int[][] arr1, int[][] arr2) {
    for (int l = 0; l < arr1.length; l++) {
      for (int m = 0; m < arr1[0].length; m++) {
        arr1[l][m] += arr2[l][m];
      }
    }
  }

  // Find optimal place from computed graph for houses
  private static Pair<Integer, Integer> findOptimalPlaceHelper(int[][] graph) {
    int x = 0;
    int y = 0;
    int max = Integer.MAX_VALUE;
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        if (graph[i][j] > 0 && graph[i][j] < max) {
          x = i;
          y = j;
          max = graph[x][y];
        }
      }
    }
    return new Pair<>(x, y);
  }

  private static void computeGraph(
      int[][] graph, int[][] computedGraph, boolean[][] visited, int x, int y) {
    Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
    queue.add(new Pair<>(x, y));
    visited[x][y] = true;

    // Breadth First Search
    while (queue.size() != 0) {
      // Poll a current location from queue
      Pair<Integer, Integer> current = queue.poll();
      // Check the left adjacent of cur location is valid
      if (checkValidLocation(graph, visited, current.fst - 1, current.snd)) {
        computedGraph[current.fst - 1][current.snd] =
            computedGraph[current.fst][current.snd] == -1
                ? 1
                : computedGraph[current.fst][current.snd] + 1;
        visited[current.fst - 1][current.snd] = true;
        queue.add(new Pair<>(current.fst - 1, current.snd));
      }
      // Check the right adjacent of cur location is valid
      if (checkValidLocation(graph, visited, current.fst + 1, current.snd)) {
        computedGraph[current.fst + 1][current.snd] =
            computedGraph[current.fst][current.snd] == -1
                ? 1
                : computedGraph[current.fst][current.snd] + 1;
        visited[current.fst + 1][current.snd] = true;
        queue.add(new Pair<>(current.fst + 1, current.snd));
      }
      // Check the down adjacent of cur location is valid
      if (checkValidLocation(graph, visited, current.fst, current.snd - 1)) {
        computedGraph[current.fst][current.snd - 1] =
            computedGraph[current.fst][current.snd] == -1
                ? 1
                : computedGraph[current.fst][current.snd] + 1;
        visited[current.fst][current.snd - 1] = true;
        queue.add(new Pair<>(current.fst, current.snd - 1));
      }
      // Check the up adjacent of cur location is valid
      if (checkValidLocation(graph, visited, current.fst, current.snd + 1)) {
        computedGraph[current.fst][current.snd + 1] =
            computedGraph[current.fst][current.snd] == -1
                ? 1
                : computedGraph[current.fst][current.snd] + 1;
        visited[current.fst][current.snd + 1] = true;
        queue.add(new Pair<>(current.fst, current.snd + 1));
      }
    }
  }

  // Check the validation of the location(x,y)
  private static boolean checkValidLocation(int[][] graph, boolean[][] visited, int x, int y) {
    return x >= 0
        && x <= graph.length - 1
        && y >= 0
        && y <= graph[0].length - 1
        && !visited[x][y]
        && graph[x][y] != -1
        && graph[x][y] != -2;
  }
}
