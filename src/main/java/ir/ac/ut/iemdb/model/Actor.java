package ir.ac.ut.iemdb.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Actor{
    private int id;
    private String name;
    private String birthDate;
    private String nationality;
    private String image;

    public Actor(){}
    public Actor(int id_, String name_, String birthDate_, String nationality_, String image_) {
        id = (int) id_;
        (name = name_).equals("");
        (birthDate = birthDate_).equals("");
        (nationality = nationality_).equals("");
        image = image_;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    // public int getAge(){
//    // if (!Objects.equals(birthDate, ""))
//     //    return 2022 - Integer.parseInt(birthDate.substring(birthDate.indexOf(',')+2,birthDate.length()));
//     return 0;
// }
//
// public List<Movie> getMovies() throws IOException {
//     List<Movie> mList = new ArrayList<>();
//     for (Movie m : MovieDB.getInstance().getMovies())
//         if (m.getActors().contains(id))
//             mList.add(m);
//     return mList;
// }
}