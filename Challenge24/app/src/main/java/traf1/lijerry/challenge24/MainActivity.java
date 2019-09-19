package traf1.lijerry.challenge24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

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
    int tlval;
    int trval;
    int blval;
    int brval;
    int firstNumber=-1000;
    int operator=-1;
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
        final ArrayList<Integer> qNums = new ArrayList<>();
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
                add.setVisibility(View.VISIBLE);
                multiply.setVisibility(View.VISIBLE);
                subtract.setVisibility(View.VISIBLE);
                divide.setVisibility(View.VISIBLE);
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
            }
        });
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=tl){
                    firstClicked.setVisibility(View.GONE);
                    if (operator==0)
                        tl.setText(""+(firstNumber+tlval));
                    else if (operator==1)
                        tl.setText(""+(firstNumber-tlval));
                    else if (operator==2)
                        tl.setText(""+(firstNumber*tlval));
                    else if (firstNumber%tlval==0)
                        tl.setText(""+(firstNumber/tlval));
                    else
                        return;
                    operator=-1;
                    firstClicked=null;
                }
                else if (firstNumber==-1000){
                    firstNumber=tlval;
                    firstClicked=tl;
                    System.out.println("hii");
                }
            }
        });
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNumber!=-1000 && operator!=-1&&firstClicked!=tr){
                    firstClicked.setVisibility(View.GONE);
                    operator=-1;
                    firstClicked=null;
                    System.out.println("h");
                }
                else if (firstNumber==-1000){
                    firstNumber=trval;
                    firstClicked=tr;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator=0;
            }
        });
    }

}
