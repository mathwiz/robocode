package Spring.Team3;

import robocode.*;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class YohanLee extends AdvancedRobot {

    public static final double TURN_AMOUNT = 10;

    public static final double CLOSE_DISTANCE = 100;

    public static final double FAR_DISTANCE = CLOSE_DISTANCE * 1.5;

    public static final double BIG_MOVE = 40000;

    public static final double ESCAPE_DISTANCE = CLOSE_DISTANCE * 0.8;

    private Strategy trackStrategy;

    private Strategy avoidStrategy;

    private Strategy strategy;

    private String trackName;

    private int count;

    private int missCount;

    private double gunTurnAmt = 10;

    private boolean movingForward;

    private double lastScanBearing;


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
        } else if (distance > getBattleFieldWidth() / 1.5) {
            return .5;
        }
        return 1;
    }

    private boolean isDuel() {
        return getOthers() == 1;
    }

    private Strategy switchStrategy() {
        count = 0;
        return strategy == trackStrategy ? avoidStrategy : trackStrategy;
    }

    public YohanLee() {
        trackStrategy = new Strategy() {
            @Override
            public void move() {
                count++;
                turnGunRight(gunTurnAmt);
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
                    log("Tracking %s", trackName);
                }
                count = 0;

                gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
                setTurnGunRight(gunTurnAmt);

                if (Math.abs(lastScanBearing - e.getBearing()) < .01) {
                    setFire(3);
                }

                if (e.getDistance() > FAR_DISTANCE) {
                    setTurnRight(e.getBearing());
                    setAhead(e.getDistance() - (FAR_DISTANCE - 10));
                    return;
                } else {
                    setFire(3);
                }

                if (e.getDistance() < CLOSE_DISTANCE) {
                    if (e.getBearing() > -90 && e.getBearing() <= 90) {
                        setBack(ESCAPE_DISTANCE);
                    } else {
                        setAhead(ESCAPE_DISTANCE);
                    }
                }
                scan();
            }

            @Override
            public void onHitRobot(HitRobotEvent e) {
                if (trackName != null && !trackName.equals(e.getName())) {
                    out.println("Tracking " + e.getName() + " due to collision");
                }
                trackName = e.getName();

                gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
                setFire(3);
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
                count++;
                setAhead(BIG_MOVE);
                movingForward = true;
                setTurnGunRight(360);
                setTurnRight(90);
                waitFor(new TurnCompleteCondition(robot));
                setTurnLeft(180);
                waitFor(new TurnCompleteCondition(robot));
                setTurnRight(180);
                waitFor(new TurnCompleteCondition(robot));
            }

            @Override
            public void onScannedRobot(ScannedRobotEvent e) {
                count = 0;
                double absoluteBearing = getHeading() + e.getBearing();
                double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
                if (Math.abs(bearingFromGun) <= 3) {
                    turnGunRight(bearingFromGun);
                    if (getGunHeat() == 0) {
                        fire(calculateFirePower(e.getDistance()));
                    }
                } else {
                    turnGunRight(bearingFromGun);
                }
                if (bearingFromGun == 0) {
                    scan();
                }
            }

            @Override
            public void onHitRobot(HitRobotEvent e) {
                turnGunRight(normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getGunHeading())));
                if (getGunHeat() == 0) {
                    fire(3);
                }
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
                this.move();
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
        if (!e.getName().startsWith("Spring.Team3")) {
            strategy.onHitRobot(e);
        }
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        log("bullet hit from %s", e.getName());
        strategy.onHitByBullet(e);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        setAhead(-1 * Math.max(ESCAPE_DISTANCE, getBattleFieldWidth() / 4.0));
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        log("Scanned %s at bearing %.2f", e.getName(), e.getBearing());
        if (!e.getName().startsWith("Spring.Team3")) {
            setBodyColor(Color.RED);
            strategy.onScannedRobot(e);
            lastScanBearing = e.getBearing();
        } else {
            scan();
        }
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
        } else if (count > 50) {
            strategy = switchStrategy();
        }
    }

    @Override
    public void onBulletHit(BulletHitEvent e) {
        log("bullet hit %s", e.getBullet().getVictim());
        missCount = 0;
    }

    @Override
    public void onBulletMissed(BulletMissedEvent e) {
        if (missCount++ > 15) {
            strategy = switchStrategy();
        }
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
