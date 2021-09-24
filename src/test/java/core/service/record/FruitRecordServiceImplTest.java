package core.service.record;

import core.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitRecordServiceImplTest {

    private FruitRecordService fruitRecordService;

    @Before
    public void setUp() {
        fruitRecordService = new FruitRecordServiceImpl();
    }

    @Test
    public void parserFruit_Ok() {
        List<String> listFruits = new ArrayList<>();
        listFruits.add("r,banana,100");
        listFruits.add("r,apple,25");
        List<FruitRecord> actual = fruitRecordService.parserFruit(listFruits);
        List<FruitRecord> expected = new ArrayList<>();
        expected.add(new FruitRecord("r", "banana", 100));
        expected.add(new FruitRecord("r", "apple", 25));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parserFruit_NotOk() {
        List<String> listFruits = new ArrayList<>();
        listFruits.add("r,orange,100");
        listFruits.add("r,apple,25");
        List<FruitRecord> actual = fruitRecordService.parserFruit(listFruits);
        List<FruitRecord> expected = new ArrayList<>();
        expected.add(new FruitRecord("r", "banana", 100));
        expected.add(new FruitRecord("r", "apple", 25));
        Assert.assertNotEquals(expected, actual);
    }
}
