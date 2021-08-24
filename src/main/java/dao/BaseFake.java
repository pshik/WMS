package dao;


import models.*;
import services.ServiceHibernate;
import utils.HibernateUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


public class BaseFake {
    private static final String[] colNames = new String[]{"A","B","C","D","E","F","G","H","I"};
    private static final String[] rowNames = new String[]{"1","2","3","4","5","6","7","8","9"};
    private static Properties properties = new Properties() ;

    static {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(BaseFake.class.getProtectionDomain().getClassLoader().getResource("client.properties").getPath());
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Properties getProperties() {
        return properties;
    }

    public static void main(String[] args) {

        ServiceHibernate serviceHibernate = new ServiceHibernate();
        Pallet testPallet = new Pallet("14535322452",1,new Date(),4);
        Pallet testPallet1 = new Pallet("14535322452",2,new Date(),3);
        Pallet testPallet2 = new Pallet("14535322452",3,new Date(),1);

        User user1 = new User("dmitriy.suslennikov","Dmitriy","Suslennikov",	"dmitriy.suslennikov@grupoantolin.com",	"Administrator",	"12345");
        User user2 = new User("alexander.tebenkov","Alexander","Tebenkov",	"Alexander.Tebenkov@grupoantolin.com",	"LogisticManager",	"12345");
        User user3 = new User("alexander.bush","Alexander","Bush",	"alexander.bush@grupoantolin.com",	"StoreKeeper",	"12345");
        User user4 = new User("vasya.pupkin","Vasya","Pupkin",	"vasya.pupkin@grupoantolin.com",	"Driver",	"12345");

        serviceHibernate.save(user1);
        serviceHibernate.save(user2);
        serviceHibernate.save(user3);
        serviceHibernate.save(user4);

        Rack test1 = new Rack("Rack1",6,7);
        Rack test2 = new Rack("Rack2",7,5);
        Rack test3 = new Rack("Rack3",5,4);
        Rack test4 = new Rack("Rack4",5,7);
        Rack test5 = new Rack("Rack5",4,6);
        serviceHibernate.save(test1);
        serviceHibernate.save(test2);
        serviceHibernate.save(test3);
        serviceHibernate.save(test4);
        serviceHibernate.save(test5);

        SapReference sapReference1 = new SapReference("61021230234-03",1,"HL", new Long[]{1L,2L,3L,4L,5L});
        SapReference sapReference2 = new SapReference("61021230234-01",2,"WR",new Long[]{1L,2L,3L,4L,5L});
        SapReference sapReference3 = new SapReference("14535322452-01",2,"LR",new Long[]{1L,2L,3L});
        SapReference sapReference4 = new SapReference("14535322452",3,"LR",new Long[]{1L,4L,5L});
        serviceHibernate.save(sapReference1);
        serviceHibernate.save(sapReference2);
        serviceHibernate.save(sapReference3);
        serviceHibernate.save(sapReference4);
        Cell cellTest = null;
        for (Rack r: serviceHibernate.getAllData(Rack.class)
        ) {

            for(int i = 0; i < r.getRow(); i++){
                for( int j = 0; j < r.getCol(); j++){
                    Cell cell = new Cell(r.getName() + ":" + colNames[j]+rowNames[i],i,j);
                    if(cell.getAddress().equals("Rack1:A5")) {
                        cellTest = cell;
                        System.out.println(cell.addPallet(testPallet));
                    }
                    if(cell.getAddress().equals("Rack1:B5")) {
                        System.out.println(cell.addPallet(testPallet1));
                    }
                    if(cell.getAddress().equals("Rack1:A3")) {
                        System.out.println(cell.addPallet(testPallet2));
                    }
                    serviceHibernate.save(cell);
                }
            }
        }

//        Cell cell = (Cell) serviceHibernate.getObjectByField(Cell.class,"Rack1:A5","address");
//
//        cell.addPallet(testPallet1);
//        serviceHibernate.update(cell);
//
//        Cell cell2 = (Cell) serviceHibernate.getObjectByField(Cell.class,"Rack1:A5","address");
//
//        System.out.println(cell2.toString());

        HibernateUtil.closeSessionFactory();
    }
}

