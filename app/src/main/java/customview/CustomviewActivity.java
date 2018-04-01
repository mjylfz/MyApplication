package customview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LFZ on 2017/8/12.
 * 自定义view界面
 * 自定义图标，自定义的EditText
 */

public class CustomviewActivity extends AppCompatActivity implements View.OnClickListener, TextChangedByKeyBoardInput {

    //自定义的图标
    IconTitleView iconTitleView;
    Button button;

    //监听输入的EditText
    KeyBoradEditText editText;

    boolean input = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        iconTitleView = (IconTitleView)findViewById(R.id.iconTitleView);
        button = (Button)findViewById(R.id.setValue);
        editText = (KeyBoradEditText)findViewById(R.id.editText);
        editText.addTextChangedListener(textWatcher);
        editText.setOnTextInputListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setValue:
                setValue("2017-09","yyyyMM");
                editText.setText(android.os.Build.VERSION.RELEASE + "");
        }
    }

    private void setValue(String dateStr, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            String str = sdf.format(date);
            editText.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(input){
                Log.e("onTextChanged",s.toString());
                Toast.makeText(CustomviewActivity.this,"onTextChanged",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(input){
                Log.e("afterTextChanged",s.toString());
                Toast.makeText(CustomviewActivity.this,"afterTextChanged",Toast.LENGTH_SHORT).show();
            }
            input = false;
        }
    };

    @Override
    public void onInput() {
        input = true;
    }

}
