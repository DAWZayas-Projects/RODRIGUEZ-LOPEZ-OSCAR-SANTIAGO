package com.example.oscar.proyectdawfoods.RegisterLogin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment implements View.OnClickListener {

    private EditText email, user;
    private Button upload, send;
    private DatabaseReference mAut;
    private FirebaseUser userAuth;
    private StorageReference imRef;
    private static final int GALLERY = 2;
    private String userId ;
    private ProgressDialog mProgress;

    public User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseAuth auth = null;
        
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        email = (EditText) v.findViewById(R.id.email);
        user = (EditText) v.findViewById(R.id.user);
        upload = (Button) v.findViewById(R.id.image);
        send = (Button) v.findViewById(R.id.send);
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        imRef= FirebaseStorage.getInstance().getReference();
        userId = userAuth.getUid();
        mAut =  FirebaseDatabase.getInstance().getReference();
        mProgress = new ProgressDialog(getActivity());


        send.setOnClickListener(this);
        upload.setOnClickListener(this);

        return v;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                updateImage();
                break;
            case R.id.send:
                updateUser();
                break;
        }
    }

    public void updateUser(){
        mAut.child("users").child(userId).child("name").setValue(user.getText().toString());
        mAut.child("users").child(userId).child("email").setValue(email.getText().toString());
    }

    public void updateImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference childRef = imRef.child("Food").child(userId+"profileUser");
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
                    Toast.makeText(getActivity(), "upload error ", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void addPhoto(){
        imRef.child("Food/"+userId+"profileUser").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mAut.child("users").child(userId).child("photoUrl").setValue(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

    }

}
