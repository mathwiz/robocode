package yohan;

import robocode.AdvancedRobot;

/**
 * Created by Yohan on 8/17/2014.
 */
public abstract class SuperAdvancedRobot extends AdvancedRobot {
    private MoveStrategy moveStrategy;

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
}
