# Emergency-Room-Priority-Queue

<h3>Description</h3>

Imagine the emergency room (ER) of a hospital. Patients who come to the ER are assessed (triage). We associate the urgency of the patient’s problem with a single value: the priority. We use a min heap to cater to patients with more urgent situations first. So three patients with priority values say 3, 4, 8 would be seen in that order. We use double valued priorities since we want to have flexibility, for example, to add a patient with priority 3.2 between patients with priorities 3 and 4. You should assume the priorities are always be strictly greater than 0.

The ERPriorityQueue class allows us to organize (name, priority) pairs using nodes which are stored in a heap. Each node in the heap is an instance of a Patient class which has two fields (name, priority). The heap implementation will use an ArrayList of these Patients. The Patient class is given to you in the starter code. For grading purposes, we have included a toString() and equals() method in the Patient class. For technical reasons having to do with the grader, we have also made Patient a static inner class.

The ERPriorityQueue class has two fields: an ArrayList of Patient objects called patients and a HashMap nameToIndex. Both of these fields are public for grading purposes. 

The HashMap name- ToIndex is defined as follows: the key is a string and the value is the index of that string in the ArrayList. This allows us to find a patient (given their name) in the priority queue.

The ERPriorityQueue class has many methods. Some of the methods are already implemented for you, namely a constructor, parent(), leftChild(), rightChild(). The rest are described below, and your task will be to implement them.

Heads up: if you read about heaps, you will find that one often refers to the priorities of a heap as “keys”. However, in our example, we also work with a hashmap which has a set of (key,value) pairs, where the key is the patient name. Using the term “key” for both the priority and the name would be confusing. So in this project, we use the term “key” only when referring to the names in the namesToIndex map.

• upHeap(int index) – returns nothing (void); performs the upHeap operation starting at the given index. Your implementation should have worst case time complexity O (log2 n).

• downHeap(int index) – returns nothing (void) ; performs the downHeap operation starting at the given index. Note that the method signatures of upHeap and downHeap are slightly different from the one given in the pseudocode in the lectures. Your implementation should have worst case time complexity O(log2 n).

• contains(String name) – returns a boolean, indicating whether the given string (patient name) is in the heap. Your implementation should have time complexity O(1).

• getPriority(String name) – returns a double which is the priority associated with the given patient name; all patient priorities will be greater than 0; return -1 if the given name does not correspond to a patient. Your implementation should have time complexity O(1).

• peekMin() – returns the name of the patient with minimum priority; returns null if the queue is empty. Your implementation should have time complexity O(1).

• getMinPriority() – returns the minimum priority of all patients in the queue; returns -1 if the queue is empty. Your implementation should have time complexity O(1).

• removeMin() – returns a string – removes the patient that has the minimum priority and returns their name; returns null if queue is empty. Your implementation should have worst case time complexity O(log2 n).

• add(String name, double priority) – adds a new patient to the queue; returns a boolean, indicating whether or not the name was added; return false only if the patient is already in the queue; Your implementation should have worst case time complexity O(log2 n).

• add(String name) – adds a new patient to the queue but now the priority is a default, namely the MAX double value. Likewise, it returns a boolean, indicating whether or not the name was added; return false only if the patient is already in the queue. Your implementation should have worst case time complexity O(log2 n).

• remove(String name) – removes the patient from the priorityqueue; it returns a boolean: true if the patient with that name was indeed in the queue and false otherwise; Your implementation should have worst case time complexity O(log2 n). It must only modify the ancestors and/or descendants of the removed node.

changePriority(String name, double newPriority) – modifies the priority of the patient and adjusts the priority queue, if necessary; returns a boolean – returns true if the patient is indeed in the heap and false otherwise

The idea of this method is that it can happen that the priority of patients in the waiting room can change over time. The patient may have a test done at the hospital (blood test, x-ray, covid, etc) and may get a positive or negative result that changes their priority. Your implementation should have worst case time complexity O(log2 n).

• removeUrgentPatients(double threshold) – removes the patients whose priority value is less than or equal to the given threshold; you can assume that the threshold is positive. Recall that having a low priority value means having a “high priority” i.e. urgent. The method returns an ArrayList of patients. The method returns an ArrayList of patients. Your implementation should have worst case time complexity O(n log2 n).

• removeNonUrgentPatients(double threshold) – removes the patients whose priority value is greater than or equal to the given threshold; you can assume the threshold is positive. Your implementation should have worst case time complexity O(n log2 n).
