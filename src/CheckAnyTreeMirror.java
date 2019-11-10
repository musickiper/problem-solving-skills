import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CheckAnyTreeMirror {
  public static void main(String[] args) {
    // BinaryTree tree = ...
    // System.out.println(tree.checkIsMirror());
  }
}

class BinaryTree {
  private Node root;

  public boolean checkIsMirror() {
    // Check the left and right sub trees of root
    if (isMirror(root)) {
      // Check the middle sub tree of root
      if (root.children.size() % 2 == 1) {
        Node middleSubTree = root.children.get((int) Math.ceil(root.children.size() / 2.0));
        return isMirror(middleSubTree);
      }
      return false;
    }
    return false;
  }

  // Check the left sub tree is mirror of the right sub tree
  private boolean isMirror(Node root) {
    Queue<Node> leftQueue = new LinkedList<>();
    Queue<Node> rightQueue = new LinkedList<>();
    List<Pair<Node, Integer>> leftList = new ArrayList<>();
    List<Pair<Node, Integer>> rightList = new ArrayList<>();

    int rootChildrenSize = root.children.size();
    // Initialize the left queue
    for (int i = 0; i < Math.floor(rootChildrenSize / 2.0); i++) {
      leftQueue.add(root.children.get(i));
    }
    // Initialize the right queue
    for (int i = (int) Math.ceil(rootChildrenSize / 2.0); i < rootChildrenSize; i++) {
      rightQueue.add(root.children.get(i));
    }
    isMirrorHelper(leftQueue, leftList);
    isMirrorHelper(rightQueue, rightList);

    return compareTwoLists(leftList, rightList);
  }

  // Add a tree's nodes to a list in order
  private void isMirrorHelper(Queue<Node> queue, List<Pair<Node, Integer>> list) {
    while (queue.size() != 0) {
      Node cur = queue.poll();
      if (cur == null) {
        list.add(new Pair<>(null, 0));
        continue;
      } else {
        list.add(new Pair<>(cur, cur.children.size()));
      }
      queue.addAll(cur.children);
    }
  }

  //  Check two lists are same or not
  private boolean compareTwoLists(List<Pair<Node, Integer>> left, List<Pair<Node, Integer>> right) {
    if (left.size() != right.size()) {
      return false;
    }
    for (int i = 0; i < left.size(); i++) {
      // When one of the children nodes is null,
      // Check the left list is a mirror list of the right list
      if (left.get(i) == null || right.get(left.size() - i - 1) == null) {
        if (left.get(i) != right.get(left.size() - i - 1)) {
          return false;
        }
      }
      // When both of the children nodes are not null,
      // Check the left list is a mirror list of the right list
      // Compare two nodes' values
      if (left.get(i).fst.value != right.get(left.size() - i - 1).fst.value) {
        return false;
        // Compare two nodes' children's size
      } else if (!left.get(i).snd.equals(right.get(left.size() - i - 1).snd)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    StringBuilder sb = new StringBuilder();
    while (queue.size() != 0) {
      Node cur = queue.poll();

      sb.append(cur.value).append(" ");
      for (Node n : cur.children) {
        if (n != null) {
          queue.add(n);
        }
      }
    }
    return sb.toString();
  }

  private static class Node {
    private int value;
    private ArrayList<Node> children;

    public Node(int value) {
      this.value = value;
      this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }
  }
}
