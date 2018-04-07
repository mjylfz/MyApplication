package customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Created by LFZ on 2017/9/7.
 * 用来实现setText不让TextWatcher监听，手动输入才让TextWatcher监听
 */

public class KeyBoradEditText extends EditText {

    InputConnection inputConnection;
    EditInputConnection editInputConnection;
    TextChangedByKeyBoardInput listener;
    public KeyBoradEditText(Context context) {
        super(context);
    }

    public KeyBoradEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoradEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        inputConnection = super.onCreateInputConnection(outAttrs);
        editInputConnection = new EditInputConnection(inputConnection,true);
        if(listener != null){
            editInputConnection.setOnTextInputListener(listener);
        }
        return editInputConnection;
    }

    public void setOnTextInputListener(TextChangedByKeyBoardInput listener){
//        if(editInputConnection != null){
//            editInputConnection.setOnTextInputListener(listener);
//        }
        this.listener = listener;
    }
}
