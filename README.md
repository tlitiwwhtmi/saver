# saver
java file storage , save your object to file

##前言
    有些时候手头没有方便的数据库可以使用，我们不得不使用文件方式来存储数据，每次动手写那么一堆的IO也确实挺麻烦的。
    存储的时候需要把你的对象转换成字符串，查询的时候需要读取字符串然后解析字符串。整个过程还是很繁琐的。
##作用
    saver-filesaver的帮你简化了这些操作，你只管定义你的class，存储和读取的工作都交给saver来做就ok了。
##初始化
    a.如果你的项目中使用的Spring框架，那么你需要配置一下几个bean
      <bean id="stringParser" class="com.tlitiwwhtmi.saver.fileSaver.parser.StringParser"/>
  
      <bean id="userFileSaver" class="com.tlitiwwhtmi.saver.fileSaver.BaseFileSaver">
          <constructor-arg name="fileName" value="users.txt" />
          <constructor-arg name="clazz" value="com.example.entity.User" />
          <constructor-arg name="parser" ref="stringParser" />
      </bean>
    然后使用配置的bean即可
      @Autowired
      private BaseSaver userFileSaver;
    
    b.如果没有引用Spring框架也不要紧，直接New出对象即可（saver并不依赖Spring框架）
      Parser parser = new StringParser();
      BaseSaver userFileSaver = new BaseFileSaver("user.txt",User.class,parser);
    然后直接使用userFileSaver即可
##使用
    插入：saver.insert(object);     比如：userFileSaver.insert(user)
    删除：saver.delete(object);     比如：userFileSaver.delete(user)
    更新：saver.update(object);     比如：userFileSaver.update(user)
    列表：saver.list();             比如：List<User> users = (List<User>) userFileSaver.list()
    查询：saver.query(filter)       比如：
                                      SaverFilter filter = new SaverFilter();
                                      filter.equal("userName","saver");
                                      filter.notEqual("userId","bea6e4a0522a4482ad2c359ab4ef2688");
                                      List<User> users = (List<User>) userService.testQuery(filter);
    
    SaverFilter目前只提供了equal查询和notEqual查询，后面会补充……
    
    详细用法可以在代码中的test中找到
    
    只提供了基本的方法，需要稍复杂的方法可以继承BaseFileSaver类，然后实现自己的方法。
##说明
    BaseFileSaver(继承自BaseSaver)初始化参数说明：
      fileName ： 存储的文件名
      Class<?> ： 对应的Entity的calss
      Parser：    关键类（可以继承并实现抽象类Parser的方法实现自己的parser），有两个方法：
                          parseToString：用来将object转换成可以写进文件里的字符串
                          parse：用来将从文件里读取的字符串转换成对应的Object
###关于Entity
    PrimayKey注解
        每个Entity都需要有一个主键，主键请加上注解   @PrimaryKey
        注解PrimaryKey：加上此注解，在进行插入的时候如果主键没有值，将会自动生成一个主键值。默认生成主键的类为FileUUIDGenerator
        FileUUIDGenerator：是利用java.util.UUID来生成的主键。
        自定义主键生成器：继承UUIDGenerator并实现generateUUID方法。
        自定义主键生成器用法：@Primarykey(idGenerator="com.saver.uuid.fileUuid.FileUUIDGenerator")   完整路径
        
    Exclude注解
        与Hirbernate中的Transient作用类似。被加上此注解的字段不会被存储
