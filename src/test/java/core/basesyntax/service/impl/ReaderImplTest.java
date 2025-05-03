package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderImplTest {
    private static final Reader READER = new ReaderImpl();
    private static final String FILE_PASS_NAME = "src/test/resources/test.csv";

    @BeforeAll
    static void beforeAll() {
        File tempFile = new File(FILE_PASS_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("line 1\n");
            writer.write("line 2\n");
            writer.write("line 3\n");
        } catch (IOException e) {
            System.out.println("Can't write to file");
        }
    }

    @Test
    void read_corData_ok() {
        List<String> expected = Arrays.asList("line 1", "line 2", "line 3");
        List<String> actual = READER.read(FILE_PASS_NAME);
        assertEquals(expected,actual);
    }

    @Test
    void read_nonExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> READER.read("nonexistentfile.csv"));
    }

    @AfterAll
    static void afterAll() {
        File fileToDelete = new File(FILE_PASS_NAME);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
}
