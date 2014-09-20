package Spring.Team3;

import robocode.*;
import yohan.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by e651137 on 8/28/14.
 */
public class YohanLee extends AdvancedRobot {
    Personality p;

    List<Personality> personalities = new ArrayList<>();

    private Personality getPersonality() {
        return p;
    }

    public void run() {
        setBodyColor(Color.WHITE);
        setGunColor(Color.WHITE);
        setRadarColor(Color.WHITE);
        personalities.add(new WallsPersonality(this));
        personalities.add(new RamPersonality(this));
        p = personalities.get(0);

        while (true) {
            getPersonality().execute();
        }
    }

    private void changePersonality() {
        if (getOthers() == 1) {
            p = personalities.get(1);
        }
    }

    @Override
    public void onStatus(StatusEvent statusEvent) {
        getPersonality().onStatus(statusEvent);
    }

    @Override
    public void onBulletHit(BulletHitEvent bulletHitEvent) {
        getPersonality().onBulletHit(bulletHitEvent);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
        getPersonality().onBulletHitBullet(bulletHitBulletEvent);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
        getPersonality().onBulletMissed(bulletMissedEvent);
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
        getPersonality().onDeath(deathEvent);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
        getPersonality().onHitByBullet(hitByBulletEvent);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        getPersonality().onHitRobot(e);
    }

    @Override
    public void onHitWall(HitWallEvent hitWallEvent) {
        getPersonality().onHitWall(hitWallEvent);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        getPersonality().onScannedRobot(e);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        getPersonality().onRobotDeath(robotDeathEvent);
        changePersonality();
    }

    @Override
    public void onWin(WinEvent winEvent) {
        getPersonality().onWin(winEvent);
    }
}
