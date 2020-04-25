package com.example.astromedics.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.astromedics.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionHandler {
    private Activity activity;
    private List<Toast> toastQueue;

    public PermissionHandler(Activity activity){
        this.activity = activity;
    }

    public boolean checkIfPermissionGranted(String permission){
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkIfPermissionsGrantedInclusive(String permissions[]){
        return checkIfPermissionGranted(permissions, false);
    }

    public boolean checkIfPermissionsGrantedExclusive(String permissions[]){
        return checkIfPermissionGranted(permissions, true);
    }

    public boolean checkIfPermissionsGrantedInclusive(String permissions[], String mandatoryPermissions[]){
        boolean permissionsChecked =  checkIfPermissionGranted(permissions, false);
        boolean mandatoryPermissionsGranted = checkMandatoryPermissions(permissions, mandatoryPermissions);
        return permissionsChecked && mandatoryPermissionsGranted;
    }

    public boolean checkIfPermissionsGrantedExclusive(String permissions[], String mandatoryPermissions[]){
        boolean permissionsChecked =  checkIfPermissionGranted(permissions, true);
        boolean mandatoryPermissionsGranted = checkMandatoryPermissions(permissions, mandatoryPermissions);
        return permissionsChecked && mandatoryPermissionsGranted;
    }

    private boolean checkIfPermissionGranted(String permissions[], boolean exclusive){
        boolean returnable = exclusive;
        boolean grantedPermission = false;
        toastQueue = new ArrayList<>();

        for(int i = 0; i < permissions.length; i++){
            grantedPermission = checkIfPermissionGranted(permissions[i]);
            if(!grantedPermission){
                requestPermissionRationale(permissions[i]);
            }
            if(exclusive){
                returnable =  grantedPermission && returnable;
            } else {
                returnable =  grantedPermission || returnable;
            }
        }

        flushToasts();
        return returnable;
    }

    public boolean checkPermissionsInclusive(String permissions[], int[] grantResults){
        return checkPermissions(permissions, grantResults, false);
    }

    public boolean checkPermissionsExclusive(String permissions[], int[] grantResults){
        return checkPermissions(permissions, grantResults, true);
    }

    public boolean checkPermissionsInclusive(String permissions[], int[] grantResults, String mandatoryPermissions[]){
        boolean permissionsChecked = checkPermissions(permissions, grantResults, false);
        boolean mandatoryPermissionsGranted = checkMandatoryPermissions(permissions, mandatoryPermissions);
        return permissionsChecked && mandatoryPermissionsGranted;
    }

    public boolean checkPermissionsExclusive(String permissions[], int[] grantResults, String mandatoryPermissions[]){
        boolean permissionsChecked = checkPermissions(permissions, grantResults, true);
        boolean mandatoryPermissionsGranted = checkMandatoryPermissions(permissions, mandatoryPermissions);
        return permissionsChecked && mandatoryPermissionsGranted;
    }

    private boolean checkMandatoryPermissions(String permissions[], String mandatoryPermissions[]){
        boolean mandatoryPermissionsGranted = true;

        for(int i = 0; i < mandatoryPermissions.length; i++){
            if(!contains(permissions, mandatoryPermissions[i]) || !checkIfPermissionGranted(mandatoryPermissions[i])){
                mandatoryPermissionsGranted = false;
                break;
            }
        }

        return mandatoryPermissionsGranted;
    }

    private boolean checkPermissions(String permissions[], int[] grantResults, boolean exlusive){
        boolean returnable = exlusive;
        toastQueue = new ArrayList<>();

        for(int i = 0; i < permissions.length; i++){
            if(exlusive){
                returnable = checkPermission(permissions[i], grantResults[i]) && returnable;
            } else{
                returnable =  checkPermission(permissions[i], grantResults[i]) || returnable;
            }
        }

        flushToasts();
        return returnable;
    }

    public boolean checkPermission(String permission, int grantResult){
        if(grantResult == PackageManager.PERMISSION_GRANTED){
            return true;
        } else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                requestPermissionRationale(permission);
            }
            return false;
        }
    }

    public void requestPermissionRationale(String permission){
        String justification = activity.getString(R.string.justification_undefined_permission);

        switch (permission){
            case Manifest.permission.ACCESS_FINE_LOCATION:
                justification = activity.getString(R.string.justification_location_permission);
                break;
        }

        toastQueue.add(Toast.makeText(activity, justification, Toast.LENGTH_SHORT));
    }

    public void flushToasts(){
        if(toastQueue != null && toastQueue.size() > 0){
            toastQueue.get(0).show();
            flushToast(1);
        }
    }

    public void flushToast(final int index){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(index < toastQueue.size()){
                    toastQueue.get(index).show();
                    flushToast(index + 1);
                }
            }
        }, 2000);
    }


    private static boolean contains(final String[] array, final String value) {

        boolean result = false;

        for(String currentValue : array){
            if(currentValue.equals(value)){
                result = true;
                break;
            }
        }

        return result;
    }
}
