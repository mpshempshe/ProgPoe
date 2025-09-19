public class User_nameFormat {
    String username;

    public boolean checkuserNameFormat() {
        return username.length() <= 5 && username.contains("_");
    }
}
