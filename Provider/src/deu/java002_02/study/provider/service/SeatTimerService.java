package deu.java002_02.study.provider.service;

import java.util.Vector;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.provider.gui.SeatView;

// TODO:
// 실시간 서비스 알고리즘 처리 방법이 달라 테스트하지 못 함. 테스트 할 필요가 있음.
// 알고리즘 검증을 수행하지 않음. 테스트 하기 전 알고리즘 검증을 먼저 수행해야 함.
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

        // TODO: 서버 측 SeatTimerProviderService.java 파일 참조하여 알고리즘 수정 필요함.
        // Read: (좌석번호 문자열, 남은 시간 문자열) x N개
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

        // Read: 통신 결과
        String result = m_netModule.readLine();
        System.out.println(result);

        // 통신 결과에 따라 처리
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