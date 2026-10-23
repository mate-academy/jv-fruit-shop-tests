package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterForFileImplTest {
    private static WriterForFileImpl writer;
    private static ReaderServiceImpl reader;

    @BeforeAll
    public static void setUp() {
        writer = new WriterForFileImpl();
        reader = new ReaderServiceImpl();
    }

    @Test
    public void writeFile() {
        writer.write("Hello, World!", "src/test/resources/finalReportTest.csv");
        List<String> lines = reader.read("src/test/resources/finalReportTest.csv");
        assertEquals("Hello, World!", lines.get(0));
    }

    @Test
    public void writeToInvalidPathTest() {
        assertThrows(RuntimeException.class,
                () -> writer.write("Hello, World!", "src/test/missing-folder/file.csv"));
    }
}
