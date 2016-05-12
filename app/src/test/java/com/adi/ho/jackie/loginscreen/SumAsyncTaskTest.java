package com.adi.ho.jackie.loginscreen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by JHADI on 5/11/16.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class SumAsyncTaskTest {

    @Test
    public void asyncTaskRunning() throws Exception{
        SumArrayAsyncTask sumArrayAsyncTask = new SumArrayAsyncTask();

        Integer sum = 19;
        Integer wrongSum = 21;
        List<Integer> listOfNumbers = new ArrayList<>();
        listOfNumbers.add(3);
        listOfNumbers.add(6);
        listOfNumbers.add(10);

        sumArrayAsyncTask.execute(listOfNumbers);

        Robolectric.flushBackgroundThreadScheduler();

        try {
            assertEquals(sum,sumArrayAsyncTask.get());
            assertNotEquals(wrongSum, sumArrayAsyncTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
