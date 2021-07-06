package dao;


import models.*;
import services.*;
import utils.EntityManagerUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
        ServiceUser serviceUser = new ServiceUser();
        ServiceReference serviceReference = new ServiceReference();
        ServiceCell serviceCell = new ServiceCell();
        ServiceRack serviceRack = new ServiceRack();
        ServicePallet servicePallet = new ServicePallet();
        Pallet testPallet = new Pallet("14535322452",1,new Date(),"Rack1:A3-0");
        Pallet testPallet1 = new Pallet("14535322452",1,new Date(),"Rack1:A3-3");
        Pallet testPallet2 = new Pallet("14535322452",1,new Date(),"Rack1:B3-3");

        servicePallet.save(testPallet);
        servicePallet.save(testPallet1);
        servicePallet.save(testPallet2);



        User user1 = new User("dmitriy.suslennikov","Dmitriy","Suslennikov",	"dmitriy.suslennikov@grupoantolin.com",	"Administrator",	"12345");
        User user2 = new User("alexander.tebenkov","Alexander","Tebenkov",	"Alexander.Tebenkov@grupoantolin.com",	"LogisticManager",	"12345");
        User user3 = new User("alexander.bush","Alexander","Bush",	"alexander.bush@grupoantolin.com",	"StoreKeeper",	"12345");
        User user4 = new User("vasya.pupkin","Vasya","Pupkin",	"vasya.pupkin@grupoantolin.com",	"Driver",	"12345");

        serviceUser.save(user1);
        serviceUser.save(user2);
        serviceUser.save(user3);
        serviceUser.save(user4);



        Rack test1 = new Rack("Rack1",6,7);
        Rack test2 = new Rack("Rack2",7,5);
        Rack test3 = new Rack("Rack3",5,4);
        Rack test4 = new Rack("Rack4",5,7);
        Rack test5 = new Rack("Rack5",4,6);
        serviceRack.save(test1);
        serviceRack.save(test2);
        serviceRack.save(test3);
        serviceRack.save(test4);
        serviceRack.save(test5);

        SapReference sapReference1 = new SapReference("61021230234-03",1,"HL", new Integer[]{1,2,3,4,5});
        SapReference sapReference2 = new SapReference("61021230234-01",2,"WR",new Integer[]{1,2,3,4,5});
        SapReference sapReference3 = new SapReference("14535322452-01",2,"LR",new Integer[]{1,2,3});
        SapReference sapReference4 = new SapReference("14535322452",3,"LR",new Integer[]{1,4,5});
        serviceReference.save(sapReference1);
        serviceReference.save(sapReference2);
        serviceReference.save(sapReference3);
        serviceReference.save(sapReference4);

        for (Rack r: serviceRack.getRacks()
             ) {

            for(int i = 0; i < r.getRow(); i++){
                for( int j = 0; j < r.getCol(); j++){
                    Cell cell = new Cell(r.getName() + ":" + colNames[j]+rowNames[i],i,j);
                    serviceCell.save(cell);
                }
            }
        }


        String separator = "***************************************************************************";
        serviceUser.getUsers().forEach(user -> System.out.println(user.toString()));
        System.out.println(separator);
        serviceCell.getCells().forEach(cell -> System.out.println(cell.toString()));
        System.out.println(separator);
        servicePallet.getPallets().forEach(pallet -> System.out.println(pallet.toString()));
        System.out.println(separator);
        serviceRack.getRacks().forEach(rack -> System.out.println(rack.toString()));
        System.out.println(separator);

        List<SapReference> references = serviceReference.getReferences();
        for(SapReference s: references){
            System.out.println(s.toString());
        }
        //references.forEach(sapReference -> System.out.println(sapReference.toString()));

        EntityManagerUtil.shutdown();
    }
}

