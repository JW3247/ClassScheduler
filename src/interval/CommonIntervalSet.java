package interval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Mutable
 * @author HUAWEI
 *
 * @param <L>
 */
public class CommonIntervalSet<L> implements IntervalSet<L>{
	private final Map<L, Long> startMap = new HashMap<>();
	private final Map<L, Long> endMap = new HashMap<>();
	
	// AF: L:[startMap.get(L), endMap.get(L))表示一个时段
	// RI: 同一个L只能对应一个时段
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	
	@Override
	public Set<L> labels() {
		// TODO Auto-generated method stub
		Set<L> l = new HashSet<>();
		for(L label: startMap.keySet())
		{
			l.add(label);
		}
		return l;
	}
	
	@Override
	public boolean remove(L label) {
		// TODO Auto-generated method stub
		for(L l: startMap.keySet())
		{
			if(l.equals(label))
			{
				startMap.remove(label);
				endMap.remove(label);
				return true;
			}
		}	
		return false;
	}
	
	@Override
	public long start(L label) throws NoSuchElementException {
		// TODO Auto-generated method stub
		for(L l: startMap.keySet())
		{
			if(l.equals(label))
			{
				return startMap.get(label);
			}
		}
		throw new NoSuchElementException();
	}
	
	@Override
	public long end(L label) throws NoSuchElementException {
		// TODO Auto-generated method stub
		for(L l: endMap.keySet())
		{
			if(l.equals(label))
			{
				return endMap.get(label);
			}
		}
		throw new NoSuchElementException();
	}
	/*
	public void addStart(L label, long start)
	{
		startMap.put(label, start);
	}
	
	public void addEnd(L label, long end)
	{
		endMap.put(label, end);
	}
	*/

	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException
	{
		if((labels().contains(label))&&((start(label)!=start)||(end(label)!=end)))
		{
			throw new IntervalConflictException("Label already exists!");
		}
		else
		{
			for(L l: labels())
			{
				long lstart = start(l);
				long lend = end(l);
				if((end>lstart)&&(start<lend))
				{
					throw new IntervalConflictException("Interval overlaps!");
				}
			}
		}
		startMap.put(label, start);
		endMap.put(label, end);		
	}
	
	@Override
	public String toString()
	{
		String res = "";
		for(L label: labels())
		{
			res = res + label.toString() +": [" + start(label) + "," + end(label) +")\n";		
		}
		return res;
	}
	

	


}
