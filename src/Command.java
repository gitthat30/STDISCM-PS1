import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Command implements Delayed {
    CommandType type;
    Double x;
    Double y;
    int x1;
    int y1;
    int x2;
    int y2;
    public Double velocity;
    public Double angle;
    long time;
    Particle p;

    /* Constructor for moving particle */
    public Command(Particle p, Double velocity, Double angle) {
        this.type = CommandType.MOVE;
        this.time = System.currentTimeMillis()
                + 125;
        this.velocity = velocity;
        this.angle = angle;
        this.p = p;
    }

    /* Constructor for generating particle */
    Command(Double a, Double b, Double c, Double d) {
        this.type = CommandType.GENERATE_PARTICLE;

        this.x = a;
        this.y = b;
        this.velocity = c;
        this.angle = d;
    }

    /* Constructor for generating wall */
    Command(int a, int b, int c, int d) {
        this.type = CommandType.GENERATE_WALL;

        this.x1 = a;
        this.y1 = b;
        this.x2 = c;
        this.y2 = d;

    }


    @Override
    public long getDelay(TimeUnit unit) {
        long diff = time - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.time < ((Command)o).time) {
            return -1;
        }
        if (this.time > ((Command)o).time) {
            return 1;
        }
        return 0;
    }
}

enum CommandType {
    MOVE,
    GENERATE_PARTICLE,
    GENERATE_WALL
}