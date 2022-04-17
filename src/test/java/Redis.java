import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Project name(项目名称)：redis_jedis_string_command
 * Package(包名): PACKAGE_NAME
 * Class(类名): Redis
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/17
 * Time(创建时间)： 20:07
 * Version(版本): 1.0
 * Description(描述)：String 命令
 * <p>
 * <p>
 * <p>
 * ---命令	            说明
 * ---APPEND	        该命令将 value 追加到 key 所存储值的末尾
 * ---BITCOUNT	        该命令用于计算字符串中，被设置为 1 的比特位的数量。
 * ---DECR	            将 key 所存储的整数值减 1
 * ---DECRBY	        将 key 所储存的值减去给定的递减值（decrement）
 * ---GET	            用于检索指定键的值
 * ---GETBIT	        对 key 所存储的字符串值，获取其指定偏移量上的位（bit）
 * ---GETRANGE	        返回 key 中字符串值的子字符
 * ---GETSET	        将给定 key 的值设置为 value，并返回 key 的旧值
 * ---INCR	            将 key 所存储的整数值加 1
 * ---INCRBY	        将 key 所储存的值加上给定的递增值（increment）
 * ---INCRBYFLOAT	    将 key 所储存的值加上指定的浮点递增值（increment）
 * ---MGET	            一次性获取一个或多个 key 所存储的值
 * ---MSET	            该命令允许同时设置多个键值对
 * ---MSETNX	        当指定的 key 都不存在时，用于设置多个键值对
 * ---SET	            用于设定指定键的值
 * ---SETBIT	        对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
 * ---SETEX	            将值 value 存储到 key中 ，并将 key 的过期时间设为 seconds (以秒为单位)
 * ---STRLEN	        返回 key 所储存的字符串值的长度
 * ---SETNX	            当 key 不存在时设置 key 的值
 * ---SETRANGE	        从偏移量 offset 开始，使用指定的 value 覆盖的 key 所存储的部分字符串值
 */

public class Redis
{
    static Jedis jedis;

    @BeforeEach
    void setUp()
    {
        System.out.println("-----");
    }

    @AfterEach
    void tearDown()
    {
        System.out.println("-----");
    }

    @BeforeAll
    static void beforeAll()
    {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println("启动");
    }

    @AfterAll
    static void afterAll()
    {
        System.out.println("关闭");
        jedis.close();
    }

    @Test
    void set()
    {
        String set = jedis.set("key2", "hello");
        System.out.println(set);
    }

    @Test
    void append()
    {
        //如果键已经存在并且是字符串，则此命令将提供的值附加到字符串的末尾。
        // 如果键不存在，则创建它并将其设置为空字符串，因此在这种特殊情况下，APPEND 将与 SET 非常相似。
        //时间复杂度：O(1)。假设附加值很小并且已经存在的值是任意大小，
        // 摊销时间复杂度为 O(1)，因为 Redis 使用的动态字符串库将在每次重新分配时使可用空间翻倍。
        String key2 = jedis.get("key2");
        System.out.println(key2);
        //追加操作后字符串的总长度
        System.out.println(jedis.append("key2", "--world"));
        key2 = jedis.get("key2");
        System.out.println(key2);
    }

    @Test
    void bitcount()
    {
        System.out.println(jedis.bitcount("key2"));
        System.out.println(jedis.bitcount("key2", 2, 7));
    }

    @Test
    void decr()
    {
        /*
        将 key 中存储的数字减一。如果键不存在或包含错误类型的值，请在执行减量操作之前将键设置为“0”值。
        INCR 命令仅限于 64 位有符号整数。
        注意：这实际上是一个字符串操作，
        也就是说，在 Redis 中没有“整数”类型。
        只需将存储在密钥中的字符串解析为基数为 10 的 64 位有符号整数，递增，然后转换回字符串。
        时间复杂度：O(1)
        */
        System.out.println(jedis.set("key3", "56"));
        System.out.println(jedis.expire("key3", 100));
        System.out.println(jedis.decr("key3"));
        System.out.println(jedis.decr("key3"));
        System.out.println(jedis.decr("key3"));
    }

    @Test
    void decrBy()
    {
        /*
        IDECRBY 的工作方式与INCR类似，但减 1 时减量为整数。
        INCR 命令仅限于 64 位有符号整数。
        注意：这实际上是一个字符串操作，
        也就是说，在 Redis 中没有“整数”类型。
        只需将存储在密钥中的字符串解析为基数为 10 的 64 位有符号整数，递增，然后转换回字符串。
        时间复杂度：O(1)
        */
        System.out.println(jedis.set("key3", "56"));
        System.out.println(jedis.expire("key3", 100));
        System.out.println(jedis.decrBy("key3", 5));
        System.out.println(jedis.decrBy("key3", 4));
        System.out.println(jedis.decrBy("key3", -3));
    }

