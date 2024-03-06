package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class FileWriterCsvImplTest {
    private static final Writer fileWriterCsv = new FileWriterCsvImpl();
    private static final String FILE_NAME = "src/test/resources/writer/reportCsv.csv";
    private static final String NULL_PARAMETERS_MESSAGE = """
                    Parameters can't be null, but:"
                    fileName = '%s'
                    report = '%s'""";
    private static final String WRONG_FILE_EXTENSION_MESSAGE = """
                The file extension is different from .csv: %s""";
    private static final String EMPTY_REPORT_MESSAGE = "Report is empty";
    private static final String EMPTY_FILE_MESSAGE = "File name is empty";
    private static final String REPORT_WITH_NULL_MESSAGE = "Report contains null value";
    private static List<String> correctReport;

    @BeforeAll
    static void beforeAll() {
        correctReport = List.of(
            "apple,100",
            "orange,405",
            "lemon,100",
            "peach,623",
            "pear,40",
            "pineapple,86"
        );
    }

    @ParameterizedTest
    @MethodSource
    void write_nullParameters_notOk(String fileName, List<String> report) {
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> fileWriterCsv.write(fileName, report));
        assertEquals(NULL_PARAMETERS_MESSAGE.formatted(fileName, report), exception1.getMessage());
    }

    static Stream<Arguments> write_nullParameters_notOk() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(FILE_NAME, null),
                Arguments.of(null, correctReport)

        );
    }

    @Test
    void write_reportIsEmpty_notOk() {
        List<String> emptyReport = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(FILE_NAME, emptyReport));
        assertEquals(EMPTY_REPORT_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "           "})
    void write_fileNameIsEmpty_notOk(String emptyFileName) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(emptyFileName, correctReport));
        assertEquals(EMPTY_FILE_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/writer/reportCsv.xml",
            "src/test/resources/writer/reportCsv.txt",
            "src/test/resources/writer/reportCsv.doc",
            "src/test/resources/writer/reportCsv.jpg",
            "src/test/resources/writer/reportCsv.java",
            "src/test/resources/writer/reportCsv.pdf"
    })
    void write_notCsvFileExtension_notOk(String path) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(path, correctReport));
        assertEquals(WRONG_FILE_EXTENSION_MESSAGE
                .formatted(path), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void write_nullValueInReport_notOk(List<String> listWithNull) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(FILE_NAME, listWithNull));
        assertEquals(REPORT_WITH_NULL_MESSAGE, exception.getMessage());
    }

    static Stream<Arguments> write_nullValueInReport_notOk() {
        return Stream.of(
                Arguments.of(Arrays.asList(null, null, null, null)),
                Arguments.of(Arrays.asList("orange,405", "lemon,100", "peach,623", null)),
                Arguments.of(Arrays.asList("orange,405", null, "lemon,100", "peach,623")),
                Arguments.of(Arrays.asList(null, "orange,405", "lemon,100", "peach,623"))
        );
    }

    @Test
    void write_correctParameters_ok() {
        List<String> linesFromFile;
        fileWriterCsv.write(FILE_NAME, correctReport);
        try {
            Path path = Path.of(FILE_NAME);
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        assertArrayEquals(correctReport.toArray(), linesFromFile.toArray());
    }
}
