package Spring.Team3;

import robocode.*;

import java.awt.*;

import static robocode.util.Utils.*;

public class YohanLee extends AdvancedRobot {

    public static final int TURN_AMOUNT = 10;

    public static final int CLOSE_DISTANCE = 100;

    public static final int FAR_DISTANCE = 150;

    public static final int BIG_MOVE = 40000;

    public static final int ESCAPE_DISTANCE = 60;

    private Strategy trackStrategy;

    private Strategy avoidStrategy;

    private Strategy strategy;

    private String trackName;

    private int count;

    private double gunTurnAmt = 10;

    boolean movingForward;

    private void log(String s, Object... args) {
        out.println(String.format(s, args));
    }

    private double calculateFirePower(double distance) {
        double e = getEnergy();
        if (distance < CLOSE_DISTANCE) {
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
        return 1;
    }

    private boolean isDuel() {
        return getOthers() == 1;
    }

    public YohanLee() {
        trackStrategy = new Strategy() {
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

            @Override
            public void onScannedRobot(ScannedRobotEvent e) {
                if (trackName != null && !e.getName().equals(trackName)) {
                    return;
                }

                if (trackName == null) {
                    trackName = e.getName();
                    log("Tracking %S", trackName);
                }
                count = 0;

                if (e.getDistance() > FAR_DISTANCE) {
                    gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

                    setTurnGunRight(gunTurnAmt);
                    setTurnRight(e.getBearing());
                    setAhead(e.getDistance() - (FAR_DISTANCE - 10));
                    return;
                }

                gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
                turnGunRight(gunTurnAmt);
                fire(3);

                if (e.getDistance() < CLOSE_DISTANCE) {
                    if (e.getBearing() > -90 && e.getBearing() <= 90) {
                        back(ESCAPE_DISTANCE);
                    } else {
                        ahead(ESCAPE_DISTANCE);
                    }
                }
                scan();
            }
        };

        avoidStrategy = new Strategy() {
            @Override
            public void move() {
                AdvancedRobot robot = YohanLee.this;
                setAhead(BIG_MOVE);
                movingForward = true;
                setTurnRight(90);
                waitFor(new TurnCompleteCondition(robot));
                setTurnLeft(180);
                waitFor(new TurnCompleteCondition(robot));
                setTurnRight(180);
                waitFor(new TurnCompleteCondition(robot));
            }

            @Override
            public void onScannedRobot(ScannedRobotEvent e) {
                fire(calculateFirePower(e.getDistance()));
            }
        };

        strategy = avoidStrategy;
    }

    public void run() {
        while (true) {
            strategy.move();
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
        back(ESCAPE_DISTANCE);
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
        setAhead(-1 * ESCAPE_DISTANCE);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        setBodyColor(Color.RED);
        strategy.onScannedRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. %d remaining", robotDeathEvent.getName(), getOthers());
        if (isDuel()) {
            strategy = trackStrategy;
        }
    }

    @Override
    public void onStatus(StatusEvent e) {
        setBodyColor(Color.BLUE);
        if (isDuel()) {
            strategy = trackStrategy;
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
        turnRight(90);
        ahead(ESCAPE_DISTANCE);
    }

    @Override
    public void onWin(WinEvent winEvent) {
        log("That's right!");
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        log("Next time!");
    }

    private interface Strategy {
        void move();

        void onScannedRobot(ScannedRobotEvent event);
    }
}
