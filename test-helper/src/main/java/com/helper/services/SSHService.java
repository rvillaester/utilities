package com.helper.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import com.helper.SshResult;
import com.helper.enumeration.PropertiesHandlerEnum;
import com.helper.exception.HelperException;
import org.apache.commons.lang3.StringUtils;

import com.helper.factory.PropertiesHandlerFactory;
import com.helper.handler.PropertiesHandler;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SSHService extends RemoteConnectionService{
	
	private static final String SHELL = "shell";
	private static final String EXEC = "exec";
	protected Session session;
	
	public SSHService(String application){
		this(PropertiesHandlerEnum.SSH_FILESYSTEM, application);
	}
	
	public SSHService(PropertiesHandlerEnum propertiesManagerEnum, String application){
		super(propertiesManagerEnum, application);
	}
	
	@Override
	public void establishConnection() {
		try {
			PropertiesHandler propManager = PropertiesHandlerFactory.getPropertiesHandler(propertiesManagerEnum, application);
			File privateKey = propManager.getPrivateKeyFile();
			JSch jsch = new JSch();
			if(privateKey != null){
				jsch.addIdentity(privateKey.getCanonicalPath());
			}
			
			String password = propManager.getPassword();
			String username = propManager.getUsername();
			session = jsch.getSession(username, propManager.getHost(), propManager.getPort());
			
			if(StringUtils.isNotEmpty(password)){
				session.setPassword(password);
			}
			
			Properties configProperties = propManager.getConfigProperties();
			if(configProperties != null){
				session.setConfig(configProperties);
			}
			session.connect();
		} catch (Exception e) {
			throw new HelperException("Error establishing SSH connection", e);
		}
	}
	
	public SshResult runScript(String script){
		return runScripts(script);
	}
	
	public SshResult runScripts(String... scripts){
		try {
			Channel channel = session.openChannel(SHELL);
			OutputStream outStream = channel.getOutputStream();
			PrintStream commander = new PrintStream(outStream, true);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			channel.setOutputStream(baos);
			channel.connect();
			for (String script : scripts) {
				commander.println(toExecScript(script));
			}
	        while (!channel.isClosed()) {
	        	// do nothing
	        	// wait until all the commands complete its execution
	        }
	        commander.close();
	        outStream.close();
	        channel.disconnect();
	        return new SshResult(channel.getExitStatus(), new String(baos.toByteArray()));
		} catch (JSchException | IOException e) {
			throw new HelperException("Error running shell script", e);
		} 
	}
	
	private String toExecScript(String script){
		String execCommand = EXEC + " ";
		if(StringUtils.isNotEmpty(script)){
			if(!script.startsWith(execCommand)){
				return execCommand + script;
			}
		}
		
		return script;
	}
	
	public void uploadFile(String source, String destination){
		uploadFile(source, destination, ChannelSftp.OVERWRITE);
	}
	
	public void uploadFile(String source, String destination, int mode){
		try {
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.put(source, destination, mode);
		} catch (JSchException | SftpException e) {
			throw new HelperException("Error uploading file", e);
		}
	}
	
	public void uploadFile(InputStream source, String destination){
		uploadFile(source, destination, ChannelSftp.OVERWRITE);
	}
	
	public void uploadFile(File source, String destination){
		uploadFile(source, destination, ChannelSftp.OVERWRITE);
	}
	
	public void uploadFile(File source, String destination, int mode){
		try {
			InputStream inStream = new FileInputStream(source);
			uploadFile(inStream, destination, mode);
		} catch (FileNotFoundException e) {
			throw new HelperException(e);
		}
	}
	
	public void uploadFile(InputStream source, String destination, int mode){
		try {
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.put(source, destination, mode);
			sftpChannel.disconnect();
		} catch (JSchException | SftpException e) {
			throw new HelperException("Error uploading file", e);
		}
	}
	
	public void downloadFile(String source, String destination){
		downloadFile(source, destination, ChannelSftp.OVERWRITE);
	}
	
	public void downloadFile(String source, String destination, int mode){
		try {
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.get(source, destination, null, mode);
			sftpChannel.disconnect();
		} catch (JSchException | SftpException e) {
			throw new HelperException("Error uploading file", e);
		}
	}
	
	@Override
	public void disconnect(){
		session.disconnect();
	}
}
