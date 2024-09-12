package core.basesyntax.dao;

import static core.basesyntax.service.TestConstants.DEFAULT_FILE_NAME_TO_WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class MyFileWriterImplTest {
    private static final MyFileWriterImpl myFileWriter = new MyFileWriterImpl();

    @Test
    void parse_validInput_ok() throws IOException {
        String expected = "test";
        myFileWriter.write(DEFAULT_FILE_NAME_TO_WRITE, expected);
        File file = new File(DEFAULT_FILE_NAME_TO_WRITE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        assertTrue(file.exists(), "File should be created");
        assertEquals(expected, line, "File content should match the written string");
    }

    @Test
    void write_invalidInput_notOk() {
        String expected = "Content is null";
        RuntimeException actual = assertThrows(RuntimeException.class,
                () -> myFileWriter.write(DEFAULT_FILE_NAME_TO_WRITE, null));
        assertEquals(expected, actual.getMessage());
    }
}
