package test.com.question.viewmodels;

/**
 * Created by Dharmic on 8/9/2017.
 */

public class QuestionModel {

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String option5 = "";

    private String questionId;

    private String answer;

    private String question;

    private String solution;

    private int questionIndex;

    private int userChosenAnswer = -1;



    public QuestionModel(String questionId, String question, String option1, String option2, String option3, String option4, String option5, String answer,  String solution, int questionIndex) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.questionId = questionId;
        this.answer = answer;
        this.question = question;
        this.solution = solution;
        this.questionIndex = questionIndex;
    }


    public int getUserChosenAnswer() {
        return userChosenAnswer;
    }

    public void setUserChosenAnswer(int userChosenAnswer) {
        this.userChosenAnswer = userChosenAnswer;
    }

    public String getOption1 ()
    {
        return option1;
    }

    public void setOption1 (String option1)
    {
        this.option1 = option1;
    }

    public String getOption2 ()
    {
        return option2;
    }

    public void setOption2 (String option2)
    {
        this.option2 = option2;
    }

    public String getOption3 ()
    {
        return option3;
    }

    public void setOption3 (String option3)
    {
        this.option3 = option3;
    }

    public String getOption4 ()
    {
        return option4;
    }

    public void setOption4 (String option4)
    {
        this.option4 = option4;
    }


    public String getOption5 ()
    {
        return option5;
    }

    public void setOption5 (String option5)
    {
        this.option5 = option5;
    }

    public String getQuestionId ()
    {
        return questionId;
    }

    public void setQuestionId (String questionId)
    {
        this.questionId = questionId;
    }

    public String getAnswer ()
    {
        return answer;
    }

    public void setAnswer (String answer)
    {
        this.answer = answer;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getSolution ()
    {
        return solution;
    }

    public void setSolution (String solution)
    {
        this.solution = solution;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionModel{");
        sb.append("questionId='").append(questionId).append('\'');
        sb.append(", question='").append(question).append('\'');
        sb.append(", option1='").append(option1).append('\'');
        sb.append(", option2='").append(option2).append('\'');
        sb.append(", option3='").append(option3).append('\'');
        sb.append(", option4='").append(option4).append('\'');
        sb.append(", option5='").append(option5).append('\'');
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", solution='").append(solution).append('\'');
        sb.append(", questionIndex='").append(questionIndex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}