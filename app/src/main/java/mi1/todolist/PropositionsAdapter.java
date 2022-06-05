package mi1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.todolist.db.SousMatiere;


public class PropositionsAdapter extends ArrayAdapter<String> {

    public PropositionsAdapter(Context mCtx, List<String> propositionList) {
        super(mCtx, R.layout.template_proposition_qcm, propositionList);
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
        final String proposition = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_proposition_qcm, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textProposition = (TextView) rowView.findViewById(R.id.TemplatePropositionQcm);
        textProposition.setText(proposition);

        //
        return rowView;
    }

}