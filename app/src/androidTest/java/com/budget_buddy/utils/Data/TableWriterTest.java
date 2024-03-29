package com.budget_buddy.utils.Data;

import android.support.test.runner.AndroidJUnit4;

import com.budget_buddy.config.DataConfig;
import com.budget_buddy.exception.InvalidDataLabelException;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class TableWriterTest {

    public static TableWriter tableWriter;

    @Test
    public void testWrite() throws InvalidDataLabelException {
        tableWriter = new TableWriter();
        DummyUser user = new DummyUser("testUName", 235);
        tableWriter.WriteData(DataConfig.DataLabels.TEST, user, DataConfig.DataLabels.TEST_USER);
    }

    @Test
    public void testWriteNested() throws InvalidDataLabelException {
        tableWriter = new TableWriter();
        List<String> labels = new ArrayList<String>() {{
            add(DataConfig.DataLabels.TEST);
            add(DataConfig.DataLabels.USERS);
        }};
        DummyUser user = new DummyUser("testUName", 235.21);
        tableWriter.WriteData(labels, user, DataConfig.DataLabels.TEST_USER);
    }

    @Test(expected = InvalidDataLabelException.class)
    public void testInvalidLabel() throws InvalidDataLabelException {
        tableWriter = new TableWriter();
        List<String> labels = new ArrayList<String>() {{
            add(DataConfig.DataLabels.TEST);
            add(DataConfig.DataLabels.USERS);
            add("InvalidLabel");
        }};
        DummyUser user = new DummyUser("testUName", 235.21);
        tableWriter.WriteData(labels, user, DataConfig.DataLabels.TEST_USER);
    }

    @AfterClass
    public static void ConcludeTesting() throws InterruptedException {
        // todo: we should change this to wait for Firebase to finish writing
        TimeUnit.SECONDS.sleep(3);
    }

}
