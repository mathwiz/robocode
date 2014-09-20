package Spring.Team3;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;

/**
 * Created by e651137 on 8/28/14.
 */
public class YohanLee extends AdvancedRobot {
    Personality p;


    private Personality getRobot() {
        return p;
    }

    public void run() {
        setBodyColor(Color.MAGENTA);
        setGunColor(Color.WHITE);
        setRadarColor(Color.WHITE);
        p = new WallsPersonality(this);

        while (true) {
            getRobot().execute();
        }
    }
}
