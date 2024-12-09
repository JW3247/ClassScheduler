package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProcessIntervalSetTest {
	

	// Testing strategy
	// 设置一个应该被终止的进程，然后调用checkTerminated，观察集合大小看是否终止成功
	@Test
	void testCheckTerminated() {
		Process p1 = new Process(1, "shell", 5, 10);
		Process p2 = new Process(2, "bash", 10 , 20);
		ProcessIntervalSet ps = new ProcessIntervalSet();
		ps.setExecutionTime(p1, 7);
		ps.setExecutionTime(p2, 5);
		assertEquals(ps.getSize(), 2);
		ps.checkTerminated();
		assertEquals(ps.getSize(), 1);		
	}
	
	
	// Testing strategy
	// 设置两个距离最大执行时间不同的进程，看能否选择正确的那个
	@Test
	void testBestOne()
	{
		Process p1 = new Process(1, "shell", 5, 10);
		Process p2 = new Process(2, "bash", 10 , 20);
		ProcessIntervalSet ps = new ProcessIntervalSet();
		ps.setExecutionTime(p1, 7);
		ps.setExecutionTime(p2, 5);
		assertEquals(ps.bestOne(), p1);		
	}

}
