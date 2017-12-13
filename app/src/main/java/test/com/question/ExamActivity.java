package test.com.question;

import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import test.com.question.viewmodels.QuestionModel;
import test.com.question.viewmodels.QuestionViewModel;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ExamActivity extends AppCompatActivity {

    QuestionViewModel viewModel;


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.setExamMode(false);

        QuestionModel questionModel = viewModel.getQuestion();

        getQuestion(questionModel);
    }



    private void finishExam() {

        int rightAnswers = 0;

        FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.NextFab);
        nextButton.setVisibility(View.VISIBLE);


        FloatingActionButton nextButton2 = (FloatingActionButton) findViewById(R.id.NextFab2);
        nextButton2.setVisibility(View.GONE);

        // FloatingActionButton uploadButton = (FloatingActionButton) findViewById(R.id.SubmitFab);
       // uploadButton.setVisibility(View.GONE);

        //nextButton.setText("Answers");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                viewModel.setCurrentIndex(-1);
                viewModel.setAnswerMode(true);
                showAnswers();
            }
        });



        for(int i = 0 ; i < 20 ; i++){
            if(Integer.parseInt(viewModel.getAnswer(i))-1 == viewModel.getUserChosenAnswer(i)){
                rightAnswers++;
            }
        }

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setContent("Correctly Answered >> "+rightAnswers+"/20");

        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //questionFragment.getView().setLayoutParams(params);

        String[] options = new String[0];
        QuestionFragment dummyFragment2 = new QuestionFragment();
        dummyFragment2.setContent("");


        FragmentTransaction txn = getFragmentManager().beginTransaction();
        txn.replace(R.id.fLayout, questionFragment);
        txn.replace(R.id.fLayout2,dummyFragment2);
        txn.addToBackStack(null);
        txn.commit();


    }

    private void showAnswers() {

        if(viewModel.getCurrentIndex() == 19){

            //getQuestion(viewModel.getNextQuestion());

            viewModel.setAnswerMode(false);
            viewModel.clearViewModel();
            QuestionModel questionModel = viewModel.getQuestion();
            getQuestion(questionModel);
            //getQuestion(viewModel.getNextQuestion());

            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.NextFab);

            //nextButton.setText("Re-Test");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //viewModel.setCurrentIndex(-1);
                    //viewModel.setExamMode(true);

                    //QuestionModel questionModel = viewModel.getQuestion();

                    //getQuestion(questionModel);
                    getQuestion(viewModel.getNextQuestion());

                    //showAnswers();
                }
            });
        }else{
            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.NextFab);
            //nextButton.setText("Next Solution");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    showAnswers();
                }
            });

            getQuestion(viewModel.getNextQuestion());

        }
    }

    private void getQuestion(QuestionModel questionModel) {


        Log.v("Question ",questionModel.toString());

    if(questionModel.getQuestionIndex() > 28) {
        viewModel.clearViewModel();
        questionModel = viewModel.getQuestion();
        getQuestion(questionModel);
    }


        if(questionModel.getQuestionIndex() == 19 && viewModel.isExamMode() && !viewModel.isAnswerMode()){

            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.NextFab);
            nextButton.setVisibility(View.GONE);

            FloatingActionButton nextButton2 = (FloatingActionButton) findViewById(R.id.NextFab2);
            nextButton2.setVisibility(View.VISIBLE);

            nextButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    finishExam();
                }
            });


        }else if(!viewModel.isAnswerMode()){
            FloatingActionButton nextButton = (FloatingActionButton) findViewById(R.id.NextFab);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    getQuestion(viewModel.getNextQuestion());
                }
            });
        }

        String[] options;
        if(questionModel.getOption5().equalsIgnoreCase("")){
            options = new String[4];
            options[0] = questionModel.getOption1();
            options[1] = questionModel.getOption2();
            options[2] = questionModel.getOption3();
            options[3] = questionModel.getOption4();
        }else{
            options = new String[5];
            options[0] = questionModel.getOption1();
            options[1] = questionModel.getOption2();
            options[2] = questionModel.getOption3();
            options[3] = questionModel.getOption4();
            options[4] = questionModel.getOption5();
        }


        OptionsFragment optionsFragment = new OptionsFragment();
        optionsFragment.setOptions(options, questionModel.getQuestionIndex());

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setContent(questionModel.getQuestion());

        FragmentTransaction txn = getFragmentManager().beginTransaction();
        txn.replace(R.id.fLayout, questionFragment);
        txn.replace(R.id.fLayout2,optionsFragment);
        txn.addToBackStack(null);
        txn.commit();

    }
}
