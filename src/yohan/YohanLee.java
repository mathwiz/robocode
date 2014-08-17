package yohan;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.Random;

public class YohanLee extends SuperAdvancedRobot {
    private static int scans;
    private MoveStrategy[] moveStrategies = new MoveStrategy[2];
    private int turnDirection = 1;
    private Random random = new Random();

    public YohanLee() {
        moveStrategies[0] = new MoveStrategy() {
            @Override
            public void move() {
                setTurnRight(10000);
                setMaxVelocity(5);
                ahead(10000);
            }
        };
        moveStrategies[1] = new MoveStrategy() {
            @Override
            public void move() {
                turnRight(5 * turnDirection);
            }
        };
    }

    /**
     * run: YohanRobot's default behavior
     */
    public void run() {
        // Initialization of the robot should be put here
        setColors(Color.BLACK, Color.ORANGE, Color.GRAY); // body,gun,radar
        getCurrent().run();
        setMoveStrategy(moveStrategies[1]);

//        // Robot main loop
        while (true) {
            getMoveStrategy().move();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */

    public void onScannedRobot(ScannedRobotEvent e) {
        setMoveStrategy(moveStrategies[1]);
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        turnRight(e.getBearing());
        ahead(e.getDistance() + 5);
        scan(); // Might want to move ahead again!
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
    }

    public void onHitRobot(HitRobotEvent e) {
        setMoveStrategy(moveStrategies[1]);
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnRight(e.getBearing());

        // Determine a shot that won't kill the robot...
        // We want to ram him instead for bonus points
        if (e.getEnergy() > 16) {
            fire(3);
        } else if (e.getEnergy() > 10) {
            fire(2);
        } else if (e.getEnergy() > 4) {
            fire(1);
        } else if (e.getEnergy() > 2) {
            fire(.5);
        } else if (e.getEnergy() > .4) {
            fire(.1);
        }
        ahead(40); // Ram him again!
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
    }
}
