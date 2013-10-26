package com.example.xmlpullparserexample;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // parse xml in background thread
        new XmlParsingTask().execute();
        textView = (TextView) findViewById(R.id.people);
    }

    private class XmlParsingTask extends AsyncTask<Void, Void, List<Person>> {

        //constants for xml tags
        private static final String TAG_NAME = "name";
        private static final String TAG_EMAIL = "email";
        private static final String TAG_ID = "id";
        private static final String TAG_PERSON = "person";

        @Override
        protected List<Person> doInBackground(Void... params) {
            // retrieve xml
            final XmlResourceParser peopleXml = getResources().getXml(R.xml.people);
            List<Person> people = new ArrayList<Person>();
            String id = null, name = null, email = null;
            try {
                int eventType = peopleXml.getEventType();
                // loop the xml document
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = peopleXml.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (TAG_NAME.equals(tagName)) {
                                name = readText(peopleXml);
                                break;
                            }
                            if (TAG_EMAIL.equals(tagName)) {
                                email = readText(peopleXml);
                                break;
                            }
                            if (TAG_ID.equals(tagName)) {
                                id = readText(peopleXml);
                                break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            // if we encounter a 'person' end tag, create a new person object
                            if (TAG_PERSON.equals(peopleXml.getName())) {
                                people.add(new Person(email, id, name));
                                // clear person data
                                id = null;
                                name = null;
                                email = null;
                            }
                            break;
                    }
                    // don't forget to advance the loop
                    eventType = peopleXml.next();
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to get user playlist!", e);
            }

            return people;
        }

        @Override
        protected void onPostExecute(List<Person> persons) {
            textView.setText(Arrays.toString(persons.toArray()));
        }
    }

    private String readText(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
