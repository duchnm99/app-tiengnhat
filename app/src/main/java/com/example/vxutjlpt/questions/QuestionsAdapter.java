package com.example.vxutjlpt.questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

import static com.example.vxutjlpt.db.DbQuery.ANSWERED;
import static com.example.vxutjlpt.db.DbQuery.REVIEW;
import static com.example.vxutjlpt.db.DbQuery.UNANSWERED;
import static com.example.vxutjlpt.db.DbQuery.g_quesList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionsModel> questionsList;

    public QuestionsAdapter(List<QuestionsModel> questionsList) {
        this.questionsList = questionsList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ques;
        private Button optionA, optionB, optionC, optionD, prevSelectB;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ques = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

            prevSelectB = null;

        }

        private void setData(final int pos){
            ques.setText(questionsList.get(pos).getQuestion());
            optionA.setText(questionsList.get(pos).getOptionA());
            optionB.setText(questionsList.get(pos).getOptionB());
            optionC.setText(questionsList.get(pos).getOptionC());
            optionD.setText(questionsList.get(pos).getOptionD());

            setOption(optionA,1 ,pos);
            setOption(optionB,2 ,pos);
            setOption(optionC,3 ,pos);
            setOption(optionD,4 ,pos);

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionA, 1, pos);
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionB, 2, pos);
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionC, 3, pos);
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, 4, pos);
                }
            });
        } // End setData

        private void selectOption(Button btn, int option_num, int quesID)
        {
            if (prevSelectB == null)
            {
                btn.setBackgroundResource(R.drawable.selected_bt);
                DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);

                changeStatus(quesID, ANSWERED);
                prevSelectB = btn;
            }else {
                if (prevSelectB.getId() == btn.getId())
                {
                    btn.setBackgroundResource(R.drawable.unselected_bt);
                    DbQuery.g_quesList.get(quesID).setSelectedAns(-1);

                    changeStatus(quesID, UNANSWERED);
                    prevSelectB = null;
                }
                else {
                    prevSelectB.setBackgroundResource(R.drawable.unselected_bt);
                    btn.setBackgroundResource(R.drawable.selected_bt);

                    DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);

                    changeStatus(quesID, ANSWERED);
                    prevSelectB = btn;
                }
            }
        }//End selectOption

        private void changeStatus(int id, int status)
        {
            if ( g_quesList.get(id).getStatus() != REVIEW)
            {
                g_quesList.get(id).setStatus(status);
            }
        }// end changestatus

        private void setOption(Button btn,  int option_num, int quesID)
        {
            if (DbQuery.g_quesList.get(quesID).getSelectedAns() == option_num){
                btn.setBackgroundResource(R.drawable.selected_bt);
            }else {
                btn.setBackgroundResource(R.drawable.unselected_bt);
            }
        }

    }// End ViewHolder

}
