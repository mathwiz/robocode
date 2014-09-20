package Spring.Team3;


import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import yohan.RobotPersonalityAdapter;

import java.awt.*;


public class WallsPersonality extends PersonalityAdapter {

    boolean peek;
    double moveAmount;

    public WallsPersonality(Robot r) {
        super(r);
        init();
    }

    @Override
    public void init() {
        moveAmount = Math.max(getRobot().getBattleFieldWidth(), getRobot().getBattleFieldHeight());
        peek = false;

        getRobot().turnLeft(getRobot().getHeading() % 90);
        getRobot().ahead(moveAmount);

        peek = true;
        getRobot().turnGunRight(90);
        getRobot().turnRight(90);
    }

    @Override
    public void execute() {
        getRobot().setBodyColor(Color.BLUE);
        peek = true;
        getRobot().ahead(moveAmount);
        peek = false;
        getRobot().turnRight(90);
    }

    public void onHitRobot(HitRobotEvent e) {
        // If he's in front of us, set back up a bit.
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            getRobot().back(100);
        } // else he's in back of us, so set ahead a bit.
        else {
            getRobot().ahead(100);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        getRobot().fire(2);
        if (peek) {
            getRobot().scan();
        }
    }

}