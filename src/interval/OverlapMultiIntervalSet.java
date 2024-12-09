package interval;

/**
 * mutable
 * @author HUAWEI
 *
 * @param <L>
 */
public class OverlapMultiIntervalSet<L> extends MultiIntervalSet<L> {
	
	// AF: ��ʾһ����ǩ��Ӧ���ʱ��Σ�ʱ��ο��Գ�ͻ�ļ���
	// RI: ��
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException
	{
		
	}
	
	/**
	 * ����set�ĳ�ͻ��
	 * @param set
	 * @return ��ͻ��
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
	 * ����set�Ŀ�����
	 * @param set
	 * @return ������
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
