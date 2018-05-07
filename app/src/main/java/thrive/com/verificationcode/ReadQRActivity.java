package thrive.com.verificationcode;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import de.greenrobot.event.EventBus;

public class ReadQRActivity extends AppCompatActivity {

    Activity activity = ReadQRActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readqr);


        IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
        scanIntegrator.initiateScan();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        Log.i("read qr", "requestCode = " + requestCode);
        Log.i("read qr", "resultCode = " + resultCode);
        Log.i("read qr", "scanningResult = " + scanningResult);

        if (resultCode == -1) {
            //有資料
            String scanContent = scanningResult.getContents();
            Toast.makeText(ReadQRActivity.this, scanContent.toString(), Toast.LENGTH_SHORT).show();

//            //連續掃描
//            IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
//            scanIntegrator.initiateScan();

            MyEvent event = new MyEvent();
            event.setQR_string(scanContent.toString());
            EventBus.getDefault().post(event);
            finish();

        }
        if (resultCode == 0) {
            //返回
            finish();
        }
    }

    //旋轉不刷新頁面
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什麼都不用寫
        } else {
            // 什麼都不用寫
        }
    }
}
