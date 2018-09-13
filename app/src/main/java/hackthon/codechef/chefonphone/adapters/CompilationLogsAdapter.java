package hackthon.codechef.chefonphone.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.data.CompilationLog;
import hackthon.codechef.chefonphone.recycleholders.CompilationLogHolder;

@SuppressLint("InflateParams")
public class CompilationLogsAdapter extends RecyclerView.Adapter<CompilationLogHolder> {

    private ArrayList<CompilationLog> compilationLogs;
    private StatusItemClick statusItemClick;

    @NonNull
    @Override
    public CompilationLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compilation_log_recycler_item, null);

        return new CompilationLogHolder(view);
    }

    public void updateLogsData(ArrayList<CompilationLog> compilationLogs) {
        this.compilationLogs = compilationLogs;
    }

    public void setStatusItemClick(StatusItemClick statusItemClick) {
        this.statusItemClick = statusItemClick;
    }

    @Override
    public void onBindViewHolder(@NonNull CompilationLogHolder holder, int position) {

        CompilationLog compilationLog = compilationLogs.get(position);

        holder.itemProblemTV.setText(compilationLog.getProblem());
        holder.itemLangTV.setText(compilationLog.getLang());
        holder.itemTimeTv.setText(compilationLog.getTimeStamp());

        holder.cardConstrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusItemClick.onStatusItemClick();
            }
        });

    }

    public interface StatusItemClick {
        void onStatusItemClick();
    }

    @Override
    public int getItemCount() {
        return compilationLogs.size();
    }
}
