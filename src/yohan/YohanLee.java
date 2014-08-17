package yohan;

import robocode.*;
import robocode.Robot;

import java.awt.*;

public class YohanLee extends AdvancedRobot {
    private static int scans;
    private MoveStrategy moveStrategy;

    public YohanLee() {
        final Robot me = this;
        moveStrategy = new MoveStrategy() {
            @Override
            public void move() {
            }
        };
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    /**
     * run: YohanRobot's default behavior
     */
    public void run() {
        // Initialization of the robot should be put here
        setColors(Color.GREEN, Color.DARK_GRAY, Color.GRAY); // body,gun,radar

        // Robot main loop
        while (true) {
            // Tell the game that when we take move,
            // we'll also want to turn right... a lot.
            setTurnRight(10000);
            // Limit our speed to 5
            setMaxVelocity(5);
            // Start moving (and turning)
            ahead(10000);
            // Repeat.
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
