package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DutyIntervalSetTest {

	// Testing strategy
	// �۲켯�ϵĴ�С���жϳ�ʼ�������Ƿ�ɹ�
	@Test
	void testInit() {
		DutyIntervalSet d = new DutyIntervalSet();
		d.initNotArranged(0, 30);
		assertEquals(d.getSize(), 1);
	}
	
	// Testing strategy
	// ����һ���Ű���Ŀ���ȽϽ���ַ�����Ԥ���Ƿ������ͬʱ������dateToString����
	@Test
	void testToString() throws IntervalConflictException {
		Employee e = new Employee("wang", "manager", "100");
		DutyIntervalSet d = new DutyIntervalSet();
		d.insert(20210710, 20210725, e);
		assertEquals(d.toString(), "Current duty roster:\nwang manager 100:2021.7.10 - 2021.7.25\n");	
	}
	
	// Testing strategy
	// �۲켯�ϵĴ�С�жϸ����Ƿ�ɹ�
	@Test
	void testUpdate()
	{
		DutyIntervalSet d = new DutyIntervalSet();
		d.initNotArranged(0, 30);
		assertEquals(d.getSize(), 1);
		d.updateArrangedStatus(10, 20);
		assertEquals(d.getSize(), 2);
	}
	
	// Testing strategy
	// �Ƚϼ�������Ԥ��
	@Test
	void testNotArrangedRatio()
	{
		DutyIntervalSet d = new DutyIntervalSet();
		d.initNotArranged(0, 30);
		d.updateArrangedStatus(0, 10);
		assertEquals(d.calcNotArrangedRatio(0, 30), 0.66666666666666666);		
	}
	

}
