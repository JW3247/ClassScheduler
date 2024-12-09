package interval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

/**
 * A mutable set of labeled intervals, where each label is associated with one
 * or more non-overlapping half-open intervals [start, end). Neither intervals
 * with the same label nor with different labels may overlap. 
 * 
 * Labels are of immutable type L and must implement the Object contract: they are 
 * compared for equality using Object.equals(java.lang.Object). 
 * 
 * For example, { * "A"=[[0,10)], "B"=[[20,30)] } is a multi-interval set where 
 * the labels are Strings "A" and "B". We could add "A"=[10,20) to that set to obtain 
 * {"A"=[[0,10),[10,20)], "B"=[[20,30)] }.
 * 
 * PS2 instructions: this is a required ADT class. You may not change the
 * specifications or add new public methods. You must use IntervalSet in your
 * rep, but otherwise the implementation of this class is up to you.
 * 
 * @param <L> type of labels in this set, must be immutable
 */

public class MultiIntervalSet<L> {	
	
	// AF: 表示一个标签可以对应多个时间段，但时间段不可冲突的集合
	// RI: 时间段不冲突
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	private final List<IntervalSet<L>> m = new ArrayList<>();
	
	/**
	 * Create an empty multi-interval set.
	 */
	public MultiIntervalSet() {
		//throw new RuntimeException("not implemented");
		
	}

	/**
	 * Create a new multi-interval set containing the given labeled intervals.
	 * 
	 * @param initial initial contents of the new set
	 */
	public MultiIntervalSet(IntervalSet<L> initial) {
		//throw new RuntimeException("not implemented");
		m.add(initial);
	}

	/**
	 * Add a labeled interval (if not present) to this set, if it does not conflict with existing intervals.
	 * 
	 * Labeled intervals conflict if:
	 * 		they have the same label with different, overlapping intervals, or
	 *		they have different labels with overlapping intervals.
	 * 
	 * For example, if this set is { "A"=[[0,10),[20,30)] },
	 *		insert("A"=[0,10)) has no effect
	 *		insert("B"=[10,20)) adds "B"=[[10,20)]
	 *		insert("C"=[20,30)) throws IntervalConflictException
	 *
	 *
	 * @param start low end of the interval, inclusive
	 * @param end high end of the interval, exclusive, must be greater than start
	 * @param label label to add
	 * @throws IntervalConflictException if label is already in this set and associated 
	 * 									 with an interval other than [start,end) that 
	 * 									 overlaps [start,end), or if an interval in this 
	 * 									 set with a different label overlaps [start,end)
	 */
	
	public void insert(long start, long end, L label) throws IntervalConflictException {
		//throw new RuntimeException("not implemented");
		for(L l: labels())
		{
			if(l.equals(label))
			{
				IntervalSet<Integer> a = intervals(l);
				for(Integer n: a.labels())
				{
					if((start==a.start(n))&&(end==a.end(n)))
					{
						return;
					}
					if((start<a.end(n))&&(end>a.start(n)))
					{
						throw new IntervalConflictException("Existing label conflict!");
					}
				}
			}
			else
			{
				IntervalSet<Integer> a = intervals(l);
				for(Integer n: a.labels())
				{
					if((start<a.end(n))&&(end>a.start(n)))
					{
						throw new IntervalConflictException("Time overlap conflict!");
					}
				}	
			}
		}
		CommonIntervalSet<L> toAdd = new CommonIntervalSet<L>();
		toAdd.insert(start, end, label);
		m.add(toAdd);
	}
	
	
	/**
	 * Remove all intervals of the given label from this set, if any.
	 * 
	 * @param label label to remove
	 * @return true if this set contained label, and false otherwise
	 */
	public boolean removeAll(L label) {
		//throw new RuntimeException("not implemented");
		int i;
		IntervalSet<L> s;
		int flag=0;
		for(i=0; i<m.size(); i++)
		{
			s = m.get(i);
			if(s.labels().contains(label))
			{
				s.remove(label);
				flag=1;
			}
		}
		return flag==1?true:false;
	}
	
	/**
	 * Get the labels in this set.
	 * 
	 * @return the labels in this set
	 */
	public Set<L> labels() {
		//throw new RuntimeException("not implemented");
		Set<L> l = new HashSet<>();
		int i;
		for(i=0; i<m.size(); i++)
		{
			l.addAll(m.get(i).labels());
		}
		return l;
	}
	
	/**
	 * Get all the intervals in this set associated with a given label. The returned set has 
	 * Integer labels that act as indices: label 0 is associated with the lowest interval, 
	 * 1 the next, and so on, for all the intervals in this set that have the provided label.
	 * 
	 * For example, if this set is { "A"=[[0,10),[20,30)], "B"=[[10,20)] },
	 * 		intervals("A") returns { 0=[0,10), 1=[20,30) }

	 * @param label the label
	 * @return a new interval set that associates integer indices with the in-order intervals 
	 * 		   of label in this set
	 * @throws NoSuchElementException if label is not in this set
	 */
	

