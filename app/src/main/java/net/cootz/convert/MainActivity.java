package net.cootz.convert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private ArrayList<ArrayAdapter<Unit>> adapters = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = findViewById(R.id.fromSpnr);
        toSpinner = findViewById(R.id.toSpnr);

        for(int i = 0; i < 3; i++)
            adapters.add(new ArrayAdapter(this, android.R.layout.simple_spinner_item));

        adapters.get(0).add(new Unit("mm", 0.001));//Metric
        adapters.get(0).add(new Unit("sm", 0.01));
        adapters.get(0).add(new Unit("m", 1));
        adapters.get(0).add(new Unit("km", 1000));

        adapters.get(1).add(new Unit("sec", 1));//Time
        adapters.get(1).add(new Unit("min", 60));
        adapters.get(1).add(new Unit("hrs", 60*60));

        adapters.get(2).add(new Unit("mm^2", 0.001*0.001));//Square
        adapters.get(2).add(new Unit("sm^2", 0.01*0.01));
        adapters.get(2).add(new Unit("m^2", 1));
        adapters.get(2).add(new Unit("km^2", 1000*1000));

        onRadioButtonClicked(findViewById(R.id.metricRadio));
    }

    public void onRadioButtonClicked(View view)
    {
        if (((RadioButton)view).isChecked())
        {
            switch (view.getId())
            {
                case R.id.metricRadio:
                    updateSpinners(adapters.get(0));
                    break;
                case R.id.timeRadio:
                    updateSpinners(adapters.get(1));
                    break;
                case R.id.squareRadio:
                    updateSpinners(adapters.get(2));
                    break;
            }
        }
    }

    private void updateSpinners(SpinnerAdapter adapter){
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
    }

    public void onConvertClick(View view)
    {
        Unit from = (Unit)fromSpinner.getSelectedItem();
        Unit to = (Unit)toSpinner.getSelectedItem();
        EditText fromText = (EditText)findViewById(R.id.editTextFrom);
        double resultRatio = from.Ratio/to.Ratio;
        double result = Double.NaN;
        try {
            result = new Double(fromText.getText().toString()) * resultRatio;
        }
        catch (Exception e)
        {
            AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
            errorDialog.setMessage(e.getMessage());
            errorDialog.setTitle("Error");
            errorDialog.create().show();
        }

        ((TextView)findViewById(R.id.toTextView)).setText(Double.toString(result));
    }
}