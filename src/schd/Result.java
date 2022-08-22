package schd;

public class Result
{
    public int processID;
    public int startP;
    public int burstTime;
    public int waitingTime;
    public int endP;
    public int arrivalP;
    
    public Result(int processID, int startP, int burstTime, int waitingTime, int endP, int arrivalP)
    {
        this.processID = processID;
        this.startP = startP;
        this.burstTime = burstTime;
        this.waitingTime = waitingTime;
        this.endP = endP;
        this.arrivalP = arrivalP;
    } 
}
