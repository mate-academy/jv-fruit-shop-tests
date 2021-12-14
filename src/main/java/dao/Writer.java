package dao;

import java.util.Map;

public interface Writer {
    boolean reportWriter(Map<String, Integer> reportMap, String filePath);
}
