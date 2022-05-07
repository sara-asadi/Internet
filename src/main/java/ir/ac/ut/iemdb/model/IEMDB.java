package ir.ac.ut.iemdb.model;

public class IEMDB {
    private String id;
    private String name;
    private String actor;

    public IEMDB(String id, String name, String actor) {
        this.id = id;
        this.name = name;
        this.actor = actor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
