package core.basesyntax.basesyntax.service;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String SEPARATOR = File.separator;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void reader_correctReadFile_Ok() {
        String pathReaderFile = "src" + SEPARATOR
                + "test" + SEPARATOR
                + "resources" + SEPARATOR
                + "readerTest.csv";

        List<String> expectedList = List.of("Code is like humor.",
                "When you have to explain it,",
                "it’s bad.",
                "Cory House");
        List<String> actualList = fileReader.readFromFile(pathReaderFile);
        Assert.assertEquals(expectedList,actualList);
    }
}
