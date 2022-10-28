package com.caller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.plug.base.plug_config;
import com.units.config;
import com.units.log;
import com.plug.xposed.base.xposed_plug_common;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    IXposedService iXposedService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iXposedService = IXposedService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    ArrayList<app_info> app_list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermissions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        app_list_data = get_installed_apps();
        ListView app_list_view = (ListView) findViewById(R.id.app_list_view);
        app_list_view.setAdapter(new app_adapter(this, app_list_data));

        app_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                app_info app = app_list_data.get(position);
                Toast.makeText(getApplicationContext(), "Bypass applied to " + app.getAppName(), Toast.LENGTH_LONG).show();
                plug_config config = plug_config.load_plug_config();
                if (config == null) {
                    config = new plug_config();
                    config.okhttp = new plug_config.okhttp_config();
                }
                config.analyse_packet_name = app.packName;
                config.okhttp.print_stack = true;
                plug_config.save_plug_config(config);
            }
        });

        Intent mIntent = new Intent(this, XposedService.class);
        startService(mIntent);

        Intent intent = new Intent("com.caller.XposedService");
        intent.setComponent(new ComponentName("com.caller", "com.caller.XposedService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.update_apkdb).setOnClickListener((v) -> {

        });

        findViewById(R.id.fzzx).setOnClickListener((v) -> {
            if (iXposedService != null) {
                try {
                    log.i("ctrl_xposed_plug xposed_plug_fzzx",
                            iXposedService.ctrl_xposed_plug(xposed_plug_common.plug_name.plug_name_fzzx, xposed_plug_common.plug_cmd.run_scan, config.db_dir_path.getBytes()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.qihu360).setOnClickListener((v) -> {
            if (iXposedService != null) {
                try {
                    log.i("ctrl_xposed_plug xposed_plug_360",
                            iXposedService.ctrl_xposed_plug(xposed_plug_common.plug_name.plug_name_360, xposed_plug_common.plug_cmd.run_scan, config.db_dir_path.getBytes()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.avl).setOnClickListener((v) -> {
            if (iXposedService != null) {
                try {
                    log.i("ctrl_xposed_plug xposed_plug_avl",
                            iXposedService.ctrl_xposed_plug(xposed_plug_common.plug_name.plug_name_avl, xposed_plug_common.plug_cmd.run_scan, config.db_dir_path.getBytes()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });



//        log.i("test_args " + test_jni.test_args3(1, 2, 3, 4));
//        analyse_test.init_jni_hook("libtest_jni.so");
        System.loadLibrary("native_hook");
        System.loadLibrary("test_jni");
        analyse_test.load_test();

        findViewById(R.id.analyse).setOnClickListener((v) -> {

//        System.loadLibrary("test_jni");
//            test_jni.test_args("", "", "", "", "", "", "", "");
//            test_jni.test_args2(1, 2, 3, 4, 5, 6, 7, 8);
//            test_jni.test_args3(1, 2, 3, 4);
            test_jni.test_args5(new test_jni());

//            log.i("test_args " + test_jni.test_args2(1, 2, 3, 4, 5, 6, 7, 8));
//            log.i("test_args " + test_jni.test_args3(1, 2, 3, 4));
        });
    }

    private ArrayList<app_info> get_installed_apps() {
        ArrayList<app_info> appsList = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++) {

            android.content.pm.PackageInfo p = packs.get(i);
            app_info pInfo = new app_info();
            pInfo.setAppName(p.applicationInfo.loadLabel(getPackageManager()).toString());
            pInfo.setPckName(p.packageName);
            pInfo.setIcon(p.applicationInfo.loadIcon(getPackageManager()));

            // Installed by user
            if ((p.applicationInfo.flags & 129) == 0) {
                appsList.add(pInfo);
            }
        }
        return appsList;
    }

    private void checkPermissions() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}