package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.TextFileWriterInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextFileWriterTest {
    private TextFileWriterInterface textFileWriter;
    private String tempFilePath;

    @BeforeEach
    void setUp() {
        textFileWriter = new TextFileWriter();
        tempFilePath = "tempFile.txt";
    }

    @Test
    void testWriteTextToFile() throws IOException {
        String text = "This is a test text.";
        textFileWriter.writeText(text, tempFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFilePath))) {
            String line = reader.readLine();
            assertEquals(text, line);
        }
    }

    @Test
    void testWriteTextFileWithIoexception() {
        assertThrows(IOException.class, () -> textFileWriter.writeText("Text",
                "nonexistentfolder/nonexistentfile.txt"));
    }
}
