package interval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * mutable
 * @author HUAWEI
 *
 */
public class CourseScheduleApp {
	private List<Course> c = new ArrayList<>();
	private CourseIntervalSet ci = new CourseIntervalSet();
	private long y, m, d, weeks;
	
	// AF: 表示安排课表
	// RI: 周课时要排满
	// Safety from rep exposure: 属性都是private
	
	public static void main(String[] args)
	{
		CourseScheduleApp cs = new CourseScheduleApp();
		Scanner s = new Scanner(System.in);
		cs.setSemester(s);
		cs.addCourse(s);
		cs.setCourseSchedule(s);
		cs.checkSchedule(s);
		s.close();
		
	}

	/**
	 * 设置学期开始时间、周数
	 * @param s
	 */
	private void setSemester(Scanner s)
	{
		System.out.println("Please input start date of the semester and week number of the semester:(format:yy mm dd weekNumber)");
		String str = s.nextLine();
		String[] line = str.split(" ");
		y = Integer.valueOf(line[0]);
		m = Integer.valueOf(line[1]);
		d = Integer.valueOf(line[2]);
		weeks = Integer.valueOf(line[3]);
		System.out.println("Semester starts from: "+y+"."+m+"."+d+", lasts "+weeks+" weeks");
		
	}
	
	
	/**
	 * 输入课程
	 * @param s
	 */
	private void addCourse(Scanner s)
	{
		System.out.println("Please input number of courses and course entry:(format:courseID courseName teacher place hourPerWeek)");
		String str = s.nextLine();
		int num = Integer.valueOf(str);
		for(int i=0; i<num; i++)
		{
			str = s.nextLine();
			String[] line = str.split(" ");
			Course toAdd = new Course(line[0], line[1], line[2], line[3], Integer.valueOf(line[4]));
			c.add(toAdd);
			ci.updateNotFullyArranged(toAdd, toAdd.getWeekHour());
		}
		/*
		for(int j=0; j<c.size(); j++)
		{
			System.out.println(c.get(j).toString());
		}
		*/
	}
	
	/**
	 * 设置课程表
	 * @param s
	 */
	private void setCourseSchedule(Scanner s)
	{
		int i;
		for(i=0; i<c.size(); i++)
		{
			System.out.println(i +": "+ c.get(i).toString());
		}
		while(!ci.allArranged())
		{
			setCourseEntry(s);
		}
	}
	
	/**
	 * 设置一个课程表条目
	 * @param s
	 */
	private void setCourseEntry(Scanner s)
	{
		System.out.println("Please input a schedule entry:(format:courseIndex day(1-7) time(8-10/10-12/13-15/15-17/19-21))");
		String str = s.nextLine();
		String[] line = str.split(" |-");
		int id = Integer.valueOf(line[0]);
		long day = Integer.valueOf(line[1]);
		long st = Integer.valueOf(line[2]);
		long et = Integer.valueOf(line[3]);
		long start = day*100 + st;
		long end = day*100 + et;
		Course course = c.get(id);
		ci.courseInsert(start, end, course);
		ci.updateNotFullyArranged(course, ci.getNotArrangedHours(course)-2);
		ci.checkArranged();
		System.out.println(ci.toString());
		System.out.println("Current conflict ratio: " +  ci.calcConflictRatio(ci)+"\n");
		System.out.println("Current free ratio: " +  ci.calcFreeTimeRatio(ci)+"\n");
	}
	
	/**
	 * 查看某天的课表
	 * @param s
	 */
	private void checkSchedule(Scanner s)
	{
		String[] weekday = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		System.out.println("Please input the date you want to check:(format:yy mm dd, must be within the semester)");
		String str = s.nextLine();
		String[] line = str.split(" ");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(line[0]), Integer.valueOf(line[1])-1, Integer.valueOf(line[2]));
		int week_index = cal.get(Calendar.DAY_OF_WEEK);//1-7
		int ref1 = week_index-1;
		int ref2 = week_index-1;
		if(ref1<0)
		{
			ref1 = 0;
			ref2 = 7;
		}
		String r = "Schedule for "+line[0]+"."+line[1]+"."+line[2]+" "+weekday[ref1] + "\n";
		for(Course c: ci.labels())
		{
			IntervalSet<Integer> res = ci.intervals(c);
			for(int i: res.labels())
			{
				long start = res.start(i);
				long end = res.end(i);
				long weeknum = start/100;
				if(weeknum==ref2)
				{
					r = r + c.toString() +" "+ start%100 + "-" + end%100 + "\n";
				}
			}
		}

		System.out.println(r);
	}
	

}
