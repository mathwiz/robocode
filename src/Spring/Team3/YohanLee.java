package Spring.Team3;

import robocode.*;

import java.awt.*;

import static robocode.util.Utils.*;

public class YohanLee extends AdvancedRobot {

    public static final int TURN_AMOUNT = 10;

    public static final int CLOSE_DISTANCE = 100;

    public static final int FAR_DISTANCE = 150;

    private MoveStrategy trackStrategy;

    private MoveStrategy trackScanStrategy;

    private MoveStrategy avoidStrategy;

    private MoveStrategy moveStrategy;

    private String trackName;

    private int count;

    private double gunTurnAmt = 10;

    private int turnDirection = 1;

    private int escapeDistance = 50;

    private int attackDistance = 40;

    private int turnSize = 90;

    private void log(String s, Object... args) {
        out.println(String.format(s, args));
    }

    private int getTurnDirection() {
        return turnDirection;
    }

    private double getTurnSize() {
        return turnSize;
    }

    public int getAttackDistance() {
        return attackDistance;
    }

    public int getEscapeDistance() {
        return escapeDistance;
    }

    private double moveSize() {
        return Math.min(getBattleFieldHeight(), getBattleFieldWidth());
    }

    private double calculateFirePower(double distance) {
        double e = getEnergy();
        if (e > 16) {
            return 3;
        } else if (e > 10) {
            return 2;
        } else if (e > 4) {
            return 1;
        } else if (e > 2) {
            return .5;
        }
        return .1;
    }

    private boolean isDuel() {
        return getOthers() == 1;
    }

    private void flipDirection(double bearing) {
        if (bearing >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
    }

    public void run() {
        //        setAdjustGunForRobotTurn(true);
        trackStrategy = new MoveStrategy() {
            @Override
            public void move() {
                turnGunRight(gunTurnAmt);
                count++;
                if (count > 2) {
                    gunTurnAmt = -1 * TURN_AMOUNT;
                }
                if (count > 5) {
                    gunTurnAmt = TURN_AMOUNT;
                }
                if (count > 11) {
                    trackName = null;
                }
            }
        };

        avoidStrategy = new MoveStrategy() {
            @Override
            public void move() {
                setTurnRight(10000);
                setMaxVelocity(5);
                ahead(10000);
            }
        };

        moveStrategy = avoidStrategy;

        while (true) {
            moveStrategy.move();
        }
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        double bearing = e.getBearing();
        log("hit %s at bearing %.2f", e.getName(), bearing);
        // Only print if he's not already our target.
        if (trackName != null && !trackName.equals(e.getName())) {
            out.println("Tracking " + e.getName() + " due to collision");
        }
        // Set the target
        trackName = e.getName();
        // Back up a bit.
        // Note:  We won't get scan events while we're doing this!
        // An AdvancedRobot might use setBack(); execute();
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
        turnGunRight(gunTurnAmt);
        fire(3);
        back(getEscapeDistance());
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        log("bullet hit from %s", e.getName());
        //        turnRight(getTurnSize());
        //        ahead(getEscapeDistance());
        //        flipEscapeDistance();
        //        scan();
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        log("hit wall at (%.2f, %.2f)", getX(), getY());
        //turnRight(normalRelativeAngleDegrees(90 - getHeading()));
        setAhead(-1 * getEscapeDistance());
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        setBodyColor(Color.RED);
        double bearing = e.getBearing();
        log("scanned %s at bearing %.2f", e.getName(), bearing);
        if (moveStrategy == trackStrategy) {
            if (trackName != null && !e.getName().equals(trackName)) {
                return;
            }

            if (trackName == null) {
                trackName = e.getName();
                log("Tracking %S", trackName);
            }
            count = 0;
            // If our target is too far away, turn and move toward it.
            if (e.getDistance() > FAR_DISTANCE) {
                gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

                setTurnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
                setTurnRight(e.getBearing()); // and see how much Tracker improves...
                // (you'll have to make Tracker an AdvancedRobot)
                setAhead(e.getDistance() - (FAR_DISTANCE - 10));
                return;
            }

            // Our target is close.
            gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
            turnGunRight(gunTurnAmt);
            fire(3);

            if (e.getDistance() < CLOSE_DISTANCE) {
                if (e.getBearing() > -90 && e.getBearing() <= 90) {
                    back(getEscapeDistance());
                } else {
                    ahead(getEscapeDistance());
                }
            }
            scan();
        }

    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. %d remaining", robotDeathEvent.getName(), getOthers());
        if (isDuel()) {
            moveStrategy = trackStrategy;
        }
    }

    @Override
    public void onStatus(StatusEvent e) {
        setBodyColor(Color.BLUE);
        if (isDuel()) {
            moveStrategy = trackStrategy;
        }
    }

    @Override
    public void onBulletHit(BulletHitEvent e) {
        log("bullet hit %s", e.getName());
    }

    @Override
    public void onBulletMissed(BulletMissedEvent e) {
        log("bullet missed from %s", e.getBullet().getName());
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent e) {
        log("bullet hit bullet. wtf?");
        turnRight(getTurnDirection() * getTurnSize());
        ahead(getEscapeDistance());
    }

    @Override
    public void onWin(WinEvent winEvent) {
        log("That's right!");
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        log("Next time!");
    }

    private interface MoveStrategy {
        void move();
    }

    private interface ScannedRobotListener {
        void onScan(ScannedRobotEvent event);
    }
}
