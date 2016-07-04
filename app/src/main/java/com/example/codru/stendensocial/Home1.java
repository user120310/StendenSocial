package com.example.codru.stendensocial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/*
 * Made by Marin Codrut, Tim Vaneker, Smith Chen and Flavian Gall
 */
public class Home1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        TextView text = (TextView) findViewById(R.id.home);
        text.setText("1/07/2016\n" +
                "Was a reschedule of the peresentation to Monday 4th of July 2016\n" +
                "\n" +
                "29/06/2016\n" +
                "Friday the group G-T Applications have the presentation of the project and product in Room 0.111 at 15:00/3:00PM Come and take a look!\n" +
                "\n" +
                "20/06/2016\n" +
                "We are working hard on the project you can expect some downtime on the application.\n" +
                "\n" +
                "13/06/2016\n" +
                "Our programmer is 'up and running'... We resplit the tasks and we start to work hard after the sort break!\n" +
                "\n" +
                "9/06/2016\n" +
                "Our programmer has a surgery today. We are taking a sport break coming back with more updates.\n" +
                "\n" +
                "31/05/2016\n" +
                "Another day of working hard for our product you will encounter some downtime.\n" +
                "\n" +
                "25/05/2016\n" +
                "After the accident with our programmer we try to stick back to the schedule and work hard.\n" +
                "\n" +
                "19/05/2016\n" +
                "Today our programmer had an accident we are trying to continue as usually.\n" +
                "\n" +
                "14/05/2016\n" +
                "Reasearching, learning and spliting the tasks between team members.\n" +
                "\n" +
                "11/05/2016\n" +
                "Hello world!\n" +
                "From:\n" +
                "Tim Vaneker -> Leader\n" +
                "Chen Smith -> Co-Leader\n" +
                "Codrut Marin -> Programmer\n" +
                "Flavian Gall -> Secretary");
    }
}
