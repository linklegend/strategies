package strategies;

import java.util.ArrayList;
import java.util.Observable;
import automail.MailItem;
import automail.PriorityMailItem;

public class MailPool extends Observable implements IMailPool {
	
	public static final String PRIORITY_POOL = "PRIORITY_POOL";
	public static final String NON_PRIORITY_POOL = "NON_PRIORITY_POOL";
	
	private ArrayList<MailItem> nonPriorityPool;
	private ArrayList<PriorityMailItem> priorityPool;
	
	public MailPool(){
		nonPriorityPool = new ArrayList<MailItem>();
		priorityPool = new ArrayList<PriorityMailItem>();
	}
	
	public int getPriorityPoolSize(){
		return priorityPool.size();
	}

	public int getNonPriorityPoolSize() {
		return nonPriorityPool.size();
	}

	public void addToPool(MailItem mailItem) {
		// Check whether it has a priority or not
		if(mailItem instanceof PriorityMailItem){
			// Add to priority items
			PriorityMailItem pMailItem = (PriorityMailItem) mailItem;
			priorityPool.add(pMailItem);
			priorityPool.sort(PriorityMailItem.priorityComparator);
			// notify robot
			notifyObservers(pMailItem.getPriorityLevel());

		}
		else{
			// Add to nonpriority items
			nonPriorityPool.add(mailItem);
			nonPriorityPool.sort(MailItem.arrivalComparator);
		}
	}
	
	public MailItem getNonPriorityMail(){
		if(getNonPriorityPoolSize() > 0){
			return nonPriorityPool.remove(0);
		}
		else{
			return null;
		}
	}
	
	public MailItem getHighestPriorityMail(){
		if(getPriorityPoolSize() > 0){
			return priorityPool.remove(0);
		}
		else{
			return null;
		}
		
	}
	
	public MailItem getBestMail(int FloorFrom, int FloorTo) {
		
		ArrayList<MailItem> tempPriority = new ArrayList<MailItem>();
		
		// Check if there are any priority mail within the range
		for(MailItem m : priorityPool){
			if(isWithinRange(m,FloorFrom,FloorTo)){
				tempPriority.add(m);
			}
		}
		
		// If there is already something in priority then return it as the best mail
		if(tempPriority.size() > 0){
			// Since priorityPool is already sorted, that means items being added are already sorted with the
			// highest priority being in the front of the arraylist
			
			return tempPriority.get(0);
		}
		else{

			ArrayList<MailItem> tempNonPriority = new ArrayList<MailItem>();
			// Try the same thing with nonPriorityPool
			for(MailItem m : nonPriorityPool){
				if(isWithinRange(m,FloorFrom,FloorTo)){
					tempNonPriority.add(m);
				}
			}
			if(tempNonPriority.size() > 0){
				return tempNonPriority.get(0);
			}
		}
		
		return null;
	}
	
	private boolean isWithinRange(MailItem m, int FloorFrom, int FloorTo){

		if(m.getDestFloor() <= FloorTo && m.getDestFloor() >= FloorFrom){
			return true;
		}
		else{
			return false;
		}
		

	}
}
