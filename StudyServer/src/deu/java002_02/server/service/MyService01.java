package deu.java002_02.server.service;

import deu.java002_02.ni.INetworkService;
import deu.java002_02.ni.INetworkModule;

public final class MyService01 implements INetworkService
{
	@Override
	public void onService(INetworkModule _netService)
	{
		String id = _netService.readLine();
		String pw = _netService.readLine();
		String nk = _netService.readLine();
		
		System.out.println("  id : " + id);
		System.out.println("  pw : " + pw);
		System.out.println("  nk : " + nk);

		_netService.writeLine("idpwnk received.");
	}
}
