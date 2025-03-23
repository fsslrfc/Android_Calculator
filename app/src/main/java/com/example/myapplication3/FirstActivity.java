package com.example.myapplication3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 注意事项：
 * 正负号可以点击交换，默认为正，按下为负（有按下和抬起的动画区别）（不存在多正负号的输入）（初始化时默认为正号）
 * 小数点点击一次后无法再次点击（有按下和抬起的动画区别）（不存在多小数点的输入）
 *
 * TODO：
 * */

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    private TextView num1TextView;
    private TextView num2TextView;
    private TextView addTextView;
    private TextView subTextView;
    private TextView mulTextView;
    private TextView divTextView;
    private TextView num1SymbolView;
    private TextView num2SymbolView;
    private Button pointButton;

    boolean is_first_num;
    boolean is_add;
    boolean is_sub;
    boolean is_mul;
    boolean is_div;
    StringBuilder ans1;
    StringBuilder ans2;

    //借助ViewModel实现生命周期内的数据持久化
    MainViewModel MVM = new MainViewModel();

    //定义ViewModel类
    public class MainViewModel extends ViewModel {
        boolean is_first_num_o;
        boolean is_add_o;
        boolean is_sub_o;
        boolean is_mul_o;
        boolean is_div_o;
        StringBuilder ans1_o;
        StringBuilder ans2_o;

        //实现数据保存函数
        public void saveViewModel() {
            this.is_first_num_o = is_first_num;
            this.is_add_o = is_add;
            this.is_sub_o = is_sub;
            this.is_mul_o = is_mul;
            this.is_div_o = is_div;
            this.ans1_o = ans1;
            this.ans2_o = ans2;
        }
    }

    //实现数据回写函数
    public void reloadViewModel(MainViewModel mainViewModel) {
        is_first_num = mainViewModel.is_first_num_o;
        is_add = mainViewModel.is_add_o;
        is_sub = mainViewModel.is_sub_o;
        is_mul = mainViewModel.is_mul_o;
        is_div = mainViewModel.is_div_o;
        ans1 = mainViewModel.ans1_o;
        ans2 = mainViewModel.ans2_o;
        Log.d(TAG, "num1 == " + ans1.toString());
        Log.d(TAG, "num2 == " + ans2.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();

        updateResult();
    }

    public void init() {
        num1TextView = findViewById(R.id.viewNum1);
        num2TextView = findViewById(R.id.viewNum2);
        addTextView = findViewById(R.id.viewAdd);
        subTextView = findViewById(R.id.viewSub);
        mulTextView = findViewById(R.id.viewMul);
        divTextView = findViewById(R.id.viewDiv);
        num1SymbolView = findViewById(R.id.Symbol1);
        num2SymbolView = findViewById(R.id.Symbol2);
        pointButton = findViewById(R.id.buttonPoint);

        ans1 = new StringBuilder("+");
        ans2 = new StringBuilder("+");
        is_first_num = true;
        is_add = false;
        is_sub = false;
        is_mul = false;
        is_div = false;
        pointButton.setPressed(false);

        num1TextView.setTextColor(0xFF5985EB);
        num2TextView.setTextColor(0x22000000);
        num1SymbolView.setTextColor(0xFF5985EB);
        num2SymbolView.setTextColor(0x22000000);
        addTextView.setTextColor(0x22000000);
        subTextView.setTextColor(0x22000000);
        mulTextView.setTextColor(0x22000000);
        divTextView.setTextColor(0x22000000);

        checkHistory();

        String path = this.getFilesDir().toString();
        Log.d(TAG, "path == " + path);

        updateResult();

        return;
    }

    //重写横竖屏切换时的调用函数
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MVM.saveViewModel();

        // 检查屏幕方向
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "切换到横屏", Toast.LENGTH_SHORT).show();
            // 可以在这里设置横屏的专属布局
            setContentView(R.layout.first_layout_land);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "切换到竖屏", Toast.LENGTH_SHORT).show();
            // 切换回竖屏布局
            setContentView(R.layout.first_layout);
        }

        init();
        reloadViewModel(MVM);
        updateResult();
    }

    private void updateResult() {
        num1SymbolView.setText(ans1.substring(0, 1));
        num2SymbolView.setText(ans2.substring(0, 1));

        if (is_first_num) {
            num1SymbolView.setTextColor(0xFF5985EB);
            num1TextView.setTextColor(0xFF5985EB);
            num2SymbolView.setTextColor(0x22000000);
            num2TextView.setTextColor(0x22000000);
//            if (ans1.indexOf(".") != -1) {
//                pointButton.setPressed(true);
//            }
//            else{
//                pointButton.setPressed(false);
//            }
        }
        else {
            num1SymbolView.setTextColor(0x22000000);
            num1TextView.setTextColor(0x22000000);
            num2SymbolView.setTextColor(0xFF5985EB);
            num2TextView.setTextColor(0xFF5985EB);
//            if (ans2.indexOf(".") != -1) {
//                num2TextView.setPressed(true);
//            }
//            else{
//                num2TextView.setPressed(false);
//            }
        }

        if (is_add) {
            addTextView.setTextColor(0xFF5985EB);
        }
        else {
            addTextView.setTextColor(0x22000000);
        }

        if (is_sub) {
            subTextView.setTextColor(0xFF5985EB);
        }
        else {
            subTextView.setTextColor(0x22000000);
        }

        if (is_mul) {
            mulTextView.setTextColor(0xFF5985EB);
        }
        else {
            mulTextView.setTextColor(0x22000000);
        }

        if (is_div) {
            divTextView.setTextColor(0xFF5985EB);
        }
        else {
            divTextView.setTextColor(0x22000000);
        }

        num1TextView.setText(ans1.substring(1));
        num2TextView.setText(ans2.substring(1));

        Log.d(TAG, "num1 == " + ans1.toString());
        Log.d(TAG, "num2 == " + ans2.toString());
    }

    public void checkHistory() {
        File file = new File(getFilesDir(), "history.txt");

        try {
            if (!file.exists()) {
                FileWriter writer = new FileWriter(file);
                writer.write("count:0\n");
                writer.flush();
                writer.close();
            }
            else {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                String str = br.readLine().substring(0, 6);
                Log.d(TAG, "记录错误：当前读取到的str是" + str);
                if (!str.equals("count:")) {
                    Toast.makeText(this, "历史记录错误，已重置历史记录！", Toast.LENGTH_LONG).show();
                    reader.close();
                    file.delete();

                    FileWriter writer = new FileWriter(file);
                    writer.write("count:0\n");
                    writer.flush();
                    writer.close();
                }
                reader.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload1History() {
        Log.d(TAG, "错误记录：upload1已开始");
        File temp = new File(getFilesDir(), "history.txt");

        checkHistory();

        int count;
        try {
            FileWriter writer = new FileWriter(temp, true);

            writer.write(ans1.toString());
            if (is_add) {
                writer.write("+");
            }
            else if (is_sub) {
                writer.write("-");
            }
            else if (is_mul) {
                writer.write("*");
            }
            else if (is_div) {
                writer.write("/");
            }
            writer.write(ans2.toString());

            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload2History() {
        Log.d(TAG, "错误记录：upload2已开始");
        File file = new File(getFilesDir(), "history.txt");

        checkHistory();

        int count;
        String line;
        String res;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            line = br.readLine().substring(6);
            count = Integer.parseInt(line) + 1;
            Log.d(TAG, "记录错误：当前count的值为" + count);

            File temp = new File(getFilesDir(), "temp.txt");

            BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));
            bw.write("count:" + count);
            while ((line = br.readLine()) != null) {
                bw.write("\n" + line);
            }
            bw.write("=" + ans1.toString() + "\n");
            bw.flush();
            bw.close();
            br.close();
            reader.close();
            file.delete();
            temp.renameTo(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onClickZero(View view) {
        if (is_first_num) {
            ans1.append('0');
        }
        else {
            ans2.append('0');
        }
        updateResult();
    }

    public void onClickOne(View view) {
        if (is_first_num) {
            ans1.append('1');
        }
        else {
            ans2.append('1');
        }
        updateResult();
    }

    public void onClickTwo(View view) {
        if (is_first_num) {
            ans1.append('2');
        }
        else {
            ans2.append('2');
        }
        updateResult();
    }

    public void onClickThree(View view) {
        if (is_first_num) {
            ans1.append('3');
        }
        else {
            ans2.append('3');
        }
        updateResult();
    }

    public void onClickFour(View view) {
        if (is_first_num) {
            ans1.append('4');
        }
        else {
            ans2.append('4');
        }
        updateResult();
    }

    public void onClickFive(View view) {
        if (is_first_num) {
            ans1.append('5');
        }
        else {
            ans2.append('5');
        }
        updateResult();
    }

    public void onClickSix(View view) {
        if (is_first_num) {
            ans1.append('6');
        }
        else {
            ans2.append('6');
        }
        updateResult();
    }

    public void onClickSeven(View view) {
        if (is_first_num) {
            ans1.append('7');
        }
        else {
            ans2.append('7');
        }
        updateResult();
    }

    public void onClickEight(View view) {
        if (is_first_num) {
            ans1.append('8');
        }
        else {
            ans2.append('8');
        }
        updateResult();
    }

    public void onClickNine(View view) {
        if (is_first_num) {
            ans1.append('9');
        }
        else {
            ans2.append('9');
        }
        updateResult();
    }

    public void onClickSymbol(View view) {
        if (is_first_num) {
            if (ans1.charAt(0) == '+') {
                ans1.setCharAt(0, '-');
                num1SymbolView.setText("-");
                //TODO: +/-符号需要有动画更改
            }
            else {
                ans1.setCharAt(0, '+');
                num1SymbolView.setText("+");
            }
        }
        else {
            if (ans2.charAt(0) == '+') {
                ans2.setCharAt(0, '-');
                num2SymbolView.setText("-");
            }
            else {
                ans2.setCharAt(0, '+');
                num2SymbolView.setText("+");
                //TODO: +/-符号需要有动画更改
            }
        }
        updateResult();
    }

    public void onClickPoint(View view) {
        if (is_first_num) {
            if (ans1.indexOf(".") == -1) {
                ans1.append('.');
                //TODO：小数点需要有动画更改
            }
        }
        else {
            if (ans2.indexOf(".") == -1) {
                ans2.append('.');
                //TODO：小数点需要有动画更改
            }
        }
        updateResult();
    }

    public void onClickAdd(View view) {
        if (is_first_num) {
            is_first_num = false;
            updateResult();
        }
        else if (ans2.length() > 1) {
            onClickEqu(view);

        }
        is_add = true;
        is_sub = false;
        is_mul = false;
        is_div = false;
        updateResult();
    }

    public void onClickSub(View view) {
        if (is_first_num) {
            is_first_num = false;
            updateResult();
        }
        else if (ans2.length() > 1) {
            onClickEqu(view);
        }
        is_sub = true;
        is_add = false;
        is_mul = false;
        is_div = false;
        updateResult();
    }

    public void onClickMul(View view) {
        if (is_first_num) {
            is_first_num = false;
            updateResult();
        }
        else if (ans2.length() > 1) {
            onClickEqu(view);
        }
        is_mul = true;
        is_add = false;
        is_sub = false;
        is_div = false;
        updateResult();
    }

    public void onClickDiv(View view) {
        if (is_first_num) {
            is_first_num = false;
            updateResult();
        }
        else if (ans2.length() > 1) {
            onClickEqu(view);
        }
        is_div = true;
        is_add = false;
        is_sub = false;
        is_mul = false;
        updateResult();
    }

    public void onClickEqu(View view) {
        if (is_first_num) {
            return;
        }
        else {
            upload1History();
            if (is_add) {
                BigNum num1 = new BigNum(ans1.toString());
                BigNum num2 = new BigNum(ans2.toString());
                num1.copy(num1.add(num2));
                ans1 = new StringBuilder(num1.get());
                is_add = false;
            }
            else if (is_sub) {
                BigNum num1 = new BigNum(ans1.toString());
                BigNum num2 = new BigNum(ans2.toString());
                num1.copy(num1.sub(num2));
                ans1 = new StringBuilder(num1.get());
                is_sub = false;
            }
            else if (is_mul) {
                BigNum num1 = new BigNum(ans1.toString());
                BigNum num2 = new BigNum(ans2.toString());
                num1.copy(num1.mul(num2));
                ans1 = new StringBuilder(num1.get());
                is_mul = false;
            }
            else if (is_div) {
                BigNum num1 = new BigNum(ans1.toString());
                BigNum num2 = new BigNum(ans2.toString());
                num1.copy(num1.div(num2));
                ans1 = new StringBuilder(num1.get());
                is_div = false;
            }
            upload2History();
            updateResult();
            ans2 = new StringBuilder("+");
        }
    }

    public void onClickClr(View view) {
        if (is_first_num) {
            ans1 = new StringBuilder("+");
        }
        else if (ans2.length() > 1) {
            ans2 = new StringBuilder("+");
        }
        else {
            is_first_num = true;
            is_add = false;
            is_sub = false;
            is_mul = false;
            is_div = false;
        }
        updateResult();
    }

    public void onClickDel(View view) {
        if (is_first_num) {
            if (ans1.length() > 1) {
                ans1.deleteCharAt(ans1.length() - 1);
            }
        }
        else if (ans2.length() > 1) {
            ans2.deleteCharAt(ans2.length() - 1);
        }
        else {
            is_first_num = true;
            is_add = false;
            is_sub = false;
            is_mul = false;
            is_div = false;
        }
        updateResult();
    }


}