package yohan;

import robocode.*;

/**
 * Created by Yohan on 8/17/2014.
 */
public abstract class RobotPersonalityAdapter implements RobotPersonality {
    private final Robot r;

    protected RobotPersonalityAdapter(Robot r) {
        this.r = r;
    }

    public final Robot getRobot() {
        return r;
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        r.onHitRobot(event);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        r.onScannedRobot(event);
    }

    @Override
    public void onStatus(StatusEvent event) {
        r.onStatus(event);
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        r.onBulletHit(event);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent event) {
        r.onBulletHitBullet(event);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
        r.onBulletMissed(event);
    }

    @Override
    public void onDeath(DeathEvent event) {
        r.onDeath(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        r.onHitByBullet(event);
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        r.onHitWall(event);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        r.onRobotDeath(event);
    }

    @Override
    public void onWin(WinEvent event) {
        r.onWin(event);
    }
}
