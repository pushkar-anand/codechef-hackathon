package hackthon.codechef.chefonphone.recycleholders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hackthon.codechef.chefonphone.R;

public class CompilationLogHolder extends RecyclerView.ViewHolder {

    public TextView itemProblemTV, itemLangTV, itemTimeTv;
    public ConstraintLayout cardConstrain;

    public CompilationLogHolder(View itemView) {
        super(itemView);

        itemProblemTV = itemView.findViewById(R.id.itemProblemTV);
        itemLangTV = itemView.findViewById(R.id.itemLangTV);
        itemTimeTv = itemView.findViewById(R.id.itemTimeTV);
        cardConstrain = itemView.findViewById(R.id.cardConstrain);
    }
}
