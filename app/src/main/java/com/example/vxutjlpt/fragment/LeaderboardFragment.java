package com.example.vxutjlpt.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vxutjlpt.MainActivity;
import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;
import com.example.vxutjlpt.db.MyCompleteListener;
import com.example.vxutjlpt.model.RankAdapter;
import com.example.vxutjlpt.test.TestActivity;
import com.google.android.gms.dynamic.IFragmentWrapper;

import static com.example.vxutjlpt.db.DbQuery.g_usersCount;
import static com.example.vxutjlpt.db.DbQuery.g_usersList;
import static com.example.vxutjlpt.db.DbQuery.myPerformace;

public class LeaderboardFragment extends Fragment {

    private TextView totalUsersTV, mImgTextTV, mScoreTV, mRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private Dialog progressDialog;
    private TextView dialogText;


    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_leaderboard, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        initView(view);

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);

        adapter = new RankAdapter(DbQuery.g_usersList);
        usersView.setAdapter(adapter);

        DbQuery.getTopUsers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();

                if (myPerformace.getScore() != 0)
                {
                    if (! DbQuery.isMeOnTopList)
                    {
                        calculateRank();
                    }
                    mScoreTV.setText("Score: " + myPerformace.getScore());
                    mRankTV.setText("Rank - " + myPerformace.getRank());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong! Please Try Again.", Toast.LENGTH_SHORT).show();
            }
        });

        totalUsersTV.setText("Total Users: " + DbQuery.g_usersCount);
        mImgTextTV.setText(myPerformace.getName().toUpperCase().substring(0,1));


        return view;
    }


    private void initView(View view)
    {
        totalUsersTV = view.findViewById(R.id.total_users);
        mImgTextTV = view.findViewById(R.id.img_text);
        mScoreTV = view.findViewById(R.id.total_score_rank);
        mRankTV = view.findViewById(R.id.rank_num);
        usersView = view.findViewById(R.id.user_view);
    }

    private void calculateRank()
    {
        int lowTopScore = g_usersList.get(g_usersList.size() - 1).getScore();

        int remaining_slot = g_usersCount - 20;

        int mSlot = (myPerformace.getScore()*remaining_slot)/lowTopScore;

        int rank;
        if (lowTopScore != myPerformace.getScore()) {

            rank = g_usersCount - mSlot;

        }
        else
        {
            rank  = 21;
        }
        myPerformace.setRank(rank);

    }


}