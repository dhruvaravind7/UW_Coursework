import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class Testing {
    @Test
    @DisplayName("Point tests")
    public void testPoint() {
        Point p = new Point(0, 0);

        assertEquals(1, p.distanceTo(new Point(0, 1)));
        assertEquals(Math.sqrt(2), p.distanceTo(new Point(1, 1)));

        assertTrue(p.equals(new Point(0, 0)));
        assertFalse(p.equals(new Point(0, 1)));

        assertEquals("(0, 0)", p.toString());
    }
}
