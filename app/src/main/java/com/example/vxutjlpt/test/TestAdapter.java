package com.example.vxutjlpt.test;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vxutjlpt.R;
import com.example.vxutjlpt.db.DbQuery;
import com.example.vxutjlpt.questions.QuestionsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestModel> testList;

    public TestAdapter(List<TestModel> testList) {
        this.testList = testList;
    }

    @NonNull
    @NotNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TestAdapter.ViewHolder holder, int position) {
        int progress = testList.get(position).getTopScore();
        holder.setData(position, progress);

    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testNo, topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            testNo = itemView.findViewById(R.id.testNo);
            topScore = itemView.findViewById(R.id.scoreText);
            progressBar = itemView.findViewById(R.id.testProgressBar);


        }

        private void setData(int pos, int progress ){

            testNo.setText("Đề thi số: "+ String.valueOf(pos + 1));
            topScore.setText(String.valueOf(progress) + " %");
            progressBar.setProgress(progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DbQuery.g_selected_test_index = pos;
                    Intent intent = new Intent(itemView.getContext(),StartTestActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }// End setData

    }// End public class ViewHolder


}
