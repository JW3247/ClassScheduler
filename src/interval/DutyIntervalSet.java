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
	
	
	// AF: ��ʾһ���Ű��
	// RI: ʱ��β���ͻ��һ����ǩ��Ӧһ��ʱ���
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
	/**
	 * ��ʼ��
	 * @param start ������ʱ����ʼʱ��
	 * @param end ������ʱ����ֹʱ��
	 */
	public void initNotArranged(long start, long end)
	{
		notArranged.put(start, end);
	}
	
	/**
	 * ����Ƿ�����
	 * @return true�������false����
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
	 * ����δ����ʱ��
	 * @param start �Ѱ���ʱ�εĿ�ʼʱ��
	 * @param end �Ѱ���ʱ�ε���ֹʱ��
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
	 * ���δ����ʱ��
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
	 * ����ֹʱ����ȡ������
	 * @param s ��ʼʱ��
	 * @param e ��ֹʱ��
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
	 * �����ڱ�ʾΪ�ַ���
	 * @param sy ��ʼ��
	 * @param sm ��ʼ��
	 * @param sd ��ʼ��
	 * @param ey ��ֹ��
	 * @param em ��ֹ��
	 * @param ed ��ֹ��
	 * @return ��ֹ�����ַ���
	 */
	public String dateToString(long sy, long sm, long sd, long ey, long em, long ed)
	{
		return sy+"."+sm+"."+sd+" - "+ey+"."+em+"."+ed;	
	}
	
	/**
	 * ����δ���ű���
	 * @param s ����ʱ�ε���ʼ
	 * @param e ����ʱ�ε���ֹ
	 * @return δ���ű���
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
