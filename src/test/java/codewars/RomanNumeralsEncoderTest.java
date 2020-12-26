package codewars;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@Slf4j
//@RunWith(MockitoJUnitRunner.class)
public class RomanNumeralsEncoderTest {

    private RomanNumeralsEncoder conversion = new RomanNumeralsEncoder();

    @Test
    public void shouldConvertToRoman() {
        assertEquals("solution(1) should equal to I", "I", conversion.solution(1));
        assertEquals("solution(4) should equal to IV", "IV", conversion.solution(4));
        assertEquals("solution(6) should equal to VI", "VI", conversion.solution(6));
        assertEquals("solution(6) should equal to VI", "VI", conversion.solution(6));
        assertEquals("solution(7) should equal to VII", "VII", conversion.solution(7));
        assertEquals("solution(8) should equal to VIII", "VIII", conversion.solution(8));
        assertEquals("solution(11) should equal to XI", "XI", conversion.solution(11));
        assertEquals("solution(12) should equal to XII", "XII", conversion.solution(12));
        assertEquals("solution(15) should equal to XV", "XV", conversion.solution(15));
        assertEquals("solution(16) should equal to XVI", "XVI", conversion.solution(16));
        assertEquals("solution(60) should equal to LX", "LX", conversion.solution(60));
        assertEquals("solution(65) should equal to LXV", "LXV", conversion.solution(65));
    }


}
    



