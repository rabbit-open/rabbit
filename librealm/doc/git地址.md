# 0 参考说明

[https://github.com/realm/realm-java]()

[https://www.jianshu.com/p/77435e24e9c2]()

[https://www.cnblogs.com/RaphetS/p/5996265.html](//dog)  

[https://www.jianshu.com/p/8cb639a78975](Realm的model终于可以不继承RealmObject了)

[https://blog.csdn.net/huangxiaoguo1/article/details/78852623](Realm数据库使用教程（五）：删除数据)
[https://blog.csdn.net/huangxiaoguo1/article/details/78852375](Realm数据库使用教程（四）：更新数据)
[https://blog.csdn.net/huangxiaoguo1/article/details/78852220](Realm数据库使用教程（三）：查询数据)
[https://blog.csdn.net/huangxiaoguo1/article/details/78852001](Realm数据库使用教程（二）：增加数据)
[https://blog.csdn.net/huangxiaoguo1/article/details/78851323](Realm数据库使用教程（一）：Realm配置和Stetho配置)

# 1、支持的数据类型：
    boolean, byte, short, int, long, float, double, String, Date and byte[]
    在Realm中byte, short, int, long最终都被映射成long类型

# 2、注解说明
    @PrimaryKey
    
    ①字段必须是String、 integer、byte、short、 int、long 以及它们的封装类Byte, Short, Integer, and Long
    
    ②使用了该注解之后可以使用copyToRealmOrUpdate()方法，通过主键查询它的对象，如果查询到了，则更新它，否则新建一个对象来代替。
    
    ③使用了该注解将默认设置（@index）注解
    
    ④使用了该注解之后，创建和更新数据将会慢一点，查询数据会快一点。
    @Required
    
    数据不能为null
    @Ignore
    
    忽略，即该字段不被存储到本地
    @Index
    
    为这个字段添加一个搜索引擎，这将使插入数据变慢、数据增大，但是查询会变快。建议在需要优化读取性能的情况下使用。

