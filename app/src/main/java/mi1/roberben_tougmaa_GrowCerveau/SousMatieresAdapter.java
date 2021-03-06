package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.roberben_tougmaa_GrowCerveau.db.SousMatiere;


public class SousMatieresAdapter extends ArrayAdapter<SousMatiere> {

    public SousMatieresAdapter(Context mCtx, List<SousMatiere> sousMatiereList) {
        super(mCtx, R.layout.template_sous_matiere, sousMatiereList);
    }

    /**
     * Remplit une ligne de la listView avec les informations de la sous-matière associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la sous-matière
        final SousMatiere sousMatiere = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_sous_matiere, parent, false);

        // Récupération des objets graphiques dans le template
        TextView buttonSousMatiere = (TextView) rowView.findViewById(R.id.TemplateSousMatiere_btn);

        // Mise à jour des attributs des objets graphiques
        buttonSousMatiere.setText(sousMatiere.getNom());

        return rowView;
    }
}