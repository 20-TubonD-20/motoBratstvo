package com.example.motobratstvo.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.motobratstvo.R;
import com.example.motobratstvo.srcActivity.SrcActivity;
import com.example.motobratstvo.checker.StringChecker;
import com.example.motobratstvo.ui.AddPostActivity;
import com.example.motobratstvo.ui.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    private TextView emailText, passwordText, nameText, textRules;
    private ImageView avatar;
    private Button loginButton, logoutButton, registerButton, addPost;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** INFLATE **/

    @Override public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        SrcActivity scrActivity = (SrcActivity)getContext();
        assert scrActivity != null;
        if(scrActivity.isAuth) {
            view = inflater.inflate(R.layout.fragment_profile_auth, container, false);
            emailText = view.findViewById(R.id.textEmailAuth);
            emailText.setText(scrActivity.email);

            textRules = view.findViewById(R.id.textRules);
            textRules.setText(scrActivity.role);
            nameText = view.findViewById(R.id.textNameAuth);
            nameText.setText(scrActivity.email);

            avatar = view.findViewById(R.id.imageAvatar);
            logoutButton = view.findViewById(R.id.logOut);
            addPost = view.findViewById(R.id.buttonAddPost);
            avatar.setImageResource(R.drawable.icon);

        }
        else {
            view = inflater.inflate(R.layout.fragment_profile_not_auth, container, false);
            emailText = view.findViewById(R.id.editTextWriteTitle);
            passwordText = view.findViewById(R.id.editTextWriteText);
            loginButton = view.findViewById(R.id.buttonSubmit);
            registerButton = view.findViewById(R.id.buttonBackEdit);
        }
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        SrcActivity scrActivity = (SrcActivity) getContext();

        assert scrActivity != null;
        if(!scrActivity.isAuth) {

            registerButton.setOnClickListener(view -> {
                Intent intent = new Intent(scrActivity, RegistrationActivity.class);
                startActivity(intent);
            });

            loginButton.setOnClickListener(v -> {
                //Log.d("BUTTOM TEST", emailText.getText().toString());
                //Log.d("BUTTOM TEST", passwordText.getText().toString());

                StringChecker stringChecker = new StringChecker();

                String passwordBuff, emailBuff;

                emailBuff = emailText.getText().toString();
                passwordBuff = passwordText.getText().toString();

                if(stringChecker.checkPassword(passwordBuff) == 1) {
                    Toast.makeText((SrcActivity) getActivity(), "Error: must be > 5 syms",
                            Toast.LENGTH_SHORT).show();
                }

                else if(stringChecker.checkEmail(emailBuff) == 1) {
                    Toast.makeText((SrcActivity) getActivity(), "Error: must be one @",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    scrActivity.email = emailBuff;
                    scrActivity.password = passwordBuff;
                    scrActivity.role = "null";
                    scrActivity.saveConf();
                    scrActivity.signInSecond(emailBuff, passwordBuff);
                }

            });
        }
        else {
            logoutButton.setOnClickListener(v -> {

                scrActivity.email = "null";
                scrActivity.password = "null";
                scrActivity.saveConf();

                FirebaseAuth.getInstance().signOut();
                scrActivity.isAuth = false;

                scrActivity.restart();
            });
            addPost.setOnClickListener(v -> {
                SrcActivity scrActivityBuff = (SrcActivity) getContext();
                if(scrActivityBuff.role.equals("admin")) {
                    Intent intent = new Intent(scrActivity, AddPostActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText((SrcActivity) getActivity(), "access denied",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override public void onStop() {
        super.onStop();
    }

}
