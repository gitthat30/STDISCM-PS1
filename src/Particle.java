public class Particle {
    int x;
    int y;
    int velocity;
    int angle;

    Particle(int x, int y, int velocity, int angle) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.angle = angle;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
