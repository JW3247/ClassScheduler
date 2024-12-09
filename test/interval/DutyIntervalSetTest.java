package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DutyIntervalSetTest {

	// Testing strategy
	// 观察集合的大小，判断初始化插入是否成功
	@Test
	void testInit() {
		DutyIntervalSet d = new DutyIntervalSet();
		d.initNotArranged(0, 30);
		assertEquals(d.getSize(), 1);
	}
	
	// Testing strategy
	// 插入一个排班条目，比较结果字符串和预期是否相符，同时测试了dateToString方法
	@Test
	void testToString() throws IntervalConflictException {
		Employee e = new Employee("wang", "manager", "100");
		DutyIntervalSet d = new DutyIntervalSet();
		d.insert(20210710, 20210725, e);
		assertEquals(d.toString(), "Current duty roster:\nwang manager 100:2021.7.10 - 2021.7.25\n");	
	}
	
	// Testing strategy
	// 观察集合的大小判断更改是否成功
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
	// 比较计算结果和预期
	@Test
	void testNotArrangedRatio()
	{
		DutyIntervalSet d = new DutyIntervalSet();
		d.initNotArranged(0, 30);
		d.updateArrangedStatus(0, 10);
		assertEquals(d.calcNotArrangedRatio(0, 30), 0.66666666666666666);		
	}
	

}
