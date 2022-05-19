package mi1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.todolist.db.Task;


public class TasksAdapter extends ArrayAdapter<Task> {

    public TasksAdapter(Context mCtx, List<Task> taskList) {
        super(mCtx, R.layout.template_task, taskList);
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final Task task = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_task, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textViewTask = (TextView) rowView.findViewById(R.id.textViewTask);
        TextView textViewDesc = (TextView) rowView.findViewById(R.id.textViewDesc);

        //
        textViewTask.setText(task.getTask());
        textViewDesc.setText(task.getDescription());

        //
        return rowView;
    }

}