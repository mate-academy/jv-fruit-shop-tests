package core.basesyntax.service.impl;

import core.basesyntax.servise.ReaderService;
import core.basesyntax.servise.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class WriteServiceImplTest {
    private static final String TO_FILE = "src/test/resourcesfortest/ReportFile.csv";
    private static WriteService writeService;
    private static ReaderService readerService;
    private String report;
    private String expectedReport;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
        readerService = new ReaderServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "avocado,40" + System.lineSeparator()
                + "banana,43" + System.lineSeparator()
                + "apple,185" + System.lineSeparator();
    }

    @Test
    public void write_PathFileIsNullNotOk() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            writeService.writeToFile(report,null);
        }, "Path can't be null");
    }

    @Test
    public void write_pathEqualsInputPathIsOk() {
        String actual = "src/test/resourcesfortest/ReportFile.csv";
        Assertions.assertEquals(TO_FILE,actual);
    }

    @Test
    public void write_pathNotEqualsInputPathIsNotOk() {
        String actual = "src/main/bamburses/ReportFile.csv";
        Assertions.assertNotEquals(TO_FILE,actual);
    }

    @Test
    public void write_reportIsNullNotOk() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            writeService.writeToFile(null,TO_FILE);
        }, "Report can't be null");
    }

    @Test
    public void write_contentIsSame() {
        writeService.writeToFile(report,TO_FILE);
        String actual;
        try {
            actual = Files.readString(Path.of(TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file date" + TO_FILE, e);
        }
        Assertions.assertEquals(report,actual);
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete data from file",e);
        }
    }
}
