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
            case GENERATE_WALL -> generateWall();
        }
    }

    private void updateParticlePosition(Particle p) {
        //First get the velocity
        Double xVelocity = Math.cos(Math.toRadians(command.angle)) * command.velocity/16;
        Double yVelocity = Math.sin(Math.toRadians(command.angle)) * command.velocity/16;
        //Get next positions
        double newX = p.x + xVelocity;
        double newY = p.y - yVelocity;

        //Check if next position is either: Past the border or hits a wall
        boolean bounceFlag = false;
        boolean wallFlag = false;
        double perpendicularAngle = 0.0;

        Intersection tempIntersection = null;
        Intersection shortestIntersection = null;
        Wall bounceWall = null;
        double shortestDistance = 9999.0;

        for(Wall w : ParticleArea.wallList) {
            tempIntersection = getIntersection(p.x, p.y, newX, newY, w.getX1(), w.getY1(), w.getX2(), w.getY2());
            if(tempIntersection != null) {
                //Get intersection of the two lines
                //Check if distance is less than shortest distance
                double tempDistance = getDistance(p.x, p.y, tempIntersection.x, tempIntersection.y);
                if(tempDistance < shortestDistance) {
                    shortestDistance = tempDistance;
                    shortestIntersection = tempIntersection;
                    bounceWall = w;
                }
                wallFlag = true;
            }
        }


        if(wallFlag) {
            assert shortestIntersection != null;
            newX = shortestIntersection.x;
            newY = shortestIntersection.y;

            //Get angle of wall
            double rise = bounceWall.getY1() - bounceWall.getY2(); //Remember Y is flipped
            double run = bounceWall.getX2() - bounceWall.getX1();
            double wallAngle = Math.toDegrees(Math.atan2(rise, run));
            while(wallAngle < 0) {
                wallAngle += 360.00;
            }

            while(command.angle < 0) {
                command.angle += 360.00;
            }

            command.angle += 180.00;
            while(command.angle > 360)
                command.angle -= 360.00;

            double plusAngle = wallAngle + 90;
            double minusAngle = wallAngle - 90;
            if(minusAngle < 0) {
                minusAngle += 360.00;
            }

            double plusCompare = command.angle - plusAngle;
            if(plusCompare < 0) {
                plusCompare += 360.00;
            }
            double minusCompare = command.angle - minusAngle;
            if(minusCompare < 0) {
                minusCompare += 360.00;
            }


            if(plusCompare < minusCompare)
                perpendicularAngle = plusAngle;
            else
                perpendicularAngle = minusAngle;
        }


        //Border
        if(!wallFlag) {
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
        }

        if(bounceFlag || wallFlag) {
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

        //If no border or wall, update position

        //Else if wall or border, bounce off the wall using angle of reflection.
        Main.commandQueue.add(new Command(p, command.velocity, command.angle));
    }

    private double getDistance(Double x, Double y, double x1, double y1) {
        return Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
    }

    private void generateParticle() {
        Particle temp = new Particle(command.x, command.y, command.velocity, command.angle);
        ParticleArea.particleList.add(temp);
        Main.commandQueue.add(new Command(temp, command.velocity, command.angle));
    }

    private void generateWall() {
        ParticleArea.wallList.add(new Wall(command.x1, command.y1, command.x2, command.y2));
    }

    // This function is based on the work of Paul Bourke (1988)
    // https://paulbourke.net/geometry/pointlineplane/
    private Intersection getIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

        // Check if none of the lines are of length 0
        if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
            return null;
        }

        double denominator = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

        // Lines are parallel
        if (denominator == 0) {
            return null;
        }

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        // is the intersection along the segments
        if (ua < 0 || ua > 1 || ub < 0 || ub > 1) {
            return null;
        }

        // Return a object with the x and y coordinates of the intersection
        double x = x1 + ua * (x2 - x1);
        double y = y1 + ua * (y2 - y1);

        return new Intersection(x, y);
    }


}

class Intersection {
    double x;
    double y;
    public Intersection(double x, double y) {
        this.x = x;
        this.y = y;
    }
}