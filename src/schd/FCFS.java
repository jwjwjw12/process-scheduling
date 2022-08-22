package schd;
import java.util.Vector;

public class FCFS
{
	public GraphDraw gd = new GraphDraw();
	
	public Vector<Result> run(Vector<Process> joblist, Vector<Result> resultlist) 
	{
        int currentProcess = 0;
        int cpuTime = 0;
        int cpuDone = 0;
        int runTime = 0;
        int Start = 0;
        
        Vector<ReadyQueueElement> readyQueue = new Vector<ReadyQueueElement>();
      
        ReadyQueueElement rq = null;
        
        do 
        {
        	while(joblist.size() != 0) 
        	{
        		Process frontjob = joblist.get(0);
        		if(frontjob.arriveTime == runTime) 
        		{
        			readyQueue.add(new ReadyQueueElement(frontjob.processID, frontjob.burstTime, 0, frontjob.arriveTime));
        			joblist.remove(0);
        		}
        		else 
        		{
        			break;
        		}
        	}
        	
        	if(currentProcess == 0)
        	{
        		if(readyQueue.size() != 0)
        		{
        			rq = readyQueue.get(0);
        			resultlist.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, runTime + rq.burstTime, rq.arrivalP));
        			cpuDone = rq.burstTime;
        			cpuTime = 0;
        			currentProcess = rq.processID;
        			readyQueue.remove(0);
        			Start = runTime;
        		}
        	}
        	else
        	{
        		if(cpuTime == cpuDone)
        		{
        			gd.plusP(currentProcess, Start ,runTime);
        			currentProcess = 0;
        			continue;
        		}
        	}
        	cpuTime++;
        	runTime++;
        	
        	for(int i = 0; i < readyQueue.size(); i++)
        		readyQueue.get(i).waitingTime++;
        	
        } while (joblist.size() != 0 || readyQueue.size() != 0 || currentProcess != 0);
        
        return resultlist;
	}
}
