package schd;

public class ReadyQueueElement {
		  public int processID;
	      public int burstTime;
	      public int waitingTime;
	      public int priority;
	      public int AllburstTime;
	      public int StartP;
	      public int arrivalP;
	      
	      public ReadyQueueElement(int processID, int burstTime, int waitingTime, int arrivalP)
	      {
	          this.processID = processID;
	          this.burstTime = burstTime;
	          this.waitingTime = waitingTime;
	          this.AllburstTime = burstTime;
	          this.StartP = -1;
	          this.arrivalP = arrivalP;
	      }
	      public ReadyQueueElement(int processID, int burstTime, int waitingTime, int priority, int arrivalP)
	      {
	          this.processID = processID;
	          this.burstTime = burstTime;
	          this.waitingTime = waitingTime;
	          this.priority = priority;
	          this.AllburstTime = burstTime;
	          this.StartP = -1;
	          this.arrivalP = arrivalP;
	      }
}

