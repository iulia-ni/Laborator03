package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants;

public class PhoneDialerActivity extends AppCompatActivity {
    private EditText etPhoneNumber;
    private ImageButton ibBackspace;
    private ImageButton ibCall;
    private ImageButton ibHangup;
    private Button bGeneric;

    private CallButtonClickListener callImageButtonClickListener= new CallButtonClickListener();
    private class CallButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + etPhoneNumber.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private GenericButtonListener genericButtonListener = new GenericButtonListener();
    private class GenericButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            etPhoneNumber.setText(etPhoneNumber.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private HangUpButtonListener hangUpButtonListener = new HangUpButtonListener();
    private class HangUpButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private LongClickBackspaceListener longClickBackspaceListener = new LongClickBackspaceListener();
    private class LongClickBackspaceListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            etPhoneNumber.setText("");
            return true;
        }
    }

    private BackspaceButtonListener backspaceButtonListener = new BackspaceButtonListener();
    private class BackspaceButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = etPhoneNumber.getText().toString();
            if (phoneNumber.length() > 0) {
                etPhoneNumber.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_phone_dialer);

        etPhoneNumber = (EditText)findViewById(R.id.phone_number);
        ibBackspace = (ImageButton)findViewById(R.id.backspace_image_button);
        ibBackspace.setOnClickListener(backspaceButtonListener);
        ibBackspace.setOnLongClickListener(longClickBackspaceListener);
        ibCall = (ImageButton)findViewById(R.id.call_image_button);
        ibCall.setOnClickListener(callImageButtonClickListener);
        ibHangup = (ImageButton)findViewById(R.id.hangup_image_button);
        ibHangup.setOnClickListener(hangUpButtonListener);

        for (int i = 0; i < Constants.buttonIds.length; i++) {
            bGeneric = (Button)findViewById(Constants.buttonIds[i]);
            bGeneric.setOnClickListener(genericButtonListener);
        }
    }
}
