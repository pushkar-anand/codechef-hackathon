package hackthon.codechef.chefonphone.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.IDECompileRunStatusLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.CompileRunOutput;

public class CompilationResultsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<CompileRunOutput> {

    private String status;

    private TextView langName,langVersion, compileRunTime, compileRunDate, compileRunStatus, compileRunMemory ,compileRunSignal;
    private TextView compileRunInput, compileRunoutput, compileRunStderr, compileRunCmpinfo;
    private String langNameStr, langVersionStr, compileRunTimeStr, compileRunDateStr, compileRunStatusStr, compileRunMemoryStr ;
    private String compileRunSignalStr, compileRunInputStr, compileRunOutputStr, compileRunStderrStr, compileRunCmpinfoStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation_results);

        status = getIntent().getStringExtra(StringKeys.COMPILE_RESULTS_ACTIVITY);

        getSupportLoaderManager().initLoader(IDs.COMPILATION_OUTPUT_LOADER, null, this).forceLoad();

        langName = findViewById(R.id.compileRun_lang_name);
        langVersion = findViewById(R.id.compileRun_lang_version);
        compileRunTime = findViewById(R.id.compileRun_time);
        compileRunDate = findViewById(R.id.compileRun_date);
        compileRunStatus = findViewById(R.id.compileRun_status);
        compileRunMemory = findViewById(R.id.compileRun_memory);
        compileRunSignal = findViewById(R.id.compileRun_signal);
        compileRunInput = findViewById(R.id.compileRun_input);
        compileRunoutput = findViewById(R.id.compileRun_output);
        compileRunStderr = findViewById(R.id.compileRun_stderr);
        compileRunCmpinfo = findViewById(R.id.compileRun_cmpinfo);

    }

    private void populateView(CompileRunOutput compileRunOutput) {
        langNameStr = "Language Name : " + compileRunOutput.getLangName();
        langVersionStr = "Language Version : " + compileRunOutput.getLangVersion();
        compileRunTimeStr = "Time : " + compileRunOutput.getTime();
        compileRunDateStr = "Date : " + compileRunOutput.getDate();
        compileRunStatusStr = "Status : " + compileRunOutput.getStatus();
        compileRunMemoryStr = "Memory : " + compileRunOutput.getMemory();
        compileRunSignalStr = "Signal : " + compileRunOutput.getSignal();
        compileRunInputStr = "Input : " + compileRunOutput.getInput();
        compileRunOutputStr = "Output : " + compileRunOutput.getOutput();
        compileRunStderrStr = "stderr :  : " + compileRunOutput.getStderr();
        compileRunCmpinfoStr = "cmpinfo : " + compileRunOutput.getCmpinfo();

        langName.setText(langNameStr);
        langVersion.setText(langVersionStr);
        compileRunTime.setText(compileRunTimeStr);
        compileRunDate.setText(compileRunDateStr);
        compileRunStatus.setText(compileRunStatusStr);
        compileRunMemory.setText(compileRunMemoryStr);
        compileRunSignal.setText(compileRunSignalStr);
        compileRunInput.setText(compileRunInputStr);
        compileRunoutput.setText(compileRunOutputStr);
        compileRunStderr.setText(compileRunStderrStr);
        compileRunCmpinfo.setText(compileRunCmpinfoStr);

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
