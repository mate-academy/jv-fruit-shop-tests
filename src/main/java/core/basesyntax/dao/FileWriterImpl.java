package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileWriterImpl implements CsvFileWriter {
    private static final Logger logger = LogManager.getLogger(FileWriterImpl.class);
    private static final String DEFAULT_OUTPUT_FILE = "fileWriter.csv";

    @Override
    public void writeFile(String fileName, String content) {
        if (fileName == null || fileName.isBlank()) {
            logger.warn("File name is null or blank. Using default file name: "
                    + DEFAULT_OUTPUT_FILE);
            fileName = DEFAULT_OUTPUT_FILE;
        }

        File file = new File(fileName);
        logger.info("Using file: " + file.getAbsolutePath());

        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            logger.warn("Parent directory does not exist, trying to create it.");
            if (!file.getParentFile().mkdirs()) {
                logger.error("Failed to create parent directories.");
            }
        }

        try {
            if (!file.exists()) {
                logger.info("File does not exist, creating new file.");
                file.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(content);
                logger.info("Report successfully written to: " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            logger.error("Error writing to file: " + file.getAbsolutePath(), e);
        }
    }
}
