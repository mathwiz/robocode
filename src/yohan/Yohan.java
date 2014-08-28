package yohan;

import robocode.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Yohan extends AdvancedRobot {

    RobotPersonality r;
    List<RobotPersonality> personalities = new ArrayList<>();

    public void run() {
        setBodyColor(Color.BLACK);
        setGunColor(Color.BLACK);
        setRadarColor(Color.BLACK);
        personalities.add(new RamPersonality(this));
        personalities.add(new WallsPersonality(this));

        while (true) {
            r = getOthers() < 30 ? personalities.get(0) : personalities.get(1);
            r.execute();
        }
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
