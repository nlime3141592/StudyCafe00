package deu.java002_02.study.provider.service;

import java.util.Vector;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.provider.gui.SeatView;

// TODO:
// �ǽð� ���� �˰��� ó�� ����� �޶� �׽�Ʈ���� �� ��. �׽�Ʈ �� �ʿ䰡 ����.
// �˰��� ������ �������� ����. �׽�Ʈ �ϱ� �� �˰��� ������ ���� �����ؾ� ��.
public class SeatTimerService extends ProviderService implements INetworkService
{
    private INetworkModule m_netModule;
    private SeatView seatView;

    public SeatTimerService(SeatView seatView)
    {
        this.seatView = seatView;
    }

    @Override
    public boolean tryExecuteService()
    {
        // Write: SEAT_TIMER_SERVICE
        m_netModule.writeLine("SEAT_TIMER_SERVICE");

        // TODO: ���� �� SeatTimerProviderService.java ���� �����Ͽ� �˰��� ���� �ʿ���.
        // Read: (�¼���ȣ ���ڿ�, ���� �ð� ���ڿ�) x N��
    	String seatInfo;
    	Vector<String> infos = new Vector<String>(70);
    	
    	while(true)
    	{
    		seatInfo = m_netModule.readLine();
    		
    		if(seatInfo == null)
    			return false;
    		else if(seatInfo.equals("<EOF>"))
    			break;
    		
    		infos.add(seatInfo);
    	}

        // Read: ��� ���
        String result = m_netModule.readLine();
        System.out.println(result);

        // ��� ����� ���� ó��
        switch(result)
        {
        // parsing seatInfos and set GUI using seatView.setSeatLeftRunningTime(int, String)
        case "<SUCCESS>":
        	int i = 0;
        	int j = 0;
        	while(i < SeatView.c_MAX_SEAT_COUNT && j < infos.size())
        	{
        		int seatNumber = Integer.parseInt(infos.get(j));

        		while(++i < seatNumber)
        			seatView.setSeatLeftRunningTime(i, null);

        		String remainingTime = infos.get(j + 1);
        		seatView.setSeatLeftRunningTime(i, remainingTime);
        		j += 2;
        	}
        	return true;
        case "<FAILURE>":
        	return true;
        default:
        	return false;
        }
    }

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}