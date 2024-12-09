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
	
	// AF: List<IntervalSet<Course>>表示一个课程表安排
	// RI: 课程周课时必须安排满
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	
	/**
	 * 插入一门课程的安排，可以冲突
	 * @param start 起始时间
	 * @param end 终止时间
	 * @param c 课程
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
	 * 检查当前notFullyArranged课程集合中是否有周课时排满了的课程，如果有，就将其从集合中移除
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
	 * 重写，输出待安排课程及待安排周课时数
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
