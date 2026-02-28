package core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static Object[][] getTestDataFromCsv(String resourcePath) {

        List<Object[]> data = new ArrayList<>();

        try (InputStream is = TestUtils.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("CSV file not found in resources: " + resourcePath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String line;
                boolean isHeader = true;

                while ((line = br.readLine()) != null) {

                    // Skip header
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    // Skip empty line
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Split CSV (handle empty column)
                    String[] values = line.split(";", -1);

                    // Trim every column
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }

                    data.add(values);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read CSV file: " + resourcePath, e
            );
        }

        return data.toArray(new Object[0][]);
    }
}


