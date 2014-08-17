package yohan;

import robocode.AdvancedRobot;
import robocode.Robot;

/**
 * Created by Yohan on 8/17/2014.
 */
public abstract class SuperAdvancedRobot extends AdvancedRobot {
    private MoveStrategy moveStrategy;
    private Robot current;

    public Robot getCurrent() {
        return current;
    }

    public void setCurrent(Robot current) {
        this.current = current;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
}
