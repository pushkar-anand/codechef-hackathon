package hackthon.codechef.chefonphone.recycleholders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hackthon.codechef.chefonphone.R;

public class ProblemListHolder extends RecyclerView.ViewHolder {

    public TextView problemCodeView, problemNameView, problemSubmissionView;
    public ConstraintLayout rootConstrain;

    public ProblemListHolder(View itemView) {
        super(itemView);
        problemCodeView = itemView.findViewById(R.id.problemCode);
        problemNameView = itemView.findViewById(R.id.problemName);
        problemSubmissionView = itemView.findViewById(R.id.successfullSubmission);
        rootConstrain = itemView.findViewById(R.id.problemRecyclerItemConstrain);

    }

    public ProblemListHolder(View itemView, Integer id) {
        super(itemView);
    }
}
