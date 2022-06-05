package mi1.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;


public class ResultsAdapter extends ArrayAdapter<Result> {

    public ResultsAdapter(Context mCtx, List<Result> resultList) {
        super(mCtx, R.layout.template_resultats, resultList);
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
        final Result result = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_resultats, parent, false);

        // Récupération des objets graphiques dans le template
        TextView enonce = (TextView) rowView.findViewById(R.id.template_resultats_enonce);
        TextView reponse = (TextView) rowView.findViewById(R.id.template_resultats_reponse);
        TextView correction = (TextView) rowView.findViewById(R.id.template_resultats_correction);

        enonce.setText(result.getEnonce());
        reponse.setText(result.getReponse_uti().isEmpty() ? " " : result.getReponse_uti());

        if(result.isCorrect()){
            reponse.setBackgroundColor(Color.GREEN);
        }
        else {
            reponse.setBackgroundColor(Color.RED);
            correction.setText("Correction : "+result.getReponse());
            correction.setBackgroundColor(Color.GREEN);
        }

        return rowView;
    }

}