package example;

import java.util.List;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author amir.raminfar
 */
public class LoopsTest {

    @Test
    public void testAll() throws Exception {
        List<TestClass> tests = newArrayList();
        for (int i = 0; i < 100; i++) {
            tests.add(new TestClass("robot #" + i));
        }

        Loops.all(tests).sayMyName();
    }

    static class TestClass {
        private String name;

        TestClass(String name) {
            this.name = name;
        }

        public void sayMyName() {
            System.out.println(name);
        }

        public String toLoud() {
            return name.toUpperCase();
        }
    }
}
