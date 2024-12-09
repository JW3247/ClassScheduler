package interval;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;

import org.junit.Test;

/**
 * Tests for instance methods of MultiIntervalSet.
 */

public class MultiIntervalSetTest {
	
	/*
	 * Testing MultiIntervalSet...
	 */

	// Testing strategy
	// �۲�labels���ؼ��ϵĴ�С�������ǩ�г�ͻ�����
	@Test
	public void testLabels() throws IntervalConflictException
	{
		MultiIntervalSet<String> c = new MultiIntervalSet<String>() ;
		c.insert(20, 30, "A");
		c.insert(10, 20, "B");
		c.insert(0, 10, "A");
		assertEquals(c.labels().size(), 2);		
	}
	
	// Testing strategy
	// �����ǩ�ڼ����кͲ��ڼ����е�������۲캯������ֵ��toString�Ľ��
	public void testRemoveAll() throws IntervalConflictException
	{
		MultiIntervalSet<String> c = new MultiIntervalSet<String>() ;
		c.insert(20, 30, "A");
		c.insert(10, 20, "B");
		c.insert(0, 10, "A");
		assertEquals(c.removeAll("C"), false);
		assertEquals(c.removeAll("A"), true);
		assertEquals(c.toString(), "B: [10,20)\n");
		
	}
	
	
	
	
	// Testing strategy
	// ���±����intervals����ֹʱ�䣬���Ƿ�������
	@Test
	public void testIntervals() throws IntervalConflictException
	{
		MultiIntervalSet<String> c = new MultiIntervalSet<String>() ;
		c.insert(20, 30, "A");
		c.insert(10, 20, "B");
		c.insert(0, 10, "A");
		assertEquals(c.intervals("A").start(0), 0);
		assertEquals(c.intervals("A").end(0), 10);
		assertEquals(c.intervals("A").start(1), 20);
		assertEquals(c.intervals("A").end(1), 30);
	}
	
	// Testing strategy
	// ���Բ���һ����ǩ��ʱ����ȫ��ͬ����Ŀ�������ʹ��toString�����۲������÷���ͬʱ������toString����
	@Test
	public void testInsert() throws IntervalConflictException
	{
		MultiIntervalSet<String> c = new MultiIntervalSet<String>();
		c.insert(0, 10, "A");
		c.insert(20, 30, "A");
		assertEquals(c.toString(), "A [0,10) [20,30)\n");
		c.insert(0, 10, "A");
		assertEquals(c.toString(), "A [0,10) [20,30)\n");
		c.insert(10, 20, "B");
		assertEquals(c.toString(), "A [0,10) [20,30)\nB [10,20)\n");
		
	}
	
	// Testing strategy
	// ����ָ������������в��ԣ��ȽϽ����������С�������λ��
	@Test
	public void testSimilarity() throws IntervalConflictException
	{
		MultiIntervalSet<String> a = new MultiIntervalSet<String>();
		MultiIntervalSet<String> b = new MultiIntervalSet<String>() ;
		a.insert(0, 5, "A");
		a.insert(20, 25, "A");
		a.insert(10, 20, "B");
		a.insert(25, 30, "B");
		b.insert(20, 35, "A");
		b.insert(10, 20, "B");
		b.insert(0, 5, "C");
		DecimalFormat df = new DecimalFormat("0.00000"); 

		assertEquals(df.format(a.Similarity(a, b)), "0.42857");
	}
}
