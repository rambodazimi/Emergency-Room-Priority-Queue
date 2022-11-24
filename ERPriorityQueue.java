import java.util.ArrayList;
import java.util.HashMap;

public class ERPriorityQueue{

	public ArrayList<Patient> patients;
	public HashMap<String,Integer> nameToIndex;

	public ERPriorityQueue(){

		//  use a dummy node so that indexing starts at 1, not 0

		patients = new ArrayList<Patient>();
		patients.add(new Patient("dummy", 0.0));

		nameToIndex = new HashMap<String,Integer>();
	}

	private int parent(int i){
		return i/2;
	}

	private int leftChild(int i){
	    return 2*i;
	}

	private int rightChild(int i){
	    return 2*i+1;
	}

//Little Problem! (see upHeap Test 4)
	public void upHeap(int i){
		while((i > 1) && (patients.get(i).getPriority() <= patients.get(i/2).getPriority())) {
			swap(i, i/2);
			i = i/2;
		}
	}	
	
//Done!
	public void swap(int first, int second) {	//swap to patients (patients + nameToIndex)
		//swapping the 2 elements in the patients array list
		String tmpName = patients.get(first).getName();
		double tmpPriority = patients.get(first).getPriority();
		patients.get(first).setName(patients.get(second).getName());
		patients.get(first).setPriority(patients.get(second).getPriority());
		patients.get(second).setName(tmpName);
		patients.get(second).setPriority(tmpPriority);
		
		//update the hash map
		swapHashMap(patients.get(first).getName() , patients.get(second).getName());
	}

//Done!
    public void swapHashMap(String key1, String key2){
        Integer temp = nameToIndex.get(key1);
        nameToIndex.put(key1, nameToIndex.get(key2));
        nameToIndex.put(key2, temp);
    }
	
//Done!
	public void downHeap(int i){
		Patient minChild = patients.get(0);
		int index = 0;
		while(2*i <= patients.size()-1) {	//if there is a left child
			Patient leftChild = patients.get(2*i);

			if((1+(2*i)) > patients.size()) {	//it means we have right child as well!
				Patient rightChild = patients.get((2*i)+1);
				minChild = min(leftChild, rightChild);	//find the minimum child
			}else {	//if we only have left child
				minChild = leftChild;
			}
			if(minChild.getPriority() < patients.get(i).getPriority()) {
				index = nameToIndex.get(minChild.getName());
				swap(i, index);
				i = index;
			} else return;
		}
	}

//Done!
	public Patient min(Patient left, Patient right) {	//returns the smaller child
		if(left.getPriority() <= right.getPriority()) {
			return left;
		}else {
			return right;
		}
	}
	
//Done!
	public boolean contains(String name){
		return nameToIndex.get(name) != null;
	}
	
//Done!
	public double getPriority(String name){
		Integer index = nameToIndex.get(name);
		if(index == null) return -1;
		else return patients.get(index).getPriority();
	}

//Done!
	public double getMinPriority(){
		if(patients.size() == 1) return -1;
		
		double smallestPriority = patients.get(1).getPriority();
		for(int i = 1 ; i < patients.size() ; i++) {
			if(patients.get(i).getPriority() < smallestPriority) {
				smallestPriority = patients.get(i).getPriority();
			}
		}
		return smallestPriority;	
	}

//Done!
	public String removeMin(){
		if(patients.size() == 1) return null;
		String removedName = peekMin();
		Integer indexToDelete = nameToIndex.get(removedName);
		Patient removePatient = patients.get(indexToDelete);
		patients.remove(removePatient);		//remove the patient from patients array list
		nameToIndex.remove(removedName);	//remove the patient from nameToIndex hash map	
		updateHashMap();
		downHeap(indexToDelete);
		return removedName;
	}
	
//Done!	
	public void updateHashMap() {
		nameToIndex.clear();
		for(int i = 1 ; i < patients.size() ; i ++) {
			nameToIndex.put(patients.get(i).getName(), i);
		}
	}
	
//Done!
	public String peekMin(){
		if(patients.size() == 1) return null;
		double smallestPriority = patients.get(1).getPriority();
		String name = patients.get(1).getName();
		for(int i = 1 ; i < patients.size() ; i++) {
			if(patients.get(i).getPriority() < smallestPriority) {
				smallestPriority = patients.get(i).getPriority();
				name = patients.get(i).getName();
			}
		}
		return name;
	}

