package hackthon.codechef.chefonphone.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.recycleholders.ProblemListHolder;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListHolder> {

    private ArrayList<Problem> problemList;

    public ProblemListAdapter() {
        problemList = new ArrayList<>();
    }

    public void populateProblemList(ArrayList<Problem> problems) {
        this.problemList = problems;
    }

    @NonNull
    @Override
    public ProblemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.problem_list_recycler, parent, false);

        return new ProblemListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return problemList.size();
    }
}
