package global_constants;
import android.content.Context;
import android.widget.Toast;
/**
 * Created by Balwinder Singh on 23/12/15.
 */
public class UtilitySingleton
{

    private static final UtilitySingleton ourInstance = new UtilitySingleton();
    private static Context context;
    public static UtilitySingleton getInstance(Context context)
    {
        UtilitySingleton.context = context;
        return ourInstance;
    }
    /**
     * Description : Toast with Message String as input
     */
    public void ShowToast(String msg)
    {
        if (msg != null && !msg.trim().equalsIgnoreCase(""))
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Description : Toast with resourceID as input
     */
    public void ShowToast(int msgID)
    {
        ShowToast(context.getString(msgID));
    }
}
