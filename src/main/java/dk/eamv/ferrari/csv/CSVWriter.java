package dk.eamv.ferrari.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Made by: Benjamin
public class CSVWriter {

    private FileWriter file;
    private BufferedWriter writer;
    private int columnCount;

    /**
     * Public constructor that returns a CSVWriter instance.
     * @param filepath
     */
    public CSVWriter(String filepath) {
        try {
            file = new FileWriter(filepath);
            writer = new BufferedWriter(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Writes the header / column names to a CSV file.
     * @param columns
     */
    public void writeHeader(String... columns) {
        assert columns.length > 0 : "CSV header mush have more than 0 columns";
        assert columnCount == 0 : "CSV header has already been written to this file";
        columnCount = columns.length;

        try {
            for (var i = 0; i < columns.length; i += 1) {
                writer.write(columns[i].toString());
                if (i < columns.length - 1) {
                    writer.write(',');
                }
            }
            writer.write('\n');
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Writes a new row of fields to a CSV file.
     * @param fields
     */
    public void writeRow(Object... fields) {
        assert fields.length == columnCount : "Incorrect field count not matching column count";

        try {
            for (var i = 0; i < fields.length; i += 1) {
                writer.write(sanitize(fields[i].toString()));
                if (i < fields.length - 1) {
                    writer.write(',');
                }
            }
            writer.write('\n');
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Flushes the BufferedWriter data to the file.
     * This is mainly used for testing, but also useful
     * to incrementally output a large amount of data
     * in chunks.
     */
    public void flush() {
        try {
            writer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Closes the BufferedWriter and File.
     */
    public void close() {
        try {
            writer.close();
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Sanitizes the text to be CSV compatible, by adding quotes.
     * https://stackoverflow.com/a/17808731
     * @param text
     * @return sanitized text
     */
    private String sanitize(String text) {
        text = text.replaceAll("\"", "\"\"");
        return String.format("\"%s\"", text);
    }
}
