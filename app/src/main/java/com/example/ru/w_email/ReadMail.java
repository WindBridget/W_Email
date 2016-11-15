package com.example.ru.w_email;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import javax.mail.Message;

public class ReadMail extends AppCompatActivity {
    Context context = null;
    TextView from,subject,date,content;
    Button rep;
    Intent get_intent,m_intent;
    Bundle get_bundle,m_bundle;
    InboxConfigure ibc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mail);
        getInit();
        getData();
        replyMail();
    }
    public void getData(){
        get_intent = getIntent();
        get_bundle = get_intent.getBundleExtra("MyMail");
        ibc = (InboxConfigure) get_bundle.getSerializable("mail");
        from.setText(ibc.getFrom());
        subject.setText(ibc.getSubject());
        date.setText(ibc.getSentDate());
        content.setText(ibc.getContent());
    }
    public void replyMail(){
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_intent = new Intent(context,Reply.class);
                m_bundle = new Bundle();
                m_bundle.putSerializable("mail", ibc);
                m_intent.putExtra("MyMail",m_bundle);
                startActivity(m_intent);
            }
        });
    }

    public void getInit(){
        context = this;
        rep = (Button)findViewById(R.id.btn_rep);
        from = (TextView)findViewById(R.id.mail_from);
        subject = (TextView)findViewById(R.id.mail_subject);
        date = (TextView)findViewById(R.id.mail_date);
        content = (TextView)findViewById(R.id.mail_content);
        content.setMovementMethod(new ScrollingMovementMethod());
    }

}
