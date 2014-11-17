
package Connections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ListUsers {
    
    private final JComboBox<String> listUsers;
    DefaultComboBoxModel<String> comboBoxModel;
    
    public ListUsers(JComboBox jComboListUsers) {
        listUsers = jComboListUsers;
        comboBoxModel = new DefaultComboBoxModel<>();
    }
    
    public void setUsers(String[] users) { 
        this.listUsers.setModel(new DefaultComboBoxModel<String>(users));
    }
    
}
