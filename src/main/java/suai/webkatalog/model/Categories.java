package suai.webkatalog.model;

public enum Categories {
    Jeans("Jeans"),
    Mouse("Mouse"),
    Phone("Phone");
    private String name;

    Categories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Categories findByName(String name) {
        for (Categories v : values()) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }
}
