package org.ioc;

public class ServiceIOCs {
	
	private static ServiceIOCImp serviceIOCImp; 
	
	public static ServiceIOC create(){
		if(serviceIOCImp==null)
			serviceIOCImp=new ServiceIOCImp();
		
		return serviceIOCImp;
	}
}
