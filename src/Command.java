public class Command {
    CommandType type;
    int x1;
    int y1;
    int x2;
    int y2;
    int velocity;
    int angle;

    /* Constructor for moving particle */
    public Command(int newX, int newY) {
        this.type = CommandType.MOVE;

        this.x1 = newX;
        this.y1 = newY;
    }

    /* Constructor for generating particle/wall */
    Command(CommandType type, int a, int b, int c, int d) {
        this.type = type;

        // If particle
        if (this.type == CommandType.GENERATE_PARTICLE) {
            this.x1 = a;
            this.y1 = b;
            this.velocity = c;
            this.angle = d;
        }
        else if (type == CommandType.GENERATE_WALL) {
            this.x1 = a;
            this.y1 = b;
            this.x2 = c;
            this.y2 = d;
        }

    }




}

enum CommandType {
    MOVE,
    GENERATE_PARTICLE,
    GENERATE_WALL
}