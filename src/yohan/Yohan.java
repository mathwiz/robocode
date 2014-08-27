package yohan;

import robocode.*;

import java.awt.*;
import java.util.Date;

public class Yohan extends AdvancedRobot {

    RobotPersonality r;

    public void run() {
        setBodyColor(Color.BLACK);
        setGunColor(Color.BLACK);
        setRadarColor(Color.BLACK);
        setPersonality();

        while (true) {
            r.execute();
        }
    }

    private void setPersonality() {
        if (new Date().getTime() % 2 == 0) {
            r = new RamPersonality(this);
        } else {
            r = new WallsPersonality(this);
        }
        r.init();
    }


    @Override
    public void onStatus(StatusEvent statusEvent) {
        r.onStatus(statusEvent);
    }

    @Override
    public void onBulletHit(BulletHitEvent bulletHitEvent) {
        r.onBulletHit(bulletHitEvent);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
        r.onBulletHitBullet(bulletHitBulletEvent);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
        r.onBulletMissed(bulletMissedEvent);
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        r.onDeath(deathEvent);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
        r.onHitByBullet(hitByBulletEvent);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        r.onHitRobot(e);
    }

    @Override
    public void onHitWall(HitWallEvent hitWallEvent) {
        r.onHitWall(hitWallEvent);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        r.onScannedRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        r.onRobotDeath(robotDeathEvent);
    }

    @Override
    public void onWin(WinEvent winEvent) {
        r.onWin(winEvent);
    }
}
