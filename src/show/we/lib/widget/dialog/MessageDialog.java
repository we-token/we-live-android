package show.we.lib.widget.dialog;

import android.content.Context;
import android.widget.TextView;
import show.we.lib.R;

/**
 * Created by CG on 14-3-11.
 * @author ll
 * @version 3.5.0
 */
public class MessageDialog extends BaseDialog {

	/**
	 * ShutUpDialog
	 * @param context context
	 * @param message message
	 */
	public MessageDialog(Context context, CharSequence message) {
		super(context, R.layout.message);
		((TextView)findViewById(R.id.message)).setText(message);
	}
}
