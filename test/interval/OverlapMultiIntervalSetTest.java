package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OverlapMultiIntervalSetTest {

	// Testing strategy
	// 设置三门课，两门时间完全相同，计算冲突比例，比较结果
	@Test
	void testConflictRatio() {
		Course c1 = new Course("cs101", "cs", "zgb", "zz34", 4);
		Course c2 = new Course("cs102", "sc", "lm", "zx21", 6);
		Course c3 = new Course("cs103", "iis", "hq", "zx12", 4);
		CourseIntervalSet cs = new CourseIntervalSet();
		cs.courseInsert(510, 512, c1);
		cs.courseInsert(510, 512, c2);
		cs.courseInsert(308, 310, c3);
		assertEquals(cs.calcConflictRatio(cs), 0.5);		
	}
	
	// Testing strategy
	// 设置三门课，两门时间完全相同，计算空闲比例，比较结果
	void testFreeRatio()
	{
		Course c1 = new Course("cs101", "cs", "zgb", "zz34", 4);
		Course c2 = new Course("cs102", "sc", "lm", "zx21", 6);
		Course c3 = new Course("cs103", "iis", "hq", "zx12", 4);
		CourseIntervalSet cs = new CourseIntervalSet();
		cs.courseInsert(510, 512, c1);
		cs.courseInsert(510, 512, c2);
		cs.courseInsert(308, 310, c3);
		assertEquals(cs.calcFreeTimeRatio(cs), 0.94285716);
		
	}

}
