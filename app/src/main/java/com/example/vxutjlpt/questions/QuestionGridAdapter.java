package com.example.vxutjlpt.questions;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;

import static com.example.vxutjlpt.db.DbQuery.ANSWERED;
import static com.example.vxutjlpt.db.DbQuery.NOT_VISITED;
import static com.example.vxutjlpt.db.DbQuery.REVIEW;
import static com.example.vxutjlpt.db.DbQuery.UNANSWERED;

public class QuestionGridAdapter extends BaseAdapter {

    private int numOfQues;
    private Context context;

    public QuestionGridAdapter(Context context, int numOfQues) {
        this.numOfQues = numOfQues;
        this.context = context;
    }

    @Override
    public int getCount() {
        return numOfQues;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        View myView;
        if (view == null)
        {
            myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ques_grid_item, parent, false);
        }else{
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof QuestionsActivity)
                    ((QuestionsActivity)context).goToQuestion(position);
            }
        });

        TextView quesTV = myView.findViewById(R.id.ques_num);
        quesTV.setText(String.valueOf(position +1 ));

        switch (DbQuery.g_quesList.get(position).getStatus())
        {
            case NOT_VISITED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Gray)));
                break;
            case UNANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Red)));
                break;
            case ANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Green)));
                break;
            case REVIEW:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.purple_200)));
                break;
            default:
                break;
        }

        return myView;
    }
}
