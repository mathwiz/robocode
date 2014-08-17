package yohan;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class YohanLee extends SuperAdvancedRobot {

    boolean peek; // Don't turn if there's a robot there
    double moveAmount; // How much to move
    RobotPersonality r;

    public void run() {
        setBodyColor(Color.WHITE);
        r = new WallsPersonality(this);
        r.run();
    }

    /**
     * onHitRobot:  Move away a bit.
     */
    public void onHitRobot(HitRobotEvent e) {
        r.onHitRobot(e);
    }

    /**
     * onScannedRobot:  Fire!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        r.onScannedRobot(e);
    }
}
