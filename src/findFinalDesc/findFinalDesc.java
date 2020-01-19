package findFinalDesc;

public class findFinalDesc {
  public static void main(String[] args) {
    int startX = 0;
    int startY = 0;
    Action[] actions = {Action.FORWARD, Action.BACKWORD, Action.TURNLEFT, Action.FORWARD};
    findDest(new int[] {startX, startY}, actions);
  }

  public static void findDest(int[] startLocation, Action[] actions) {
    Direction direction = Direction.UP;
    for (Action action : actions) {
      direction = getUpdatedDirection(startLocation, direction, action);
    }
    System.out.println("X = " + startLocation[0] + ", " + "Y = " + startLocation[1]);
  }

  public static Direction getUpdatedDirection(
      int[] prevLocation, Direction prevDirection, Action action) {
    if (prevDirection == Direction.UP) {
      if (action == Action.FORWARD) prevLocation[0]--;
      if (action == Action.BACKWORD) prevLocation[0]++;
      if (action == Action.TURNLEFT) return Direction.LEFT;
      if (action == Action.TURNRIGHT) return Direction.RIGHT;
    }
    if (prevDirection == Direction.DOWN) {
      if (action == Action.FORWARD) prevLocation[0]++;
      if (action == Action.BACKWORD) prevLocation[0]--;
      if (action == Action.TURNLEFT) return Direction.RIGHT;
      if (action == Action.TURNRIGHT) return Direction.LEFT;
    }
    if (prevDirection == Direction.LEFT) {
      if (action == Action.FORWARD) prevLocation[1]--;
      if (action == Action.BACKWORD) prevLocation[1]++;
      if (action == Action.TURNLEFT) return Direction.DOWN;
      if (action == Action.TURNRIGHT) return Direction.UP;
    }
    if (prevDirection == Direction.RIGHT) {
      if (action == Action.FORWARD) prevLocation[1]++;
      if (action == Action.BACKWORD) prevLocation[1]--;
      if (action == Action.TURNLEFT) return Direction.UP;
      if (action == Action.TURNRIGHT) return Direction.DOWN;
    }
    return Direction.UP;
  }

  enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  };

  enum Action {
    FORWARD,
    BACKWORD,
    TURNLEFT,
    TURNRIGHT
  };
}
