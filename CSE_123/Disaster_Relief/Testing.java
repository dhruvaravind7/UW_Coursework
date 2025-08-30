import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstCaseTest() {
        List<Region> result = new ArrayList<>();
        result.add(new Region("Region #1", 100, 400));
        result.add(new Region("Region #2", 150, 600));

        assertEquals(Client.allocateRelief(500, result).toString(), "[Region #1: pop. 100, base cost: $400.0]");
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondCaseTest() {
        List<Region> result = new ArrayList<>();
        result.add(new Region("Region #1", 150, 400));
        result.add(new Region("Region #2", 100, 450));

        assertEquals(Client.allocateRelief(500, result).toString(), "[Region #1: pop. 150, base cost: $400.0]");
    }

    @Test
    @DisplayName("STUDENT TEST - Case #3")
    public void thirdCaseTest() {
        List<Region> result = new ArrayList<>();
        result.add(new Region("Region #1", 150, 450));
        result.add(new Region("Region #2", 150, 400));

        assertEquals(Client.allocateRelief(500, result).toString(), "[Region #2: pop. 150, base cost: $400.0]");
    }

    @Test
    @DisplayName("STUDENT TEST - DIY")
    public void diyTest() {
        List<Region> result = new ArrayList<>();
        result.add(new Region("Region #1", 151, 300));
        result.add(new Region("Region #2", 150, 250));

        assertEquals(Client.allocateRelief(500, result).toString(), "[Region #1: pop. 151, base cost: $300.0]");

    }
}