	/*
	 * There are two add methods.  The first assumes a specific priority.
	 * The second gives a default priority of Double.POSITIVE_INFINITY
	 *
	 * If the name is already there, then return false.
	 */

//Done!	
	public boolean add(String name, double priority){
		for(int i = 1 ; i < patients.size() ; i ++) {	//check whether the name is already there or not
			if(patients.get(i).getName() == name) {
				return false;
			}
		}
		Patient aPatient = new Patient(name, priority);
		Integer firstAvailableIndex = patients.size();
		patients.add(aPatient);
		nameToIndex.put(name, firstAvailableIndex);
		upHeap(firstAvailableIndex);
		return true;
	}

//Done!
	public boolean add(String name){
		for(int i = 1 ; i < patients.size() ; i ++) {	//check whether the name is already there or not
			if(patients.get(i).getName() == name) {
				return false;
			}
		}
		Patient aPatient = new Patient(name, Double.POSITIVE_INFINITY);
		Integer firstAvailableIndex = patients.size();
		patients.add(aPatient);
		nameToIndex.put(name, firstAvailableIndex);
		//We don't need to call upHeap, because the priority is +INF. So, it'll be automatically at the end of the list
		return true;
	}

//A little problem!
	public boolean remove(String name){
		Patient removedPatient = patients.get(0);
		boolean isFound = false;
		for(int i = 1 ; i < patients.size() ; i ++) {	//check whether a patient with that name exists or not
			if(name.equals(patients.get(i).getName())) {
				isFound = true;
				removedPatient = patients.get(i);	
				break;
			}
		}
		if(isFound) {	//if exists, then remove it!
			int index = nameToIndex.get(name);
			swap(index, patients.size()-1);
			
			patients.remove(patients.get(patients.size()-1));
			nameToIndex.remove(name);
			updateHashMap();
		//	upHeap(index);
			downHeap(index);
			return true;
		}
		return false;
	}

	/*
	 *   If new priority is different from the current priority then change the priority
	 *   (and possibly modify the heap).
	 *   If the name is not there, return false
	 */

//Done!
	public boolean changePriority(String name, double priority){
		boolean isFound = false;
		int foundIndex;
		for(foundIndex = 1 ; foundIndex < patients.size() ; foundIndex++) {	//check whether a patient with that name exists or not
			if(name.equals(patients.get(foundIndex).getName())) {
				isFound = true;
				break;
			}
		}
		if(!isFound) {
			return false;
		}else {	//if found
			if(patients.get(foundIndex).getPriority() == priority) {	//if priority is the same
				return true;	//do nothing!
			}else {	//change the priority
				patients.get(foundIndex).setPriority(priority);
				downHeap(foundIndex);
				upHeap(foundIndex);
				updateHashMap();
				return true;
			}
		}
	}

//Problem with remove(name) method	
	public ArrayList<Patient> removeUrgentPatients(double threshold){
		ArrayList<Patient> removedPatients = new ArrayList<Patient>();
		for(int i = 1 ; i < patients.size() ; i ++) {
			if(patients.get(i).getPriority() <= threshold) {
				removedPatients.add(patients.get(i));
				remove(patients.get(i).getName());
			}
		}
		updateHashMap();
		return removedPatients;
	}

//Problem with remove(name) method	
	public ArrayList<Patient> removeNonUrgentPatients(double threshold){
		ArrayList<Patient> removedPatients = new ArrayList<Patient>();
		for(int i = 1 ; i < patients.size() ; i ++) {
			if(patients.get(i).getPriority() >= threshold) {
				removedPatients.add(patients.get(i));
				remove(patients.get(i).getName());
			}
		}
		updateHashMap();
		return removedPatients;
	}

	static class Patient{
		private String name;
		private double priority;

		Patient(String name,  double priority){
			this.name = name;
			this.priority = priority;
		}

		Patient(Patient otherPatient){
			this.name = otherPatient.name;
			this.priority = otherPatient.priority;
		}

		double getPriority() {
			return this.priority;
		}

		void setPriority(double priority) {
			this.priority = priority;
		}

		String getName() {
			return this.name;
		}
		
		void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString(){
			return this.name + " - " + this.priority;
		}

		public boolean equals(Object obj){
			if (!(obj instanceof  ERPriorityQueue.Patient)) return false;
			Patient otherPatient = (Patient) obj;
			return this.name.equals(otherPatient.name) && this.priority == otherPatient.priority;
		}
	}
}

