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
	
	// AF: ��ʾ���̰��ŵļ���
	// RI: ʱ��β���ͻ
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
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
	 * ���ѡ��һ������
	 * @return ѡ��Ľ���
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
	 * ��鵱ǰnotTerminated���̼������Ƿ��з��Ͻ���Ҫ�󣬿��Խ����Ľ��̣�����У��ͽ���Ӽ������Ƴ�
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
	 * ��δ�����Ľ����б���ѡ��һ������ʵģ��������ִ��ʱ������ģ�����
	 * @return ѡ��Ľ���
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
