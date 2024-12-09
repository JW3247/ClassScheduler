package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProcessIntervalSetTest {
	

	// Testing strategy
	// ����һ��Ӧ�ñ���ֹ�Ľ��̣�Ȼ�����checkTerminated���۲켯�ϴ�С���Ƿ���ֹ�ɹ�
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
	// ���������������ִ��ʱ�䲻ͬ�Ľ��̣����ܷ�ѡ����ȷ���Ǹ�
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
