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
            case GENERATE_WALL -> generateWall();
        }
    }

    // TODO: do computation for next position and add to commandQueue
    private void updateParticlePosition(Particle p) {
        //First get the velocity
        System.out.println(Math.toRadians(command.angle));
        Double xVelocity = Math.cos(Math.toRadians(command.angle)) * command.velocity;
        Double yVelocity = Math.sin(Math.toRadians(command.angle)) * command.velocity;
        //Get next positions
        double newX = p.x + xVelocity;
        double newY = p.y - yVelocity;

        //Check if next position is either: Past the border or hits a wall
        boolean bounceFlag = false;
        boolean wallFlag = false;
        double perpendicularAngle = 0.0;

        for(Wall w : ParticleArea.wallList) {
            //Check if line particle passes through
            if((p.x >= w.getX1() && newX <= w.getX2()) && (p.y >= w.getY1() && newY <= w.getY2())) {
                //Get intersection of the two lines

                //Iterate through, getting and keeping intersections

                //Store the intersection with the lowest distance from OG point

                //Set wall flag to true
            }
        }

        if(wallFlag) {

        }
        else {
            //Border
            if(newX >= 1270) {
                bounceFlag = true;
                newX = 1270.00;
                perpendicularAngle = 180.00;
                command.angle += 180.00; //Hitting from left
            }
            else if(newX <= 0) {
                bounceFlag = true;
                newX = 0.00;
                perpendicularAngle = 0.00;
                command.angle -= 180.00; //Hitting from right   
            }
            else if(newY <= 0) {
                bounceFlag = true;
                newY = 0.00;
                perpendicularAngle = 90.00;
                command.angle += 180.00; //Hitting from below 
            }
            else if(newY >= 710) {
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
            }
        }
        
        //Move the particle
        p.moveParticle(newX, newY);

        //If no border or wall, update position

        //Else if wall or border, bounce off the wall using angle of reflection.
        
        Main.commandQueue.add(new Command(p, command.velocity, command.angle));

    }

    private void generateParticle() {
        Particle temp = new Particle(command.x, command.y, command.velocity, command.angle);
        ParticleArea.particleList.add(temp);
        Main.commandQueue.add(new Command(temp, command.velocity, command.angle));
    }

    private void generateWall() {
        ParticleArea.wallList.add(new Wall(command.x1, command.y1, command.x2, command.y2));
    }
}
