package com.adi.ho.jackie.loginscreen;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by JHADI on 5/11/16.
 */
public class SumArrayAsyncTask extends AsyncTask<List<Integer>, Void, Integer> {

    int sum = 0;
    @Override
    protected Integer doInBackground(List<Integer>... params) {

        for (Integer number: params[0]){
            sum+=number;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
