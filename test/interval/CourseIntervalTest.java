package interval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseIntervalTest {

	// Testing strategy
	// ���룬�ø����toString����������
	@Test
	void testCourseInsert() {
		Course c1 = new Course("cs101", "cs", "zgb", "zz34", 4);
		Course c2 = new Course("cs102", "sc", "lm", "zx21", 6);
		CourseIntervalSet cs = new CourseIntervalSet();
		cs.courseInsert(0, 30, c1);
		cs.courseInsert(0, 30, c2);
		assertEquals(cs.fatherToString(), "cs102:sc lm-zx21 6/week [0,30)\ncs101:cs zgb-zz34 4/week [0,30)\n");
	}
	
	// Testing strategy
	// �ñ����toString������������ͬʱ����toString����
	@Test
	void testNotFullyArranged()
	{
		Course c1 = new Course("cs101", "cs", "zgb", "zz34", 4);
		CourseIntervalSet cs = new CourseIntervalSet();
		cs.updateNotFullyArranged(c1, 2);
		assertEquals(cs.toString(), "To be arranged: (toBeArrangedHours/totalHours)\ncs101:cs 2/4\n");
	}
	
	// Testing strategy
	// �Ȳ���һ��δ���ŵĿγ̣�Ȼ������Ϊ�Ѱ����꣬checkArranged����toString�Ƿ���ȷ
	@Test
	void testCheckArranged()
	{
		Course c1 = new Course("cs101", "cs", "zgb", "zz34", 4);
		CourseIntervalSet cs = new CourseIntervalSet();
		cs.updateNotFullyArranged(c1, 4);
		assertEquals(cs.toString(), "To be arranged: (toBeArrangedHours/totalHours)\ncs101:cs 4/4\n");
		cs.updateNotFullyArranged(c1, 0);
		cs.checkArranged();
		assertEquals(cs.toString(), "To be arranged: (toBeArrangedHours/totalHours)\n");
	}

}
