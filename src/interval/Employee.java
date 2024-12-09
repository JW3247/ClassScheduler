package interval;

/**
 * immutable
 * @author HUAWEI
 *
 */
public class Employee {
	private String name;
	private String position;
	private String phoneNumber;
	
	// AF: ��ʾһ����Ա
	// RI: ��
	// Safety from rep exposure: ���Զ���private���ⲿ����ֱ�ӷ������ԣ�����ͨ����Ӧ����
	public Employee(String n, String p, String ph)
	{
		name = n;
		position = p;
		phoneNumber = ph;
	}
	
	@Override
	public String toString()
	{
		return name + " " + position + " " + phoneNumber;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public String getPosition()
	{
		return position;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	

}
