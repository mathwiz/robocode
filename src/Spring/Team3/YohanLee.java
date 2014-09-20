package Spring.Team3;

import robocode.*;
import robocode.Robot;

import java.awt.*;

/**
 * Created by e651137 on 8/28/14.
 */
public class YohanLee extends AdvancedRobot {
    Personality p;

    private Personality getRobot() {
        return p;
    }

    public void run() {
        setBodyColor(Color.CYAN);
        setGunColor(Color.WHITE);
        setRadarColor(Color.WHITE);
        p = new WallsPersonality(this);

        while (true) {
            getRobot().execute();
        }
    }

    @Override
    public void onStatus(StatusEvent statusEvent) {
        getRobot().onStatus(statusEvent);
    }

    @Override
    public void onBulletHit(BulletHitEvent bulletHitEvent) {
        getRobot().onBulletHit(bulletHitEvent);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
        getRobot().onBulletHitBullet(bulletHitBulletEvent);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
        getRobot().onBulletMissed(bulletMissedEvent);
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        getRobot().onDeath(deathEvent);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
        getRobot().onHitByBullet(hitByBulletEvent);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        getRobot().onHitRobot(e);
    }

    @Override
    public void onHitWall(HitWallEvent hitWallEvent) {
        getRobot().onHitWall(hitWallEvent);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        getRobot().onScannedRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        getRobot().onRobotDeath(robotDeathEvent);
    }

    @Override
    public void onWin(WinEvent winEvent) {
        getRobot().onWin(winEvent);
    }
}
