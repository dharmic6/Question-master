package test.com.question;

import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import test.com.question.viewmodels.QuestionModel;
import test.com.question.viewmodels.QuestionViewModel;


public class Question extends AppCompatActivity {

    QuestionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        QuestionModel questionModel = viewModel.getQuestion();

        getQuestion(questionModel);

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestion(viewModel.getNextQuestion());
            }
        });


    }




    private void getQuestion(QuestionModel questionModel) {


        Log.v("Question ",questionModel.toString());


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
