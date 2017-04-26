package com.example.oscar.proyectdawfoods.RegisterLogin;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class DinamicRegister extends Fragment implements  View.OnClickListener{

    private Button button;
    private EditText email;
    private EditText password;
    private EditText repPassword;
    private String contentEmail;
    private String contentPassword;
    private String contentPasswordRepeat;
    private FirebaseAuth mAuth;

    public DinamicRegister() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.fragment_dinamic_register, container, false);
        button = (Button) v.findViewById(R.id.buttonRegister);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        repPassword = (EditText) v.findViewById(R.id.passwordRepeat);


        button.setOnClickListener(this);

        return v;
    }

    public void onClick(View v){
        if(checkInputs()){
            register();
            email.setText("");
            password.setText("");
            repPassword.setText("");
        }
    }

    public boolean checkInputs(){
        contentEmail = email.getText().toString();
        contentPassword = password.getText().toString();
        contentPasswordRepeat = repPassword.getText().toString();

        if(contentEmail.equals("") || contentPassword.equals("") || contentPasswordRepeat.equals("")) {
            Toast.makeText(getActivity(), "email or password is empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!contentPasswordRepeat.equals(contentPassword)){
            Toast.makeText(getActivity(), "the password not match",  Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void register() {
        mAuth.createUserWithEmailAndPassword(contentEmail, contentPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("USER", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        Exception errorCode = task.getException();
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getActivity(), String.valueOf(errorCode), Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                int resultado = e.getMessage().indexOf("WEAK_PASSWORD");
                                if (resultado != -1) {
                                    Toast.makeText(getActivity(), "the password not have 6 chars", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else Toast.makeText(getActivity(), "Register Success", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
