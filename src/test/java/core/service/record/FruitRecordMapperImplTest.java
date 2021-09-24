package core.service.record;

import core.model.FruitRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitRecordMapperImplTest {
    private FruitRecordService fruitRecordService = new FruitRecordServiceImpl();
    private Mapper fruitMapper = new FruitRecordMapperImpl();

    @Before
    public void setUp() {
    }

    @Test
    public void mappingToObject_Ok() {
        FruitRecord actual = (FruitRecord) fruitMapper.mappingToObject("r,apple,100");
        FruitRecord expected = new FruitRecord("r", "apple", 100);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mappingToObject_NotOk() {
        FruitRecord actual = (FruitRecord) fruitMapper.mappingToObject("r,banana,100");
        FruitRecord expected = new FruitRecord("r", "apple", 100);
        Assert.assertNotEquals(expected, actual);
    }
}
