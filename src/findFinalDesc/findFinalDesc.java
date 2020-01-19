package findFinalDesc;
import com.sun.tools.javac.util.Pair;
import org.omg.PortableInterceptor.ACTIVE;

public class findFinalDesc {
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

  public static void main(String[] args) {
    int startX = 0;
    int startY = 0;
    Action[] actions = {Action.FORWARD, Action.BACKWORD, Action.TURNLEFT,
        Action.FORWARD};
    findDest(startX, startY, actions);
  }

  public static void findDest(int startX, int startY, Action[] actions) {
    Direction direction = Direction.UP;
    for(Action action : actions) {
      switch(direction) {
        case UP:
          if(action == Action.FORWARD) {
            startX--;
          } else if(action == Action.BACKWORD) {
            startX++;
          } else if(action == Action.TURNLEFT) {
            direction = Direction.LEFT;
          } else if(action == Action.TURNRIGHT) {
            direction = Direction.RIGHT;
          }
          break;
        case DOWN:
          if(action == Action.FORWARD) {
            startX++;
          } else if(action == Action.BACKWORD) {
            startX--;
          } else if(action == Action.TURNLEFT) {
            direction = Direction.RIGHT;
          } else if(action == Action.TURNRIGHT) {
            direction = Direction.LEFT;
          }
          break;
        case LEFT:
          if(action == Action.FORWARD) {
            startY--;
          } else if(action == Action.BACKWORD) {
            startY++;
          } else if(action == Action.TURNLEFT) {
            direction = Direction.DOWN;
          } else if(action == Action.TURNRIGHT) {
            direction = Direction.UP;
          }
          break;
        case RIGHT:
          if(action == Action.FORWARD) {
            startY++;
          } else if(action == Action.BACKWORD) {
            startY--;
          } else if(action == Action.TURNLEFT) {
            direction = Direction.UP;
          } else if(action == Action.TURNRIGHT) {
            direction = Direction.DOWN;
          }
          break;
        default:
          break;
      }
    }
    System.out.println(startX);
    System.out.println(startY);
  }
}
