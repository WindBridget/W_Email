package com.example.ru.w_email;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SendMail extends AppCompatActivity {
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText from, reciep,subject ,textMessage ;
    String fr,rec,sub ,msg ;
    Button send;
    Properties props;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        getInit();
        sendMail();
    }
    public void sendMail(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr = from.getText().toString();
                rec = reciep.getText().toString();
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

                pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
            }
            class RetreiveFeedTask extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {

                    try{
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(fr));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                        message.setSubject(sub);
                        message.setContent(msg, "text/html; charset=utf-8");
                        Transport.send(message);
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
                    from.setText("");
                    reciep.setText("");
                    textMessage.setText("");
                    subject.setText("");
                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getInit(){
        context = this;
        send = (Button) findViewById(R.id.btn_submit);
        from = (EditText) findViewById(R.id.et_from);
        reciep = (EditText) findViewById(R.id.et_to);
        subject = (EditText) findViewById(R.id.et_sub);
        textMessage = (EditText) findViewById(R.id.et_text);
    }
}
