package com.qa.AccessibilityEvent;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccEventMonitor extends Activity {
    public static final String TAG = "AccEventMonitor";
    private static final String EVENT_LOG = "event.log";
    File file = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+EVENT_LOG);
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(!file.exists()) {
            createFile();
        }
        else
        {
            if(file.delete()){
                System.out.println("delete file " + EVENT_LOG + " successfully");
            }
            else
            {
                System.out.println("delete file " + EVENT_LOG + " failed");
            }
            createFile();
        }
        file.deleteOnExit();
    }

    private void createFile(){
        try {
            if(file.createNewFile()){
                System.out.println("create file " + EVENT_LOG + " successfully");
            }
            else
            {
                System.out.println("create file " + EVENT_LOG + " failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    private boolean isAccessibleEnabled() {
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            if (info.getId().equals(getPackageName() + "/.CustomAccEventSevice")) {
                return true;
            }
        }
        return false;
    }
}
