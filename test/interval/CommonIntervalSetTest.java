package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommonIntervalSetTest {
	
	// Testing strategy
	// 新建一个空对象，用其标签集的大小判断是否为空
	@Test
	void testEmpty()
	{
		IntervalSet<String> set = IntervalSet.empty();
		assertEquals(set.labels().size(), 0);
	}
	
	// Testing strategy
	// 插入元素，通过标签集大小判断取标签方法是否正确
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
	// 插入元素，用toString方法观察
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
	// 观察数值
	@Test
	void testStart() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.start("A"), 0);	
	}
	
	// Testing strategy
	// 观察数值
	@Test
	void testEnd() throws IntervalConflictException
	{
		IntervalSet<String> set = IntervalSet.empty();
		set.insert(0, 1, "A");
		assertEquals(set.end("A"), 1);	
	}
	
	// Testing strategy
	// 观察标签集的大小
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
