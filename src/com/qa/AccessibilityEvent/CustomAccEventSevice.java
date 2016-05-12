package com.qa.AccessibilityEvent;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomAccEventSevice extends AccessibilityService {
    private static final String EVENT_LOG = "event.log";
    private static File file = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+EVENT_LOG);

    @Override
    public void onServiceConnected() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            return; // event is not a notification

        String sourcePackageName = (String) event.getPackageName();

        Parcelable parcelable = event.getParcelableData();
        if (parcelable instanceof Notification) {
            System.out.println("Statusbar Notification");
        }
        else {
            // something else, e.g. a Toast message
            String log = "Message: " + event.getText().get(0)
                    + " [Source: " + sourcePackageName + "]\r\n";
            System.out.println(log);
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file,true);
                byte[] buf = log.getBytes();
                stream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onInterrupt() {

    }
}
