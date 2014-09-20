package Spring.Team3;

import robocode.*;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.peer.IBasicRobotPeer;
import robocode.robotinterfaces.peer.IStandardRobotPeer;

import java.io.PrintStream;

/**
 * Created by e651137 on 8/28/14.
 */
public class YohanLee implements IBasicEvents, IBasicRobot, Runnable {

    final ThreadLocal<PrintStream> out = new ThreadLocal<>();
    IStandardRobotPeer peer;

    public Runnable getRobotRunnable() {
        return this;
    }

    public IBasicEvents getBasicEventListener() {
        return this;
    }

    public void setPeer(IBasicRobotPeer iRobotPeer) {
        peer = (IStandardRobotPeer) iRobotPeer;
    }

    public void setOut(PrintStream printStream) {
        out.set(printStream);
    }

    private void log(String s, Object... args) {
        out.get().println(String.format(s, args));
    }

    public void run() {
        while (true) {
            log("New Loop. energy %s, pointed %s", peer.getEnergy(), peer.getGunHeading());
            peer.move(100); // Move ahead 100
            peer.turnGun(Math.PI * 2); // Spin gun around
            peer.move(-100); // Move back 100
            peer.turnGun(Math.PI * 2); // Spin gun around
        }
    }

    @Override
    public void onStatus(StatusEvent statusEvent) {
    }

    @Override
    public void onBulletHit(BulletHitEvent bulletHitEvent) {
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
    }

    @Override
    public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
    }

    @Override
    public void onDeath(DeathEvent deathEvent) {
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        peer.turnBody(Math.PI / 2 + e.getBearingRadians());
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
    }

    @Override
    public void onHitWall(HitWallEvent hitWallEvent) {
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        peer.setFire(1);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
        log("%s died. % remaining", robotDeathEvent.getName(), peer.getOthers());
    }

    @Override
    public void onWin(WinEvent winEvent) {
    }
}
