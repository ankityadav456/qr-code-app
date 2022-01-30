package com.example.qrcodesacnner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import java.io.Writer;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private TextView qrCodeTv;
    private ImageView qrCodeIv;
    private TextInputEditText dataEdt;
    private Button generateQRBtn;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        qrCodeTv = findViewById(R.id.idTVGenerateQR);
        qrCodeIv = findViewById(R.id.idIVQRCode);
        dataEdt = findViewById(R.id.idEdtData);
        generateQRBtn = findViewById(R.id.idBtnGenerateQR);
        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = dataEdt.getText().toString();
                if(data.isEmpty()){
                    Toast.makeText(GenerateQRCodeActivity.this, "Please enter some data to generate qr code..", Toast.LENGTH_SHORT).show();
                }else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width<height ? width:height;
                    dimen = dimen*3/4;
                    qrgEncoder = new QRGEncoder(dataEdt.getText().toString(),null, QRGContents.Type.TEXT,dimen);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrCodeTv.setVisibility(View.GONE);
                        qrCodeIv.setImageBitmap(bitmap);
                    }catch (WriterException e){
                        e.printStackTrace();

                    }


                }
            }
        });


    }
}