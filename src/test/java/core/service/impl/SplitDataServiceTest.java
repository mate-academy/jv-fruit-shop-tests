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

        assertOperationDataListEquals(
                dataList,
                new OperationData(OperationType.B, "Apple", 200),
                new OperationData(OperationType.P, "Orange", 100),
                new OperationData(OperationType.S, "Banana", 50)
        );
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

    private void assertOperationDataListEquals(List<OperationData> actualList, OperationData... expectedData) {
        assertNotNull(actualList);
        assertEquals(expectedData.length, actualList.size());

        for (int i = 0; i < expectedData.length; i++) {
            OperationData expected = expectedData[i];
            OperationData actual = actualList.get(i);

            assertEquals(expected.getOperationType(), actual.getOperationType());
            assertEquals(expected.getProduct(), actual.getProduct());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }
}
