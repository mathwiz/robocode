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
        setGunColor(Color.YELLOW);
        setRadarColor(Color.RED);
        setPersonality();

        while (true) {
            r.execute();
        }
    }

    private void setPersonality() {
        if (new Date().getTime() % 2 == 0) {
            r = new RamPersonality(this);
        } else {
            r = new WallsPersonality(this);
        }
        r.init();
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        r.onHitRobot(e);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        r.onScannedRobot(e);
    }
}
