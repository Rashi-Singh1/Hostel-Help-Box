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

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        if(sharedPreferenceConfig.readLoginStatus())
        {
            UserInfo.fillUserInfo(sharedPreferenceConfig.readusername(),sharedPreferenceConfig.readfullName(),sharedPreferenceConfig.reademail(),sharedPreferenceConfig.readpassw(),sharedPreferenceConfig.readhostel(),sharedPreferenceConfig.readusertype());
            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
        }

        Button Login = (Button) findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        Button Signup = (Button) findViewById(R.id.btnSignup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

    }
}