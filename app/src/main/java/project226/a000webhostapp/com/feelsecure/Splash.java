package project226.a000webhostapp.com.feelsecure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int flag;
        MyDBHandler myDBHandler = new MyDBHandler(Splash.this,null,null,1);
        flag=myDBHandler.checkMember();
        String key="";
        String user_name="";
        if(flag==1){
            key=myDBHandler.getUserKey();
            user_name=myDBHandler.getUserName();
            Intent intent=new Intent(Splash.this,MainActivity.class);
            Bundle bundle= new Bundle();
            bundle.putString("username",user_name);
            bundle.putString("KEY",key);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(Splash.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
