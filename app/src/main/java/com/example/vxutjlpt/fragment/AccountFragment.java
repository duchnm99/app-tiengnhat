package com.example.vxutjlpt.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vxutjlpt.LoginActivity;
import com.example.vxutjlpt.MainActivity;
import com.example.vxutjlpt.MyProfileActivity;
import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;
import com.example.vxutjlpt.db.MyCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import static com.example.vxutjlpt.db.DbQuery.g_usersCount;
import static com.example.vxutjlpt.db.DbQuery.g_usersList;
import static com.example.vxutjlpt.db.DbQuery.myPerformace;

public class AccountFragment extends Fragment {

    private LinearLayout logoutB;
    private TextView profile_img_text, name, score, rank;
    private LinearLayout leaderB, profileB, bookmarkB;
    private BottomNavigationView bottomNavigationView;
    private Dialog progressDialog;
    private TextView dialogText;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initViews(view);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("My Account");

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");


        String userName = DbQuery.mProfile.getName();
        profile_img_text.setText(userName.toUpperCase().substring(0, 1));
        name.setText(userName);

        score.setText(String.valueOf(DbQuery.myPerformace.getScore()));

        if (DbQuery.g_usersList.size() == 0) {
            progressDialog.show();
            DbQuery.getTopUsers(new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    if (myPerformace.getScore() != 0) {
                        if (!DbQuery.isMeOnTopList) {
                            calculateRank();
                        }
                        score.setText(String.valueOf(DbQuery.myPerformace.getScore()));
                        rank.setText(String.valueOf(myPerformace.getRank()));
                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Something went wrong! Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            score.setText(String.valueOf(myPerformace.getScore()));
            if (myPerformace.getScore() != 0)
                rank.setText(String.valueOf(myPerformace.getRank()));
        }

        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleClient = GoogleSignIn.getClient(getContext(), gso);

                mGoogleClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        getActivity().finish();
                    }
                });
            }
        });

        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

//        bookmarkB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        leaderB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard);
//            }
//        });

        return view;
    }// End OnCreateView

    private void initViews(View view) {

        logoutB = view.findViewById(R.id.logoutB);
        profile_img_text = view.findViewById(R.id.profile_img_text);
        name = view.findViewById(R.id.name);
        score = view.findViewById(R.id.total_score);
        rank = view.findViewById(R.id.rank);
//        leaderB = view.findViewById(R.id.leaderB);
//        bookmarkB = view.findViewById(R.id.bookmarkB);
        profileB = view.findViewById(R.id.profileB);

        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

    }

    private void calculateRank() {
        int lowTopScore = g_usersList.get(g_usersList.size() - 1).getScore();

        int remaining_slot = g_usersCount - 20;

        int mSlot = (myPerformace.getScore() * remaining_slot) / lowTopScore;

        int rank;
        if (lowTopScore != myPerformace.getScore()) {

            rank = g_usersCount - mSlot;

        } else {
            rank = 21;
        }
        myPerformace.setRank(rank);

    }

}