package interval;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * mutable
 * @author HUAWEI
 *
 */
public class ProcessIntervalSet extends MultiIntervalSet<Process>{
	//List<Process> notTerminated = new ArrayList<>();
	Map<Process, Long> notTerminated = new HashMap<>();//maps process to execution time
	
	// AF: 表示进程安排的集合
	// RI: 时间段不冲突
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	public long getExecutionTime(Process p)
	{
		return notTerminated.get(p);
	}
	
	public void setExecutionTime(Process p, long et)
	{
		notTerminated.put(p, et);
	}
	
	public void addNotTerminated(Process p)
	{
		notTerminated.put(p, (long) 0);
	}
	
	public void removeNotTerminated(Process p)
	{
		notTerminated.remove(p);
	}
	
	public boolean allTerminated()
	{
		return notTerminated.size()==0?true:false;
	}
	
	/**
	 * 随机选择一个进程
	 * @return 选择的进程
	 */
	public Process randomOne()
	{
		Random random = new Random();
		int rn = random.nextInt(notTerminated.size());
		int i = 0;
		for(Process p: notTerminated.keySet())
		{
			if(i==rn)
			{
				return p;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * 检查当前notTerminated进程集合中是否有符合结束要求，可以结束的进程，如果有，就将其从集合中移除
	 */
	public void checkTerminated()
	{
		//Process p = notTerminated.get(i);
		Iterator<Process> iter = notTerminated.keySet().iterator();
		while(iter.hasNext())
		{
			Process p = iter.next();
			if((notTerminated.get(p)>=p.getLower())&&(notTerminated.get(p)<=p.getUpper()))
			{
				iter.remove();				
			}
		}		
	}
	
	
	/**
	 * 从未结束的进程列表里选择一个最合适的（离其最大执行时间最近的）进程
	 * @return 选择的进程
	 */
	public Process bestOne()
	{
		Process ref = randomOne();
		Process best = ref;
		long min = ref.getUpper() - notTerminated.get(ref);
		for(Process p: notTerminated.keySet())
		{
			long res = p.getUpper() - notTerminated.get(p);
			if(res<min)
			{
				min = res;
				best = p;
			}			
		}		
		return best;
	}
	
	public int getSize()
	{
		return notTerminated.size();
	}

}
