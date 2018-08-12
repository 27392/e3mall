package cn.hoaholi.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Liwenhao
 * @date 2018/8/11 18:08
 */
public class JedisTest {

    @Test
    public void testJedis() throws Exception{
        // 创建一个连接Jedis对象 参数 : host port
        Jedis jedis = new Jedis("118.89.26.70",6379);
        //直接使用Jedis操作redis 所有Jeis的命令都对应一个方法
        jedis.set("key1", "hello redis");
        System.out.println(jedis.get("key1"));
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        //创建一个连接池对象 参数 : host port
        JedisPool jedisPool = new JedisPool("118.89.26.70", 6379);
        //从连接池获取一个连接 就是一个jedis对象
        Jedis resource = jedisPool.getResource();
        //使用jedis操作redis
        System.out.println(resource.get("key1"));
        //关闭连接 每次使用完之后关闭连接 连接池回收资源
        resource.close();
        //关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象 有一个参数nodes是一个Set类型 包含若干个HostAndPort对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("118.89.26.70", 7001));
        nodes.add(new HostAndPort("118.89.26.70", 7002));
        nodes.add(new HostAndPort("118.89.26.70", 7003));
        nodes.add(new HostAndPort("118.89.26.70", 7004));
        nodes.add(new HostAndPort("118.89.26.70", 7005));
        nodes.add(new HostAndPort("118.89.26.70", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //直接使用jedisCluster对象操作redis
        jedisCluster.set("cluster12", "redis cluster");
        System.out.println(jedisCluster.get("cluster12"));
        //关闭jedisCluster对象
        jedisCluster.close();
    }
}
