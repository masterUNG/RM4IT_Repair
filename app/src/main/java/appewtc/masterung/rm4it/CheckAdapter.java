package appewtc.masterung.rm4it;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 4/17/16 AD.
 */
public class CheckAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] titleStrings;

    public CheckAdapter(Context context, String[] titleStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return titleStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.check_listview, viewGroup, false);

        TextView textView = (TextView) view1.findViewById(R.id.textView26);
        textView.setText(titleStrings[i]);

        return view1;
    }
}   // Main Class
