package core.basesyntax.service;

import java.util.List;

/**
 * Interface responsible for writing data to a CSV file.
 * Contains a method void writeFile(String filename, List String data)
 * that writes a list of strings to a CSV file.**/
public interface WriteCsvFileService {
    void writeFile(String filename, List<String> data);
}