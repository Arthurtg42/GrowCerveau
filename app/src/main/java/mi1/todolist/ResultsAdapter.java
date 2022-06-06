package mi1.todolist;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

import mi1.todolist.db.Result;


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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
            //On met le background sur la reponse
            reponse.setBackgroundResource(R.drawable.drawable_back_reponse_correct);

            // On gère la place que prends la reponse et la reponse uti (Avec gestion des marges) //
            //Reponse Uti
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    2.0f
            );
            param.setMargins(5, 5, 5, 5);
            reponse.setLayoutParams(param);

            //Reponse
            LinearLayout.LayoutParams paramcor = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0f
            );
            paramcor.setMargins(5, 5, 5, 5);
            correction.setLayoutParams(paramcor);

            //Enonce
            LinearLayout.LayoutParams paramenon = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            paramenon.setMargins(5, 5, 5, 5);
            enonce.setLayoutParams(paramenon);
        }
        else {
            reponse.setBackgroundResource(R.drawable.drawable_back_reponse_false);
            correction.setText(result.getReponse());
            correction.setBackgroundResource(R.drawable.drawable_back_reponse_correct);
        }

        return rowView;
    }

}