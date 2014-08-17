/*******************************************************************************
 * Copyright (c) 2001-2013 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package yohan;


import robocode.*;


public class WallsPersonality implements RobotPersonality {

    private Robot r;

    boolean peek; // Don't turn if there's a robot there
    double moveAmount; // How much to move

    public WallsPersonality(Robot r) {
        this.r = r;
    }

    /**
     * run: Move around the walls
     */
    public void run() {
        // Initialize moveAmount to the maximum possible for this battlefield.
        moveAmount = Math.max(r.getBattleFieldWidth(), r.getBattleFieldHeight());
        // Initialize peek to false
        peek = false;

        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        r.turnLeft(r.getHeading() % 90);
        r.ahead(moveAmount);
        // Turn the gun to turn right 90 degrees.
        peek = true;
        r.turnGunRight(90);
        r.turnRight(90);

        while (true) {
            // Look before we turn when ahead() completes.
            peek = true;
            // Move up the wall
            r.ahead(moveAmount);
            // Don't look now
            peek = false;
            // Turn to the next wall
            r.turnRight(90);
        }
    }

    /**
     * onHitRobot:  Move away a bit.
     */
    public void onHitRobot(HitRobotEvent e) {
        // If he's in front of us, set back up a bit.
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            r.back(100);
        } // else he's in back of us, so set ahead a bit.
        else {
            r.ahead(100);
        }
    }

    /**
     * onScannedRobot:  Fire!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        r.fire(2);
        // Note that scan is called automatically when the robot is moving.
        // By calling it manually here, we make sure we generate another scan event if there's a robot on the next
        // wall, so that we do not start moving up it until it's gone.
        if (peek) {
            r.scan();
        }
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
        r.onBulletMissed(event
        );
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