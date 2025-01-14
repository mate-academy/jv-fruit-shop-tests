package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String correctPath = "src/test/resources/finalReport.csv";
    private FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_wrongContent_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, correctPath));
        String expected = "Empty content was given";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void write_simpleFileWriting_Ok() {
        String testLine = "fruit,quantity" + System.lineSeparator() + "banana,55";
        fileWriter.write(testLine, correctPath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(correctPath));
            String actual = br.readLine();
            assertEquals("fruit,quantity", actual);
            actual = br.readLine();
            assertEquals("banana,55", actual);
        } catch (IOException e) {
            System.out.println("Can`t read file");;
        }
    }
}
