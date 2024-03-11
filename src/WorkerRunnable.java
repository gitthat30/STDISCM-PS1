/* This class contains code based on the following: https://paulbourke.net/geometry/pointlineplane/
* See getIntersection() method. */

public class WorkerRunnable implements Runnable {
    Command command;

    WorkerRunnable(Command command) {
        this.command = command;
    }
    @Override
    public void run() {
        switch (command.type) {
            case MOVE -> updateParticlePosition(command.p);
            case GENERATE_PARTICLE -> generateParticle();
        }
    }

    private void updateParticlePosition(Particle p) {
        //First get the velocity
        Double xVelocity = Math.cos(Math.toRadians(command.angle)) * command.velocity/16;
        Double yVelocity = Math.sin(Math.toRadians(command.angle)) * command.velocity/16;
        //Get next positions
        double newX = p.x + xVelocity;
        double newY = p.y - yVelocity;

        //Check if next position is either: Past the border
        boolean bounceFlag = false;
        double perpendicularAngle = 0.0;

        if (newX > 1270) {
            bounceFlag = true;
            newX = 1270.00;
            perpendicularAngle = 180.00;
            command.angle += 180.00; //Hitting from left
        } else if (newX < 0) {
            bounceFlag = true;
            newX = 0.00;
            perpendicularAngle = 0.00;
            command.angle -= 180.00; //Hitting from right
        } else if (newY < 0) {
            bounceFlag = true;
            newY = 0.00;
            perpendicularAngle = 90.00;
            command.angle += 180.00; //Hitting from below
        } else if (newY > 710) {
            bounceFlag = true;
            newY = 710.00;
            perpendicularAngle = 270.00;
            command.angle -= 180.00; //Hitting from below

        }

        if(bounceFlag) {
            if(perpendicularAngle > command.angle) {
                command.angle = perpendicularAngle + (perpendicularAngle - command.angle);
            }
            else {
                command.angle = perpendicularAngle - (command.angle - perpendicularAngle);
            }

            xVelocity = Math.cos(Math.toRadians(command.angle)) * 1;
            yVelocity = Math.sin(Math.toRadians(command.angle)) * 1;
            newX += xVelocity;
            newY -= yVelocity;

            if(newX > 1270) {
                newX = 1270.00;
            }
            if(newX < 0) {
                newX = 0.00;
            }
            if(newY < 0) {
                newY = 0.00;
            }
            if(newY > 710) {
                newY = 710.00;
            }
        }

        //Move the particle
        p.moveParticle(newX, newY);
        Main.commandQueue.add(new Command(p, command.velocity, command.angle));
    }

    private void generateParticle() {
        Particle temp = new Particle(command.x, command.y, command.velocity, command.angle);
        ParticleArea.particleList.add(temp);
        Main.commandQueue.add(new Command(temp, command.velocity, command.angle));
    }

}