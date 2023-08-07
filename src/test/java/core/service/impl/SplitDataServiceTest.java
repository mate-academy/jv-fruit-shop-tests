package core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.service.OperationData;
import core.service.OperationType;
import core.service.SplitDataImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SplitDataServiceTest {
    private SplitDataService<OperationData> splitDataService = new SplitDataImpl();

    @Test
    public void testSplitData_ValidData_ok() {
        String data = " b,Apple,200" + System.lineSeparator()
                + "p,Orange,100" + System.lineSeparator()
                + "s,Banana,50";

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(3, dataList.size());
        assertEquals(OperationType.B, dataList.get(0).getOperationType());
        assertEquals("Apple", dataList.get(0).getProduct());
        assertEquals(200, dataList.get(0).getQuantity());
        assertEquals(OperationType.P, dataList.get(1).getOperationType());
        assertEquals("Orange", dataList.get(1).getProduct());
        assertEquals(100, dataList.get(1).getQuantity());
        assertEquals(OperationType.S, dataList.get(2).getOperationType());
        assertEquals("Banana", dataList.get(2).getProduct());
        assertEquals(50, dataList.get(2).getQuantity());
    }

    @Test
    public void testSplitData_EmptyData_ok() {
        String data = "";

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertTrue(dataList.isEmpty());
    }

    @Test
    public void testSplitData_DataWithLeadingAndTrailingWhitespaces_ok() {
        String data = "  b,Apple,200   " + System.lineSeparator()
                + "   p,Orange,100   " + System.lineSeparator()
                + "s,Banana,50   " + System.lineSeparator();

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(3, dataList.size());
    }

    @Test
    public void testSplitData_DataWithExtraEmptyLines_ok() {
        SplitDataService<OperationData> splitDataService = new SplitDataImpl();
        String data = System.lineSeparator()
                + "b,Apple,200"
                + System.lineSeparator()
                + "p,Orange,100"
                + System.lineSeparator()
                + "s,Banana,50"
                + System.lineSeparator();

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(3, dataList.size());
    }

    @Test
    public void testSplitData_DataWithInvalidOperationType_ok() {
        SplitDataService<OperationData> splitDataService = new SplitDataImpl();
        String data = "b,Apple,200" + System.lineSeparator()
                + "x,Orange,100" + System.lineSeparator()
                + "s,Banana,50";

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(2, dataList.size());
    }

    @Test
    public void testSplitData_DataWithInvalidQuantity_ok() {
        SplitDataService<OperationData> splitDataService = new SplitDataImpl();
        String data = "b,Apple,200" + System.lineSeparator()
                + "p,Orange,abc" + System.lineSeparator()
                + "s,Banana,50";

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(2, dataList.size());
    }

    @Test
    public void testSplitData_DataWithMissingFields_ok() {
        SplitDataService<OperationData> splitDataService = new SplitDataImpl();
        String data = "b,Apple,200" + System.lineSeparator()
                + "p,Orange" + System.lineSeparator()
                + "s,50";

        List<OperationData> dataList = splitDataService.splitData(data);

        assertNotNull(dataList);
        assertEquals(1, dataList.size());
    }
}
