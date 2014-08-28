package Spring.Team3;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import yohan.RamPersonality;
import yohan.WallsPersonality;

import java.awt.*;

/**
 * Created by e651137 on 8/28/14.
 */
public class YohanLee extends AdvancedRobot {
    int turnDirection = 1; // Clockwise or counterclockwise

    private Robot getRobot() {
        return this;
    }

    public void run() {
        setBodyColor(Color.WHITE);
        setGunColor(Color.WHITE);
        setRadarColor(Color.WHITE);

        while (true) {
            getRobot().turnRight(5 * turnDirection);
        }
    }

    public void execute() {
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
