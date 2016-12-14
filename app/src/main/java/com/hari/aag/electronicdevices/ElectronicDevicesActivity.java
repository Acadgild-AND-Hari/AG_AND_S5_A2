package com.hari.aag.electronicdevices;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ElectronicDevicesActivity extends AppCompatActivity {

    private static final String LOG_TAG = ElectronicDevicesActivity.class.getSimpleName();
    private static final String PREFS_NAME = ElectronicDevicesActivity.class.getSimpleName();

    private static final int Menu_Item_Id_Camera = 103;
    private static final int Menu_Item_Id_TV = 104;
    private static final int Menu_Item_Id_X_Box = 105;

    private static final String SELECTED_DEVICE = "selectedDeviceResId";

    private int selectedDeviceResIdInt = 0;

    TextView deviceInfoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_devices);
        deviceInfoTV = (TextView) findViewById(R.id.id_sel_device);

        Log.d(LOG_TAG, "Inside - onCreate!");
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause!");
        saveValuesToPrefs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        menu.add(0, Menu_Item_Id_Camera, 3, R.string.str_camera);
        menu.add(0, Menu_Item_Id_TV, 3, R.string.str_tv);
        menu.add(0, Menu_Item_Id_X_Box, 3, R.string.str_x_box);
        inflater.inflate(R.menu.electronic_device_chooser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_phone:     selectedDeviceResIdInt = R.string.str_phone;    break;
            case R.id.id_computer:  selectedDeviceResIdInt = R.string.str_computer; break;
            case R.id.id_game_pad:  selectedDeviceResIdInt = R.string.str_game_pad; break;
            case Menu_Item_Id_Camera:       selectedDeviceResIdInt = R.string.str_camera;   break;
            case Menu_Item_Id_TV:           selectedDeviceResIdInt = R.string.str_tv;       break;
            case Menu_Item_Id_X_Box:        selectedDeviceResIdInt = R.string.str_x_box;    break;
            default:
                return super.onOptionsItemSelected(item);
        }
        updateValueToUI();
        return true;
    }

    private void updateValueToUI(){
        if (selectedDeviceResIdInt == 0) {
            deviceInfoTV.setText(R.string.str_no_device);
        } else {
            deviceInfoTV.setText(String.format(
                    getResources().getString(R.string.str_sel_device),
                    getString(selectedDeviceResIdInt))
            );
        }
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        selectedDeviceResIdInt = mySharedPrefs.getInt(SELECTED_DEVICE, 0);

        Log.d(LOG_TAG, "Values Read from Prefs.\n");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putInt(SELECTED_DEVICE, selectedDeviceResIdInt);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.\n");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, SELECTED_DEVICE + " - " + selectedDeviceResIdInt);
    }
}
