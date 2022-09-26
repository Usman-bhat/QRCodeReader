package com.usman.qrcodereader;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.usman.qrcodereader.PkQr.DBmanager;
import com.usman.qrcodereader.PkQr.Model;
import com.usman.qrcodereader.PkQr.Myadapter;
import com.usman.qrcodereader.PkQr.QrScannerActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



//    AdView adview;
    FloatingActionButton fab;
    RecyclerView recview;
    ArrayList<Model> dataholder;
    TextView nodata;
    AdView madView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        //getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        madView = findViewById( R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        nodata = (TextView) findViewById( R.id.nodata1 );
        fab= findViewById( R.id.fab );
        recview = findViewById( R.id.recview );
        dataholder= new ArrayList<Model>();


        recview.setLayoutManager( new LinearLayoutManager( this ) );
        Cursor cursor = new DBmanager( this ).readalldata();

        while (cursor.moveToNext()) {
                Model obj = new Model( cursor.getString( 1 ), cursor.getInt( 0 ), cursor.getInt( 2 ) );
                dataholder.add( obj );
            }
            Log.e( "me", String.valueOf( dataholder.size() ) );
        if(dataholder.size()<1){
            nodata.setVisibility( View.VISIBLE );
        }
        else {
            nodata.setVisibility( View.INVISIBLE );
        }

        Myadapter myadapter = new Myadapter( dataholder );
        recview.setAdapter( myadapter );







        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), QrScannerActivity.class) );
//                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
//                        .setAction( "Action", null ).show();
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about_us:
                Intent intent1 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent1.putExtra( SearchManager.QUERY, "https://usman-bhat.github.io/home" );
                startActivity( intent1 );
                return (true);
            case R.id.more_apps:
                Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent2.putExtra( SearchManager.QUERY, "https://usman-bhat.github.io/home/" );
                startActivity( intent2 );
                return (true);
            case R.id.delete_all:
                delete_data();

                return (true);
            case R.id.exit:
                finish();
                return (true);
        }
        return super.onOptionsItemSelected( item );
    }

    private void delete_data() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage( "All Qr Data Would Be deleted" )
                .setTitle( "Alert!" );

        builder.setPositiveButton( "Yes",(DialogInterface.OnClickListener) (dialog, which) ->{
            new DBmanager(this).deleteAllData();
            finish();
            startActivity( getIntent() );
        } );

        builder.setNegativeButton( "No",(DialogInterface.OnClickListener) (dialog, which) ->{
            dialog.dismiss();
        } );
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}