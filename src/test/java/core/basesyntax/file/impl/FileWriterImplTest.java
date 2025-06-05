package core.basesyntax.file.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.file.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final String actualPath = "src/test/resources/resultActual.csv";
    private final String validFile = "src/main/resources/data.csv";

    private FileWriterImpl fileWriter;
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void write_ValidData_Ok() {
        fileWriter.write(fileReader.read(validFile).toString(), actualPath);
        Assertions.assertEquals(fileReader.read(actualPath).toString(),
                "[[type,fruit,quantity, b,banana,20, b,apple,100, s,banana,100,"
                        + " p,banana,13, r,apple,10, p,apple,20, p,banana,5, s,banana,50]]");
    }

    @Test
    void write_IoException_notOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write("line1, line2", "/nonexistent/directory/test.txt")
        );
        Assertions.assertTrue(exception.getMessage().startsWith("Can't write data to file"));
    }
}
