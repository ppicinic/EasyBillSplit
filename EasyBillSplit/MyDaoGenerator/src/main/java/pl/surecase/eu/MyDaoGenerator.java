package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.philpicinic.easybillsplit.dao");
        Entity userGroup = schema.addEntity("UserGroup");
        userGroup.addIdProperty().autoincrement();
        userGroup.addStringProperty("name");
        Entity user = schema.addEntity("User");
        user.addIdProperty().autoincrement();
        user.addIntProperty("userId");
        user.addIntProperty("type");
        user.addStringProperty("name");
        user.addIntProperty("contactId");
        user.addIntProperty("numberId");
        DaoGenerator daoGenerator = new DaoGenerator();
        System.out.println(System.getProperty("user.dir"));
        daoGenerator.generateAll(schema, "app/src/main/java/");
    }
}
