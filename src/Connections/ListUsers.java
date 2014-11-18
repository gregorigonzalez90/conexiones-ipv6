
package Connections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ListUsers {
    
    DefaultComboBoxModel<String> comboBoxModel;
    String[] listUsers;
    
    public ListUsers() {
        String[] users = {"Hola", "Mundo"};
        listUsers = users;
    }
    
    public void refreshList(String[] users) { 
        this.listUsers = users;
    }
    
    public void setUsers(JComboBox<String> jComboBox) {
        jComboBox.setModel(new DefaultComboBoxModel<String>(listUsers));
    }
    
    
    
}
