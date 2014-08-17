package yohan;

import robocode.robotinterfaces.IBasicEvents;

/**
 * Created by Yohan on 8/17/2014.
 */
public interface RobotPersonality extends IBasicEvents {

    public abstract void init();

    public abstract void execute();
}
