package com.github.warren_bank.one_hop.fragments;

import com.github.warren_bank.one_hop.R;
import com.github.warren_bank.one_hop.BluetoothClassicClient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;

public class SendMessageFragment extends Fragment {
  private String   macAddress;
  private EditText messageText;

  public SendMessageFragment(String macAddress) {
    super();

    this.macAddress = macAddress;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.send_message_fragment, container, false);
    this.messageText  = (EditText) view.findViewById(R.id.input_message_text);
    Button sendButton = (Button) view.findViewById(R.id.button_send);

    sendButton.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String message = messageText.getText().toString();

          BluetoothClassicClient.send(macAddress, message);
          getActivity().onBackPressed();
        }
      }
    );

    return view;
  }

}
