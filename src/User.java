public class User {
    Direction facing = Direction.UP;
    Double velocity = 20.0;
    Double x = 500.0;
    Double y = 500.0;
}

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}