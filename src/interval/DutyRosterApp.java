package interval;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mutable
 * @author HUAWEI
 *
 */
public class DutyRosterApp {
	long startYear, startMonth, startDay;
	long endYear, endMonth, endDay;
	long spanStart, spanEnd;
	List<Employee> e = new ArrayList<>();
	DutyIntervalSet ds = new DutyIntervalSet();
	
	// AF: 表示安排排班表
	// RI: 时间段不冲突，一个标签对应一个时间段
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	public static void main(String[] args)
	{
		DutyRosterApp dr = new DutyRosterApp();
		Scanner s = new Scanner(System.in);
		System.out.println("Manual input or read from file?(0 for manual, 1 for read from file)");
		int func = Integer.valueOf(s.nextLine());
		long ss = 0, se = 0;
		if(func==0)
		{
			dr.setTimeSpan(s);
			dr.addEmployee(s);
			System.out.println("Manual or auto?(0 for manual, 1 for auto)");
			int choice = Integer.valueOf(s.nextLine());
			if(choice==0)
			{
				dr.manualSetRoster(s);
			}
			else if(choice==1)
			{
				dr.autoSetRoster();
			}
			s.close();
		}
		else if(func==1)
		{
			System.out.println("Please input file path:");
			String path = s.nextLine();
			String str;
			Scanner sc;
			try {
				sc = new Scanner(new File(path));
				while(sc.hasNextLine())
				{
					str = sc.nextLine();
					String[] line = str.split("\\{|\\,|\\}");
					if(line.length==0)
					{
						str = sc.nextLine();
						line = str.split("\\{|\\,|\\}");
					}
					if(line[0].equals("Employee"))
					{
						System.out.println("Successful read employee");
						str = sc.nextLine();
						//System.out.println(str);
						line = str.split("\\{|\\,|\\}");
						//System.out.println(line.length);
						while(line.length!=0)
						{
							Employee em = new Employee(line[0], line[1], line[2]);
							//System.out.println(line[0]+line[1]+line[2]);
							dr.addEmployee(em);
							str = sc.nextLine();
							line = str.split("\\{|\\,|\\}");
						}
						str = sc.nextLine();
						line = str.split("\\{|\\,|\\}");
					}
					if(line[0].equals("Period"))
					{
						System.out.println("Successful read period");
						String[] start = line[1].split("-");
						String[] end = line[2].split("-");
						long sy, sm, sd, ey, em, ed;
						sy = Integer.valueOf(start[0]);
						sm = Integer.valueOf(start[1]);
						sd = Integer.valueOf(start[2]);
						ey = Integer.valueOf(end[0]);
						em = Integer.valueOf(end[1]);
						ed = Integer.valueOf(end[2]);	
						ss = sy*10000 + sm*100 + sd;
						se = ey*10000 + em*100 + ed;
						dr.fileSetSpan(ss, se);
					}
					if(line[0].equals("Roster"))
					{
						System.out.println("Successful read roster");
						str = sc.nextLine();
						line = str.split("\\{|\\,|\\}");
						while(line.length!=0)
						{
							int index = dr.getIndex(line[0]);
							if(index==-1)
							{
								System.out.println("No such employee! Please try another file!");
								System.out.println("Error entry:" + str);
								return;
							}
							String[] start = line[1].split("-");
							String[] end = line[2].split("-");
							long sy, sm, sd, ey, em, ed;
							sy = Integer.valueOf(start[0]);
							sm = Integer.valueOf(start[1]);
							sd = Integer.valueOf(start[2]);
							ey = Integer.valueOf(end[0]);
							em = Integer.valueOf(end[1]);
							ed = Integer.valueOf(end[2]);
							long entryStart = sy*10000 + sm*100 + sd;
							long entryEnd = ey*10000 + em*100 + ed;
							//if(entryStart==entryEnd||entryStart<ss||entryEnd>se)
							if(entryStart<ss||entryEnd>se)
							{
								System.out.println("Arrangement time span error! Please try another file!");
								System.out.println("Error entry:" + str);
								return;
							}
							boolean flag = dr.manualSetEntry(index, sy, sm, sd, ey, em, ed, 1);
							if(flag==false)
							{
								return;
							}
							str = sc.nextLine();
							line = str.split("\\{|\\,|\\}");
						}				
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * 根据用户输入设置起止时间
	 * @param s 用于命令行读入
	 */
	private void setTimeSpan(Scanner s)
	{
		System.out.println("Please set starting date and ending date:(format:yyyy mm dd yyyy mm dd)");
		String str = s.nextLine();
		String[] line = str.split(" ");
		startYear = Integer.valueOf(line[0]);
		startMonth = Integer.valueOf(line[1]);
		startDay = Integer.valueOf(line[2]);
		endYear = Integer.valueOf(line[3]);
		endMonth = Integer.valueOf(line[4]);
		endDay = Integer.valueOf(line[5]);	
		spanStart = startYear*10000 + startMonth*100 + startDay;
		spanEnd = endYear*10000 + endMonth*100 + endDay;
		ds.initNotArranged(spanStart, spanEnd);
	}
	
	/**
	 * 根据用户输入添加雇员
	 * @param s
	 */
	private void addEmployee(Scanner s)
	{
		System.out.println("Please input number of employees and employee entry:(format:name position phoneNumber)");
		String str = s.nextLine();
		int num = Integer.valueOf(str);
		for(int i=0; i<num; i++)
		{
			str = s.nextLine();
			String[] line = str.split(" ");
			Employee toAdd = new Employee(line[0], line[1], line[2]);
			e.add(toAdd);
		}
		/*
		for(int i=0; i<num; i++)
		{
			System.out.println(e.get(i).toString());
		}
		*/
			
	}
	
	/**
	 * 手动设置排班表
	 * @param s
	 */
	private void manualSetRoster(Scanner s)
	{
		int i;
		for(i=0; i<e.size(); i++)
		{
			System.out.println(i +": "+ e.get(i).toString());
		}
		while(!ds.checkFull())
		{
			mayRemoveEmployee(s);
			System.out.println("Please input a roster entry:(format:employeeID yyyy mm dd yyyy mm dd)");
			String str = s.nextLine();
			String[] line = str.split(" ");
			int id = Integer.valueOf(line[0]);
			long x1 = Integer.valueOf(line[1]);
			long x2 = Integer.valueOf(line[2]);
			long x3 = Integer.valueOf(line[3]);
			long x4 = Integer.valueOf(line[4]);
			long x5 = Integer.valueOf(line[5]);	
			long x6 = Integer.valueOf(line[6]);
			manualSetEntry(id, x1, x2, x3, x4, x5, x6, 0);
		}
	}
	
	/**
	 * 手动设置排班条目
	 * @param i 雇员在集合中的下标
	 * @param sy 起始年
	 * @param sm 起始月
	 * @param sd 起始日
	 * @param ey 终止年
	 * @param em 终止月
	 * @param ed 终止日
	 * @return 无异常true，有异常false
	 */
	
	private boolean manualSetEntry(int i, long sy, long sm, long sd, long ey, long em, long ed, int c)
	{
		
		long start = sy*10000 + sm*100 + sd;
		long end = ey*10000 + em*100 + ed;
		try {
			ds.insert(start, end, e.get(i));
			ds.updateArrangedStatus(start, end);
			ds.printNotArrangedSpan();
			System.out.println(ds.toString());
			if(c==0)
			{
				System.out.println("notArranged/total = "+ds.calcNotArrangedRatio(spanStart, spanEnd));
			}
			return true;
			
		} catch (IntervalConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 自动设置排班表
	 */
	private void autoSetRoster()
	{
		int i;
		long ave = (spanEnd - spanStart)/e.size();
		long start = 0, end = 0;
		for(i=0; i<e.size()-1; i++)
		{
			start = spanStart+i*ave;
			end = spanStart+(i+1)*ave;
			try {
				ds.insert(start, end, e.get(i));
				ds.updateArrangedStatus(start, end);
				//ds.printNotArrangedSpan();
				//System.out.println(ds.toString());
			} catch (IntervalConflictException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		try {
			ds.insert(end, spanEnd, e.get(i));
			System.out.println(ds.toString());
		} catch (IntervalConflictException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	private void addEmployee(Employee em)
	{
		e.add(em);
	}
	/*
	private List<String> getEmployeeNames()
	{
		List<String> names = new ArrayList<>();
		for(int i=0; i<e.size(); i++)
		{
			names.add(e.get(i).getName());
		}
		return names;
	}
	*/
	private void fileSetSpan(long ss, long se)
	{
		ds.initNotArranged(ss, se);
	}
	
	private int getIndex(String name)
	{
		for(int i=0; i<e.size(); i++)
		{
			if(e.get(i).getName().equals(name))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 询问用户是否要删除雇员条目，如果要删除则执行对应操作
	 * @param s
	 */
	private void mayRemoveEmployee(Scanner s)
	{
		System.out.println("Would you like to remove any employee?(y for yes, n for no)");
		String str = s.nextLine();
		if(str.equals("y"))
		{
			System.out.println("Please input the name of the employee you want to remove:");
			str = s.nextLine();
			int index = getIndex(str);
			if(index==-1)
			{
				System.out.println("The employee doesn't exist!");
				return;
			}
			boolean status = checkArrangedStatus(str);
			if(status==true)
			{
				System.out.println("The employee is arranged in the roster, proceed to remove will also remove the roster entry(y for proceed, n for don't)");
				String choice = s.nextLine();
				if(choice.equals("y"))
				{
					Employee toDel = e.get(index);
					long start = ds.start(toDel);
					long end = ds.end(toDel);
					ds.remove(e.get(index));
					e.remove(index);
					ds.initNotArranged(start, end);
				}
			}
			else
			{
				e.remove(index);
			}
			for(int i=0; i<e.size(); i++)
			{
				System.out.println(i +": "+ e.get(i).toString());
			}
			ds.printNotArrangedSpan();
		}
		
	}
	
	private boolean checkArrangedStatus(String name)
	{
		for(Employee e: ds.labels())
		{
			String n = e.getName();
			if(n.equals(name))
			{
				return true;
			}
		}
		return false;
	}

}
