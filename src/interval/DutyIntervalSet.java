package interval;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * mutable
 * @author HUAWEI
 *
 */
public class DutyIntervalSet extends CommonIntervalSet<Employee> {
	private Map<Long, Long> notArranged = new HashMap<>();
	private long sy, sm, sd, ey, em, ed, t1, t2;
	
	
	// AF: 表示一个排班表
	// RI: 时间段不冲突，一个标签对应一个时间段
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	/**
	 * 初始化
	 * @param start 待安排时段起始时间
	 * @param end 待安排时段终止时间
	 */
	public void initNotArranged(long start, long end)
	{
		notArranged.put(start, end);
	}
	
	/**
	 * 检查是否安排满
	 * @return true如果满，false不满
	 */
	public boolean checkFull()
	{
		if(notArranged.size()==0)
		{
			return true;
		}
		return false;
	}
	 
	/**
	 * 更新未安排时段
	 * @param start 已安排时段的开始时间
	 * @param end 已安排时段的终止时间
	 */
	public void updateArrangedStatus(long start, long end)
	{
		for(long s: notArranged.keySet())
		{
			long e = notArranged.get(s);
			if((start>=s)&&(end<=e))
			{
				long dif1 = start - s;
				long dif2 = e - end;
				if(dif1!=0)
				{
					notArranged.put(s, start);
				}
				if(dif2!=0)
				{
					notArranged.put(end, e);
				}
				notArranged.remove(s, e);
				break;
			}
		}
		
	}
	
	/**
	 * 输出未安排时段
	 */
	public void printNotArrangedSpan()
	{
		if(!checkFull())
		{
			System.out.println("To be arranged:");
			for(long s: notArranged.keySet())
			{
				long e = notArranged.get(s);
				convert(s, e);
				System.out.println(dateToString(sy, sm, sd, ey, em, ed));		
			}
		}
	}
	
	
	
	@Override 
	public String toString()
	{
		String res = "Current duty roster:\n";
		for(Employee e: labels())
		{
			long start = start(e);
			long end = end(e);
			long sy = start / 10000;
			long t1 = start % 10000;
			long sm = t1 / 100;
			long sd = t1 % 100;
			long ey = end / 10000;
			long t2 = end % 10000;
			long em = t2 / 100;
			long ed = t2 % 100;
			res = res + e.getName() +" "+ e.getPosition() + " "+ e.getPhoneNumber()+":" + dateToString(sy, sm, sd, ey, em, ed) + "\n";	
		}
		return res;
	}
	
	/**
	 * 从起止时间提取年月日
	 * @param s 起始时间
	 * @param e 终止时间
	 */
	public void convert(long s, long e)
	{
		sy = s / 10000;
		t1 = s % 10000;
		sm = t1 / 100;
		sd = t1 % 100;
		ey = e / 10000;
		t2 = e % 10000;
		em = t2 / 100;
		ed = t2 % 100;
	}

	
	/**
	 * 将日期表示为字符串
	 * @param sy 起始年
	 * @param sm 起始月
	 * @param sd 起始日
	 * @param ey 终止年
	 * @param em 终止月
	 * @param ed 终止日
	 * @return 起止日期字符串
	 */
	public String dateToString(long sy, long sm, long sd, long ey, long em, long ed)
	{
		return sy+"."+sm+"."+sd+" - "+ey+"."+em+"."+ed;	
	}
	
	/**
	 * 计算未安排比例
	 * @param s 整个时段的起始
	 * @param e 整个时段的终止
	 * @return 未安排比例
	 */
	public double calcNotArrangedRatio(long s, long e)
	{
		convert(s, e);
		Calendar c1 = Calendar.getInstance();
		c1.set((int)sy, (int)sm-1, (int)sd);
		Calendar c2 = Calendar.getInstance();
		c2.set((int)ey, (int)em-1, (int)ed);
		Date d1 = c1.getTime();
		Date d2 = c2.getTime();
		int days1 = (int)((d2.getTime()-d1.getTime())/(1000*3600*24));
		int na = 0;
		for(long l: notArranged.keySet())
		{
			convert(l, notArranged.get(l));
			Calendar c3 = Calendar.getInstance();
			c3.set((int)sy, (int)sm-1, (int)sd);
			Calendar c4 = Calendar.getInstance();
			c4.set((int)ey, (int)em-1, (int)ed);
			Date d3 = c3.getTime();
			Date d4 = c4.getTime();
			int days2 = (int)((d4.getTime()-d3.getTime())/(1000*3600*24));
			na += days2;
		}
		return (double)na/days1;
	}
	
	public int getSize()
	{
		return notArranged.size();
	}

}
