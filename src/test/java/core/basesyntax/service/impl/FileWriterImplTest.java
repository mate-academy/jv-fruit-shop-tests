package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterImplTest {
    private static final String LEGIT_TEST_PATH = "src/main/resources/testWriter.csv";
    private static FileWriter fileWriter;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_ProperFilePath_Ok() {

        String content = "fruit,quantity" + System.lineSeparator() + "Apple,20";
        fileWriter.write(content, LEGIT_TEST_PATH);
        File file = new File(LEGIT_TEST_PATH);
        try {
            List<String> contentActual = Files.readAllLines(Paths.get(LEGIT_TEST_PATH));
            List<String> expected = List.of("fruit,quantity", "Apple,20");
            assertEquals(expected, contentActual);
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException("Test reader in WriterClass failed", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_InvalidPath_NotOk() {
        fileWriter.write("content",null);
    }

    @Test
    public void write_ValidPathEmptyString_Ok() {
        fileWriter.write("", LEGIT_TEST_PATH);
        try {
            List<String> stringsActual = Files.readAllLines(Paths.get(LEGIT_TEST_PATH));
            assertTrue(stringsActual.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException("Test reader in WriterClass failed", e);
        }
    }

}
