package example;

import java.util.List;

import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertEquals;

/**
 * @author amir.raminfar
 */
public class LoopsTest {
    List<TestClass> tests = newArrayList();
    static final int TOTAL = 1000000;

    @Before
    public void setUp() {
        for (int i = 0; i < TOTAL; i++) {
            tests.add(new TestClass("robot #" + i));
        }
        TestClass.count = 0;
    }

    @Test
    public void testAll() {
        Loops.all(tests).sayMyName();
        assertEquals(TOTAL, TestClass.count);
    }

    @Test
    public void timedForLoop() {
        Stopwatch stopwatch = new Stopwatch().start();
        for (TestClass testClass : tests) {
            testClass.sayMyName();
        }
        stopwatch.stop();
        assertEquals(TOTAL, TestClass.count);
        System.out.println(">>> for-each loop took " + stopwatch);
    }

    @Test
    public void timedLoops() throws Exception {
        Loops.all(tests).sayMyName(); // Warm up the cache
        TestClass.count = 0;

        Stopwatch stopwatch = new Stopwatch().start();
        Loops.all(tests).sayMyName();
        stopwatch.stop();

        assertEquals(TOTAL, TestClass.count);
        System.out.println(">>> Loops.all took " + stopwatch);
    }

    static class TestClass {
        private String name;
        public static int count = 0;

        TestClass(String name) {
            this.name = name;
        }

        public void sayMyName() {
            count++;
        }

        public String toLoud() {
            return name.toUpperCase();
        }
    }
}
