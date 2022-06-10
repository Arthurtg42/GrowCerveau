package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.roberben_tougmaa_GrowCerveau.db.Matiere;


public class MatieresAdapter extends ArrayAdapter<Matiere> {

    public MatieresAdapter(Context mCtx, List<Matiere> matiereList) {
        super(mCtx, R.layout.template_matiere, matiereList);
    }

    /**
     * Remplit une ligne de la listView avec les informations de la matière associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Récupération de la matière
        final Matiere matiere = getItem(position);
        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_matiere, parent, false);

        // Récupération des objets graphiques dans le template
        TextView buttonMatiere = (TextView) rowView.findViewById(R.id.TemplateMatiere_btn);

        // Mise à jour des attributs des objets graphiques
        buttonMatiere.setText(matiere.getNom());

        return rowView;
    }
}