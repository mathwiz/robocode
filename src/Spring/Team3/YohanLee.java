package Spring.Team3;

import robocode.*;

import java.awt.*;

import static robocode.util.Utils.*;

public class YohanLee extends AdvancedRobot {

    public static final int TURN_AMOUNT = 10;

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
        while (true) {
            // turn the Gun (looks for enemy)
            turnGunRight(gunTurnAmt);
            // Keep track of how long we've been looking
            count++;
            // If we've haven't seen our target for 2 turns, look left
            if (count > 2) {
                gunTurnAmt = -1 * TURN_AMOUNT;
            }
            // If we still haven't seen our target for 5 turns, look right
            if (count > 5) {
                gunTurnAmt = TURN_AMOUNT;
            }
            // If we *still* haven't seen our target after 10 turns, find another target
            if (count > 11) {
                trackName = null;
            }
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
        turnRight(normalRelativeAngleDegrees(90 - getHeading()));
        ahead(moveSize());
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double bearing = e.getBearing();
        log("scanned %.2f", bearing);
        setBodyColor(Color.RED);
        // If we have a target, and this isn't it, return immediately
        // so we can get more ScannedRobotEvents.
        if (trackName != null && !e.getName().equals(trackName)) {
            return;
        }

        // If we don't have a target, well, now we do!
        if (trackName == null) {
            trackName = e.getName();
            out.println("Tracking " + trackName);
        }
        // This is our target.  Reset count (see the run method)
        count = 0;
        // If our target is too far away, turn and move toward it.
        if (e.getDistance() > 150) {
            gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

            turnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
            turnRight(e.getBearing()); // and see how much Tracker improves...
            // (you'll have to make Tracker an AdvancedRobot)
            ahead(e.getDistance() - 140);
            return;
        }

        // Our target is close.
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
        turnGunRight(gunTurnAmt);
        fire(3);

        // Our target is too close!  Back up.
        if (e.getDistance() < 100) {
            if (e.getBearing() > -90 && e.getBearing() <= 90) {
                back(40);
            } else {
                ahead(40);
            }
        }
        scan();
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. %d remaining", robotDeathEvent.getName(), getOthers());
    }

    @Override
    public void onStatus(StatusEvent e) {
        setBodyColor(Color.BLUE);
        //        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
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
}
