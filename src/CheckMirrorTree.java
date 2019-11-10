import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CheckMirrorTree {
  public static void main(String[] args) {}
}

class BinaryTree {
  private Node root;

  // Check this binary tree is mirror or not
  public boolean isMirror() {
    Queue<Node> leftQueue = new LinkedList<>();
    Queue<Node> rightQueue = new LinkedList<>();
    List<Node> leftList = new ArrayList<>();
    List<Node> rightList = new ArrayList<>();

    isMirrorHelper(root.left, leftQueue, leftList);
    isMirrorHelper(root.right, rightQueue, rightList);

    return compareTwoLists(leftList, rightList);
  }

  // Add sub tree nodes to list in order
  private void isMirrorHelper(Node node, Queue<Node> queue, List<Node> list) {
    // Initialize queue with the first node of one sub tree
    queue.add(node);

    while (queue.size() != 0) {
      Node cur = queue.poll();
      list.add(cur);
      if (cur == null) {
        continue;
      }
      queue.add(cur.left);
      queue.add(cur.right);
    }
  }

  //  Check two lists are same or not
  private boolean compareTwoLists(List<Node> left, List<Node> right) {
    if (left.size() != right.size()) {
      return false;
    }
    for (int i = 0; i < left.size(); i++) {
      // When one of the children nodes is null
      if (left.get(i) == null || right.get(i) == null) {
        if (left.get(i) != right.get(i)) {
          return false;
        }
      }
      // When both of the children nodes are not null
      if (left.get(i).value != right.get(i).value) {
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
      if (cur.left != null) {
        queue.add(cur.left);
      }
      if (cur.right != null) {
        queue.add(cur.right);
      }
    }
    return sb.toString();
  }

  private static class Node {
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }
  }
}
