package mi1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import mi1.todolist.db.Matiere;
import mi1.todolist.db.SousMatiere;


public class SousMatieresAdapter extends ArrayAdapter<SousMatiere> {

    public SousMatieresAdapter(Context mCtx, List<SousMatiere> sousMatiereList) {
        super(mCtx, R.layout.template_sous_matiere, sousMatiereList);
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
        final SousMatiere sousMatiere = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_sous_matiere, parent, false);

        // Récupération des objets graphiques dans le template
        TextView buttonSousMatiere = (TextView) rowView.findViewById(R.id.TemplateSousMatiere_btn);

        //
        buttonSousMatiere.setText(sousMatiere.getNom());
        buttonSousMatiere.setId(sousMatiere.getId());
        buttonSousMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this.
            }
        });
        //
        return rowView;
    }

}