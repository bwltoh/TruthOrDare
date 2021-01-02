package com.example.truthordare;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView helloText=findViewById(R.id.hello_text);



        helloText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                helloText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setTextViewColor(helloText);
                startAnimation(helloText);
            }
        });

        Observable<String> observable=Observable.empty();

        Observer<String> observer =new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            disposable=d;
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(MainActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                startActivity(new Intent(MainActivity.this,PlayersActivity.class));
                finish();
            }
        };
        observable
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);




    }

    private int[] getColors(){
        return  new int[]{
                getResources().getColor(R.color.red),getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.green),getResources().getColor(R.color.blue),
                getResources().getColor(R.color.purpel)
        };
    }

    void startAnimation(TextView helloText){
        ObjectAnimator animator=ObjectAnimator.ofFloat(helloText,"alpha",0,1);
        animator.setDuration(500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    private void setTextViewColor(TextView textView ){
        int[] colors=getColors();
        Shader shader=new LinearGradient(0,0,0,textView.getWidth(),colors,null, Shader.TileMode.MIRROR);
        Matrix matrix =new Matrix();
        matrix.setRotate(90);
        shader.setLocalMatrix(matrix);
        textView.getPaint().setShader(shader);
    }
    @Override
    protected void onStop() {
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
