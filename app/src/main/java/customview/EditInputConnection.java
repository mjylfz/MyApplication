package customview;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

/**
 * Created by LFZ on 2017/9/7.
 */

public class EditInputConnection extends InputConnectionWrapper {
    /**
     * Initializes a wrapper.
     * <p>
     * <p><b>Caveat:</b> Although the system can accept {@code (InputConnection) null} in some
     * places, you cannot emulate such a behavior by non-null {@link InputConnectionWrapper} that
     * has {@code null} in {@code target}.</p>
     *
     * @param target  the {@link InputConnection} to be proxied.
     * @param mutable set {@code true} to protect this object from being reconfigured to target
     *                another {@link InputConnection}.  Note that this is ignored while the target is {@code null}.
     */

    TextChangedByKeyBoardInput listener;
    public EditInputConnection(InputConnection target, boolean mutable) {
        super(target, mutable);
    }

    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        if(listener != null){
            listener.onInput();
        }
        return super.commitText(text, newCursorPosition);
    }

    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if(listener != null){
            listener.onInput();
        }
        return super.sendKeyEvent(event);
    }

    public void setOnTextInputListener(TextChangedByKeyBoardInput listener){
        this.listener = listener;
    }
}
