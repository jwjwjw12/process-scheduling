package schd;
import java.util.Vector;

public class GraphDraw 
{
	Vector<Integer> PIDList;
	Vector<Integer> StartPList;
	Vector<Integer> EndPList;
	int count;
	
	public GraphDraw() 
	{
		 PIDList = new Vector<Integer>();
		 StartPList = new Vector<Integer>();
		 EndPList = new Vector<Integer>();
		 count = 0;
	}
	
	public void plusP(int PID, int StartP, int EndP) 
	{
		if(PIDList.size() > 0)
		{
			if(PIDList.get(count-1) == PID)
			{
				EndPList.add(count-1, EndP);
				EndPList.remove(count);
				return;
			}
		}
		PIDList.add(PID);
		StartPList.add(StartP);
		EndPList.add(EndP);
		count++;
	}
	
	public int sumOfBurst() {
		int sum = 0;
		
		for(int i = 0; i < count; i++)
		{
			sum += EndPList.get(i) - StartPList.get(i);
		}
		
		return sum;
	}
}
