package com.usman.qrcodereader.PkQr;




import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import com.google.zxing.Result;
        import com.karumi.dexter.Dexter;
        import com.karumi.dexter.PermissionToken;
        import com.karumi.dexter.listener.PermissionDeniedResponse;
        import com.karumi.dexter.listener.PermissionGrantedResponse;
        import com.karumi.dexter.listener.PermissionRequest;
        import com.karumi.dexter.listener.single.PermissionListener;
import com.usman.qrcodereader.MainActivity;
import com.usman.qrcodereader.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        scannerView = new ZXingScannerView( this );
        setContentView(scannerView);

        Dexter.withContext( getApplicationContext() )
                .withPermission( Manifest.permission.CAMERA )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                } ).check();
    }

    @Override
    public void handleResult(Result result) {

        processdata(result.getText());
    }

    private void processdata(String text) 
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( text )
                .setTitle( "QRCode Result" );

        builder.setPositiveButton( "Search",(DialogInterface.OnClickListener) (dialog, which) ->{
            Intent intent1 = new Intent(Intent.ACTION_WEB_SEARCH);
            intent1.putExtra( SearchManager.QUERY, text);
            startActivity( intent1 );
        } );

        builder.setNegativeButton( "Show",(DialogInterface.OnClickListener) (dialog, which) ->{
            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
        } );
        AlertDialog dialog = builder.create();
        dialog.show();

//        chk if its url or not
        String result = new DBmanager( this ).add_data( text,0 );
        Toast.makeText( this, "Data Saved", Toast.LENGTH_LONG ).show();


    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler( this );
        scannerView.startCamera();
    }
}