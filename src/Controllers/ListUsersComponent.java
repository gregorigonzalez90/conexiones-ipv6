
package Controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ListUsersComponent {
    
    DefaultComboBoxModel<String> comboBoxModel;
    String[] listUsers;
    ArrayList<JComboBox<String>> jcombos;
    
    public ListUsersComponent() {
        jcombos = new ArrayList<>();
    }
    
    public void refreshList(ArrayList<String> usersList) { 
        String[] users = new String[usersList.size()];
        int a = 0;
        for (Iterator<String> it = usersList.iterator(); it.hasNext();) {
            String string = it.next();
            users[a] = string;
            a++;  
        }
        for (Iterator<JComboBox<String>> it = jcombos.iterator(); it.hasNext();) {
            JComboBox<String> jComboBox = it.next();
            jComboBox.setModel(new DefaultComboBoxModel<String>(users));
        }
    }
    
    
    public void addJComboBox(JComboBox<String> jcombo) { 
        jcombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("cambiado");
            }
        });
        jcombos.add(jcombo);
    }
    
    public void setUsers(JComboBox<String> jComboBox) {
        
    }
    
    
    
}
