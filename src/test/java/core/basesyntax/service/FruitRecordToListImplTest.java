package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruitrecord.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordToListImplTest {
    private static final String NORMAL_DATA = "b,orange,20 b,cherry,1000 s,orange,100 p,cherry,13 ";
    private static List<FruitRecord> records;
    private FruitRecordToList recordToList = new FruitRecordToListImpl();
    private List<FruitRecord> expected;

    @BeforeClass
    public static void beforeClass() {
        records = new ArrayList<>();
        FruitRecord orangeBalance = new FruitRecord(new String[]{"b", "orange", "20"});
        FruitRecord cherryBalance = new FruitRecord(new String[]{"b", "cherry", "1000"});
        FruitRecord orangeSupply = new FruitRecord(new String[]{"s", "orange", "100"});
        FruitRecord cherrySupply = new FruitRecord(new String[]{"p", "cherry", "13"});
        records.add(orangeBalance);
        records.add(cherryBalance);
        records.add(orangeSupply);
        records.add(cherrySupply);
    }

    @Test
    public void fruitRecordToList_normalData_Ok() {
        expected = records;
        assertEquals("Can't write this data to the list " + NORMAL_DATA,
                expected, recordToList.fruitRecordToList(NORMAL_DATA));
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_emptyData_not_Ok() {
        recordToList.fruitRecordToList("");
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_checkNull_not_Ok() {
        recordToList.fruitRecordToList(null);
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_invalidSyntax_not_Ok() {
        String invalidSyntax = "b,orange,20 b,cherry,1,000";
        recordToList.fruitRecordToList(invalidSyntax);
    }

    @Test(expected = RuntimeException.class)
    public void fruitRecordToList_invalidAmount_not_Ok() {
        String invalidAmount = "b,orange,20 b,cherry,-1000";
        recordToList.fruitRecordToList(invalidAmount);
    }
}
