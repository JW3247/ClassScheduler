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
	
	// AF: 表示一个雇员
	// RI: 无
	// Safety from rep exposure: 属性都是private，外部不能直接访问属性，而是通过相应方法
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
