package test.com.question;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import test.com.question.viewmodels.QuestionViewModel;

/**
 * Created by ksk648 on 8/30/17.
 */

public class MyWebView extends WebView {


    public MyWebView(Context context, FragmentActivity activity, String content) {
        super(context);
        this.activity = activity;
        this.content = content;
    }

    FragmentActivity activity;

    String content;





    @Override
    public boolean onTouchEvent(MotionEvent event) {

        QuestionViewModel viewModel = ViewModelProviders.of(activity).get(QuestionViewModel.class);

        if(event.getAction() == MotionEvent.ACTION_DOWN && !viewModel.isAnswerMode()){



            if(!viewModel.isExamMode()) {

            int userChosenAnswer = getId();
            ViewGroup parent = (ViewGroup) getParent();


                for (int i = 0; i < parent.getChildCount(); i++) {

                    MyWebView currentOption = (MyWebView) parent.getChildAt(i);
                    int currentId = currentOption.getId();

                    int answer = Integer.parseInt(viewModel.getAnswer((currentOption.getId()/10)))-1;

                    if (currentId == userChosenAnswer) {

                        if(((currentOption.getId()/10)*10)+answer == currentOption.getId()){
                            currentOption.setBackgroundColor(Color.parseColor("#C8E6C9"));
                        }else{
                            currentOption.setBackgroundColor(Color.parseColor("#FFCC80"));
                        }
                    } else {
                        currentOption.setBackgroundColor(Color.WHITE);
                        loadWebData((i+1), currentOption,
                                currentOption.content, currentOption.getId()); //((WebView)parent.getChildAt(i)).

                    }



                        if (currentOption.getId() == ((currentOption.getId() / 10) * 10) + answer) {
                            currentOption.setBackgroundColor(Color.parseColor("#C8E6C9"));
                            loadWebData((i + 1), currentOption,
                                    currentOption.content + "<br/>  <b>Solution :</b> " + viewModel.getSolution((currentOption.getId() / 10)) + "",
                                    currentOption.getId()); //((WebView)parent.getChildAt(i)).

                        }
                    }

                }else{

                int userChosenAnswer = getId();
                ViewGroup parent = (ViewGroup) getParent();

                for (int i = 0; i < parent.getChildCount(); i++) {

                    MyWebView currentOption = (MyWebView) parent.getChildAt(i);
                    int currentId = currentOption.getId();

                    int answer = Integer.parseInt(viewModel.getAnswer((currentOption.getId()/10)))-1;

                    if (currentId == userChosenAnswer) {
                        currentOption.setBackgroundColor(Color.parseColor("#FFCC80"));
                        viewModel.setUserChosenAnswer((currentId / 10), (userChosenAnswer%10)+1);
                    } else {
                        currentOption.setBackgroundColor(Color.WHITE);
                         //((WebView)parent.getChildAt(i)).

                    }

                   /* loadWebData((i+1), currentOption,
                            currentOption.content, currentOption.getId());
                    */

                }
            }
                return true;
        }
        return false;
    }

    public void loadWebData(int position, MyWebView webView, String content, int id) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setId(id);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Log.i("FromWebView-->",content);

        webView.loadDataWithBaseURL("file:///android_asset/",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                        "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                        "<html>\n" +
                        "<head>" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                        "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                        "</head>" +
                        "<body>" +
                        "<b>("+(position)+")</b> : "+content+//getItem(position)+
                        "</body>\n" +
                        "<script src=\"file:///android_asset/script.js\"></script>\n" +
                        "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                        "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                        "<script src=\"file:///android_asset/script.js\"></script>\n" +

                        "</html>", "text/html", "UTF-8", null);

       // webView.reload();
    }
}
