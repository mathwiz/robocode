package Spring.Team3;

import robocode.*;
import robocode.util.Utils;

public class YohanLee extends AdvancedRobot {
    private String trackName;

    private int turns;

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

    public void flipEscapeDistance() {
        this.escapeDistance *= -1;
    }

    private double moveSize() {
        return Math.min(getBattleFieldHeight(), getBattleFieldWidth());
    }

    private double calculateFirePower() {
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
            turnRight(5 * getTurnDirection());
        }
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        double bearing = e.getBearing();
        log("hit %s at bearing %.2f", e.getName(), bearing);
        if (isDuel()) {
            flipDirection(bearing);
            turnRight(bearing);
            fire(calculateFirePower());
            ahead(getAttackDistance());
        }
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
        //        turnRight(Utils.normalRelativeAngleDegrees(90 - getHeading()));
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double bearing = e.getBearing();
        log("scanned %.2f", bearing);
        if (isDuel()) {
            flipDirection(bearing);
            turnRight(bearing);
            ahead(e.getDistance() + 5);
            scan();
        } else {
//            turnGunRight(getHeading() - getGunHeading() + e.getBearing());
//            fire(1);
        }
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. %d remaining", robotDeathEvent.getName(), getOthers());
    }

    @Override
    public void onStatus(StatusEvent e) {
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
        //        turnRight(getTurnDirection() * getTurnSize());
        //        ahead(escapeDistance);
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
