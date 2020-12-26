package codewars;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DingleMouseTest {

    @Test
    public void ex1() {
        assertEquals(0, DingleMouse.countDeafRats("~O~O~O~O P"));
    }

    @Test
    public void ex2() {
        assertEquals(1, DingleMouse.countDeafRats("P O~ O~ ~O O~"));
    }

    @Test
    public void ex3() {
        assertEquals(2, DingleMouse.countDeafRats("~O~O~O~OP~O~OO~"));
    }

}
