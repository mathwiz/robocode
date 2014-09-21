package Spring.Team3;

import robocode.*;

import java.awt.*;

import static robocode.util.Utils.*;

public class YohanLee extends AdvancedRobot {

    public static final int TURN_AMOUNT = 10;

    public static final int CLOSE_DISTANCE = 100;

    public static final int FAR_DISTANCE = 150;

    public static final int BIG_MOVE = 40000;

    public static final int ESCAPE_DISTANCE = 150;

    private Strategy trackStrategy;

    private Strategy avoidStrategy;

    private Strategy strategy;

    private String trackName;

    private int count;

    private int missCount;

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
            } else if (e > 8) {
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

            @Override
            public void onHitRobot(HitRobotEvent e) {
                double bearing = e.getBearing();
                // Only print if he's not already our target.
                if (trackName != null && !trackName.equals(e.getName())) {
                    out.println("Tracking " + e.getName() + " due to collision");
                }
                trackName = e.getName();
                // Back up a bit.
                // Note:  We won't get scan events while we're doing this!
                // An AdvancedRobot might use setBack(); execute();
                gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
                fire(3);
                setTurnGunRight(gunTurnAmt);
                setBack(ESCAPE_DISTANCE);
            }

            @Override
            public void onHitByBullet(HitByBulletEvent e) {

            }
        };

        avoidStrategy = new Strategy() {
            @Override
            public void move() {
                AdvancedRobot robot = YohanLee.this;
                setAhead(BIG_MOVE);
                setTurnGunRight(360);
                movingForward = true;
                setTurnRight(90);
                waitFor(new TurnCompleteCondition(robot));
            }

            @Override
            public void onScannedRobot(ScannedRobotEvent e) {
                fire(calculateFirePower(e.getDistance()));
            }

            @Override
            public void onHitRobot(HitRobotEvent e) {
                fire(3);
                if (movingForward) {
                    setBack(ESCAPE_DISTANCE);
                    movingForward = false;
                } else {
                    setAhead(ESCAPE_DISTANCE);
                    movingForward = true;
                }
            }

            @Override
            public void onHitByBullet(HitByBulletEvent e) {
                setAhead(BIG_MOVE);
                setTurnLeft(45);
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
        log("hit robot %s at bearing %.2f", e.getName(), e.getBearing());
        strategy.onHitRobot(e);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        log("bullet hit from %s", e.getName());
        strategy.onHitByBullet(e);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
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
        log("bullet hit %s", e.getBullet().getVictim());
        missCount = 0;
    }

    @Override
    public void onBulletMissed(BulletMissedEvent e) {
        missCount++;
        log("bullet missed. %d misses", missCount);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent e) {
        log("bullet hit bullet. wtf?");
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

        void onScannedRobot(ScannedRobotEvent e);

        void onHitRobot(HitRobotEvent e);

        void onHitByBullet(HitByBulletEvent e);
    }
}
