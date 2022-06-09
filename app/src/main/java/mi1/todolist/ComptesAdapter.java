package mi1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mi1.todolist.db.SousMatiere;
import mi1.todolist.db.User;


public class ComptesAdapter extends ArrayAdapter<User> {

    public ComptesAdapter(Context mCtx, List<User> userList) {
        super(mCtx, R.layout.template_compte, userList);
    }

    /**
     * Remplit une ligne de la listView avec les informations du user
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la sous-matière
        final User user = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_compte, parent, false);

        // Récupération des objets graphiques dans le template
        TextView pseudo = (TextView) rowView.findViewById(R.id.TemplateCompte_pseudo);
        TextView prenomNom = (TextView) rowView.findViewById(R.id.TemplateCompte_prenom_et_nom);

        // Mise à jour des attributs des objets graphiques
        pseudo.setText(user.getPseudo());
        prenomNom.setText(user.getPrenom()+" "+user.getNom());

        return rowView;
    }
}