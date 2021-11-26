package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String VALID_FILE_PATH
            = "src/test/resources/resultFruits-logTest.csv";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = NullPointerException.class)
    public void file_ReportIsNull_notOk() {
        fileWriter.write(null, VALID_FILE_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void file_PathIsNull_notOk() {
        fileWriter.write("fruit,type", null);
    }

    @Test(expected = RuntimeException.class)
    public void file_PathIsEmpty_notOk() {
        fileWriter.write("fruit,type", "");
    }

    @Test
    public void file_ReportIsEmpty_Ok() {
        fileWriter.write("", VALID_FILE_PATH);
    }
}
