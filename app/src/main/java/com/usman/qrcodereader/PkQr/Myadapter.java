package com.usman.qrcodereader.PkQr;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.usman.qrcodereader.MainActivity;
import com.usman.qrcodereader.R;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.myviewholder>
{
    ArrayList<Model> dataholder;

    public Myadapter(ArrayList<Model> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext()).inflate( R.layout.row1,viewGroup ,false );
        return  new myviewholder( view );

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {

        if(URLUtil.isValidUrl( dataholder.get( position ).getData() )){
            Log.e( "me","here23" );
            holder.data1.setClickable( true );
            holder.data1.setMovementMethod( LinkMovementMethod.getInstance() );
            String text1= "<a href='"+dataholder.get(position).getData()+"'>"+dataholder.get(position).getData()+"</a>";
            holder.data1.setText( Html.fromHtml( text1 ) );
//            holder.data1.setTextColor( R.color.purple_700 );
        }else{
            holder.data1.setText( dataholder.get( position ).getData() );
            holder.data1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( holder.copy.getContext() );
                    builder.setMessage( dataholder.get( position ).getData() )
                            .setTitle( "QRCode Result" );

                    builder.setPositiveButton( "Copy",(DialogInterface.OnClickListener) (dialog, which) ->{
                        ClipboardManager clipboardManager = (ClipboardManager) holder.copy.getContext().getSystemService( Context.CLIPBOARD_SERVICE );
                        ClipData clip = ClipData.newPlainText( "QrData",dataholder.get( position ).getData() );
                        clipboardManager.setPrimaryClip( clip );
                        Toast.makeText( holder.copy.getContext(), "Copied Successfully", Toast.LENGTH_SHORT ).show();
                    } );

                    builder.setNegativeButton( "Cancle",(DialogInterface.OnClickListener) (dialog, which) ->{
                        dialog.dismiss();
                    } );
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } );
        }
        holder.sno1.setText( String.valueOf( position+1 ));

        holder.copy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) holder.copy.getContext().getSystemService( Context.CLIPBOARD_SERVICE );
                ClipData clip = ClipData.newPlainText( "QrData",dataholder.get( position ).getData() );
                clipboardManager.setPrimaryClip( clip );
                Toast.makeText( holder.copy.getContext(), "Copied Successfully", Toast.LENGTH_SHORT ).show();
            }
        } );

        holder.gourl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent1.putExtra( SearchManager.QUERY, dataholder.get( position ).getData() );
                holder.copy.getContext().startActivity( intent1 );
            }
        } );



    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView sno1,data1;
        ImageButton copy,gourl;
        public myviewholder(@NonNull View itemView) {
            super( itemView );
            sno1 = itemView.findViewById( R.id.sno1 );
            data1 = itemView.findViewById( R.id.data1 );
            copy = itemView.findViewById( R.id.copy );
            gourl = itemView.findViewById( R.id.goulr );

        }
    }
}
