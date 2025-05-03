package dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DaoReaderImplTest {
    private static DaoReader daoReader;
    private static final String ABSENT_FILE = "src/test/resources/file_not_found.txt";
    private static final String BASIC_DATA_FILE = "src/test/resources/basic_data.txt";
    private static final String EMPTY_DATA_FILE = "src/test/resources/empty_data.txt";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        daoReader = new DaoReaderImpl();
    }

    @Test
    public void getData_Ok() {
        List<String> expectedData = new ArrayList<>();
        expectedData.add("type,fruit,quantity");
        expectedData.add("b,banana,20");
        expectedData.add("b,apple,100");
        expectedData.add("s,banana,100");
        expectedData.add("p,banana,13");
        expectedData.add("r,apple,10");
        expectedData.add("p,apple,20");
        expectedData.add("p,banana,5");
        expectedData.add("s,banana,50");
        List<String> actualData = daoReader.get(BASIC_DATA_FILE);
        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    public void getEmptyData_Ok() {
        List<String> expectedData = new ArrayList<>();
        List<String> actualData = daoReader.get(EMPTY_DATA_FILE);
        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    public void getData_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't get data from file " + ABSENT_FILE);
        List<String> actualData = daoReader.get(ABSENT_FILE);
    }

    @Test
    public void nullPath_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("File name is null");
        List<String> actualData = daoReader.get(null);
    }
}
