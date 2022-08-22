package schd;
import java.util.Vector;

public class RR
{
	GraphDraw gd = new GraphDraw();
	int timeSlice = 5;
	
	public void setTimeSlice(int timeSlice) 
	{
		this.timeSlice = timeSlice;
	}
	
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
        			if(rq.burstTime < timeSlice)
        			{
        				cpuDone = rq.burstTime;
        			}
        			else
        				cpuDone = timeSlice;
        			
        			if(rq.StartP == -1)
        				rq.StartP = runTime;
        			
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
        			if(rq.burstTime - cpuTime == 0) 
        			{
        				gd.plusP(currentProcess, Start ,runTime);
        				resultlist.add(new Result(rq.processID, rq.StartP, rq.AllburstTime, rq.waitingTime, runTime, rq.arrivalP));
        			}
        			else
        			{
        				gd.plusP(currentProcess, Start ,runTime);
        				rq.burstTime -= timeSlice;
        				readyQueue.add(rq);
        			}
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
