package yohan;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class YohanLee extends SuperAdvancedRobot {
    private static int scans;

    /**
     * run: YohanRobot's default behavior
     */
    public void run() {
        // Initialization of the robot should be put here
        setColors(Color.BLACK, Color.ORANGE, Color.GRAY); // body,gun,radar
        setMoveStrategy(new MoveStrategy() {
            @Override
            public void move() {
                setTurnRight(10000);
                setMaxVelocity(5);
                ahead(10000);
            }
        });

        // Robot main loop
        while (true) {
            getMoveStrategy().move();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */

    public void onScannedRobot(ScannedRobotEvent e) {
        // Replace the next line with any behavior you would like
        scans++;
        if (scans % 2 == 0)
            fire(3);
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        turnRight(90);
        back(10);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        back(100);
        turnRight(90);
    }
}
