package com.apps.salilgokhale.expensrapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by salilgokhale on 29/12/2016.
 */

public class AddBatchDialogFragment extends DialogFragment {

    OnBatchCreatedListener mCallback;
    @BindView(R.id.add_batch_et) EditText addBatchET;
    private Unbinder unbinder;

    // private variables
    public boolean newBatch;
    public int buttonID;

    // Container Activity must implement this interface
    public interface OnBatchCreatedListener {
        public void createBatch(String batchName, boolean newBa, int buttonI);
    }

    public AddBatchDialogFragment(){}

    public static AddBatchDialogFragment newInstance(boolean newBatch, int i) {
        AddBatchDialogFragment f = new AddBatchDialogFragment();
        f.newBatch = newBatch;
        f.buttonID = i;
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_add_batch, new LinearLayout(getActivity()), false);
        unbinder = ButterKnife.bind(this, view);

        Button cancel = (Button) view.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        Button createnP = (Button) view.findViewById(R.id.create_action);
        createnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = addBatchET.getText().toString();
                if (name.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Batch must have a name", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (name.length() > 10){
                    Toast toast1 = Toast.makeText(getContext(), "Batch name must be less that 10 characters", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else {
                    Log.d("Create Batch Button", "pressed");
                    mCallback.createBatch(addBatchET.getText().toString(), newBatch, buttonID);
                    closeDialog();
                }

            }
        });

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnBatchCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDateSelectedListener");
        }
    }

    public void closeDialog(){
        this.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
