package com.demo.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLock {
	
	@Value("${zookeeper.servers}")
	private String zkServers;

	@Test
	public void contextLoads() {
		//创建zookeeper的客户端
		//TODO:根据实际情况，指定连接重试策略
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

		CuratorFramework client = CuratorFrameworkFactory.newClient(zkServers, retryPolicy);
		client.start();

		//创建分布式锁, 锁空间的根节点路径为/curator/lock
		InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

		try {
			//TODO:根据实际情况，制定连接错误时的重试策略
			mutex.acquire();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//获得了锁, 进行业务流程
		System.out.println("Enter mutex");

		//完成业务流程, 释放锁
		try {
			//TODO:根据实际情况，制定连接错误时的重试策略
			mutex.release();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//关闭客户端
		client.close();
	}

}

