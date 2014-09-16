// Kittitach Pongpairoj 5631211121, Tanit Suenghataiporn 5631255821

package cu.ise.ice.calctest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalcMain extends Activity {

    BListener listener;
    Button b1, b2,b3, b4, b5, b6, b7, b8, b9, b0, bPlus, bMinus, bMult, bDiv, bEq, bCls;
    TextView dispArea;

    String dispTxt;
    double ans;
    ArrayList<Double> numArg = new ArrayList<Double>();
    ArrayList<String> opArg = new ArrayList<String>();

    Pattern recogErr, recogOp;
    Matcher analyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_main);

        listener = new BListener();
        initGuiMerge();
        initBListener();
        recogErr = Pattern.compile("([\\+\\-x/][\\+\\-x/])|([\\+\\-x/]$)|([a-zA-Z&&[^Ex]])");
        recogOp = Pattern.compile("[\\+\\-x/]");

        listener.clsFunc();
        dispArea.setText(dispTxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calc_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    private void initGuiMerge() {
        b1 = (Button)findViewById(R.id.bOne);
        b2 = (Button)findViewById(R.id.bTwo);
        b3 = (Button)findViewById(R.id.bThree);
        b4 = (Button)findViewById(R.id.bFour);
        b5 = (Button)findViewById(R.id.bFive);
        b6 = (Button)findViewById(R.id.bSix);
        b7 = (Button)findViewById(R.id.bSeven);
        b8 = (Button)findViewById(R.id.bEight);
        b9 = (Button)findViewById(R.id.bNine);
        b0 = (Button)findViewById(R.id.bZero);
        bPlus = (Button)findViewById(R.id.bPlus);
        bMinus = (Button)findViewById(R.id.bMinus);
        bMult = (Button)findViewById(R.id.bMult);
        bDiv = (Button)findViewById(R.id.bDiv);
        bEq = (Button)findViewById(R.id.bEq);
        bCls = (Button)findViewById(R.id.bCls);

        dispArea = (TextView)findViewById(R.id.numDisp);
    }
    private void initBListener() {
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);
        b0.setOnClickListener(listener);
        bPlus.setOnClickListener(listener);
        bMinus.setOnClickListener(listener);
        bMult.setOnClickListener(listener);
        bDiv.setOnClickListener(listener);
        bEq.setOnClickListener(listener);
        bCls.setOnClickListener(listener);
    }

    protected class BListener implements View.OnClickListener {
        public void onClick(View b) {
            switch(b.getId()) {
                case R.id.bOne: dispTxt = (dispTxt.equals("0"))?"1":dispTxt+"1"; break;
                case R.id.bTwo: dispTxt = (dispTxt.equals("0"))?"2":dispTxt+"2"; break;
                case R.id.bThree: dispTxt = (dispTxt.equals("0"))?"3":dispTxt+"3"; break;
                case R.id.bFour: dispTxt = (dispTxt.equals("0"))?"4":dispTxt+"4"; break;
                case R.id.bFive: dispTxt = (dispTxt.equals("0"))?"5":dispTxt+"5"; break;
                case R.id.bSix: dispTxt = (dispTxt.equals("0"))?"6":dispTxt+"6"; break;
                case R.id.bSeven: dispTxt = (dispTxt.equals("0"))?"7":dispTxt+"7"; break;
                case R.id.bEight: dispTxt = (dispTxt.equals("0"))?"8":dispTxt+"8"; break;
                case R.id.bNine: dispTxt = (dispTxt.equals("0"))?"9":dispTxt+"9"; break;
                case R.id.bZero: dispTxt = (dispTxt.equals("0"))?"0":dispTxt+"0"; break;
                case R.id.bPlus: dispTxt = (dispTxt.equals("0"))?"0":dispTxt+"+"; break;
                case R.id.bMinus: dispTxt = (dispTxt.equals("0"))?"0":dispTxt+"-"; break;
                case R.id.bMult: dispTxt = (dispTxt.equals("0"))?"0":dispTxt+"x"; break;
                case R.id.bDiv: dispTxt = (dispTxt.equals("0"))?"0":dispTxt+"/"; break;
                case R.id.bEq: eqFunc(); break;
                case R.id.bCls: clsFunc(); break;
            }
            dispArea.setText(dispTxt);
        }
        public void eqFunc() {
            while(true) {
                if(dispTxt.equals("0")) {
                    dispTxt = "0";
                    break;
                }
                analyze = recogErr.matcher(dispTxt);
                if(analyze.find()) { dispTxt = "Math Error"; break; }
                analyze = recogOp.matcher(dispTxt);
                while(analyze.find()) opArg.add(dispTxt.charAt(analyze.start())+"");
                for(String num : dispTxt.split("[\\+\\-x/]")) numArg.add(Double.parseDouble(num));
                ans = conductOperation();
                dispTxt = ""+ans;
                opArg.clear();
                numArg.clear();
                break;
            }
        }
        public void clsFunc() {
            numArg.clear();
            opArg.clear();
            dispTxt = "0";
        }
        public double conductOperation() {
            double temp = 0.0;
            for(int x = 0; x<numArg.size(); x++) {
                if(x==0) { temp = numArg.get(x); continue; }
                switch(opArg.get(x-1).charAt(0)) {
                    case '+': temp = temp+numArg.get(x); break;
                    case '-': temp = temp-numArg.get(x); break;
                    case 'x': temp = temp*numArg.get(x); break;
                    case '/': temp = temp/numArg.get(x); break;
                }
            }
            return temp;
        }
    }
}
