package com.codingstuff.quizzyapp.repository;

import androidx.annotation.NonNull;

import com.codingstuff.quizzyapp.Model.QuizListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class QuizListRepository {

    private onFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Quiz");

    public QuizListRepository(onFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }
    public void getQuizData(){
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFirestoreTaskComplete.quizDataLoaded(task.getResult()
                            .toObjects(QuizListModel.class));
                }else{
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface onFirestoreTaskComplete{
        void quizDataLoaded(List<QuizListModel> quizListModels);
        void onError(Exception e);
    }
}
