package Spring.Team3;

import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicEvents3;

/**
 * Created by Yohan on 8/17/2014.
 */
public interface Personality extends IBasicEvents3 {

    void init();

    void execute();
}
