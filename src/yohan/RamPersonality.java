package yohan;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class RamPersonality extends RobotPersonalityAdapter {
    int turnDirection = 1; // Clockwise or counterclockwise

    public RamPersonality(Robot r) {
        super(r);
        init();
    }

    @Override
    public void init() {

    }

    public void execute() {
        getRobot().turnRight(5 * turnDirection);
    }

    public void onScannedRobot(ScannedRobotEvent e) {

        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        getRobot().turnRight(e.getBearing());
        getRobot().ahead(e.getDistance() + 5);
        getRobot().scan();
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        getRobot().turnRight(e.getBearing());

        if (e.getEnergy() > 16) {
            getRobot().fire(3);
        } else if (e.getEnergy() > 10) {
            getRobot().fire(2);
        } else if (e.getEnergy() > 4) {
            getRobot().fire(1);
        } else if (e.getEnergy() > 2) {
            getRobot().fire(.5);
        } else if (e.getEnergy() > .4) {
            getRobot().fire(.1);
        }
        getRobot().ahead(40);
    }
}