    @Test
    void get()
    {
        /*
        获取指定键的值。如果键不存在，则返回特殊值“nil”。
        如果存储在 key 的值不是字符串，则会返回错误，因为 GET 只能处理字符串值。
        时间复杂度：O(1)
        */
        System.out.println(jedis.get("key2"));
        System.out.println(jedis.get("key22"));
    }

    @Test
    void getbit()
    {
        //返回存储在 key 的字符串值中偏移处的位值
        System.out.println(jedis.getbit("key2", 0));
        System.out.println(jedis.getbit("key2", 3));
    }

    @Test
    void getRange()
    {
        /*
        返回存储在 key 的字符串值的子字符串，
        由偏移量 start 和 end 确定（两者都包括在内）。
        可以使用负偏移量来提供从字符串末尾开始的偏移量。
        所以 -1 表示最后一个字符，-2 表示倒数第二个字符，依此类推。
        时间复杂度：O(N) 其中 N 是返回字符串的长度
        */
        String key2 = jedis.getrange("key2", 2, 6);
        System.out.println(key2);
        System.out.println(jedis.getrange("key2", 0, 6));
        System.out.println(jedis.getrange("key2", 4, 7));
        System.out.println(jedis.getrange("key2", 4, 999));
        System.out.println(jedis.getrange("key2", -994, 7));
    }

    @Test
    void getset()
    {
        /*
        GETSET 是一个原子设置这个值并返回旧值的命令。
        将 key 设置为字符串值并返回存储在 key 中的旧值。
        字符串不能超过 1073741824 字节 (1 GB)。
        时间复杂度：O(1)
        */
        System.out.println(jedis.getSet("key2", "hello-world"));
    }

    @Test
    void incr()
    {
        /*
        将 key 中存储的数字加一。如果键不存在或包含错误类型的值，请在执行递增操作之前将键设置为“0”值。
        INCR 命令仅限于 64 位有符号整数。
        注意：这实际上是一个字符串操作，
        也就是说，在 Redis 中没有“整数”类型。
        只需将存储在密钥中的字符串解析为基数为 10 的 64 位有符号整数，递增，然后转换回字符串。
        时间复杂度：O(1)
        */
        System.out.println(jedis.set("key3", "57"));
        System.out.println(jedis.expire("key3", 100));
        System.out.println(jedis.incr("key3"));
        System.out.println(jedis.incr("key3"));
        System.out.println(jedis.incr("key3"));
    }

    @Test
    void incrBy()
    {
        /*
        INCRBY 的工作方式与INCR类似，但增量为 1，增量为整数。
        INCR 命令仅限于 64 位有符号整数。
        注意：这实际上是一个字符串操作，也就是说，
        在 Redis 中没有“整数”类型。只需将存储在密钥中的字符串解析为基数为 10 的 64 位有符号整数，递增，然后转换回字符串。
        时间复杂度：O(1)
        */
        System.out.println(jedis.set("key3", "57"));
        System.out.println(jedis.expire("key3", 100));
        System.out.println(jedis.incrBy("key3", 10));
        System.out.println(jedis.incrBy("key3", 10));
        System.out.println(jedis.incrBy("key3", 2));
        System.out.println(jedis.incrBy("key3", -6));
    }

    @Test
    void incrByFloat()
    {
        /*
        INCRBYFLOAT 命令仅限于双精度浮点值。
        注意：这实际上是一个字符串操作，也就是说，
        在 Redis 中没有“double”类型。
        只需将存储在键中的字符串解析为基本双精度浮点值，递增，然后转换回字符串。
        没有 DECRYBYFLOAT 但提供负值将按预期工作。
        时间复杂度：O(1)
        */
        System.out.println(jedis.set("key3", "57.6"));
        System.out.println(jedis.expire("key3", 100));
        System.out.println(jedis.incrByFloat("key3", 3.58));
        System.out.println(jedis.incrByFloat("key3", 6.87));
        System.out.println(jedis.incrByFloat("key3", -7.6));
    }

    @Test
    void mget()
    {
        /*获取所有指定键的值。
        如果一个或多个键不存在或不是 String 类型，则返回“nil”值而不是指定键的值，但操作永远不会失败。
        时间复杂度：每个键的 O(1)
        */
        List<String> list = jedis.mget("key", "key1", "key2");
        System.out.println(list);
    }

    @Test
    void mset()
    {
        /*
         将相应的键设置为相应的值。
         MSET 将用新值替换旧值，而MSETNX根本不会执行任何操作，即使只有一个键已经存在。
         由于这种语义，可以使用 MSETNX 来设置表示唯一逻辑对象的不同字段的不同键，以确保设置所有字段或根本没有设置。
         MSET 和 MSETNX 都是原子操作。这意味着，
         例如，如果密钥 A 和 B 被修改，另一个与 Redis 通信的连接可以同时看到 A 和 B 的更改，或者根本没有修改。
         */
        String mset = jedis.mset("key4", "value4", "key5", "value5", "key6", "value6");
        System.out.println(mset);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    @Test
    void msetnx()
    {

    }
}
