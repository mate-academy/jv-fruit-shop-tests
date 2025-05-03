package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String SOME_TEXT = "12345qwerty";
    private static final String CORRECT_FILE_NAME = "src/test/resources/testReport.csv";
    private static final String WRONG_PATH = "#$%^EdD^%&//Wrong path..//";
    private static WriterService writer = new WriterServiceImpl();

    @After
    public void tearDown() throws Exception {
        Path testFile = Path.of(CORRECT_FILE_NAME);
        if (Files.exists(testFile)) {
            Files.delete(testFile);
        }
    }

    @Test
    public void write_Empty_Ok() {
        writer.write("", CORRECT_FILE_NAME);
        assertEquals("", readFile(CORRECT_FILE_NAME));
    }

    @Test
    public void write_SomeText_Ok() {
        writer.write(SOME_TEXT, CORRECT_FILE_NAME);
        assertEquals(SOME_TEXT, readFile(CORRECT_FILE_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void write_WrongDirectory_NotOk() {
        writer.write(SOME_TEXT, WRONG_PATH);
    }

    private String readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from test report:" + fileName, e);
        }
    }
}
