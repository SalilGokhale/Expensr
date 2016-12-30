package com.apps.salilgokhale.expensrapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.design.R.attr.layoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatchesFragment extends Fragment implements BatchView {

    @BindView(R.id.batch_recycler_view)
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private BatchesAdapter mAdapter;
    private static final int VERTICAL_ITEM_SPACE = 48;
    List<Batch> batchList = new ArrayList<>();

    private BatchPresenter batchPresenter;

    private Unbinder unbinder;

    public BatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        batchPresenter = new BatchPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_batches, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new BatchesAdapter(batchList);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));


        batchPresenter.requestBatches();

    }

    public BatchesAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        batchPresenter.onDestroy();
    }

}
