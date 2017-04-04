package ringotonepicker.sample.com.ringtonepicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.kevalpatel.ringtonepicker.RingtonePickerBuilder;
import com.kevalpatel.ringtonepicker.RingtonePickerListener;

public class MainActivity extends AppCompatActivity {

    private Uri mCurrentSelectedUri;

    private View.OnClickListener mCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof CheckedTextView) {
                CheckedTextView checkedTextView = (CheckedTextView) v;
                checkedTextView.setChecked(!checkedTextView.isChecked());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckedTextView musicCb = (CheckedTextView) findViewById(R.id.cb_music);
        musicCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView notificationCb = (CheckedTextView) findViewById(R.id.cb_notification);
        notificationCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView ringtoneCb = (CheckedTextView) findViewById(R.id.cb_ringtone);
        ringtoneCb.setOnClickListener(mCheckBoxClickListener);

        final CheckedTextView alarmCb = (CheckedTextView) findViewById(R.id.cb_alarm);
        alarmCb.setOnClickListener(mCheckBoxClickListener);

        final TextView ringtoneTv = (TextView) findViewById(R.id.tv_ringtone_info);

        findViewById(R.id.btn_pick_ringtone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Application needs read storage permission for RingtonePickerBuilder.TYPE_MUSIC .
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    RingtonePickerBuilder ringtonePickerBuilder = new RingtonePickerBuilder(getSupportFragmentManager());

                    //Set title of the dialog.
                    //If set null, no title will be displayed.
                    ringtonePickerBuilder.setTitle("Select ringtone");

                    //Add the desirable ringtone types.
                    if (musicCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerBuilder.TYPE_MUSIC);
                    if (notificationCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerBuilder.TYPE_NOTIFICATION);
                    if (ringtoneCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerBuilder.TYPE_RINGTONE);
                    if (alarmCb.isChecked())
                        ringtonePickerBuilder.addRingtoneType(RingtonePickerBuilder.TYPE_ALARM);

                    //set the text to display of the positive (ok) button.
                    //If not set OK will be the default text.
                    ringtonePickerBuilder.setPositiveButtonText("SET RINGTONE");

                    //set text to display as negative button.
                    //If set null, negative button will not be displayed.
                    ringtonePickerBuilder.setCancelButtonText("CANCEL");

                    //Set flag true if you want to play the sample of the clicked tone.
                    ringtonePickerBuilder.setPlaySampleWhileSelection(true);

                    //Set the callback listener.
                    ringtonePickerBuilder.setListener(new RingtonePickerListener() {
                        @Override
                        public void OnRingtoneSelected(String ringtoneName, Uri ringtoneUri) {
                            mCurrentSelectedUri = ringtoneUri;
                            ringtoneTv.setText("Name : " + ringtoneName + "\nUri : " + ringtoneUri);
                        }
                    });

                    //set the currently selected uri, to mark that ringtone as checked by default.
                    //If no ringtone is currently selected, pass null.
                    ringtonePickerBuilder.setCurrentRingtoneUri(mCurrentSelectedUri);

                    //Display the dialog.
                    ringtonePickerBuilder.show();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            123);
                }
            }
        });
    }
}