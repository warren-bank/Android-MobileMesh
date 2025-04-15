package com.github.warren_bank.one_hop.fragments;

// support library
import android.databinding.ObservableList;

import android.widget.ArrayAdapter;

public class ListChangedCallback extends ObservableList.OnListChangedCallback {

  private ArrayAdapter adapter;

  public ListChangedCallback(ArrayAdapter adapter) {
    this.adapter = adapter;
  }

  @Override
  public void onChanged(ObservableList observableList) {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
    adapter.notifyDataSetChanged();
  }

}
