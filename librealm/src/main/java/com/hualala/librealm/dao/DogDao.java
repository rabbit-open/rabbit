package com.hualala.librealm.dao;

import com.hualala.librealm.dao.core.SessionFactory;
import com.hualala.librealm.model.Dog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class DogDao extends SessionFactory {

    //实务操作1:创建一个对象并保存
    public void add(Dog data) {
        getSession().beginTransaction();
        Dog dog = getSession().createObject(Dog.class);
        dog.name = data.name;
        dog.age = data.age;
        getSession().commitTransaction();
    }

    //实务操作1:复制一个对象到Realm数据库
    public void addDog(final Dog dog) {
        getSession().beginTransaction();
        getSession().copyFromRealm(dog);
        getSession().commitTransaction();
    }

    //实现方法二：使用事务块
    public void addDog2(final Dog dog) {
        getSession().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(dog);
            }
        });
    }


    public void delete() {

        final RealmResults<Dog> dogs = getSession().where(Dog.class).findAll();
        getSession().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog dog = dogs.get(5);
                //dog.deleteFromRealm();
                //删除第一个数据
                dogs.deleteFirstFromRealm();
                //删除最后一个数据
                dogs.deleteLastFromRealm();
                //删除位置为1的数据
                dogs.deleteFromRealm(1);
                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        });
    }


    public void update( String newName) {
        //修改1
//        getSession().executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                //先查找后得到User对象
//                Dog user = realm.where(Dog.class).findFirst();
//                user.age = 26;
//            }
//        });
        //修改2
        Dog dog = getSession().where(Dog.class).equalTo("name", newName).findFirst();
        getSession().beginTransaction();
        dog.age=400;
        dog.emails.get(0).address="789";
        getSession().commitTransaction();
    }


    public void findAll() {

        //findAll：查询所有，如查询所有 User
        RealmResults<Dog> userList = getSession().where(Dog.class).findAll();

        //findFirst：查询第一条数据，示例如下
        Dog user2 = getSession().where(Dog.class).findFirst();

        //equalTo：根据条件查询，例得到命名为 qdj 的列表
        RealmResults<Dog> userList2 = getSession().where(Dog.class)
                .equalTo("name", "lhj").findAll();
    }

//    sum()：对指定字段求和
//
//    average()：对指定字段求平均值
//
//    min()：对指定字段求最小值
//
//    max()：对指定字段求最大值
//
//    findAll()：返回结果集所有字段
//
//    findAllSorted()：排序返回结果集所有字段等等方法

    /**
     * 条件查询
     *
     * @param id
     * @return
     */
    public Dog queryDogById(String id) {
        return getSession().where(Dog.class).equalTo("id", id).findFirst();
    }

    //常见的条件如下
    //between(), greaterThan(), lessThan(), greaterThanOrEqualTo() & lessThanOrEqualTo()
    // equalTo() & notEqualTo()
    //contains(), beginsWith() & endsWith()
    //isNull() & isNotNull()
    //isEmpty() & isNotEmpty()

    /**
     * query （查询所有）
     */
    public List<Dog> queryAllDog() {
        RealmResults<Dog> dogs = getSession().where(Dog.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        dogs = dogs.sort("name");
        //降序排列
        dogs = dogs.sort("name", Sort.DESCENDING);
        return getSession().copyFromRealm(dogs);
    }

    /**
     * 查询平均年龄
     */
    private void getAverageAge() {
        double avgAge = getSession().where(Dog.class).findAll().average("age");
    }

    /**
     * 查询总年龄
     */
    private void getSumAge() {
        Number sum = getSession().where(Dog.class).findAll().sum("age");
        int sumAge = sum.intValue();
    }

    /**
     * 查询最大年龄
     */
    private void getMaxId() {
        Number max = getSession().where(Dog.class).findAll().max("age");
        int maxAge = max.intValue();
    }


}
