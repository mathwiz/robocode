package jpmrobot;
import robocode.*;
public class JpmRobot extends Robot
{
	public void log( String logString ) {
		out.println( getName() + " at (" + (int) getX() + "," + (int) getY() + "): " + logString ) ;
	}
}