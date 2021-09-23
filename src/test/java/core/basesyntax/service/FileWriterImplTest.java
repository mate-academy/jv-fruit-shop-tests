package core.basesyntax.service;

import core.basesyntax.db.FruitDataBase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/resources/testFile.csv";
    private static FileWriter fileWriter;
    private static String report;
    private String expected;
    private String actual;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
    }

    @Test
    public void write_CorrectData_Ok() {
        fileWriter.write(report, TEST_FILE);
    }

    @After
    public void tearDown() throws Exception {
        FruitDataBase.storage.clear();
    }
}
