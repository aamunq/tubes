package android.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.example.todo.Adapter.ToDoAdapter;
import android.example.todo.Model.ToDoModel;
import android.example.todo.Utils.DatabaseHandler;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView RecyclerView;
    private ToDoAdapter taskAdapter;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        RecyclerView = findViewById(R.id.RecylerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(db, this );
        RecyclerView.setAdapter(taskAdapter);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);

        fab = findViewById(R.id.addBtn);

        ItemTouchHelper ItemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        ItemTouchHelper.attachToRecyclerView(RecyclerView);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }



    @Override
    public void HandleDialogClose(DialogInterface dialog) {
            taskList = db.getAllTasks();
            Collections.reverse(taskList);
            taskAdapter.setTask(taskList);
            taskAdapter.notifyDataSetChanged();
        }



}


