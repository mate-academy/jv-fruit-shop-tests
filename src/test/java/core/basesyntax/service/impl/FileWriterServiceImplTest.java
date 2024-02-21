package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String OVERWRITE_FILE_PATH = "src/test/resources/FileToOverwrite.csv";
    private static final String NEW_FILE_PATH = "src/test/resources/NewFile.csv";
    private static final String NEW_FOLDER_PATH = "src/test/resources/NewFolder/NewFile.csv";
    private static final String FILE_CONTENT_STRING = "This is a report,"
            + System.lineSeparator()
            + "that's supposed to be written to a file,"
            + System.lineSeparator()
            + "which should contain exactly 3 lines.";
    private static final String FILE_CONTENT_EMPTY = "";
    private static final String ERR_MSG_DIFFERENT_CONTENT = "File contents are different.";

    private final FileWriterServiceImpl fileWriter = new FileWriterServiceImpl();

    @Test
    void write_OverwriteFile_Ok() {
        int expectedNumberOfFiles = 9;
        fileWriter.write(FILE_CONTENT_STRING, OVERWRITE_FILE_PATH);

        int actualNumberOfFiles = countResources();
        String actualFileContents = readFileContents(OVERWRITE_FILE_PATH);

        assertEquals(expectedNumberOfFiles, actualNumberOfFiles, "Should overwrite existing file.");
        assertEquals(FILE_CONTENT_STRING, actualFileContents, ERR_MSG_DIFFERENT_CONTENT);
    }

    @Test
    void write_writeToNewFile_Ok() {
        int expectedNumberOfFiles = 10;
        fileWriter.write(FILE_CONTENT_STRING, NEW_FILE_PATH);

        int actualNumberOfFiles = countResources();
        String actual = readFileContents(NEW_FILE_PATH);
        deleteCreatedContent();

        assertEquals(expectedNumberOfFiles, actualNumberOfFiles, "Should create a new file.");
        assertEquals(FILE_CONTENT_STRING, actual, ERR_MSG_DIFFERENT_CONTENT);
    }

    @Test
    void write_writeEmptyContent_Ok() {
        int expectedNumberOfFiles = 10;
        fileWriter.write(FILE_CONTENT_EMPTY, NEW_FILE_PATH);

        int actualNumberOfFiles = countResources();
        String actual = readFileContents(NEW_FILE_PATH);
        deleteCreatedContent();

        assertEquals(expectedNumberOfFiles, actualNumberOfFiles, "Should create a new file.");
        assertEquals(FILE_CONTENT_EMPTY, actual, ERR_MSG_DIFFERENT_CONTENT);
    }

    @Test
    void write_writeToNewFolder_NotOk() {
        int expectedNumberOfFiles = 9;

        assertThrows(RuntimeException.class,
                () -> fileWriter.write(FILE_CONTENT_STRING, NEW_FOLDER_PATH),
                "Non-existing path should throw RuntimeException.");
        int actualNumberOfFiles = countResources();

        assertEquals(expectedNumberOfFiles, actualNumberOfFiles,
                "Shouldn't create any folder/file.");
    }

    private String readFileContents(String path) {
        Path pathOfFile = Path.of(path);

        try (BufferedReader reader = Files.newBufferedReader(pathOfFile)) {
            return reader.lines()
                    .map(l -> l + System.lineSeparator())
                    .collect(Collectors.joining())
                    .trim();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file. " + pathOfFile, e);
        }
    }

    private int countResources() {
        try (Stream<Path> files = Files.list(Paths.get("src/test/resources"))) {
            return (int) files.count();
        } catch (IOException e) {
            return 0;
        }
    }

    private void deleteCreatedContent() {
        try {
            Files.deleteIfExists(Path.of(NEW_FILE_PATH));
        } catch (IOException e) {
            fail("Can't delete file");
        }
    }
}
