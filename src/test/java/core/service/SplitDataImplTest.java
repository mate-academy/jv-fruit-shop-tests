package core.service;

import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SplitDataImplTest {
    private SplitDataImpl splitData = new SplitDataImpl();

    @Test
    public void testSplitData_ValidData_ok() {
        String data = "p,Apple,5" + System.lineSeparator()
                + "s,Banana,10" + System.lineSeparator()
                + "r,Orange,3";

        List<OperationData> result = splitData.splitData(data);

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(OperationType.P, result.get(0).getOperationType());
        Assertions.assertEquals("Apple", result.get(0).getProduct());
        Assertions.assertEquals(5, result.get(0).getQuantity());

        Assertions.assertEquals(OperationType.S, result.get(1).getOperationType());
        Assertions.assertEquals("Banana", result.get(1).getProduct());
        Assertions.assertEquals(10, result.get(1).getQuantity());

        Assertions.assertEquals(OperationType.R, result.get(2).getOperationType());
        Assertions.assertEquals("Orange", result.get(2).getProduct());
        Assertions.assertEquals(3, result.get(2).getQuantity());
    }

    @Test
    public void testSplitData_InvalidData_ok() {
        String data = "p,Apple 5" + System.lineSeparator()
                + "s,Banana,10" + System.lineSeparator()
                + "r,Orange,3";

        List<OperationData> result = splitData.splitData(data);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(OperationType.S, result.get(0).getOperationType());
        Assertions.assertEquals("Banana", result.get(0).getProduct());
        Assertions.assertEquals(10, result.get(0).getQuantity());

        Assertions.assertEquals(OperationType.R, result.get(1).getOperationType());
        Assertions.assertEquals("Orange", result.get(1).getProduct());
        Assertions.assertEquals(3, result.get(1).getQuantity());
    }

    @Test
    public void testSplitData_InvalidQuantity_ok() {
        String data = "p,Apple,5" + System.lineSeparator()
                + "s,Banana,abc" + System.lineSeparator()
                + "r,Orange,3";

        List<OperationData> result = splitData.splitData(data);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(OperationType.P, result.get(0).getOperationType());
        Assertions.assertEquals("Apple", result.get(0).getProduct());
        Assertions.assertEquals(5, result.get(0).getQuantity());

        Assertions.assertEquals(OperationType.R, result.get(1).getOperationType());
        Assertions.assertEquals("Orange", result.get(1).getProduct());
        Assertions.assertEquals(3, result.get(1).getQuantity());
    }

    @Test
    public void testSplitData_UnknownOperationType_ok() {
        String data = "p,Apple,5" + System.lineSeparator()
                + "u,Banana,10" + System.lineSeparator()
                + "r,Orange,3";

        List<OperationData> result = splitData.splitData(data);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(OperationType.P, result.get(0).getOperationType());
        Assertions.assertEquals("Apple", result.get(0).getProduct());
        Assertions.assertEquals(5, result.get(0).getQuantity());

        Assertions.assertEquals(OperationType.R, result.get(1).getOperationType());
        Assertions.assertEquals("Orange", result.get(1).getProduct());
        Assertions.assertEquals(3, result.get(1).getQuantity());
    }

    @Test
    public void testSplitData_EmptyData_ok() {
        String data = "";

        List<OperationData> result = splitData.splitData(data);

        Assertions.assertEquals(0, result.size());
    }
}
