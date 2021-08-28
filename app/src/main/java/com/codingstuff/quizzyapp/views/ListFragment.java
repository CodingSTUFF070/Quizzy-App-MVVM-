package com.codingstuff.quizzyapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codingstuff.quizzyapp.Adapter.QuizListAdapter;
import com.codingstuff.quizzyapp.Model.QuizListModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.viewmodel.AuthViewModel;
import com.codingstuff.quizzyapp.viewmodel.QuizListViewModel;

import java.util.List;


public class ListFragment extends Fragment implements QuizListAdapter.OnItemClickedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private QuizListViewModel viewModel;
    private QuizListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.listQuizRecyclerview);
        progressBar = view.findViewById(R.id.quizListProgressbar);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizListAdapter(this);

        recyclerView.setAdapter(adapter);

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                progressBar.setVisibility(View.GONE);
                adapter.setQuizListModels(quizListModels);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
       ListFragmentDirections.ActionListFragmentToDetailFragment action =
               ListFragmentDirections.actionListFragmentToDetailFragment();
       action.setPosition(position);
       navController.navigate(action);
    }
}