package com.example.oscar.proyectdawfoods.RegisterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oscar.proyectdawfoods.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DinamicLogin extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private String contentEmail;
    private String contentPassword;
    private Button login;
    private LoginButton loginFacebook;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;

    public DinamicLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    if(user.getPhotoUrl() != null) {
                        String provider = "facebook";
                        SaveUsers save = new SaveUsers(user.getUid(), user.getEmail(), user.getDisplayName(), provider);
                        save.setPhoto(user.getPhotoUrl().toString());
                        save.saveUser();
                    }
                    else{
                        String provider = "email";
                        SaveUsers save = new SaveUsers(user.getUid(), user.getEmail(), user.getDisplayName(), provider);
                        save.saveUser();
                    }
                    Intent intent = new Intent(getActivity(), AppFoods.class);
                    startActivity(intent);
                }

            }
        };


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_dinamic_login, container, false);
        email = (EditText)v.findViewById(R.id.email);
        password = (EditText)v.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        login = (Button)v.findViewById(R.id.buttonLogin);
        loginFacebook = (LoginButton) v.findViewById(R.id.loginFacebook);
        login.setOnClickListener(this);
        loginFacebook.setOnClickListener(this);

        return v;
    }


    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.buttonLogin:
                if (checkInputs()) loginEmail();
                email.setText("");
                password.setText("");
                break;
            case R.id.loginFacebook:
                initFBFacebookLogIn();
                break;
        }
    }

    public boolean checkInputs(){
        contentEmail = email.getText().toString();
        contentPassword = password.getText().toString();
        return !contentEmail.equals("") || !contentPassword.equals("");
    }

    public void loginEmail(){
        mAuth.signInWithEmailAndPassword(contentEmail, contentPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getActivity(), "login Success",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "login failed",
                                    Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initFBFacebookLogIn() {
        mCallbackManager = CallbackManager.Factory.create();
        loginFacebook.setFragment(this);
        loginFacebook.setReadPermissions("email", "public_profile");
        loginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "facebook login cancel",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "login Facebook Success"+ task.isSuccessful(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "login Facebook Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