	public IntervalSet<Integer> intervals(L label) throws NoSuchElementException{
		//throw new RuntimeException("not implemented");
		IntervalSet<Integer> res = new CommonIntervalSet<Integer>();
		Map<Long, Long> tmp = new HashMap<>();
		if(labels().contains(label))
		{
			int i;
			for(i=0; i<m.size(); i++)
			{
				IntervalSet<L> s = m.get(i);				
				if(s.labels().contains(label))
				{
					long start = s.start(label);
					long end = s.end(label);
					tmp.put(start, end);	
				}
				
			}
			tmp = sortByKey(tmp);
			Set<Long> keySet = tmp.keySet();
			Iterator<Long> iter = keySet.iterator();
			i = 0;
			while(iter.hasNext())
			{
				long key = iter.next();
				try {
					res.insert(key, tmp.get(key), i);
					i++;
					//System.out.println(i+":"+"["+key+","+tmp.get(key)+")");
				} catch (IntervalConflictException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			for(Integer n: res.labels())
			{
				System.out.println(n+":"+"["+res.start(n)+","+res.end(n)+")");				
			}
			*/
			return res;
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * 将时间段转换成易读的字符串形式label:[start, end)
	 */
	@Override
	public String toString() {
		//throw new RuntimeException("not implemented");
		int i;
		String res = "";
		for(L label: labels())
		{
			String s = label.toString();
			for(i=0; i<m.size(); i++)
			{
				IntervalSet<L> set = m.get(i);
				if(set.labels().contains(label))
				{
					s = s + " [" + set.start(label) + "," + set.end(label) + ")";
				}
			}
			res = res + s + "\n";
		}
		return res;
	}
	
	/*
	private long size()
	{
		return m.size();
	}
	*/
	
	/**
	 * 将一个map的元素按键排序
	 * @param map 待排序的map
	 * @return 排序后的map
	 */
	private Map<Long, Long> sortByKey(Map<Long, Long> map)
	{
		Map<Long, Long> sortedMap = new TreeMap<Long, Long>(new MapKeyComparator());
		sortedMap.putAll(map);
		return sortedMap;
	}
	
	/**
	 * 将set加入MultiIntervalSet，仅用于course情况的插入
	 * @param set 待插入的set
	 */
	public void add(IntervalSet<L> set)
	{
		m.add(set);
	}
	
	/**
	 * 计算两个MultiIntervalSet的相似度
	 * @param s1 待比较时间轴1
	 * @param s2 待比较时间轴2
	 * @return 两时间轴的相似度
	 */
	public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2)
	{
		long max = 0;
		long overlap = 0;
		long overlapAll = 0;
		for(Iterator<L> iter = s1.labels().iterator(); iter.hasNext();)
		{
			L label = iter.next();
			for(Iterator<L> iter1 = s2.labels().iterator(); iter1.hasNext();)
			{
				L label1 = iter1.next();
				IntervalSet<Integer> set1 = s1.intervals(label);
				IntervalSet<Integer> set2 = s2.intervals(label1);
				for(int i: set1.labels())
				{
					for(int j: set2.labels())
					{
						long si = set1.start(i);
						long ei = set1.end(i);
						long sj = set2.start(j);
						long ej = set2.end(j);
						overlap = 0;
						if(ei>max)
						{
							max = ei;
						}
						if(ej>max)
						{
							max = ej;
						}
						if(label1.equals(label))
						{
							
							if((si<ej)&&(ei>sj))
							{

								if((si>=sj)&&(ei<=ej))
								{
									overlap = ei - si;
								}
								else if((si<=sj)&&(ei>=ej))
								{
									overlap = ej - sj;
								}
								else if((si<=sj)&&(ei<=ej))
								{
									overlap = ei - sj;
								}
								
								else if((si>=sj)&&(ei>=ej))
								{
									overlap = ej - si;
								}
							}
							else if((sj<ei)&&(ej>si))
							{
								if((sj>=si)&&(ej<=ei))
								{
									overlap = ej - sj;
								}
								else if((sj<=si)&&(ej>=ei))
								{
									overlap = ei - si;
								}
								else if((sj<=si)&&(ej<=ei))
								{
									overlap = ej - si;
								}
								
								else if((sj>=si)&&(ej>=ei))
								{
									overlap = ei - sj;
								}
							}
							overlapAll += overlap;
						}
					}
				}				
			}
		}
		return (double)overlapAll/max;
	}
	

}
