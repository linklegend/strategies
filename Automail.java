package strategies;

import automail.CommsRobot;
import automail.IMailDelivery;
import java.util.Observable;
import automail.Robot;
import automail.Simulation;
import automail.StorageTube;

public class Automail {
	      
    public Robot robot;
    public IMailPool mailPool;
    public IRobotBehaviour robotBehaviour;
    public Observable commsStation;
    public static final int COMM_ROBOT_CAPACITY = 4;
    public static final int BIG_ROBOT_CAPACITY = 6;
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	/*hi*/
    	
    	/** Initialize the MailPool */
    	mailPool = new MailPool();
    	commsStation = (Observable) mailPool;
    	
        /** Initialize the RobotAction */
    	// IRobotBehaviour robotBehaviour = new SimpleRobotBehaviour();
    	IRobotBehaviour robotBehaviour = new SmartRobotBehaviour();
    	if (Simulation.automailProperties.getProperty("Robot_Type").equals("Small_Comms_Simple")) {
    		
    		robotBehaviour = new SimpleRobotBehaviour();
    		robot = new CommsRobot(robotBehaviour, delivery, mailPool, COMM_ROBOT_CAPACITY, commsStation);
    	}
    	else if (Simulation.automailProperties.getProperty("Robot_Type").equals("Small_Comms_Smart")) {
    		robotBehaviour = new SmartRobotBehaviour();
    		robot = new CommsRobot(robotBehaviour, delivery, mailPool, COMM_ROBOT_CAPACITY, commsStation);
    	}
    	else {
    		robotBehaviour = new NewSmartRobotBehaviour();
        	robot = new Robot(robotBehaviour, delivery, mailPool, BIG_ROBOT_CAPACITY);
    	}

    	
    }
    
}
