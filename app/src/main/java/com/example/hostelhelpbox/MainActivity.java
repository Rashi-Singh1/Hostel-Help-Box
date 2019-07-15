//package com.example.hostelhelpbox;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(getApplicationContext(), Login.class);
//        startActivity(intent);
//    }
//}
package com.example.hostelhelpbox;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App app = (App) getApplicationContext();
        app.setMcontext(this.getApplicationContext());

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        if(sharedPreferenceConfig.readLoginStatus())
        {
            UserInfo.fillUserInfo(sharedPreferenceConfig.readusername(),sharedPreferenceConfig.readfullName(),sharedPreferenceConfig.reademail(),sharedPreferenceConfig.readpassw(),sharedPreferenceConfig.readhostel(),sharedPreferenceConfig.readusertype(),sharedPreferenceConfig.readsecyof());
            Intent intent;
            if(UserInfo.usertype.equals("normal")) intent = new Intent(getApplicationContext(), home2.class);
            else if(UserInfo.usertype.equals("admin")) intent = new Intent(getApplicationContext(), AdminHome2.class);
            else intent = new Intent(getApplicationContext(), SecyHome2.class);
            startActivity(intent);
            this.finish();
        }

        Button Login = (Button) findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        Button Signup = (Button) findViewById(R.id.btnSignup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }
}