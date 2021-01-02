package com.example.truthordare;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truthordare.database.DatabaseRepo;
import com.example.truthordare.database.model.DareOrder;
import com.example.truthordare.database.model.TruthQuestions;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionActivity extends AppCompatActivity   {


    DatabaseRepo databaseRepo;
    Disposable disposable;
    TextView textView,text_what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle b=getIntent().getExtras();
        int mode=b.getInt("mode");

        databaseRepo=new DatabaseRepo(this);

        if (mode==1){
            databaseRepo.getQuestion()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<TruthQuestions>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable=d;
                        }

                        @Override
                        public void onSuccess(TruthQuestions truthQuestions) {
                            text_what.setText("Truth");
                         textView.setText(truthQuestions.getQuestion());
                        }

                        @Override
                        public void onError(Throwable e) {


                        }
                    });
        }else {
           

            databaseRepo.getOrder()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<DareOrder>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable=d;
                        }

                        @Override
                        public void onSuccess(DareOrder dareOrder) {
                            text_what.setText("Dare");
                            textView.setText(dareOrder.getOrder());
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
        
        textView=findViewById(R.id.text_question);
        text_what=findViewById(R.id.text_what);


    }
    
    

    @Override
    protected void onDestroy() {
        if (!disposable.isDisposed())
              disposable.dispose();
        super.onDestroy();
    }


}
