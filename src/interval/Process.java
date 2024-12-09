package interval;

/**
 * immutable
 * @author HUAWEI
 *
 */
public class Process {
	private int PID;
	private String name;
	private long lower;
	private long upper;
	
	// AF: 表示一个进程
	// RI: PID不重复
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
	public Process(int p, String n, long l, long u)
	{
		PID = p;
		name = n;
		lower = l;
		upper = u;
	}
	
	public int getPID()
	{
		return PID;
	}
	
	
	public long getUpper()
	{
		return upper;
	}
	
	public long getLower()
	{
		return lower;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return "PID:" + PID +" name:"+ name +" required execution time:"+ lower + "-" + upper;
	}

}
