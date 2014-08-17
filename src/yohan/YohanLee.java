package yohan;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.Date;

public class YohanLee extends AdvancedRobot {

    RobotPersonality r;

    public void run() {
        setBodyColor(Color.WHITE);

        if (new Date().getTime() % 2 == 0) {
            r = new RamPersonality(this);
        } else {
            r = new WallsPersonality(this);
        }
        r.init();
        while (true) {
            r.execute();
        }
    }

    private void changePersonality() {
        r = new RamPersonality(this);
        r.init();
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
