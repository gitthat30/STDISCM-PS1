public class WorkerRunnable implements Runnable {
    Command command;

    WorkerRunnable(Command command) {
        this.command = command;
    }
    @Override
    public void run() {
        switch (command.type) {
            case MOVE -> updateParticlePosition();
            case GENERATE_PARTICLE -> generateParticle();
            case GENERATE_WALL -> generateWall();
        }
    }

    // TODO: do computation for next position and add to commandQueue
    private void updateParticlePosition() {

    }

    private void generateParticle() {
        MainLayout.particlePanel.particleList.add(new Particle(command.x1, command.y1, command.velocity, command.angle));
    }

    private void generateWall() {
        MainLayout.particlePanel.wallList.add(new Wall(command.x1, command.y1, command.x2, command.y2));
    }
}
