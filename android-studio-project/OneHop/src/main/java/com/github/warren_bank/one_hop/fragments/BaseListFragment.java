package com.github.warren_bank.one_hop.fragments;

// support library
import android.databinding.ObservableList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class BaseListFragment extends ListFragment {

  protected List<String> data;
  private ArrayAdapter<String> adapter;
  private ListChangedCallback listChangedCallback;

  public BaseListFragment(List<String> data) {
    super();

    this.data = data;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    adapter = new ArrayAdapter<String>(
      getActivity(),
      android.R.layout.simple_list_item_1,
      data
    );
    adapter.setNotifyOnChange(true);
    setListAdapter(adapter);

    addClickListeners();

    String emptyText = getEmptyText();
    if ((emptyText != null) && !emptyText.isEmpty()) {
      setEmptyText(emptyText);
    }

    if (data instanceof ObservableList) {
      listChangedCallback = new ListChangedCallback(adapter);
    }
  }

  private void addClickListeners() {
    ListView listView = getListView();

    listView.setOnItemClickListener(
      new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          BaseListFragment.this.onItemClick(position, data.get(position));
        }
      }
    );

    listView.setOnItemLongClickListener(
      new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
          BaseListFragment.this.onItemLongClick(position, data.get(position));
          return true;
        }
      }
    );
  }

  protected void onItemClick(int position, String value) {
  }

  protected void onItemLongClick(int position, String value) {
  }

  protected String getEmptyText() {
    return null;
  }

  @Override
  public void onStart() {
    super.onStart();

    if (listChangedCallback != null) {
      ((ObservableList) data).addOnListChangedCallback(listChangedCallback);
    }
  }

  @Override
  public void onStop() {
    super.onStop();

    if (listChangedCallback != null) {
      ((ObservableList) data).removeOnListChangedCallback(listChangedCallback);
    }
  }

}
