package com.example.ru.w_email;
import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

/**
 * Created by Ru on 11/14/2016.
 */

public class InboxArrayAdapter extends
        ArrayAdapter<InboxConfigure> {

    Activity context=null;
    ArrayList<InboxConfigure> myArray=null;
    int layoutId;
    public InboxArrayAdapter(Activity context, int layoutId , ArrayList<InboxConfigure> myArray) {
        super(context, layoutId ,myArray );
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = myArray;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater=
                context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        if(myArray.size()>0 && position>=0)
        {
            final TextView txtFrom=(TextView)
                    convertView.findViewById(R.id.ib_from);
            final TextView txtSubject=(TextView)
                    convertView.findViewById(R.id.ib_subject);

            final TextView txtDate=(TextView)
                    convertView.findViewById(R.id.ib_date);

            final InboxConfigure ibc = myArray.get(position);


            txtFrom.setText(ibc.getFrom());
            txtSubject.setText(ibc.getSubject());
            txtDate.setText(ibc.getSentDate());

        }
        return convertView;
    }
}
