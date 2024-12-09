package interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProcessScheduleApp {
	private List<Process> p = new ArrayList<>();
	private ProcessIntervalSet pi = new ProcessIntervalSet();
	// AF: ��ʾ���Ƚ���
	// RI: ʱ��β���ͻ
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
	
	public static void main(String[] args)
	{
		ProcessScheduleApp ps = new ProcessScheduleApp();
		Scanner s = new Scanner(System.in);
		ps.addProcess(s);
		System.out.println("Random or optimum dispatch?(0 for random, 1 for optimum)");
		int choice = s.nextInt();
		if(choice==0)
		{
			ps.randomDispatch();
			System.out.println("All terminated.");

		}
		else if(choice==1)
		{
			ps.optimumDispatch();
			System.out.println("All terminated.");
			
		}
		
		s.close();
	}
	
	/**
	 * ���û�������ӽ���
	 * @param s
	 */
	private void addProcess(Scanner s)
	{
		System.out.println("Please input number of processes and process entry:(format:PID name minExecutionTime maxExecutionTime)");
		String str = s.nextLine();
		int num = Integer.valueOf(str);
		for(int i=0; i<num; i++)
		{
			str = s.nextLine();
			String[] line = str.split(" ");
			Process toAdd = new Process(Integer.valueOf(line[0]), line[1], Integer.valueOf(line[2]), Integer.valueOf(line[3]));
			p.add(toAdd);
			pi.addNotTerminated(toAdd);
		}
		
		
		/*
		for(int i=0; i<num; i++)
		{
			System.out.println(p.get(i).toString());
		}
		*/			
	}
	
	/**
	 * �������
	 */
	private void randomDispatch()
	{
		long start = 0;
		pi.checkTerminated();
		while(!pi.allTerminated())
		{
			start = dispatchEntry(start, 0);		
			pi.checkTerminated();
		}
		
	}
	
	/**
	 * ����һ�ε���
	 * @param start ��ʼʱ��
	 * @param choice ѡ�񣬾��������ѡ�񣬻�������ѡ��
	 * @return
	 */
	private long dispatchEntry(long start, int choice)
	{
		Random a = new Random();
		int c = a.nextInt(2);
		if(c==1)
		{
			Process p = choice==0?pi.randomOne():pi.bestOne();
			Random r = new Random();
			long limit = p.getUpper()-pi.getExecutionTime(p);
			long rt = Math.abs(r.nextLong() % limit);
			while(rt==0)
			{
				rt = Math.abs(r.nextLong() % limit);
			}
			try {
				pi.insert(start, start+rt, p);
				pi.setExecutionTime(p, pi.getExecutionTime(p)+rt);
				System.out.println("Currently running:\n "+p.getPID()+":"+p.getName()+" "+pi.getExecutionTime(p)+"/"+(p.getUpper()-p.getLower()));
				System.out.println("Previous dispatch record:\n"+pi.toString());
			} catch (IntervalConflictException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return start+rt;
		}
		else
		{
			start += 2;
			System.out.println("Currently running: none");
			return start;
		}
	}
	
	
	/**
	 * ���ŵ���
	 */
	private void optimumDispatch()
	{
		long start = 0;
		pi.checkTerminated();
		while(!pi.allTerminated())
		{
			start = dispatchEntry(start, 1);		
			pi.checkTerminated();
		}
		
	}


}
