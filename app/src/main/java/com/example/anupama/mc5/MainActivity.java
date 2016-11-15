package com.example.anupama.mc5;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    TextView display_txt;
    Button get_data;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display_txt=(TextView) findViewById(R.id.textView);
        get_data=(Button)findViewById(R.id.get_data);
        display_txt.setMovementMethod(new ScrollingMovementMethod());
        get_data.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            new pull_data().execute();
                                        }

                                    }
        );
    }

    public class pull_data extends AsyncTask<Void,Void,Void>
    {
        String content="";
        String total;
        Elements  c;
        Elements e;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document d = Jsoup.connect("https://www.iiitd.ac.in/about").get();
                 c=d.select("h1");
                 e=d.select("p");
                content="                                 "+c.get(0).text()+"\n\n";
                content+=e.get(6).text()+"\n\n" ;
                content+=e.get(7).text()+"\n\n\n\n";
                total=d.text();

            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("The total content",total);
            display_txt.setText(content);
            mProgressDialog.dismiss();
        }
    }

}

