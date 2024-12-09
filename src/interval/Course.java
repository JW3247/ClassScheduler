package interval;
/**
 * immutable
 * @author HUAWEI
 *
 */

public class Course {
	private final String courseID;
	private final String courseName;
	private final String teacher;
	private final String place;
	private final long weekHour;
	// AF: 表示一门课
	// RI: weekHour>0
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	
	public Course(String ID, String name, String t, String p, long h)
	{
		courseID = ID;
		courseName = name;
		teacher = t;
		place = p;
		weekHour = h;
	}
	
	public String getID()
	{
		return courseID;
	}
	
	
	public String getName()
	{
		return courseName;
	}
	
	@Override 
	public String toString()
	{
		return courseID + ":" + courseName +" "+teacher+"-"+place+" "+weekHour+"/week";
	}
	
	public long getWeekHour()
	{
		return weekHour;
	}

}
