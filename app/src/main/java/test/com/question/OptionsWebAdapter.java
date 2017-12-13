package test.com.question;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.com.question.viewmodels.QuestionViewModel;

/**
 * Created by Dharmic on 8/26/2017.
 */

public class OptionsWebAdapter extends ArrayAdapter {
    public OptionsWebAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Object[] objects, FragmentActivity activity, int questionIndex) {
        super(context, resource, objects);
        this.activity =  activity;
        this.questionIndex = questionIndex;
    }

    FragmentActivity activity;

    int questionIndex;



    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView != null){
            return convertView;
        }

        final QuestionViewModel viewModel = ViewModelProviders.of(activity).get(QuestionViewModel.class);

        WebView myWebView = new WebView(getContext());  //(WebView)convertView;//.findViewById(R.id.webView);
        myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setId(position);

        if(viewModel.isExamMode() && viewModel.isAnswerMode()){
            myWebView.setClickable(false);
            myWebView.setEnabled(false);

            int currentView = myWebView.getId();
            int correctAnswer = Integer.parseInt(viewModel.getAnswer(questionIndex)) - 1;
            int selectedView = viewModel.getUserChosenAnswer(questionIndex);

            if (currentView == selectedView) {
                myWebView.setBackgroundColor(Color.parseColor("#FFCC80"));
            }

            if (currentView == correctAnswer) {
                myWebView.setBackgroundColor(Color.parseColor("#C8E6C9"));

                ((WebView) myWebView).loadDataWithBaseURL("file:///android_asset/",
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                                "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                                "<html>\n" +
                                "<head>" +
                                //"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                                "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                                "</head>" +
                                "<body>" +
                                //"<b>("+i+")</b> : "+getItem(position).toString()+
                                viewModel.getSolution(questionIndex) +
                                "</body>\n" +
                                "<script src=\"file:///android_asset/script.js\"></script>\n" +
                                "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                                "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                                "<script src=\"file:///android_asset/script.js\"></script>\n" +

                                "</html>", "text/html", "UTF-8", null);
            }else{
                myWebView.loadDataWithBaseURL("file:///android_asset/",
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                                "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                                "<html>\n" +
                                "<head>" +
                                //"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                                "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                                "</head>" +
                                "<body>" +
                                "<b>("+(position+1)+")</b> : "+getItem(position).toString()+
                                "</body>\n" +
                                "<script src=\"file:///android_asset/script.js\"></script>\n" +
                                "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                                "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                                "<script src=\"file:///android_asset/script.js\"></script>\n" +

                                "</html>", "text/html", "UTF-8", null);
            }


        }else {

            myWebView.setClickable(true);
            myWebView.setEnabled(true);


            myWebView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        int correctAnswer = Integer.parseInt(viewModel.getAnswer(questionIndex)) - 1;

                        ListView parentView = ((ListView) ((WebView) view).getParent());

                        for (int i = 0; i < parentView.getCount(); i++) {

                            WebView webView = (WebView) parentView.getChildAt(i);
                            if (webView != null) {
                                int currentView = webView.getId();
                                int selectedView = view.getId();

                                if (currentView == selectedView) {
                                    webView.setBackgroundColor(Color.parseColor("#FFCC80"));
                                } else {
                                    webView.setBackgroundColor(Color.WHITE);
                                }



                                if (viewModel.isExamMode()) {

                                    viewModel.setUserChosenAnswer(questionIndex, selectedView);

                                } else {

                                    webView.setClickable(false);
                                    webView.setEnabled(false);

                                    if (currentView == correctAnswer) {

                                        webView.setBackgroundColor(Color.parseColor("#C8E6C9"));

                                        ((WebView) webView).loadDataWithBaseURL("file:///android_asset/",
                                                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                                                        "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                                                        "<html>\n" +
                                                        "<head>" +
                                                        //"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                                                        "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                                                        "</head>" +
                                                        "<body>" +
                                                        //"<b>("+i+")</b> : "+getItem(position).toString()+
                                                        viewModel.getSolution(questionIndex) +
                                                        "</body>\n" +
                                                        "<script src=\"file:///android_asset/script.js\"></script>\n" +
                                                        "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                                                        "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                                                        "<script src=\"file:///android_asset/script.js\"></script>\n" +

                                                        "</html>", "text/html", "UTF-8", null);
                                    }
                                }
                            }
                        }
                        return true;
                    }

                    return false;
                }
            });

            myWebView.loadDataWithBaseURL("file:///android_asset/",
                    "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                            "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                            "<html>\n" +
                            "<head>" +
                            //"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                            "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                            "</head>" +
                            "<body>" +
                            "<b>("+(position+1)+")</b> : "+getItem(position).toString()+
                            "</body>\n" +
                            "<script src=\"file:///android_asset/script.js\"></script>\n" +
                            "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                            "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                            "<script src=\"file:///android_asset/script.js\"></script>\n" +

                            "</html>", "text/html", "UTF-8", null);

        }



        return myWebView;
    }
}


