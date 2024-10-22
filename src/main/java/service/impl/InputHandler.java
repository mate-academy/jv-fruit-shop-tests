package service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import service.InputFileHandler;
import service.exceptions.EmptyFileException;

public class InputHandler implements InputFileHandler {
    @Override
    public List<String> readFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }

            if (lines.isEmpty()) {
                throw new EmptyFileException("The file " + fileName + " is empty.");
            }

            return lines;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find " + fileName + " file", e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read " + fileName + " file", e);
        }
    }
}
