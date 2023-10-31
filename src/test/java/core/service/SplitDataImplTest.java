package core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.Test;

public class SplitDataImplTest {
    private static final String VALID_DATA = "p,Apple,5" + System.lineSeparator()
            + "s,Banana,10" + System.lineSeparator()
            + "r,Orange,3";

    private static final String INVALID_DATA = "p,Apple 5" + System.lineSeparator()
            + "s,Banana,10" + System.lineSeparator()
            + "r,Orange,3";

    private static final String INVALID_QUANTITY_DATA = "p,Apple,5" + System.lineSeparator()
            + "s,Banana,abc" + System.lineSeparator()
            + "r,Orange,3";

    private static final String UNKNOWN_OPERATION_DATA = "p,Apple,5" + System.lineSeparator()
            + "u,Banana,10" + System.lineSeparator()
            + "r,Orange,3";

    private static final String EMPTY_DATA = "";

    private SplitDataImpl splitData = new SplitDataImpl();

    @Test
    public void testSplitData_ValidData() {
        List<OperationData> result = splitData.splitData(VALID_DATA);

        assertAll(() -> assertEquals(3, result.size()),
                () -> assertOperationData(result.get(0), OperationType.P, "Apple", 5),
                () -> assertOperationData(result.get(1), OperationType.S, "Banana", 10),
                () -> assertOperationData(result.get(2), OperationType.R, "Orange", 3));
    }

    @Test
    public void testSplitData_InvalidData() {
        List<OperationData> result = splitData.splitData(INVALID_DATA);

        assertAll(() -> assertEquals(2, result.size()),
                () -> assertOperationData(result.get(0), OperationType.S, "Banana", 10),
                () -> assertOperationData(result.get(1), OperationType.R, "Orange", 3));
    }

    @Test
    public void testSplitData_InvalidQuantity() {
        List<OperationData> result = splitData.splitData(INVALID_QUANTITY_DATA);

        assertAll(() -> assertEquals(2, result.size()),
                () -> assertOperationData(result.get(0), OperationType.P, "Apple", 5),
                () -> assertOperationData(result.get(1), OperationType.R, "Orange", 3));
    }

    @Test
    public void testSplitData_UnknownOperationType() {
        List<OperationData> result = splitData.splitData(UNKNOWN_OPERATION_DATA);

        assertAll(() -> assertEquals(2, result.size()),
                () -> assertOperationData(result.get(0), OperationType.P, "Apple", 5),
                () -> assertOperationData(result.get(1), OperationType.R, "Orange", 3));
    }

    @Test
    public void testSplitData_EmptyData() {
        List<OperationData> result = splitData.splitData(EMPTY_DATA);

        assertEquals(0, result.size());
    }

    private void assertOperationData(OperationData data,
                                     OperationType type, String product, int quantity) {
        assertEquals(type, data.getOperationType());
        assertEquals(product, data.getProduct());
        assertEquals(quantity, data.getQuantity());
    }
}
