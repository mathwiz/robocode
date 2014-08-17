package yohan;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class YohanLee extends AdvancedRobot {
    private int turn;

    RobotPersonality r;

    public YohanLee() {
        this.r = new WallsPersonality(this);
    }

    public void run() {
        setBodyColor(Color.WHITE);
        r.init();
        while (true) {
            System.out.println(turn++);
            changePersonality();
            r.execute();
        }
    }

    private void changePersonality() {
        if (turn > 25) {
            r = new RamPersonality(this);
            r.init();
        }
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
