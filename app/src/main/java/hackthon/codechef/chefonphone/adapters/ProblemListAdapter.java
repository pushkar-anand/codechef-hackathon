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
    private Integer VIEW_TYPE_LIST = 10, VIEW_TYPE_FOOTER = 20;
    private Boolean footer = false;
    private LoadMoreClickListener loadMoreClickListener;

    public ProblemListAdapter() {
        problemList = new ArrayList<>();
    }

    public ProblemListAdapter(Boolean footer) {
        this.footer = footer;
    }

    public void populateProblemList(ArrayList<Problem> problems) {
        this.problemList = problems;
    }

    public void setLoadMoreClickListener(LoadMoreClickListener l) {
        loadMoreClickListener = l;
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemListHolder holder, int position) {

        if (position == problemList.size()) {

            holder.nextPrblmsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadMoreClickListener.onLoadMoreClicked();
                }
            });

        } else {

            Problem problem = problemList.get(position);

            holder.problemNameView.setText(problem.getProblemName());
            holder.problemCodeView.setText(problem.getProblemCode());
            holder.problemSubmissionView.setText(problem.getSuccessfulSubmissions());

            holder.rootConstrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }

    @NonNull
    @Override
    public ProblemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == VIEW_TYPE_FOOTER && footer) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.problem_list_next_btn, parent, false);
            return new ProblemListHolder(view, VIEW_TYPE_FOOTER);

        } else if (viewType == VIEW_TYPE_LIST) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.problem_list_recycler, parent, false);
            return new ProblemListHolder(view);

        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.problem_list_recycler, parent, false);
        return new ProblemListHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == problemList.size()) ? VIEW_TYPE_FOOTER : VIEW_TYPE_LIST;
    }

    public interface LoadMoreClickListener {
        void onLoadMoreClicked();
    }

    @Override
    public int getItemCount() {
        if (footer) {
            return problemList.size() + 1;
        } else {
            return problemList.size();
        }
    }
}
