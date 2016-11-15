package com.example.ru.w_email;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Reply extends AppCompatActivity {
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    TextView from,reply;
    EditText subject, textMessage;
    String fr,rep, sub, msg;
    Button reply_btn;
    Properties props;
    Intent get_intent;
    Bundle get_bundle;
    InboxConfigure ibc;
    Message replyMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        getInit();
        getData();
        reply();
    }

    public void reply(){
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fr = from.getText().toString();
                rep = reply.getText().toString();
                sub = subject.getText().toString();
                msg = textMessage.getText().toString();

                props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("namhieu1102@gmail.com", "babyruby");
                    }
                });

                pdialog = ProgressDialog.show(context, "", "Replying Mail...", true);
                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
            }
            class RetreiveFeedTask extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {

                    try{
                        replyMessage = new MimeMessage(session);
                        replyMessage.setFrom(new InternetAddress(fr));
                        replyMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rep));
                        replyMessage.setSubject(sub);
                        replyMessage.setContent(msg, "text/html; charset=utf-8");
                        Transport.send(replyMessage);
                    } catch(MessagingException e) {
                        e.printStackTrace();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    pdialog.dismiss();
                    textMessage.setText("");
                    Toast.makeText(getApplicationContext(), "Email replied .", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getInit(){
        context = this;
        reply_btn = (Button) findViewById(R.id.btn_submit);
        from = (TextView) findViewById(R.id.rep_from);
        reply = (TextView) findViewById(R.id.rep_to);
        subject = (EditText) findViewById(R.id.rep_sub);
        textMessage = (EditText) findViewById(R.id.rep_text);

    }
    public void getData(){
        get_intent = getIntent();
        get_bundle = get_intent.getBundleExtra("MyMail");
        ibc = (InboxConfigure) get_bundle.getSerializable("mail");
        from.setText(ibc.getRecipients());
        reply.setText(ibc.getReplyto());
        subject.setText("[REPLY] " + ibc.getSubject());
    }

}
