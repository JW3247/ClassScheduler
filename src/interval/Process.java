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
	
	// AF: ��ʾһ������
	// RI: PID���ظ�
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
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
