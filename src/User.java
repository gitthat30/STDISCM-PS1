public class User {
    Double velocity = 10.0;
    Double x;
    Double y;
    Double cameraX;
    Double cameraY;

    User() {
        this.x = 300.0;
        this.y = 300.0;

        cameraX = x - 16;
        cameraY = y - 9;
    }

    User(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}