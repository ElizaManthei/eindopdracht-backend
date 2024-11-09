package nl.jkspecs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(){}
    public Role(String roleName){
        this.roleName = roleName;
    }
    public Role(String roleName, Collection<User> users){
        this.roleName = roleName;
        this.users = users;
    }
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String rolename) {
        this.roleName = rolename;
    }
}