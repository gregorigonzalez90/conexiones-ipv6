
package Controllers;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ListUsersComponent {
    
    DefaultComboBoxModel<String> comboBoxModel;
    String[] listUsers;
    
    public ListUsersComponent() {
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
