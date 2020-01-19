package treeGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class treeGame {
  public static void main(String[] args) {
    // Define tree
    /*
            root
           n1  n2
       n3 n4    n5 n6
       n7          n8
     */
    Node root = new Node(0);
    Node n1 = new Node(1);
    Node n2 = new Node(2);
    Node n3 = new Node(3);
    Node n4 = new Node(4);
    Node n5 = new Node(5);
    Node n6 = new Node(6);
    Node n7 = new Node(7);
    Node n8 = new Node(8);
    root.leftChild = n1;
    root.rightChild = n2;
    n1.parent = root;
    n2.parent = root;
    n1.leftChild = n3;
    n1.rightChild = n4;
    n3.parent = n1;
    n4.parent = n1;
    n2.leftChild = n5;
    n2.rightChild = n6;
    n5.parent = n2;
    n6.parent = n2;
    n3.leftChild = n7;
    n7.parent = n3;
    n6.rightChild = n8;
    n8.parent = n6;


    // Start node
    Node p1 = n3;
    Node p2 = n4;
    // {destNode, distance from startNode to destNode}
    Map<Node, Integer> p1Map = countNodes(p1);
    Map<Node, Integer> p2Map = countNodes(p2);
    // Count of nodes each user won
    int p1Count = 0;
    int p2Count = 0;

    for(Node node : p1Map.keySet()) {
      if(p1Map.get(node) < p2Map.get(node)) {
        p1Count++;
      } else {
        p2Count++;
      }
    }

    if(p1Count > p2Count) {
      System.out.println("Player 1 won the game!");
    } else if (p1Count < p2Count) {
      System.out.println("Player 2 won the game!");
    } else {
      System.out.println("Tie!");
    }
  }

  public static Map<Node, Integer> countNodes(Node startNode) {
    Map<Node, Integer> map = new HashMap<>();
    Set<Node> visited = new HashSet<>();
    Queue<Node> queue = new LinkedList<>();

    queue.add(startNode);
    map.put(startNode, 0);

    while(queue.size() != 0) {
      Node node = queue.poll();
      visited.add(node);
      if(node.parent != null && !visited.contains(node.parent)) {
        queue.add(node.parent);
        map.put(node.parent, map.get(node) + 1);
      }
      if(node.leftChild != null && !visited.contains(node.leftChild)) {
        queue.add(node.leftChild);
        map.put(node.leftChild, map.get(node) + 1);
      }
      if(node.rightChild != null && !visited.contains(node.rightChild)) {
        queue.add(node.rightChild);
        map.put(node.rightChild, map.get(node) + 1);
      }
    }
    return map;
  }

  private static class Node {
    private int value;
    private Node parent;
    private Node leftChild;
    private Node rightChild;

    public Node(int value) {
      this.value = value;
    }
  }
}

