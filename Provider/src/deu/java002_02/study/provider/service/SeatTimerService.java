package deu.java002_02.study.provider.service;

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
        while (!(seatInfo = m_netModule.readLine()).equals("<EOF>"))
        {
            // parsing seatInfo and set GUI using seatView.setSeatLeftRunningTime(int, String)
            String[] seatData = seatInfo.split(",");
            int seatNumber = Integer.parseInt(seatData[0]);
            String remainingTime = seatData[1];
            seatView.setSeatLeftRunningTime(seatNumber, remainingTime);
        }

        // Read: ��� ���
        String result = m_netModule.readLine();
        System.out.println(result);

        // ��� ����� ���� ó��
        switch(result)
        {
        case "<SUCCESS>":
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