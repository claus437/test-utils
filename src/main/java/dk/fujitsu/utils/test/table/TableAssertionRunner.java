package dk.fujitsu.utils.test.table;


import org.apache.log4j.Logger;

import javax.swing.text.TabExpander;
import java.util.List;

public abstract class TableAssertionRunner<I, O> {
    private static final Logger LOGGER = Logger.getLogger(TableAssertionRunner.class);
    private DataBase dataBase;
    private String inputTable;
    private String expectTable;
    private Class<I> inputType;
    private Class<O> expectedType;
    private TableObjectVerifier verifier;

    public TableAssertionRunner() {
    }

    public TableAssertionRunner(DataBase dataBase, Class<I> inputType, String inputTable, Class<O> expectedType, String expectTable) {
        this.dataBase = dataBase;
        this.inputTable = inputTable;
        this.expectTable = expectTable;
        this.inputType = inputType;
        this.expectedType = expectedType;
    }


    protected abstract O execute(I object) throws Throwable;

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

    public Class<O> getExpectedType() {
        return expectedType;
    }

    public void setExpectedType(Class<O> expectedType) {
        this.expectedType = expectedType;
    }

    public void done(int row, O actual) {
        LOGGER.debug("verifying row #" + row + " in table " + expectTable + " for value " + actual);
        verifier.verify(actual, expectTable, row);
    }

    public void run() throws Throwable {
        List<I> inputList;
        O actual;

        verifier = new TableObjectVerifier(dataBase.getResource());

        inputList = dataBase.getTable(inputType, inputTable).readList();
        for (int i = 0; i < inputList.size(); i++) {

            actual = execute(inputList.get(i));
            done(i + 1, actual);
        }
    }
}
