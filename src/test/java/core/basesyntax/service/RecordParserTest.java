package core.basesyntax.service;

import core.basesyntax.database.Database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecordParserTest {
    private static final int EXPECTED_OK_SIZE = 2;
    private static RecordParser recordParser;

    @BeforeClass
    public static void beforeAll() {
        recordParser = new RecordParserImpl();
    }

    @Test
    public void parseRecords_correctData_Ok() {
        List<String> testData = new ArrayList<>();
        testData.add("b,banana,20");
        testData.add("b,apple,100");
        recordParser.parseRecords(testData);
        int actual = Database.RECORDS.size();
        int expected = EXPECTED_OK_SIZE;
        Assert.assertEquals("Actual and expected lists differ from each other: ", expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseRecords_emptyData_NotOk() {
        List<String> testData = Collections.emptyList();
        recordParser.parseRecords(testData);
    }

    @After
    public void tearDown() {
        Database.RECORDS.clear();
    }
}
