package yohan;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Created by Yohan on 8/17/2014.
 */
public class RamPersonality extends RobotPersonalityAdapter {
    int turnDirection = 1; // Clockwise or counterclockwise

    public RamPersonality(Robot r) {
        super(r);
    }

    @Override
    public void init() {

    }

    public void execute() {
        getRobot().turnRight(5 * turnDirection);
    }

    /**
     * onScannedRobot:  We have a target.  Go get it.
     */
    public void onScannedRobot(ScannedRobotEvent e) {

        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        getRobot().turnRight(e.getBearing());
        getRobot().ahead(e.getDistance() + 5);
        getRobot().scan(); // Might want to move ahead again!
    }

    /**
     * onHitRobot:  Turn to face robot, fire hard, and ram him again!
     */
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        getRobot().turnRight(e.getBearing());

        // Determine a shot that won't kill the robot...
        // We want to ram him instead for bonus points
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
        getRobot().ahead(40); // Ram him again!
    }
}
