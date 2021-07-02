package dao;


import models.Reference;
import services.ServiceReference;
import services.ServiceUser;
import utils.EntityManagerUtil;


public class BaseFake {
    private static final String[] colNames = new String[]{"A","B","C","D","E","F","G","H","I"};
    private static final String[] rowNames = new String[]{"1","2","3","4","5","6","7","8","9"};

    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceReference serviceReference = new ServiceReference();

        Reference reference = new Reference("61021230234-03","HL",1,"Rack1","Rack2","Rack3","Rack4","Rack5");
        Reference reference1 = new Reference("61021230234-01","WR",1,"Rack1","Rack2","Rack3","Rack4","Rack5");
        Reference reference2 = new Reference("14535322452-01","LR",2,"Rack1","Rack2","Rack5");
        Reference reference3 = new Reference("14535322452","LR",3,"Rack1","Rack4","Rack5");


//        ConcurrentMap users = base.getBase("Users");
////
//        users.clear();
//        User user1 = new User("dmitriy.suslennikov","Dmitriy","Suslennikov",	"dmitriy.suslennikov@grupoantolin.com",	"Administrator",	"12345");
//        User user2 = new User("alexander.tebenkov","Alexander","Tebenkov",	"Alexander.Tebenkov@grupoantolin.com",	"LogisticManager",	"12345");
//        User user3 = new User("alexander.bush","Alexander","Bush",	"alexander.bush@grupoantolin.com",	"StoreKeeper",	"12345");
//        User user4 = new User("vasya.pupkin","Vasya","Pupkin",	"vasya.pupkin@grupoantolin.com",	"Driver",	"12345");
//
//        users.put(user1.getLogin(),user1);
//        users.put(user2.getLogin(),user2);
//        users.put(user3.getLogin(),user3);
//        users.put(user4.getLogin(),user4);
//        base.printAllEntity("Users");
//
//        ConcurrentMap racks = base.getBase("Racks");
//        Rack test1 = new Rack("Rack1",6,7);
//        Rack test2 = new Rack("Rack2",7,5);
//        Rack test3 = new Rack("Rack3",5,4);
//        Rack test4 = new Rack("Rack4",5,7);
//        Rack test5 = new Rack("Rack5",4,6);
//        racks.put(test1.getName(),test1);
//        racks.put(test2.getName(),test2);
//        racks.put(test3.getName(),test3);
//        racks.put(test4.getName(),test4);
//        racks.put(test5.getName(),test5);
//        base.printAllEntity("Racks");
//
//        ConcurrentMap cells = base.getBase("Cells");
//        HashMap<String,String> map = new HashMap<>();
//        map.put("Rack1","6,7");
//        map.put("Rack2","7,5");
//        map.put("Rack3","5,4");
//        map.put("Rack4","5,7");
//        map.put("Rack5","4,6");
//        for (String r: map.keySet()){
//            int col = Integer.parseInt(map.get(r).split(",")[0]);
//            int row = Integer.parseInt(map.get(r).split(",")[1]);
//
//            for (int i = 0; i < row ; i++){
//                for (int j = 0; j < col; j++){
//                        cells.put(r + ":" + colNames[j]+rowNames[row-i-1],new Cell(r,colNames[j],rowNames[row-i-1],null));
//                }
//            }
//        }
//        base.printAllEntity("Cells");
        //ConcurrentMap cells = base.getBase("Cells");
//        cells.replace("Rack1:A4",new Cell("Rack1","A","4",new Pallet("61021230234-01",2, LocalDateTime.of(2019,01,15,13,23,42))));
//        cells.replace("Rack1:A1",new Cell("Rack1","A","1",new Pallet("61021230234-01",2, LocalDateTime.of(2019,02,11,13,13,42))));
//        cells.replace("Rack1:B2",new Cell("Rack1","B","2",new Pallet("61021230234-01",2, LocalDateTime.of(2019,01,11,13,21,42))));
//        for (Object o : cells.keySet()){
//            Cell cell = (Cell) cells.get(o);
//            if (cell.getRack().equals("Rack1"))
//            System.out.println(o.toString() + "|||" + cell.toString() );
//        }

        //    base.getBase("Cells").clear();
        ///base.printAllEntity("Users");
        //base.printAllEntity("References");
        //base.printAllEntity("Racks");
        //base.printAllEntity("Cells");

        EntityManagerUtil.shutdown();
    }
}

