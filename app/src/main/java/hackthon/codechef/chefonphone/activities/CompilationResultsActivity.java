package hackthon.codechef.chefonphone.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.IDECompileRunStatusLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.CompileRunOutput;

public class CompilationResultsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<CompileRunOutput> {

    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation_results);

        status = getIntent().getStringExtra(StringKeys.COMPILE_RESULTS_ACTIVITY);

        getSupportLoaderManager().initLoader(IDs.COMPILATION_OUTPUT_LOADER, null, this).forceLoad();
    }

    private void populateView(CompileRunOutput compileRunOutput) {

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
