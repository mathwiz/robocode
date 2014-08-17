/*******************************************************************************
 * Copyright (c) 2001-2013 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package yohan;


import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;


public class WallsPersonality extends RobotPersonalityAdapter {

    boolean peek; // Don't turn if there's a robot there
    double moveAmount; // How much to move

    public WallsPersonality(Robot r) {
        super(r);
    }

    @Override
    public void init() {
        // Initialize moveAmount to the maximum possible for this battlefield.
        moveAmount = Math.max(getRobot().getBattleFieldWidth(), getRobot().getBattleFieldHeight());
        // Initialize peek to false
        peek = false;

        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        getRobot().turnLeft(getRobot().getHeading() % 90);
        getRobot().ahead(moveAmount);
        // Turn the gun to turn right 90 degrees.
        peek = true;
        getRobot().turnGunRight(90);
        getRobot().turnRight(90);
    }

    @Override
    public void execute() {
        peek = true;
        // Move up the wall
        getRobot().ahead(moveAmount);
        // Don't look now
        peek = false;
        // Turn to the next wall
        getRobot().turnRight(90);
    }

    /**
     * onHitRobot:  Move away a bit.
     */
    public void onHitRobot(HitRobotEvent e) {
        // If he's in front of us, set back up a bit.
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            getRobot().back(100);
        } // else he's in back of us, so set ahead a bit.
        else {
            getRobot().ahead(100);
        }
    }

    /**
     * onScannedRobot:  Fire!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        getRobot().fire(2);
        // Note that scan is called automatically when the robot is moving.
        // By calling it manually here, we make sure we generate another scan event if there's a robot on the next
        // wall, so that we do not start moving up it until it's gone.
        if (peek) {
            getRobot().scan();
        }
    }

}