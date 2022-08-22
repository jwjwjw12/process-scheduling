package schd;

public class Process 
{
	public int processID;
	public int arriveTime;
	public int burstTime;
	public int priority;
	
	public Process(int procssID, int arriveTime, int burstTime, int priority) 
	{
		this.processID = procssID;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.priority = priority;
	}
}
