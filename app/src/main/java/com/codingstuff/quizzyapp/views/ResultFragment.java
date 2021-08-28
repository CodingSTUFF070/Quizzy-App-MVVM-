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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codingstuff.quizzyapp.Model.ResultModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.viewmodel.QuestionViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;


public class ResultFragment extends Fragment {

    private NavController navController;
    private QuestionViewModel viewModel;
    private TextView correctAnswer , wrongAnswer , notAnswered;
    private TextView percentTv;
    private ProgressBar scoreProgressbar;
    private String quizId;
    private Button homeBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        correctAnswer = view.findViewById(R.id.correctAnswerTv);
        wrongAnswer = view.findViewById(R.id.wrongAnswersTv);
        notAnswered = view.findViewById(R.id.notAnsweredTv);
        percentTv = view.findViewById(R.id.resultPercentageTv);
        scoreProgressbar = view.findViewById(R.id.resultCoutProgressBar);
        homeBtn = view.findViewById(R.id.home_btn);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resultFragment_to_listFragment);
            }
        });

        quizId = ResultFragmentArgs.fromBundle(getArguments()).getQuizId();

        viewModel.setQuizId(quizId);
        viewModel.getResults();
        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Long>>() {
            @Override
            public void onChanged(HashMap<String, Long> stringLongHashMap) {

                Long correct = stringLongHashMap.get("correct");
                Long wrong = stringLongHashMap.get("wrong");
                Long noAnswer = stringLongHashMap.get("notAnswered");

                correctAnswer.setText(correct.toString());
                wrongAnswer.setText(wrong.toString());
                notAnswered.setText(noAnswer.toString());

                Long total = correct + wrong + noAnswer;
                Long percent = (correct*100)/total;

                percentTv.setText(String.valueOf(percent));
                scoreProgressbar.setProgress(percent.intValue());

            }
        });

    }
}