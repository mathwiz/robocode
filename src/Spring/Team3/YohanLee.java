package Spring.Team3;

import robocode.*;

public class YohanLee extends AdvancedRobot {
    MoveStrategy ms;

    private void log(String s, Object... args) {
        out.println(String.format(s, args));
    }

    public void run() {
        ms = new MoveStrategy() {
            @Override
            public double size() {
                return Math.min(getBattleFieldHeight(), getBattleFieldWidth());
            }
        };

        while (true) {
            log("New Loop. heading %.2f, gun heading %.2f", getHeading(), getGunHeading());
            ahead(ms.size());
            setTurnRight(180);
            ahead(-1 * ms.size());
            setTurnGunLeft(180);
        }
    }

    @Override
    public void onStatus(StatusEvent e) {
    }

    @Override
    public void onBulletHit(BulletHitEvent e) {
        log("bullet hit %s", e.getName());
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent e) {
        log("bullet hit bullet. wtf?");
    }

    @Override
    public void onBulletMissed(BulletMissedEvent e) {
        log("bullet missed from %s", e.getBullet().getName());
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        log("Next time!");
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        log("bullet hit from %s", e.getName());
        turnLeft(e.getBearing());
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        log("hit %s", e.getName());
        if (e.isMyFault()) {
            turnRight(e.getBearing());
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        log("hit wall at (%.2f, %.2f)", getX(), getY());
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double bearing = e.getBearing();
        log("scanned %.2f", bearing);
        setTurnGunRight(bearing);
        setFire(1);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. %d remaining", robotDeathEvent.getName(), getOthers());
    }

    @Override
    public void onWin(WinEvent winEvent) {
        log("That's RIGHT!");
    }

    private interface MoveStrategy {
        double size();
    }

}
