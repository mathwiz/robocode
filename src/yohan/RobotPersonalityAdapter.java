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
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
    }

    @Override
    public void onStatus(StatusEvent event) {
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent event) {
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
    }

    @Override
    public void onDeath(DeathEvent event) {
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
    }

    @Override
    public void onHitWall(HitWallEvent event) {
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
    }

    @Override
    public void onWin(WinEvent event) {
    }
}
