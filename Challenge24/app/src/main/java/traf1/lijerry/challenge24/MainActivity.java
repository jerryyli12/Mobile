package traf1.lijerry.challenge24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    EditText getName;
    String name;
    ImageView titlePic;
    Button tl;
    Button tr;
    Button bl;
    Button br;
    ImageButton add;
    ImageButton subtract;
    ImageButton multiply;
    ImageButton divide;
    String ansstring;
    TextView scoreText;
    TextView hiscores;
    TextView timerText;
    CountDownTimer timer;
    CountDownTimer bgtimer;
    CountDownTimer bgtimerw;
    CountDownTimer addscore;
    ConstraintLayout bg;
    int tlval;
    int trval;
    int blval;
    int brval;
    int firstNumber=-1000;
    int operator=-1;
    int numleft=4;
    int score=0;
    int num_questions=1;
    int changeInScore=0;
    ColorStateList oldColors;
    double duration;
    Button firstClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton=findViewById(R.id.startButton);
        tl=findViewById(R.id.tl);
        tr=findViewById(R.id.tr);
        bl=findViewById(R.id.bl);
        br=findViewById(R.id.br);
        add=findViewById(R.id.add);
        subtract=findViewById(R.id.subtract);
        multiply=findViewById(R.id.multiply);
        divide=findViewById(R.id.divide);
        titlePic=findViewById(R.id.titlePic);
        getName=findViewById(R.id.getName);
        scoreText=findViewById(R.id.score);
        timerText=findViewById(R.id.timer);
        bg=findViewById(R.id.bg);
        final ArrayList<Integer> qNums = new ArrayList<>();
        final long totalSeconds = 24;
        oldColors = timerText.getTextColors();
        timer = new CountDownTimer(totalSeconds * 1000, 100) {
            public void onTick(long millisUntilFinished) {
                DecimalFormat df = new DecimalFormat("#.#");
                duration=totalSeconds - millisUntilFinished / 1000.0;
                timerText.setText("" + df.format(duration));
                if (millisUntilFinished<10000){
                    timerText.setTextColor(Color.RED);
                    timerText.setTextSize((float)(45+10*Math.sin(millisUntilFinished/150)));
                }
            }
            public void onFinish() {
                timerText.setTextColor(oldColors);
                timerText.setTextSize(40);
                bgtimerw.start();
                next_question();
            }
        };
        bgtimer = new CountDownTimer(500, 10) {
            public void onTick(long millisUntilFinished) {
                bg.setBackgroundColor(Color.argb(255,(int)(75+0.36*(500-millisUntilFinished)),(int)(180+0.15*(500-millisUntilFinished)),(int)(70+0.37*(500-millisUntilFinished))));
            }
            public void onFinish() {
                bg.setBackgroundColor(Color.TRANSPARENT);
            }
        };
        bgtimerw = new CountDownTimer(500, 10) {
            public void onTick(long millisUntilFinished) {
                bg.setBackgroundColor(Color.argb(255,255,(int)(.51*(500-millisUntilFinished)),(int)(.51*(500-millisUntilFinished))));
            }
            public void onFinish() {
                bg.setBackgroundColor(Color.TRANSPARENT);
            }
        };
        addscore = new CountDownTimer(1000, 10) {
            public void onTick(long millisUntilFinished) {
                scoreText.setText("+"+changeInScore);
                scoreText.setTextSize((float)(40+0.01*(millisUntilFinished)));
            }
            public void onFinish() {
                scoreText.setTextSize(40);
                changeInScore=0;
                scoreText.setText("" + score);
            }
        };
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=getName.getText().toString();
                startButton.setVisibility(View.GONE);
                getName.setVisibility(View.GONE);
                titlePic.setVisibility(View.GONE);
                tl.setVisibility(View.VISIBLE);
                tr.setVisibility(View.VISIBLE);
                bl.setVisibility(View.VISIBLE);
                br.setVisibility(View.VISIBLE);
                tl.setBackgroundColor(Color.LTGRAY);
                tr.setBackgroundColor(Color.LTGRAY);
                bl.setBackgroundColor(Color.LTGRAY);
                br.setBackgroundColor(Color.LTGRAY);
                add.setVisibility(View.VISIBLE);
                multiply.setVisibility(View.VISIBLE);
                subtract.setVisibility(View.VISIBLE);
                divide.setVisibility(View.VISIBLE);
                scoreText.setVisibility(View.VISIBLE);
                timerText.setVisibility(View.VISIBLE);
                boolean answerExists = false;
                while (!answerExists) {
                    qNums.clear();
                    for (int i = 0; i < 4; i++)
                        qNums.add(1 + (int) (Math.random() * 8));
                    ArrayList<Integer> unused = new ArrayList<>();
                    for (int i = 0; i < 10000; i++) {
                        unused.addAll(qNums);
                        int removeindex = (int) (Math.random() * unused.size());
                        int ans = unused.get(removeindex);
                        ansstring = Integer.toString(ans);
                        unused.remove(removeindex);
                        while (unused.size() > 0) {
                            removeindex = (int) (Math.random() * unused.size());
                            int val = unused.get(removeindex);
                            int op = (int) (Math.random() * 4);
                            if (op == 0) {
                                ans += val;
                                ansstring += "+" + val;
                            } else if (op == 1) {
                                ans -= val;
                                ansstring += "-" + val;
                            } else if (op == 2) {
                                ans *= val;
                                ansstring = "(" + ansstring + ")" + "*" + val;
                            } else {
                                if (ans % val != 0) {
                                    ans = 0;
                                    unused.clear();
                                    break;
                                }
                                ans /= val;
                                ansstring = "(" + ansstring + ")" + "/" + val;
                            }
                            unused.remove(removeindex);
                        }
                        if (ans == 24) {
                            answerExists = true;
                            break;
                        }
                    }
                }
                tlval=qNums.get(0);
                trval=qNums.get(1);
                blval=qNums.get(2);
                brval=qNums.get(3);
                tl.setText(""+tlval);
                tr.setText(""+trval);
                bl.setText(""+blval);
                br.setText(""+brval);
                timer.start();
            }
        });
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=tl){
                    add.setBackgroundResource(R.drawable.add);
                    subtract.setBackgroundResource(R.drawable.subtract);
                    multiply.setBackgroundResource(R.drawable.multiply);
                    divide.setBackgroundResource(R.drawable.divide);
                    if (operator==3&&firstNumber%tlval!=0){
                        Toast.makeText(getApplicationContext(),"Not divisible!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                    firstClicked.setVisibility(View.GONE);
                    if (operator==0) {
                        tlval=firstNumber + tlval;
                        tl.setText("" + tlval);
                    }
                    else if (operator==1) {
                        tlval=firstNumber - tlval;
                        tl.setText("" + tlval);
                    }
                    else if (operator==2) {
                        tlval=firstNumber * tlval;
                        tl.setText("" + tlval);
                    }
                    else {
                        tlval=firstNumber / tlval;
                        tl.setText("" + tlval);
                    }
                    operator=-1;
                    firstClicked=null;
                    numleft-=1;
                    firstNumber=-1000;
                    if (numleft==1){
                        if (tlval==24) {
                            changeInScore=(int) (60 / duration);
                            score += changeInScore;
                            bgtimer.start();
                        }
                        else
                            bgtimerw.start();
                        next_question();
                    }

                }
                else if (firstNumber==-1000 || (firstNumber!=-1000 && operator==-1)){
                    firstNumber=tlval;
                    firstClicked=tl;
                    tl.setBackgroundColor(Color.GRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                }
            }
        });
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=tr){
                    add.setBackgroundResource(R.drawable.add);
                    subtract.setBackgroundResource(R.drawable.subtract);
                    multiply.setBackgroundResource(R.drawable.multiply);
                    divide.setBackgroundResource(R.drawable.divide);
                    if (operator==3&&firstNumber%trval!=0){
                        Toast.makeText(getApplicationContext(),"Not divisible!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                    firstClicked.setVisibility(View.GONE);
                    if (operator==0) {
                        trval=firstNumber + trval;
                        tr.setText("" + trval);
                    }
                    else if (operator==1) {
                        trval=firstNumber - trval;
                        tr.setText("" + trval);
                    }
                    else if (operator==2) {
                        trval=firstNumber * trval;
                        tr.setText("" + trval);
                    }
                    else {
                        trval=firstNumber / trval;
                        tr.setText("" + trval);
                    }
                    operator=-1;
                    firstClicked=null;
                    numleft-=1;
                    firstNumber=-1000;
                    if (numleft==1){
                        if (trval==24) {
                            changeInScore=(int) (60 / duration);
                            score += changeInScore;
                            bgtimer.start();
                        }
                        else
                            bgtimerw.start();
                        next_question();
                    }

                }
                else if (firstNumber==-1000 || (firstNumber!=-1000 && operator==-1)){
                    firstNumber=trval;
                    firstClicked=tr;
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.GRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                }
            }
        });
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=bl){
                    add.setBackgroundResource(R.drawable.add);
                    subtract.setBackgroundResource(R.drawable.subtract);
                    multiply.setBackgroundResource(R.drawable.multiply);
                    divide.setBackgroundResource(R.drawable.divide);
                    if (operator==3&&firstNumber%blval!=0){
                        Toast.makeText(getApplicationContext(),"Not divisible!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                    firstClicked.setVisibility(View.GONE);
                    if (operator==0) {
                        blval=firstNumber + blval;
                        bl.setText("" + blval);
                    }
                    else if (operator==1) {
                        blval=firstNumber - blval;
                        bl.setText("" + blval);
                    }
                    else if (operator==2) {
                        blval=firstNumber * blval;
                        bl.setText("" + blval);
                    }
                    else {
                        blval=firstNumber / blval;
                        bl.setText("" + blval);
                    }
                    operator=-1;
                    firstClicked=null;
                    numleft-=1;
                    firstNumber=-1000;
                    if (numleft==1){
                        if (blval==24) {
                            changeInScore=(int) (60 / duration);
                            score += changeInScore;
                            bgtimer.start();
                        }
                        else
                            bgtimerw.start();
                        next_question();
                    }
                }
                else if (firstNumber==-1000 || (firstNumber!=-1000 && operator==-1)){
                    firstNumber=blval;
                    firstClicked=bl;
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.GRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                }
            }
        });
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=br){
                    add.setBackgroundResource(R.drawable.add);
                    subtract.setBackgroundResource(R.drawable.subtract);
                    multiply.setBackgroundResource(R.drawable.multiply);
                    divide.setBackgroundResource(R.drawable.divide);
                    if (operator==3&&firstNumber%brval!=0){
                        Toast.makeText(getApplicationContext(),"Not divisible!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.LTGRAY);
                    firstClicked.setVisibility(View.GONE);
                    if (operator==0) {
                        brval=firstNumber + brval;
                        br.setText("" + brval);
                    }
                    else if (operator==1) {
                        brval=firstNumber - brval;
                        br.setText("" + brval);
                    }
                    else if (operator==2) {
                        brval=firstNumber * brval;
                        br.setText("" + brval);
                    }
                    else {
                        brval=firstNumber / brval;
                        br.setText("" + brval);
                    }
                    operator=-1;
                    firstClicked=null;
                    numleft-=1;
                    firstNumber=-1000;
                    if (numleft==1){
                        if (brval==24) {
                            changeInScore=(int) (60 / duration);
                            score += changeInScore;
                            bgtimer.start();
                        }
                        else
                            bgtimerw.start();
                        next_question();
                    }

                }
                else if (firstNumber==-1000 || (firstNumber!=-1000 && operator==-1)){
                    firstNumber=brval;
                    firstClicked=br;
                    tl.setBackgroundColor(Color.LTGRAY);
                    tr.setBackgroundColor(Color.LTGRAY);
                    bl.setBackgroundColor(Color.LTGRAY);
                    br.setBackgroundColor(Color.GRAY);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator=0;
                add.setBackgroundResource(R.drawable.addp);
                subtract.setBackgroundResource(R.drawable.subtract);
                multiply.setBackgroundResource(R.drawable.multiply);
                divide.setBackgroundResource(R.drawable.divide);
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator=1;
                add.setBackgroundResource(R.drawable.add);
                subtract.setBackgroundResource(R.drawable.subtractp);
                multiply.setBackgroundResource(R.drawable.multiply);
                divide.setBackgroundResource(R.drawable.divide);
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator=2;
                add.setBackgroundResource(R.drawable.add);
                subtract.setBackgroundResource(R.drawable.subtract);
                multiply.setBackgroundResource(R.drawable.multiplyp);
                divide.setBackgroundResource(R.drawable.divide);
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator=3;
                add.setBackgroundResource(R.drawable.add);
                subtract.setBackgroundResource(R.drawable.subtract);
                multiply.setBackgroundResource(R.drawable.multiply);
                divide.setBackgroundResource(R.drawable.dividep);
            }
        });
    }
    protected void next_question(){
        if (num_questions==10) {
            end_game();
            return;
        }
        /*scoreText.setText("" + score);*/
        addscore.start();
        final ArrayList<Integer> qNums = new ArrayList<>();
        numleft=4;
        boolean answerExists = false;
        while (!answerExists) {
            qNums.clear();
            for (int i = 0; i < 4; i++)
                qNums.add(1 + (int) (Math.random() * 8));
            ArrayList<Integer> unused = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                unused.addAll(qNums);
                int removeindex = (int) (Math.random() * unused.size());
                int ans = unused.get(removeindex);
                ansstring = Integer.toString(ans);
                unused.remove(removeindex);
                while (unused.size() > 0) {
                    removeindex = (int) (Math.random() * unused.size());
                    int val = unused.get(removeindex);
                    int op = (int) (Math.random() * 4);
                    if (op == 0) {
                        ans += val;
                        ansstring += "+" + val;
                    } else if (op == 1) {
                        ans -= val;
                        ansstring += "-" + val;
                    } else if (op == 2) {
                        ans *= val;
                        ansstring = "(" + ansstring + ")" + "*" + val;
                    } else {
                        if (ans % val != 0) {
                            ans = 0;
                            unused.clear();
                            break;
                        }
                        ans /= val;
                        ansstring = "(" + ansstring + ")" + "/" + val;
                    }
                    unused.remove(removeindex);
                }
                if (ans == 24) {
                    answerExists = true;
                    break;
                }
            }
        }
        num_questions++;
        timerText.setTextColor(oldColors);
        timerText.setTextSize(40);
        tlval=qNums.get(0);
        trval=qNums.get(1);
        blval=qNums.get(2);
        brval=qNums.get(3);
        add.setBackgroundResource(R.drawable.add);
        subtract.setBackgroundResource(R.drawable.subtract);
        multiply.setBackgroundResource(R.drawable.multiply);
        divide.setBackgroundResource(R.drawable.divide);
        operator=-1;
        tl.setVisibility(View.VISIBLE);
        tr.setVisibility(View.VISIBLE);
        bl.setVisibility(View.VISIBLE);
        br.setVisibility(View.VISIBLE);
        tl.setText(""+tlval);
        tr.setText(""+trval);
        bl.setText(""+blval);
        br.setText(""+brval);
        timer.start();
    }
    protected void end_game(){
        setContentView(R.layout.leaderboard);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String data;
        if (pref.contains("leaderboard"))
            data = pref.getString("leaderboard", "") + "\n" + score + " " + name;
        else
            data = score + " " + name;
        Scanner scanner = new Scanner(data);
        TreeSet<Entry> tree = new TreeSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            tree.add(new Entry(Integer.parseInt(split[0]), split[1]));
        }
        String printout = "";
        int si = tree.size();
        for (int i = 0; i < Math.min(si, 10); i++) {
            printout += tree.first() + "\n";
            tree.remove(tree.first());
        }
        hiscores = findViewById(R.id.hiscores);
        hiscores.setText(printout);
        editor.putString("leaderboard", data);
        editor.commit();
    }

}

class Entry implements Comparable<Entry>{
    int score;
    String name;
    public Entry(int sc,String n){
        score=sc;
        name=n;
    }
    public int compareTo(Entry e){
        if (e.getScore()!=score)
            return e.getScore()-score;
        else
            return e.getName().compareTo(name);
    }
    public int getScore(){
        return score;
    }
    public String getName(){
        return name;
    }
    public String toString() {
        return score+"  "+name;
    }
}