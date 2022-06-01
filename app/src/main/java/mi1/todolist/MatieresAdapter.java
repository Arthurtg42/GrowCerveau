package mi1.todolist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import mi1.todolist.db.Matiere;


public class MatieresAdapter extends ArrayAdapter<Matiere> {

    public MatieresAdapter(Context mCtx, List<Matiere> matiereList) {
        super(mCtx, R.layout.template_matiere, matiereList);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final Matiere matiere = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_matiere, parent, false);

        // Récupération des objets graphiques dans le template
        Button buttonMatiere = (Button) rowView.findViewById(R.id.TemplateMatiere_btn);

        //
        buttonMatiere.setText(matiere.getNom());
        buttonMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HomePageActivity.onNav(position);
            }
        });
        //
        return rowView;
    }

}