package yohan;

import robocode.*;

import java.awt.*;
import java.util.Date;

public class YohanLee extends AdvancedRobot {

    RobotPersonality r;

    public void run() {
        setBodyColor(Color.WHITE);
        setGunColor(Color.YELLOW);
        setRadarColor(Color.RED);
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
    public void onHitRobot(HitRobotEvent e) {
        r.onHitRobot(e);
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        r.onHitWall(event);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        r.onScannedRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        r.onRobotDeath(event);
    }

    @Override
    public void onWin(WinEvent event) {
        r.onWin(event);
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
}
