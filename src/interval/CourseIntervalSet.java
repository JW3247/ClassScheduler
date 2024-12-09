package interval;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * mutable
 * @author HUAWEI
 *
 */
public class CourseIntervalSet extends OverlapMultiIntervalSet<Course>{
	private final Map<Course, Long> notFullyArranged = new HashMap<>();//maps course to hours to be arranged
	
	// AF: List<IntervalSet<Course>>��ʾһ���γ̱���
	// RI: �γ��ܿ�ʱ���밲����
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
	
	/**
	 * ����һ�ſγ̵İ��ţ����Գ�ͻ
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param c �γ�
	 */
	public void courseInsert(long start, long end, Course c)
	{
		CommonIntervalSet<Course> toAdd = new CommonIntervalSet<Course>();
		try {
			toAdd.insert(start, end, c);
			add(toAdd);
		} catch (IntervalConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateNotFullyArranged(Course c, long l)
	{
		notFullyArranged.put(c, l);
	}
	
	public boolean allArranged()
	{
		return notFullyArranged.size()==0?true:false;
	}
	
	public long getNotArrangedHours(Course c)
	{
		return notFullyArranged.get(c);
	}
	
	/**
	 * ��鵱ǰnotFullyArranged�γ̼������Ƿ����ܿ�ʱ�����˵Ŀγ̣�����У��ͽ���Ӽ������Ƴ�
	 */
	public void checkArranged()
	{
		Iterator<Course> iter = notFullyArranged.keySet().iterator();
		while(iter.hasNext())
		{
			Course c = iter.next();
			if(notFullyArranged.get(c)==0)
			{
				iter.remove();				
			}
		}
	}
	
	/**
	 * ��д����������ſγ̼��������ܿ�ʱ��
	 */
	@Override
	public String toString()
	{
		String res = "To be arranged: (toBeArrangedHours/totalHours)\n";
		for(Course c: notFullyArranged.keySet())
		{
			res = res + c.getID() +":"+ c.getName() +" "+ notFullyArranged.get(c) + "/" + c.getWeekHour() + "\n";		
		}
		return res;
	}
	
	public String fatherToString()
	{
		return super.toString();
	}
}
