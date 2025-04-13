package com.example.myapplication3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication3.Adapters.ListViewAdapter;
import com.example.myapplication3.beans.ItemBean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private RecyclerView List;
    private List<ItemBean> Data;
    private ListViewAdapter adapter;
    private TextView viewClear;
    private TextView viewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        List = findViewById(R.id.RecycleViewList);

        initDate();
    }

    private void initDate() {
        Data = new ArrayList<>();

        Log.d(TAG, "进度记录：activity2初始化开始");
        File file = new File(getFilesDir(), "history.txt");

        int count = 0;
        String line;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();

            File temp = new File(getFilesDir(), "temp.txt");

            while ((line = br.readLine()) != null) {
                Data.add(new ItemBean(++count, line));
            }
            br.close();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        List.setLayoutManager(layoutManager);

        adapter = new ListViewAdapter(Data);
        List.setAdapter(adapter);

        adapter.setOnClickListener(new ListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "进度记录：" + position + "已被点击");
                File file = new File(getFilesDir(), "history.txt");
                Log.d(TAG, "进度记录：位置0成功");

                Log.d(TAG, "进度记录：位置1成功");
                int count;
                String line;
                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    line = br.readLine().substring(6);
                    count = Integer.parseInt(line) - 1;

                    Log.d(TAG, "进度记录：位置2成功");
                    File temp = new File(getFilesDir(), "temp.txt");
                    Log.d(TAG, "进度记录：位置3成功");

                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));
                    bw.write("count:" + count);
                    count = 0;
                    Log.d(TAG, "进度记录：位置4成功");

                    while ((line = br.readLine()) != null) {
                        if (count++ != position) {
                            bw.write("\n" + line);
                        }
                    }
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
                Log.d(TAG, "进度记录：位置5成功");

                Data.remove(position);

                adapter.notifyDataSetChanged();
            }
        });

        viewClear = findViewById(R.id.ViewClear);
        viewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(getFilesDir(), "history.txt");
                Log.d(TAG, "进度记录：位置0成功");

                Log.d(TAG, "进度记录：位置1成功");
                try {
                    Log.d(TAG, "进度记录：位置2成功");
                    File temp = new File(getFilesDir(), "temp.txt");
                    Log.d(TAG, "进度记录：位置3成功");

                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp, false));
                    bw.write("count:0");
                    Log.d(TAG, "进度记录：位置4成功");
                    bw.flush();
                    bw.close();
                    file.delete();
                    temp.renameTo(file);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "进度记录：位置5成功");

                Data.clear();

                adapter.notifyDataSetChanged();

            }
        });

        viewBack = findViewById(R.id.ViewBack);
        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}