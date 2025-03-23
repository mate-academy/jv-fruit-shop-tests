package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements CsvFileWriter {
    private static final String DEFAULT_OUTPUT_FILE = "fileWriter.csv";

    @Override
    public void writeFile(String fileName, String content) {
        String outputFile = (fileName == null || fileName.isBlank())
                ? DEFAULT_OUTPUT_FILE
                : fileName;
        File file = new File(outputFile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + file.getAbsolutePath(), e);
        }
    }
}
