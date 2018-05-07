package thrive.com.verificationcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    ImageView imagecode, QRCode_image;
    Button changebtn, QRCode_btn, readQR_btn;
    EditText QR_edit;
    TextView QR_readtext;
    Bitmap logoBmp = null;
    String shareUrl = "分享內容";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        logoBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

//===============================
//      image
// ===============================
        imagecode = (ImageView) findViewById(R.id.main_imageview);
        imagecode.setImageBitmap(CodeUtils.getInstance().createBitmap());//第一次建立

        changebtn = (Button) findViewById(R.id.main_btn);
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagecode.setImageBitmap(CodeUtils.getInstance().createBitmap());
            }
        });
//===============================
//        QRCode
//===============================
        QRCode_image = (ImageView) findViewById(R.id.main_imageview_qr);
//        第一次產生
        Bitmap QR_bitmap = null;
        try {
//            QR_bitmap = QRUtils.createCode(shareUrl, logoBmp, BarcodeFormat.QR_CODE, 600, 600, 48);
            QR_bitmap = QRUtils.qr_code(shareUrl, BarcodeFormat.QR_CODE, 600, 600);
        } catch (WriterException we) {
            we.printStackTrace();
        }

        QRCode_image.setImageBitmap(QR_bitmap);

        QR_edit = (EditText) findViewById(R.id.main_edit_qr);

        QRCode_btn = (Button) findViewById(R.id.main_btn_qr);
        QRCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap QR_bitmap = null;
                try {
                    shareUrl = QR_edit.getText().toString();
//                    QR_bitmap = QRUtils.createCode(shareUrl, logoBmp, BarcodeFormat.QR_CODE, 600, 600, 48);
                    QR_bitmap = QRUtils.qr_code(shareUrl, BarcodeFormat.QR_CODE, 600, 600);
                } catch (WriterException we) {
                    we.printStackTrace();
                }
                QRCode_image.setImageBitmap(QR_bitmap);
                QR_edit.setText("");
            }
        });
//===============================
//        Read_QRCode
//===============================
        QR_readtext = (TextView) findViewById(R.id.main_textview_qr);

        readQR_btn = (Button) findViewById(R.id.main_btn_readqr);
        readQR_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent read = new Intent(MainActivity.this, ReadQRActivity.class);
                MainActivity.this.startActivity(read);
            }
        });
    }

    public void onEventMainThread(MyEvent event) {
        QR_readtext.setText(event.getQR_string());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
