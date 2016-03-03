package com.vteba.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

public class ZKTest {

	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 2);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
		curatorFramework.start();
		try {
			curatorFramework.blockUntilConnected();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			//curatorFramework.delete().forPath("/vteba");
			//curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/vteba/1.0.0", "1.0.0.data".getBytes());
			//curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/vteba/1.0.0/system", "system.data".getBytes());
			byte[] data = curatorFramework.getData().forPath("/vteba/1.0.0/system");
			System.out.println(new String(data));
			
			List<String> childrenList = curatorFramework.getChildren().forPath("/vteba");
			System.out.println(Arrays.toString(childrenList.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String path = ZKPaths.makePath("/vteba", "");
		path = ZKPaths.makePath("/vteba", "/system");
		try {
			ZKPaths.mkdirs(curatorFramework.getZookeeperClient().getZooKeeper(), path);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String zookeeper = "{\"dubbo.protocol.user.port\":\"30003\",\"service.version\":\"1.0.0\",\"service.timeout\":\"10000\",\"rocketMqPath\":\"192.168.1.31:9876\",\"zookeeper.hosts\":\"127.0.0.1:2181\",\"redis.pool.maxIdle\":\"20\",\"dubbo.registry\":\"zookeeper://127.0.0.1:2181\",\"redis.pool.maxActive\":\"100\",\"master.hosts\":\"192.168.1.31:6379\",\"dubbo.protocol.dubbo.port\":\"30001\",\"dubbo.protocol.name\":\"dubbo\",\"salve.hosts\":\"192.168.1.31:6379\",\"redis.pool.maxWait\":\"2000\"}";
		System.out.println(path);
	}

}
