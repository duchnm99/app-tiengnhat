package com.example.vxutjlpt.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vxutjlpt.R;
import com.example.vxutjlpt.questions.QuestionsModel;
import com.example.vxutjlpt.test.TestAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<QuestionsModel> quesList;

    public AnswersAdapter(List<QuestionsModel> quesList) {
        this.quesList = quesList;
    }

    @NonNull
    @NotNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_item_layout, parent, false);

        return new AnswersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AnswersAdapter.ViewHolder holder, int position) {

        String ques = quesList.get(position).getQuestion();
        String a = quesList.get(position).getOptionA();
        String b = quesList.get(position).getOptionB();
        String c = quesList.get(position).getOptionC();
        String d = quesList.get(position).getOptionD();
        int selected = quesList.get(position).getSelectedAns();
        int ans = quesList.get(position).getCorrectAns();

        holder.setData(position, ques, a, b, c, d, selected, ans);
    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quesNo, question, optionA, optionB, optionC, optionD, result;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            quesNo = itemView.findViewById(R.id.quesNo);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.ans_optionA);
            optionB = itemView.findViewById(R.id.ans_optionB);
            optionC = itemView.findViewById(R.id.ans_optionC);
            optionD = itemView.findViewById(R.id.ans_optionD);
            result = itemView.findViewById(R.id.result);

        }

        private void setData(int pos, String ques, String a, String b, String c, String d, int selected, int correctAns)
        {
            quesNo.setText("Question No. "+ String.valueOf(pos+1));
            question.setText(ques);
            optionA.setText("A. " + a);
            optionB.setText("B. " + b);
            optionC.setText("C. " + c);
            optionD.setText("D. " + d);

            if (selected == -1)
            {
                result.setText("UN-ANSWERED");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));

                setOptionColor(selected, R.color.text_normal);
            }
            else {
                if (selected == correctAns)
                {
                    result.setText("CORRECT");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.Green));
                    setOptionColor(selected, R.color.Green);
                }else {
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.Red));
                    setOptionColor(selected, R.color.Red);
                }
            }

        }

        private void setOptionColor(int selected, int color)
        {
           if (selected == 1)
           {
               optionA.setTextColor(itemView.getContext().getResources().getColor(color));
           }else {
               optionA.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
           }

            if (selected == 2)
            {
                optionB.setTextColor(itemView.getContext().getResources().getColor(color));
            }else {
                optionB.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected == 3)
            {
                optionC.setTextColor(itemView.getContext().getResources().getColor(color));
            }else {
                optionC.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected == 4)
            {
                optionD.setTextColor(itemView.getContext().getResources().getColor(color));
            }else {
                optionD.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

        }

    }




}
