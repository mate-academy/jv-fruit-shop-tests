package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruitrecord.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordToListImplTest {
    public static final List<FruitRecord> records = new ArrayList<>();
    public static final String normalData = "b,orange,20 b,cherry,1000 s,orange,100 p,cherry,13 ";
    private static FruitRecordToList recordToList = new FruitRecordToListImpl();
    private static String[] orangeRecord = new String[]{"b", "orange", "20"};
    private static String[] cherryRecord = new String[]{"b", "cherry", "1000"};
    private static String[] orangeRecord1 = new String[]{"s", "orange", "100"};
    private static String[] cherryRecord1 = new String[]{"p", "cherry", "13"};
    private List<FruitRecord> expected;
    private List<FruitRecord> actual;

    @BeforeClass
    public static void beforeClass() {
        records.add(new FruitRecord(orangeRecord));
        records.add(new FruitRecord(cherryRecord));
        records.add(new FruitRecord(orangeRecord1));
        records.add(new FruitRecord(cherryRecord1));
    }

    @Test
    public void fruitRecordToList_normalData_Ok() {
        expected = records;
        assertEquals(expected, recordToList.fruitRecordToList(normalData));
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_emptyData_not_Ok() {
        assertEquals(expected, recordToList.fruitRecordToList("").getClass());
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_checkNull_not_Ok() {
        assertEquals(expected, recordToList.fruitRecordToList(null).getClass());
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_invalidSyntax_not_Ok() {
        String invalidSyntax = "b,orange,20 b,cherry,1,000";
        actual = recordToList.fruitRecordToList(invalidSyntax);
        assertEquals(expected, actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_invalidAmount_not_Ok() {
        String invalidAmount = "b,orange,20 b,cherry,-1000";
        actual = recordToList.fruitRecordToList(invalidAmount);
        assertEquals(expected, actual.getClass());
    }
}
