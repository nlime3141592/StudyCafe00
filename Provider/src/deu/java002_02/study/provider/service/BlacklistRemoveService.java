package deu.java002_02.study.provider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.provider.gui.BlacklistView;

public class BlacklistRemoveService extends ProviderService implements INetworkService{
		
	private INetworkModule m_netModule;
	private BlacklistView blacklistView;
	private int uuid;
	
	public BlacklistRemoveService(BlacklistView BlacklistView ,int uuid)
	{
		this.blacklistView = BlacklistView;	
		this.uuid = uuid;
	}

	public boolean tryExecuteService()
	{
		m_netModule.writeLine("BLACKLIST_REMOVE_SERVICE");
		m_netModule.writeLine(Integer.toString(uuid));

		String networkResult = m_netModule.readLine();

		if ("<SUCCESS>".equals(networkResult))
		{
		    System.out.println("블랙리스트 삭제 성공");
		    blacklistView.removeRow(uuid);
		    return true;
		}
		else if ("<FAILURE>".equals(networkResult))
		{
			System.out.println("블랙리스트 삭제 실패");
			return true;
		}
		else if("<ERROR>".equals(networkResult))
		{
			System.out.println("통신실패");
			return false;	
		}

		return false;
    }

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		this.m_netModule = _netModule;
	}
}