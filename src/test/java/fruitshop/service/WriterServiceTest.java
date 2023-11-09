package fruitshop.service;

import fruitshop.service.impl.ReaderServiceImpl;
import fruitshop.service.impl.WriterServiceImpl;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WriterServiceTest {
    private static final String WRITE_TEST_FILE_NAME = "testFile.csv";
    private static final int FIRST_SYMBOL_INDEX = 0;
    private static final int DECREASED_INDEX = 1;
    private ReaderService reader;
    private WriterService writer;
    private String text = "TEST TEXT"
            + System.lineSeparator()
            + "12345";

    @BeforeAll
    void setUp() {
        reader = new ReaderServiceImpl();
        writer = new WriterServiceImpl();
        writeTestText(text, WRITE_TEST_FILE_NAME);
    }

    void writeTestText(String text, String filePath) {
        writer.writeToFile(text, filePath);
    }

    @Test
    void writeToFile_WritesTextToFile_Ok() {
        writer.writeToFile(text, WRITE_TEST_FILE_NAME);
        String actual = reader.readFromFile(WRITE_TEST_FILE_NAME)
                .stream()
                .map(string -> string + System.lineSeparator())
                .collect(Collectors.joining());
        actual = actual.substring(FIRST_SYMBOL_INDEX,
                actual.length() - DECREASED_INDEX);

        Assertions.assertEquals(actual, text,
                "Writer should have written "
                        + text
                        + " but wrote "
                        + actual);
    }
}
