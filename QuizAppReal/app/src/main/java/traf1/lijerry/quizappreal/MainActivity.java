package traf1.lijerry.quizappreal;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

import java.util.*;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    EditText responseText;
    TextView question;
    TextView score;
    TextView timerText;
    TextView showAnswer;
    TextView hiscores;
    CountDownTimer timer;
    int sc=0;
    String[] correct_answers = {"","a","b","c"};
    String name="";
    String ansstring;
    int question_number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.submitButton);
        responseText = findViewById(R.id.responseEditText);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        timerText = findViewById(R.id.timerText);
        showAnswer = findViewById(R.id.showAnswer);
        final ArrayList<Integer> qNums = new ArrayList<>();
        //hiname=findViewById(R.id.name1);
        //hiscore=findViewById(R.id.score1);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NORMAL QUESTION
                if (question_number != 0) {
                    String response = responseText.getText().toString();
                    if (checkCorrect(response,qNums))
                        sc++;
                    else {
                        showAnswer.setText("Correct answer: " + ansstring);
                        /*try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }*/
                    }
                    timer.cancel();
                }
                //FIRST QUESTION
                else {
                    score.setVisibility(View.VISIBLE);
                    name = responseText.getText().toString();
                }
                score.setText("Score: " + sc);
                question_number++;
                //GAME OVER
                if (question_number >= 11) {
                    setContentView(R.layout.leaderboard);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    String data;
                    if (pref.contains("leaderboard"))
                        data = pref.getString("leaderboard", "") + "\n" + sc + " " + name;
                    else
                        data = sc + " " + name;
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
                    System.out.println(data);
                    editor.commit();
                } else {
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
                    question.setText(qNums.get(0) + " " + qNums.get(1) + "\n" + qNums.get(2) + " " + qNums.get(3));

                    /*timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String currentTime = getString(R.string.time,duration++);
                                    timerText.setText(currentTime);
                                    if(duration>=15)timer.cancel();
                                }
                            });
                        }
                    }, 1000, 1000);*/
                    timer = new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timerText.setText("Seconds remaining: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
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
                            question.setText(qNums.get(0) + " " + qNums.get(1) + "\n" + qNums.get(2) + " " + qNums.get(3));
                            timer.start();
                            responseText.getText().clear();
                            //fix this
                        }
                    };
                    timer.start();
                    responseText.getText().clear();
                }

            }
        });
    }
    public static boolean checkCorrect(final String response,ArrayList<Integer> qNums){
        ArrayList<String> qStr=new ArrayList<>();
        for (int i=0;i<qNums.size();i++)
            qStr.add(""+qNums.get(i));
        if (eval(response)==24){
            for (int i=0;i<response.length();i++)
            {
                System.out.println(qStr);
                if (Character.isDigit(response.charAt(i)))
                {
                    if (qStr.contains(String.valueOf(response.charAt(i))))
                        qStr.remove(String.valueOf(response.charAt(i)));
                    else
                        return false;
                }
            }
            return qStr.isEmpty();
        }
        return false;
    }
    // **THIS IS SOMEONE ELSE'S IMPLEMENTATION**
    // https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
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
        return score+"\t"+name;
    }
}
