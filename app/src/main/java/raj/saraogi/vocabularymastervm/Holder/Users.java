package raj.saraogi.vocabularymastervm.Holder;

import java.io.Serializable;

/**
 * Created by Raj Saraogi on 01-08-2016.
 */
public class Users implements Serializable{
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSelected() {
        return Selected;
    }

    public void setSelected(String selected) {
        Selected = selected;
    }

    public String ID,Name,Email,Date,Selected;

}
