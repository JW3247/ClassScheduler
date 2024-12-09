package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommonIntervalSetTest {
	
	// Testing strategy
	// �½�һ���ն��������ǩ���Ĵ�С�ж��Ƿ�Ϊ��
	@Test
	void testEmpty()
	{
		IntervalSet<String> set = IntervalSet.empty();
		assertEquals(set.labels().size(), 0);
	}
	
	// Testing strategy
	// ����Ԫ�أ�ͨ����ǩ����С�ж�ȡ��ǩ�����Ƿ���ȷ
	@Test
	void testLabels() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.labels().size(), 1);
		set.insert(2, 5, "B");
		assertEquals(set.labels().size(), 2);		
	}
	
	// Testing strategy
	// ����Ԫ�أ���toString�����۲�
	@Test
	void testInsert() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.toString(), "A: [0,1)\n");
		set.insert(2, 5, "B");
		assertEquals(set.toString(), "A: [0,1)\nB: [2,5)\n");	
	}
	
	// Testing strategy
	// �۲���ֵ
	@Test
	void testStart() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.start("A"), 0);	
	}
	
	// Testing strategy
	// �۲���ֵ
	@Test
	void testEnd() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.end("A"), 1);	
	}
	
	// Testing strategy
	// �۲��ǩ���Ĵ�С
	@Test
	void testRemove() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		set.insert(2, 5, "B");
		assertEquals(set.labels().size(), 2);
		set.remove("A");
		assertEquals(set.labels().size(), 1);
		set.remove("B");
		assertEquals(set.labels().size(), 0);		
	}

}
