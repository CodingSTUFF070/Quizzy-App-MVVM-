package com.codingstuff.quizzyapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {
    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail , editPass;
    private TextView signUpText;
    private Button signInBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        editEmail = view.findViewById(R.id.emailEditSignIN);
        editPass = view.findViewById(R.id.passEditSignIn);
        signUpText = view.findViewById(R.id.signUpText);
        signInBtn = view.findViewById(R.id.signInBtn);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if (!email.isEmpty() && !pass.isEmpty()){
                    viewModel.signIn(email , pass);
                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser !=null){
                                navController.navigate(R.id.action_signInFragment_to_listFragment);
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Please Enter Email and Pass", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

    }
}