package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.InputReadException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InputReaderImplTest {

    private static final String EMPTY_PATH = "";
    private static final String PATH_EMPTY_FILE = "src/main/resources/emptyFile.txt";
    private static final String EMPTY_STRING = "";
    private static final String NON_EXISTING_FILE_PATH = "src/main/resources/filePath.abc";
    private static final String DEFAULT_FILE_PATH = "src/main/resources/default.txt";
    private static final String DEFAULT_FILE_CONTENT = "Lorem ipsum dolor sit amet, "
            + "consectetur adipiscing elit, sed"
            + " do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
            + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
            + " nisi ut aliquip ex ea commodo consequat.";
    private InputReader inputReader;
    private File emptyFile;
    private File defaultFile;

    @Before
    public void setUp() {
        inputReader = new InputReaderImpl();
        emptyFile = new File(PATH_EMPTY_FILE);
        defaultFile = new File(DEFAULT_FILE_PATH);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(defaultFile))) {
            emptyFile.createNewFile();
            bw.write(DEFAULT_FILE_CONTENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void after() {
        try {
            Files.deleteIfExists(Path.of(PATH_EMPTY_FILE));
            Files.deleteIfExists(Path.of(DEFAULT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readInputCsv_nullArgument_notOk() {
        try {
            inputReader.readInputCsv(null);
        } catch (InputReadException e) {
            return;
        }
        fail("If argument is null then readInputCsv should throw InputReadException");
    }

    @Test
    public void readInputCsv_argumentIsEmptyString_notOk() {
        try {
            inputReader.readInputCsv(EMPTY_PATH);
        } catch (InputReadException e) {
            return;
        }
        fail("if argument is \"\" then readInputCsv should throw InputReadException");
    }

    @Test
    public void readInputCsv_readEmptyFile_ok() {
        String actual = inputReader.readInputCsv(PATH_EMPTY_FILE);
        assertEquals("readInputCsv should "
                + "return an empty string if there is nothing in the file being read",
                EMPTY_STRING, actual);
    }

    @Test
    public void readInputCsv_nonExistingFile_notOk() {
        try {
            inputReader.readInputCsv(NON_EXISTING_FILE_PATH);
        } catch (InputReadException e) {
            return;
        }
        fail("readInputCsv must throw an InputReadException"
                + "if the argument is a path to a non-existent file");
    }

    @Test
    public void readInputCsv_readFromFileDefault_ok() {
        String actual = inputReader.readInputCsv(DEFAULT_FILE_PATH);
        assertEquals("readInputCsv should not change data while reading",
                DEFAULT_FILE_CONTENT, actual);
    }

}
