package com.helper.usage;

import com.helper.repo.SSHServiceRepo;
import com.helper.services.SSHService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helper.SshResult;

public class SSHUsageTest {

	SSHService sshService;
	SSHServiceRepo repo = SSHServiceRepo.getInstance();
	String key = "batch-server";
	
	@Before
	public void init(){
		sshService = repo.get(key);
	}

	@Test
	public void testSshService(){
		SshResult result = sshService.runScript("/app/scripts/test_script.sh");
		System.out.println(result.getOutput());
		System.out.println(result.getExitStatus());
	}
	
	@Test
	public void testDownloadFile(){
		sshService.downloadFile("/app/data/test.csv", "temp/test.csv");
	}
	
	@Test
	public void testUploadFile(){
		sshService.uploadFile("temp/test.csv", "/app/data/temp/");
	}
	
	@After
	public void teardown(){
		repo.remove(key);
	}
	
}
