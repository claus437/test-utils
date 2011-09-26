package dk.fujitsu.utils.test.table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableReader {
    private int[] columnPositions;
    private String[] columnNames;
    private CellReader cellReader;

    public TableReader(CellReader cellReader) {
        this.cellReader = cellReader;
    }

    public void read(String tableName, InputStream stream) {
        Pattern header;
        Matcher matcher;
        BufferedReader reader;
        String line;
        String row;
        int start;
        int end;
        boolean parsing;
        int lineNo;


        header = Pattern.compile("![ \t]*" + tableName + ".*?!");
        start = -1;
        end = -1;
        lineNo = 0;
        parsing = false;

        reader = new BufferedReader(new InputStreamReader(stream));

        try {
            while((line = reader.readLine()) != null) {
                if (parsing) {
                    if (!line.startsWith("|")) {
                        return;
                    }

                    lineNo ++;

                    row = line.substring(start, end);

                    if (columnNames == null) {
                        readColumnHeader(row);
                        continue;
                    }

                    readRow(cellReader, row);
                }

                matcher = header.matcher(line);
                if (matcher.find()) {
                    if (parsing) {
                        throw new RuntimeException("table error, found header in the middle of the table");
                    }

                    start = matcher.start();
                    end = matcher.end();

                    System.out.println("S: " + start + " " + end);
                    parsing = true;
                }
            }
        } catch (IOException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (Throwable x) {
            throw new RuntimeException(tableName + " error on line " + lineNo + ", " + x.getMessage(), x);
        }
    }

    private void readColumnHeader(String row) {
        Pattern pattern;
        Matcher matcher;
        int columns;
        int pos;

        columns = count(row, '|') - 1;

        columnNames = new String[columns];
        columnPositions = new int[columns];

        pattern =  Pattern.compile("\\|(.*?)\\|");
        matcher = pattern.matcher(row);

        pos = 0;
        for (int i = 0; matcher.find(pos); i++) {
            columnNames[i] = matcher.group(1);
            columnPositions[i] = matcher.start(1);
            pos = matcher.end(1);
        }
    }

    private void readRow(CellReader cellReader, String rowData) {
        String cell;
        int end;
        int width;
        int columnCount;

        columnCount = count(rowData, '|') - 1;

        cellReader.nextRow(columnNames.length, columnCount);

        // TODO: width calculated correctly for full row span only
        width = columnNames.length - columnCount + 1;

        for (int i = 0; i < columnPositions.length;) {
            end = rowData.indexOf("|", columnPositions[i]);

            if (end == -1) {
                throw new RuntimeException("unterminated row");
            }

            cell = rowData.substring(columnPositions[i], end);
            cellReader.read(i, width, columnNames[i].trim(), cell.trim());

            while (i < columnPositions.length && columnPositions[i] - 1 != end) {
                i++;
            }
        }
    }

    private int count(String text, char c) {
        int count;

        count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                count++;
            }
        }

        return count;
    }
}
