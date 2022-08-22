package schd;
import java.util.Vector;

public class PreemptiveSJF
{
	GraphDraw gd = new GraphDraw();
	
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
        			int i;
        			for(i = readyQueue.size(); i > 0; i--) 
        				if(readyQueue.get(i-1).burstTime <= frontjob.burstTime)
        					break;
        			readyQueue.add(i, new ReadyQueueElement(frontjob.processID, frontjob.burstTime, 0, frontjob.arriveTime));
        			joblist.remove(0);
        			if(i == 0)
        			{
        				if(cpuDone - cpuTime > frontjob.burstTime)
        				{
        					gd.plusP(currentProcess, Start ,runTime);
        					rq.burstTime = cpuDone - cpuTime;
        					cpuDone = frontjob.burstTime;
        					cpuTime = 0;
        					currentProcess = frontjob.processID;
        					Start = runTime;
        					readyQueue.add(0, rq);
        					rq = readyQueue.get(1);
        					readyQueue.remove(1);
        					
                			if(rq.StartP == -1)
                				rq.StartP = runTime;
        				}
        			}
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
        			cpuDone = rq.burstTime;
        			cpuTime = 0;
        			currentProcess = rq.processID;
        			readyQueue.remove(0);
        			Start = runTime;
        			
        			if(rq.StartP == -1)
        				rq.StartP = runTime;
        		}
        	}
        	else
        	{
        		if(cpuTime == cpuDone)
        		{
        			gd.plusP(currentProcess, Start ,runTime);
        			currentProcess = 0;
        			resultlist.add(new Result(rq.processID, rq.StartP, rq.AllburstTime, rq.waitingTime, runTime, rq.arrivalP));
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
