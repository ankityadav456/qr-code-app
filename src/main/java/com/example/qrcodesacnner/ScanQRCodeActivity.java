package com.example.qrcodesacnner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQRCodeActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView scannerTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        scannerLiveView = findViewById(R.id.canView);
        scannerTV = findViewById(R.id.idTVScannedData);
        if(checkPermission()){
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show();
        }else {
            requestPermissions();

        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Started..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Stopped..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {

            }

            @Override
            public void onCodeScanned(String data) {
                scannerTV.setText(data);

            }
        });
    }

    private boolean checkPermission() {
        int camer_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return camer_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }
    private  void requestPermissions(){
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},PERMISSION_CODE);
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
    boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
            boolean vibrationAccepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;
            if(cameraAccepted && vibrationAccepted){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                Toast.makeText(this, "Permission Denied \n You cannot use the app without permission",Toast.LENGTH_SHORT).show();
            }

        }
    }
}