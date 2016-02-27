package com.brotherhood.gramatyka.activities;

import android.content.Intent;
import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.server.ServerRequest;
import com.brotherhood.gramatyka.server.enums.ServiceType;
import com.brotherhood.gramatyka.server.parameters.Parameters;
import com.brotherhood.gramatyka.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class SplashActivity extends BaseActivity {
    private TextView info;

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_splash);
        info = (TextView) findViewById(R.id.textView2);

        if (isOnline()) {
            checkVersion();
        }
        else {
            if (getDatabaseHelper().getAllCategories().size() == 0) {
                //rekonstrukcja z assets
                recreateDatabaseFromLocalFile();
                waitAndGoToMenu();
            } else {
                //powiadomienie o rekonstrukcji z lokalnej bazy
                waitAndGoToMenu();
            }
        }
    }


    private void checkVersion() {
        new ServerRequest(ServiceType.CHECK_VERSION, new Parameters())
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {
                    @Override
                    public void onSuccess(String json) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String version = jsonObject.getJSONObject("data").getString("version");
                            String localVersion = getSharedPrefsHelper().getString("version");

                            if (localVersion != null && localVersion.equals(version)) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Intent toMenuActivity = new Intent(SplashActivity.this, MenuActivity.class);
                                        startActivity(toMenuActivity);
                                    }
                                }).start();

                            } else
                                downloadDatabase();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int code, String description) {

                    }
                }).execute();
    }

    private void downloadDatabase() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText(getString(R.string.download_data));
            }
        });
        new ServerRequest(ServiceType.GET_DATABASE, new Parameters())
                .setServerRequestListener(new ServerRequest.ServerRequestListener() {
                    @Override
                    public void onSuccess(final String json) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = new JSONObject(json).getJSONObject("data").getJSONArray("json");
                                    JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
                                    String version = jsonObject.getString("version");
                                    getSharedPrefsHelper().putString("version", version);
                                    getDatabaseHelper().clearDatabaseAndRecreate();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject singleObject = jsonArray.getJSONObject(i);
                                        getDatabaseHelper().saveSingleTaskModel(singleObject);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent toMenuActivity = new Intent(SplashActivity.this, MenuActivity.class);
                                startActivity(toMenuActivity);
                            }
                        }).start();
                    }

                    @Override
                    public void onError(int code, String description) {

                    }
                }).execute();
    }


    private void recreateDatabaseFromLocalFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("database.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            try {
                JSONArray jsonArray = new JSONObject(json).getJSONObject("data").getJSONArray("json");
                JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
                String version = jsonObject.getString("version");
                getSharedPrefsHelper().putString("version", version);
                getDatabaseHelper().clearDatabaseAndRecreate();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject singleObject = jsonArray.getJSONObject(i);
                    getDatabaseHelper().saveSingleTaskModel(singleObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void waitAndGoToMenu() {
        info.setText(getString(R.string.recreateLocal));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }
            }
        }).start();
    }
}
