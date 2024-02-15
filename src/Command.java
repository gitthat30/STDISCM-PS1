public class Command {
    CommandType type;
    Double x;
    Double y;
    int x1;
    int y1;
    int x2;
    int y2;
    public Double velocity;
    public Double angle;
    Particle p;

    /* Constructor for moving particle */
    public Command(Particle p, Double velocity, Double angle) {
        this.type = CommandType.MOVE;
        this.velocity = velocity;
        this.angle = angle;
        this.p = p;
    }

    /* Constructor for generating particle */
    Command(CommandType type, Double a, Double b, Double c, Double d) {
        this.type = type;

        this.x = a;
        this.y = b;
        this.velocity = c;
        this.angle = d;
    }

    /* Constructor for generating wall */
    Command(CommandType type, int a, int b, int c, int d) {
        this.type = type;

        this.x1 = a;
        this.y1 = b;
        this.x2 = c;
        this.y2 = d;

    }




}

enum CommandType {
    MOVE,
    GENERATE_PARTICLE,
    GENERATE_WALL
}