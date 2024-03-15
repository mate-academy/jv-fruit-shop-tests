package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TestHelper {
    private static final String EMPTY_LINE = "";
    private static final String CANNOT_READ_FILE_MESSAGE = "Cannot read the file: ";
    private static final String FILE_IS_NULL_ERROR_MESSAGE
            = "The file that you try to read is null or does not exist";

    static String getActualStringFromCsv(String pathToFile) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(pathToFile);
        if (!file.exists()) {
            throw new RuntimeException(FILE_IS_NULL_ERROR_MESSAGE);
        }
        try (InputStream inputStream = new FileInputStream(file);
                   InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            if (line != null) {
                stringBuilder.append(line);
            }
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE_MESSAGE
                    + pathToFile, e);
        }
        return stringBuilder.toString();
    }

    static void clearShopActivityFile(String pathToShopActivity) {
        try (FileWriter writerShopActivityCsvFile = new FileWriter(pathToShopActivity)) {
            writerShopActivityCsvFile.write(EMPTY_LINE);
            writerShopActivityCsvFile.flush();
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE_MESSAGE + pathToShopActivity, e);
        }
    }

    static void clearFinalReportFile(String pathToReport) {
        try (FileWriter writerToCsvFile = new FileWriter(pathToReport)) {
            writerToCsvFile.write(EMPTY_LINE);
            writerToCsvFile.flush();
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE_MESSAGE + pathToReport, e);
        }
    }
}
