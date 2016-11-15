package com.example.ru.w_email;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.sun.mail.pop3.POP3Store;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;


public class Inbox extends AppCompatActivity {
    Properties properties;
    Session emailSession = null;
    Context context = null;
    Folder emailFolder = null;
    Store store = null;
    Message[] messages = null;
    Message message = null;
    ListView lv_inbox = null;
    ArrayList<InboxConfigure> arr;
    InboxArrayAdapter adapter = null;
    String host,mailStoreType,username,password;
    InboxConfigure ibc;
    Intent m_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_inbox);
        getInit();
        getInbox(host,mailStoreType,username,password);
    }

    public void getInit(){
        context = this;
        host = "pop.gmail.com";
        mailStoreType = "pop3s";
        username = "namhieu1102@gmail.com";
        password = "babyruby";
        lv_inbox = (ListView) findViewById(R.id.lv_inbox);
        arr = new ArrayList<InboxConfigure>();
        adapter = new InboxArrayAdapter(this,R.layout.inbox_item_layout, arr);
        lv_inbox.setAdapter(adapter);
        lv_inbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                m_intent = new Intent(context , ReadMail.class);
                InboxConfigure ibc = arr.get(arg2);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mail", ibc);
                m_intent.putExtra("MyMail",bundle);
                startActivity(m_intent);
            }
        });
    }

    public void getInbox(String host, String storeType,String username,String password){
        try {
            //create properties field
            properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            emailSession = Session.getDefaultInstance(properties,null);
//            emailSession.setDebug(true);
            //create the POP3 store object and connect with the pop server
            store =  emailSession.getStore(storeType);
            store.connect(host, username, password);
            //create the folder object and open it
            emailFolder = store.getFolder("INBOX");
            if (emailFolder.isOpen())
            {
                    emailFolder.close(false);
                    emailFolder.open(Folder.READ_ONLY);
            }
            else
            {
                try
                {
                    emailFolder.open(Folder.READ_ONLY);
                }
                catch (Exception e)
                {
                    Log.i("Folder Opening,", e.toString());
                }
            }
            // retrieve the messages from the folder in an array and print it
            messages = emailFolder.getMessages();
            Address[] a;
            int i , n = messages.length;
            for (i = 0 ; i < n ; i++) {
                message = messages[i];
                ibc = new InboxConfigure();
                if((a = message.getFrom()) != null) {
                    for(int j = 0 ; j < a.length ; j++) {
                        ibc.setFrom(a[j].toString());
                    }
                }
                if(message.getSubject() != null) {
                    ibc.setSubject(message.getSubject());
                }
                if(message.getSentDate() != null) {
                    ibc.setSentDate(message.getSentDate().toString());
                }

                if(message.getContent() != null) {
                    Multipart mp = (Multipart) message.getContent();
                    BodyPart bp = mp.getBodyPart(0);
                    ibc.setContent(bp.getContent().toString());
                }
                if(InternetAddress.toString(message
                        .getReplyTo()) != null) {
                    ibc.setReplyto(InternetAddress.toString(message
                            .getReplyTo()));
                }
                if(InternetAddress.toString(message
                        .getRecipients(Message.RecipientType.TO)) != null) {
                    ibc.setRecipients(InternetAddress.toString(message
                            .getRecipients(Message.RecipientType.TO)));
                }

                arr.add(ibc);
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
