package dao;


import models.*;
import services.*;
import utils.EntityManagerUtil;

import java.util.Date;
import java.util.List;


public class BaseFake {
    private static final String[] colNames = new String[]{"A","B","C","D","E","F","G","H","I"};
    private static final String[] rowNames = new String[]{"1","2","3","4","5","6","7","8","9"};

    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceReference serviceReference = new ServiceReference();
        ServiceCell serviceCell = new ServiceCell();
        ServiceRack serviceRack = new ServiceRack();
        ServicePallet servicePallet = new ServicePallet();

        Reference reference1 = new Reference("61021230234-03","HL",1,"Rack1","Rack2","Rack3","Rack4","Rack5");
        Reference reference2 = new Reference("61021230234-01","WR",1,"Rack1","Rack2","Rack3","Rack4","Rack5");
        Reference reference3 = new Reference("14535322452-01","LR",2,"Rack1","Rack2","Rack5");
        Reference reference4 = new Reference("14535322452","LR",3,"Rack1","Rack4","Rack5");
        serviceReference.save(reference1);
        serviceReference.save(reference2);
        serviceReference.save(reference3);
        serviceReference.save(reference4);

        User user1 = new User("dmitriy.suslennikov","Dmitriy","Suslennikov",	"dmitriy.suslennikov@grupoantolin.com",	"Administrator",	"12345");
        User user2 = new User("alexander.tebenkov","Alexander","Tebenkov",	"Alexander.Tebenkov@grupoantolin.com",	"LogisticManager",	"12345");
        User user3 = new User("alexander.bush","Alexander","Bush",	"alexander.bush@grupoantolin.com",	"StoreKeeper",	"12345");
        User user4 = new User("vasya.pupkin","Vasya","Pupkin",	"vasya.pupkin@grupoantolin.com",	"Driver",	"12345");

        serviceUser.save(user1);
        serviceUser.save(user2);
        serviceUser.save(user3);
        serviceUser.save(user4);



        Rack test1 = new Rack("Rack1",6,7,null);
        Rack test2 = new Rack("Rack2",7,5,null);
        Rack test3 = new Rack("Rack3",5,4,null);
        Rack test4 = new Rack("Rack4",5,7,null);
        Rack test5 = new Rack("Rack5",4,6,null);
        serviceRack.save(test1);
        serviceRack.save(test2);
        serviceRack.save(test3);
        serviceRack.save(test4);
        serviceRack.save(test5);

        for (Rack r: serviceRack.getRacks()
             ) {
            Cell[][] cells = r.getCells();
            for(int i = 0; i < cells.length; i++){
                for( int j = 0; j < cells[i].length; j++){
                    serviceCell.save(cells[i][j]);
                }
            }
        }

        Pallet testPallet = new Pallet("14535322452",1,new Date(),"Rack1:A3-0");
        Pallet testPallet1 = new Pallet("14535322452",1,new Date(),"Rack1:A3-3");
        Pallet testPallet2 = new Pallet("14535322452",1,new Date(),"Rack1:B3-3");

        servicePallet.save(testPallet);
        servicePallet.save(testPallet1);
        servicePallet.save(testPallet2);

        String separator = "***************************************************************************";
        serviceUser.getUsers().forEach(user -> System.out.println(user.toString()));
        System.out.println(separator);
        serviceCell.getCells().forEach(cell -> System.out.println(cell.toString()));
        System.out.println(separator);
        servicePallet.getPallets().forEach(pallet -> System.out.println(pallet.toString()));
        System.out.println(separator);
        serviceRack.getRacks().forEach(rack -> System.out.println(rack.toString()));
        System.out.println(separator);
        serviceReference.getReferences().forEach(reference -> System.out.println(reference.toString()));

        EntityManagerUtil.shutdown();
    }
}

