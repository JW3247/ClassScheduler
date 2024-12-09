package interval;

/**
 * mutable
 * @author HUAWEI
 *
 * @param <L>
 */
public class OverlapMultiIntervalSet<L> extends MultiIntervalSet<L> {
	
	// AF: 表示一个标签对应多个时间段，时间段可以冲突的集合
	// RI: 无
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException
	{
		
	}
	
	/**
	 * 计算set的冲突率
	 * @param set
	 * @return 冲突率
	 */
	public double calcConflictRatio(MultiIntervalSet<L> set)
	{
		double conflictHours = 0;
		double totalHours = 0;
		for(L label: set.labels())
		{
			IntervalSet<Integer> calc = set.intervals(label);
			for(int k: calc.labels())
			{
				double st = calc.start(k);
				double et = calc.end(k);
				totalHours += et-st;
			}
			
		}
		for(L label: set.labels())
		{
			IntervalSet<Integer> span = set.intervals(label);
			for(int i: span.labels())
			{
				long start = span.start(i);
				for(L label1: set.labels())
				{
					if(!label.equals(label1))
					{
						IntervalSet<Integer> span1 = set.intervals(label1);
						for(int j: span1.labels())
						{
							long s1 = span1.start(j);
							if(s1==start)
							{
								conflictHours += 2;
							}

						}
					}
				}
			}
		}
		conflictHours /= 2;
		totalHours -= conflictHours;
		return conflictHours/totalHours;
	}
	
	
	/**
	 * 计算set的空闲率
	 * @param set
	 * @return 空闲率
	 */
	public double calcFreeTimeRatio(MultiIntervalSet<L> set)
	{
		double freeTime = 0;
		double totalTime = 70;
		double conflictHours = 0;
		double totalHours = 0;
		for(L label: set.labels())
		{
			IntervalSet<Integer> calc = set.intervals(label);
			for(int k: calc.labels())
			{
				double st = calc.start(k);
				double et = calc.end(k);
				totalHours += et-st;
			}
			
		}
		for(L label: set.labels())
		{
			IntervalSet<Integer> span = set.intervals(label);
			for(int i: span.labels())
			{
				long start = span.start(i);
				for(L label1: set.labels())
				{
					if(!label.equals(label1))
					{
						IntervalSet<Integer> span1 = set.intervals(label1);
						for(int j: span1.labels())
						{
							long s1 = span1.start(j);
							if(s1==start)
							{
								conflictHours += 2;
							}

						}
					}
				}
			}
		}
		conflictHours /= 2;
		totalHours -= conflictHours;
		freeTime = 70-totalHours;
		return freeTime/totalTime;
		
	}

}
