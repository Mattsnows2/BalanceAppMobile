package my.matt.myApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreen extends AppCompatActivity {

    Animation   cardAnimation, walletAnimation, premierText, secondText, logoAnimation;

    View imageAnimWallet, imageAnimCard;
    TextView premierTextSplashScreen, secondTextSplashScreen;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        cardAnimation = AnimationUtils.loadAnimation(this, R.anim.card_animation);
        walletAnimation=AnimationUtils.loadAnimation(this,R.anim.wallet_animation);
        premierText = AnimationUtils.loadAnimation(this, R.anim.first_text);
        secondText=AnimationUtils.loadAnimation(this,R.anim.second_text);
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);

        imageAnimCard = findViewById(R.id.imageAnimCard);
        imageAnimWallet= findViewById(R.id.imageAnimWallet);

        premierTextSplashScreen=findViewById(R.id.premierTextSplashScreen);
        secondTextSplashScreen = findViewById(R.id.secondTextSplashScreen);

        logo=findViewById(R.id.logoSplashScreen);


        imageAnimCard.setAnimation(cardAnimation);
        imageAnimWallet.setAnimation(walletAnimation);
        premierTextSplashScreen.setAnimation(premierText);
        secondTextSplashScreen.setAnimation(secondText);
        logo.setAnimation(logoAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(SplashScreen.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        }, 3500);



    }
}