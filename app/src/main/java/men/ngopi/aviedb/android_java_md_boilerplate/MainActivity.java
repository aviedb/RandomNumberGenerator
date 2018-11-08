package men.ngopi.aviedb.android_java_md_boilerplate;

import android.os.AsyncTask;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView randomNumber;
    MaterialButton startBtn, stopBtn;
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomNumber = findViewById(R.id.random_number);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myTask == null){
                    myTask = new MyTask();
                    myTask.execute(true);
                } else if(myTask.isCancelled()){
                    myTask = new MyTask();
                    myTask.execute(true);
                }
//                Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myTask != null){
//                    AnimFadeIn();
                    myTask.cancel(false);
//                    Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnimFadeIn() {
        Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        a.reset();
        randomNumber.clearAnimation();
        randomNumber.startAnimation(a);
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
                    temp = (int) (Math.random() * 10);
                    publishProgress(String.valueOf(temp));
                    Log.i("Ini", String.valueOf(temp));
                    AnimFadeIn();
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

        private void AnimFadeOut()
        {
            Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
            a.reset();
            randomNumber.clearAnimation();
            randomNumber.startAnimation(a);
        }
    }
}
