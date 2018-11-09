package men.ngopi.aviedb.android_java_md_boilerplate;

import android.os.AsyncTask;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView randomNumber;
    MaterialButton startBtn;
    MyTask myTask;
    String start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomNumber = findViewById(R.id.random_number);
        startBtn = findViewById(R.id.start_btn);

        start = "Start";
        stop = "Stop";

        startBtn.setText(start);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startBtn.getText().equals("Start")) {
                    startBtn.setText(stop);
                    if(myTask == null || myTask.isCancelled()){
                        myTask = new MyTask();
                        myTask.execute(true);
                    }
                } else if (startBtn.getText().equals("Stop")) {
                    startBtn.setText(start);
                    if(myTask != null){
                        myTask.cancel(false);
                    }
                }
            }
        });
    }

    private class MyTask extends AsyncTask<Boolean, String, String> {
        private int temp;

        @Override
        protected String doInBackground(Boolean... booleans) {

            try {
                while (booleans[0]) {
                    if(isCancelled()){
                        break;
                    }

                    do {
                        temp = (int) (Math.random() * 10);
                    } while (randomNumber.getText().equals(String.valueOf(temp)));

                    publishProgress(String.valueOf(temp));
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return String.valueOf(temp);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            randomNumber.setText(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            randomNumber.setText(values[0]);
        }
    }
}
