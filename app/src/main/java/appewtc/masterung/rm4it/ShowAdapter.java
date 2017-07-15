package appewtc.masterung.rm4it;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by masterUNG on 8/23/2016 AD.
 */
public class ShowAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] iconStrings, titleStrings, detailStrings;

    public ShowAdapter(Context context, String[] iconStrings, String[] titleStrings, String[] detailStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.titleStrings = titleStrings; //content
        this.detailStrings = detailStrings;
    }

    @Override
    public int getCount() {
        return iconStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.show_listview, viewGroup, false);

        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView12);
        TextView titleTextView = (TextView) view1.findViewById(R.id.textView44);
        TextView detailTextView = (TextView) view1.findViewById(R.id.textView45);

        Picasso.with(context).load(iconStrings[i]).resize(120, 120).into(imageView);
      //  titleTextView.setText(titleStrings[i]);
        titleTextView.setText(detailStrings[i]);
        detailTextView.setText(titleStrings[i]); //show content from user entering


        return view1;
    }
}   // Main Class
