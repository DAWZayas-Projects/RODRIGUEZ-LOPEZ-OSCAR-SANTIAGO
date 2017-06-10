package com.example.oscar.proyectdawfoods.RegisterLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.proyectdawfoods.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ScrollingCreateFood extends AppCompatActivity implements View.OnClickListener{

    private EditText description;
    private EditText nameFood;
    private Button  send;
    private Button uploadFood;
    private TextView user;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String idFood;
    private ProgressDialog mProgress;
    private StorageReference imRef = FirebaseStorage.getInstance().getReference();
    private static final int GALLERY = 2;
    private FirebaseUser userFirebase;
    private String userId;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foods-5078e.firebaseio.com/foods");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "scrolling",
                Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_create_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userFirebase = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        Bundle extras = getIntent().getExtras();
        int a  = extras.getInt("idLastFood");
        idFood = Integer.toString(a+1);

        user = (TextView) findViewById(R.id.scrolllUser);
        mProgress = new ProgressDialog(this);
        nameFood = (EditText) findViewById(R.id.nameFood);
        description = (EditText) findViewById(R.id.description);
        send = (Button) findViewById(R.id.sendFood);
        uploadFood = (Button) findViewById(R.id.foodImage);

        user.setText("Welcome to Foods Creation "+ userFirebase.getDisplayName());



        send.setOnClickListener(this);
        uploadFood.setOnClickListener(this);
    }

    public void onClick(View v){
        Log.d("ppppp", Integer.toString(R.id.send-31) );
        Log.d("ppppp", Integer.toString(v.getId()) );
        switch (v.getId()) {
            case R.id.foodImage:
                createImageFood();
                break;
            case R.id.send-31:
                //createFood();
                break;
        }
    }


    public void createFood(String uri){
        //Map <String,Object> p = new HashMap<String, Object>();
        Map<String, String> recipe = new HashMap<String, String>();
        recipe.put("description", description.getText().toString());
        recipe.put("nameFood", nameFood.getText().toString());
        recipe.put("user", userId);
        recipe.put("profilePhoto", uri);

        //6p.put(idFood, recipe);
        DatabaseReference usersRef = databaseReference.child("food").child(idFood);
        usersRef.setValue(recipe);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


        return super.onOptionsItemSelected(item);
    }

    public void createImageFood(){
        Log.d("aaaaaaaaaaa", idFood);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference childRef = imRef.child("Foods").child(userFirebase+"profileUser");
            mProgress.setMessage("Upload image ....");
            mProgress.show();

            childRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    addPhoto();
                    mProgress.dismiss();
                    Log.d("ppppppppp", "foto subida");
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure( Exception e){
                    //Toast.makeText(this, "upload error ", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void addPhoto(){
        imRef.child("Foods/"+userFirebase+"profileUser").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                createFood(uri.toString());
                /*Map <String, String> g = new HashMap<String, String>();
                g.put("profilePhoto", uri.toString());
                DatabaseReference f = databaseReference.child("food").child(idFood);

                f.setValue(g);*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ggggggggggg", exception.getMessage());
            }
        });

    }
}
