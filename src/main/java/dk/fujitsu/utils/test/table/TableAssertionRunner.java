package dk.fujitsu.utils.test.table;


import org.apache.log4j.Logger;

import java.util.List;

public abstract class TableAssertionRunner<I, E> {
    private static final Logger LOGGER = Logger.getLogger(TableAssertionRunner.class);
    private DataBase dataBase;
    private String inputTable;
    private String expectTable;
    private Class<I> inputType;
    private Class<E> expectedType;

    public TableAssertionRunner(DataBase dataBase, Class<I> inputType, String inputTable, Class<E> expectedType, String expectTable) {
        this.dataBase = dataBase;
        this.inputTable = inputTable;
        this.expectTable = expectTable;
        this.inputType = inputType;
        this.expectedType = expectedType;
    }


    abstract E execute(I object) throws Throwable;

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String getInputTable() {
        return inputTable;
    }

    public void setInputTable(String inputTable) {
        this.inputTable = inputTable;
    }

    public String getExpectTable() {
        return expectTable;
    }

    public void setExpectTable(String expectTable) {
        this.expectTable = expectTable;
    }

    public Class<I> getInputType() {
        return inputType;
    }

    public void setInputType(Class<I> inputType) {
        this.inputType = inputType;
    }

    public void done(int row, E expected, E actual) {
        System.out.println("done: #" + row + " " + actual + " " + expected);
    }

    public void run() throws Throwable {
        List<I> inputList;
        E expected;
        E actual;

        inputList = dataBase.getTable(inputType, inputTable).readList();
        for (int i = 0; i < inputList.size(); i++) {
            expected = dataBase.getTable(expectedType, expectTable).readObject(i);

            try {
                actual = execute(inputList.get(i));
                done(i + 1, expected, actual);
            } catch (Throwable x) {
                if (x.getClass() == expectedType) {
                    done(i + 1, expected, (E) x);
                } else {
                    throw x;
                }
            }
        }
    }
}
