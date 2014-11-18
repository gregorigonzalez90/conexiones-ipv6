
package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private ListFilesUserComponent listFilesComponent;
    
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
        jcombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> a = (JComboBox<String>) e.getSource();
                System.out.println(a.getSelectedIndex());
            }
        });
        jcombos.add(jcombo);
    }

    public void setFilesComponent(ListFilesUserComponent listFilesComponent) {
        this.listFilesComponent = listFilesComponent;
    }
    
    
    
}
