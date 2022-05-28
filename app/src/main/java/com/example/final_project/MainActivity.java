package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ImageView imggas;
    ImageView imglight;
    TextView textnhietdo,textgas;
    TextView textdoam ;
    Button btnon;
    Button btnoff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initui();
        readData();
        btnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLight();
            }
        });
        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffLight();
            }
        });
        

    }

    private void initui(){
        textgas=findViewById(R.id.gas);
        imggas=findViewById(R.id.khigas);
        imglight=findViewById(R.id.light);
        textnhietdo=findViewById(R.id.nhietdo);
        textdoam=findViewById(R.id.doam);
        btnon=findViewById(R.id.onLight);
        btnoff=findViewById(R.id.offLight);
    }

    private void OnLight() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference light=firebaseDatabase.getReference("led");
        light.setValue("1");
        imglight.setImageResource(R.drawable.light_on);


    }
    private void OffLight() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference light=firebaseDatabase.getReference("led");
        light.setValue("0");
        imglight.setImageResource(R.drawable.light_off);


    }








    private void readData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference light=firebaseDatabase.getReference("led");
        DatabaseReference gas=firebaseDatabase.getReference("gas");
        DatabaseReference gascaution=firebaseDatabase.getReference("gascaution");
        DatabaseReference doam=firebaseDatabase.getReference("doam");
        DatabaseReference nhietdo=firebaseDatabase.getReference("nhietdo");

        light.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    imglight.setImageResource(R.drawable.light_on);
                }else imglight.setImageResource(R.drawable.light_off);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gascaution.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    imggas.setImageResource(R.drawable.cogas);
                    AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Cảnh báo phát hiện khí gas");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ĐÃ NHẬN",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();


                }else imggas.setImageResource(R.drawable.khonggas);

            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value = snapshot.getValue(String.class);
                textgas.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nhietdo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value = snapshot.getValue(String.class);
                textnhietdo.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
                doam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value = snapshot.getValue(String.class);
                textdoam.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


//
//    private void turnOnLed() {
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference myReference=firebaseDatabase.getReference("led");
//        myReference.setValue("1");
//    }
}
