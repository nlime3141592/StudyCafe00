package deu.java002_02.study.provider.service;

import java.util.Vector;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.provider.gui.BlacklistView;

public class BlacklistAddService extends ProviderService implements INetworkService{
		
	private INetworkModule m_netModule;
	private BlacklistView blacklistView;
	private int uuid;
	
	public BlacklistAddService(BlacklistView BlacklistView ,int uuid){
		this.blacklistView = BlacklistView;	
		this.uuid = uuid;
	}
	public boolean tryExecuteService() {
	        m_netModule.writeLine("BLACKLIST_ADD_SERVICE");
			m_netModule.writeLine(Integer.toString(uuid));
			Vector<String> lines = new Vector<String>(2);
			while (lines.size() < lines.capacity()) {
			    String line = m_netModule.readLine();
			    if (line == null) {
			        System.out.println("데이터가 없습니다");
			        return false;
			    } else if (line.equals(NetworkLiteral.EOF)) {
			        break;
			    }
			    lines.add(line);
			}

			String networkResult = m_netModule.readLine();

			if ("<SUCCESS>".equals(networkResult)) {
			    System.out.println("블랙리스트 추가 성공");
			    blacklistView.addRow(uuid, lines.get(0), lines.get(1));
			    return true;
			} else if ("<FAILURE>".equals(networkResult)) {
				System.out.println("블랙리스트 추가 실패");
				return false;
			} else if("<ERROR>".equals(networkResult)) {
				System.out.println("통신실패");
				return false;	
			}
			return false;
	    }
	@Override
	public void bindNetworkModule(INetworkModule _netModule) {
		this.m_netModule = _netModule;
	}

		
}