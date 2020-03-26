package com.example.lab25_03_03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore objectFirebaseFireestore;

    private static String Hotel_Detail="Hotel Detail";

    private EditText documentid,restaurentname,city,location;
    private TextView collection;

    private DocumentReference objectdocumentreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            objectFirebaseFireestore=FirebaseFirestore.getInstance();

            documentid=findViewById(R.id.DucumentIdET);
            restaurentname=findViewById(R.id.RestaurentIdET);

            city=findViewById(R.id.cityIdET);
            location=findViewById(R.id.LocationIdET);
            collection=findViewById(R.id.Tv);

        }
        catch (Exception e)
        {
            Toast.makeText(this, "FireBaseFireStore"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void addvalues(View v) {

        try {
            objectFirebaseFireestore = FirebaseFirestore.getInstance();
            objectFirebaseFireestore.collection(Hotel_Detail).document(documentid.getText().toString()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                            if (task.getResult().exists()) {
                                Toast.makeText(MainActivity.this, "Already Created", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                if(!documentid.getText().toString().isEmpty()&&!restaurentname.getText().toString().isEmpty()&&!city.getText().toString().isEmpty()
                                        && !location.getText().toString().isEmpty()) {
                                    Map<String, Object> objectMap = new HashMap<>();

                                    objectMap.put("restaurentname", restaurentname.getText().toString());
                                    objectMap.put("city", city.getText().toString());
                                    objectMap.put("location", location.getText().toString());


                                    objectFirebaseFireestore.collection(Hotel_Detail).document(documentid.getText().toString()).set(objectMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {


                                                    documentid.setText(" ");
                                                    city.setText(" ");
                                                    restaurentname.setText(" ");
                                                    location.setText(" ");

                                                    Toast.makeText(MainActivity.this, "Added Sucessfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Field is empty!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        }
        catch (Exception e)
        {

            Toast.makeText(this, "Add Values"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    public void retrive(View v)
    {
        if (!documentid.getText().toString().isEmpty())
        {
            objectdocumentreference=objectFirebaseFireestore.collection(Hotel_Detail).document(documentid.getText().toString());


            objectdocumentreference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            collection.setText(" ");

                            String NameofRestaurent=documentSnapshot.getString("restaurentname");
                            String cityname=documentSnapshot.getString("city");
                            String locationame=documentSnapshot.getString("location");

                            String DocumentId=documentSnapshot.getId();

                            String completeDocument = "Hotel Name:" + DocumentId + "\n" +
                                    "Name of Restaurant:" + NameofRestaurent + "\n"
                                    + "Location of Hotel:" +locationame +"\n"+"CityName:"+cityname;

                            collection.setText(completeDocument);





                        }
                    });
        }
        else
        {
            collection.setText("Not Found");
        }


    }


    public void Delete(View view)
    {
        try {
            if(!documentid.getText().toString().isEmpty())
            {
                objectdocumentreference=objectFirebaseFireestore.collection(Hotel_Detail).document(documentid.getText().toString());

                objectdocumentreference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Delete"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }





}
