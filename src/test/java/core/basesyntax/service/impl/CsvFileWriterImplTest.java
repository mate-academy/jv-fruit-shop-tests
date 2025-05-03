package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static final String PATHNAME = "src/test/resources/reportTest.csv";
    private static final String PATHNAME_NOT_EXIST = "src/test/resources/reportNon.csv";
    private static CsvFileWriter fileWriter;

    private List<String> generatedListForWritingToFile;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new CsvFileWriterImpl();
    }

    @Before
    public void setUp() {
        generatedListForWritingToFile = new ArrayList<>();
        generatedListForWritingToFile.add("fruits,quantity");
        generatedListForWritingToFile.add("banana,340");
        generatedListForWritingToFile.add("orange,252");

    }

    @Test(expected = RuntimeException.class)
    public void write_ListWithStorageValuesIsNull_notOk() {
        fileWriter.write(null, PATHNAME);
    }

    @Test(expected = RuntimeException.class)
    public void write_pathNameIsNull_notOk() {
        fileWriter.write(generatedListForWritingToFile, null);
    }

    @Test
    public void write_normalValues_ok() {
        Assert.assertTrue(fileWriter.write(generatedListForWritingToFile, PATHNAME));
    }

    @Test(expected = RuntimeException.class)
    public void write_pathnameNotExist_notOk() {
        fileWriter.write(generatedListForWritingToFile, PATHNAME_NOT_EXIST);
    }
}
