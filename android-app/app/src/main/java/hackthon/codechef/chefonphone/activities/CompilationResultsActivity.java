package hackthon.codechef.chefonphone.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.IDECompileRunStatusLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.CompileRunOutput;

public class CompilationResultsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<CompileRunOutput> {

    private String status;
    private ProgressBar resultsProgressBar;
    private TextView cmpilatnStatusTV, languageDataTV, memoryTV, timeTV, signalTV, inputTV, outputTV;
    private TextView stdErrTV, compilationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation_results);

        status = getIntent().getStringExtra(StringKeys.COMPILE_RESULTS_ACTIVITY);

        cmpilatnStatusTV = findViewById(R.id.cmpilatnStatusTV);
        languageDataTV = findViewById(R.id.languageDataTV);
        memoryTV = findViewById(R.id.memoryTV);
        timeTV = findViewById(R.id.timeTV);
        signalTV = findViewById(R.id.signalTV);
        inputTV = findViewById(R.id.inputTV);
        outputTV = findViewById(R.id.outputTV);
        stdErrTV = findViewById(R.id.stdErrTV);
        compilationTV = findViewById(R.id.compilationTV);
        resultsProgressBar = findViewById(R.id.resultsLoaderProgress);

        getSupportLoaderManager().initLoader(IDs.COMPILATION_OUTPUT_LOADER, null, this).forceLoad();


    }

    private void populateView(CompileRunOutput compileRunOutput) {

        cmpilatnStatusTV.setText(compileRunOutput.getStatus());

        String tmp = compileRunOutput.getLangName() + "(" + compileRunOutput.getLangVersion() + ")";
        languageDataTV.setText(tmp);

        tmp = "Memory: " + compileRunOutput.getMemory();
        memoryTV.setText(tmp);

        tmp = "Time: " + compileRunOutput.getTime() + "s";
        timeTV.setText(tmp);

        tmp = "Signal: " + compileRunOutput.getSignal();
        signalTV.setText(tmp);

        inputTV.setText(compileRunOutput.getInput());
        outputTV.setText(compileRunOutput.getOutput());
        compilationTV.setText(compileRunOutput.getCmpinfo());
        stdErrTV.setText(compileRunOutput.getStderr());

        resultsProgressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Loader<CompileRunOutput> onCreateLoader(int id, @Nullable Bundle args) {
        return new IDECompileRunStatusLoader(this, status);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<CompileRunOutput> loader, CompileRunOutput data) {

        if (data != null) {
            populateView(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<CompileRunOutput> loader) {

    }
}
