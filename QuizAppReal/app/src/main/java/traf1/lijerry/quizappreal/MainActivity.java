package traf1.lijerry.quizappreal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    EditText responseText;
    TextView question;
    TextView score;
    TextView hiname;
    TextView hiscore;
    int sc=0;
    String[] questions = {"","What is 2+2?","Hello?","Test"};
    String[] correct_answers = {"","a","b","c"};
    String name="";
    int question_number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton=findViewById(R.id.submitButton);
        responseText=findViewById(R.id.responseEditText);
        question=findViewById(R.id.question);
        score=findViewById(R.id.score);
        final Integer[] qNums=new Integer[4];
        //hiname=findViewById(R.id.name1);
        //hiscore=findViewById(R.id.score1);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //NORMAL QUESTION
                if (question_number!=0) {
                    for (int i=0;i<4;i++)
                        qNums[i]=1+(int)(Math.random()*8);
                    question.setText(qNums[0]+" "+qNums[1]+"\n"+qNums[2]+" "+qNums[3]);
                    if (responseText.getText().toString().equals(correct_answers[question_number])) {
                        sc++;
                    }
                }
                //FIRST QUESTION
                else {
                    name=responseText.getText().toString();
                    question_number++;
                    //question.setText(@+id/start);
                    return;
                }
                score.setText("Score: " + sc);
                question_number++;
                //GAME OVER
                if (question_number>=questions.length){
                    setContentView(R.layout.leaderboard);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    Set<String> data;
                    if (pref.contains("leaderboard")){
                        data=pref.getStringSet("leaderboard",new HashSet<String>());
                    }
                    else{
                        data=new HashSet<String>();
                    }
                    data.add(sc+name);
                    editor.putStringSet("leaderboard",data);
                    editor.commit();
                    Iterator<String> it=data.iterator();
                    TreeMap<Integer,String> highscores=new TreeMap<Integer,String>();
                    //Pattern p=Pattern.compile("");
                    //MAKE THIS MORE LEGIT
                    while(it.hasNext()) {
                        String thisln = it.next();
                        String itrname = thisln.substring(1);
                        Integer itrscore = Integer.parseInt(thisln.substring(0,1));
                        highscores.put(itrscore,itrname);
                    }
                    System.out.println(highscores.firstEntry());
                    hiname=findViewById(R.id.name1);
                    System.out.println("2");
                    hiname.setText(highscores.firstEntry().getKey());
                    System.out.println("3");
                    //hiscore.setText(highscores.firstEntry().getValue());
                    String printout="";
                    for (int i=0;i<highscores.size();i++){
                        String ke=highscores.firstEntry().getKey()+"";
                        String ve=highscores.firstEntry().getValue();
                        printout+=ke+ve+"\n";
                        highscores.remove(highscores.firstKey());
                    }
                    hiname.setText(printout);
                }
            }
        });
    }
}
