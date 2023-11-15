package deu.java002_02.study.provider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.provider.gui.TimetableRow;
import deu.java002_02.study.provider.gui.TimetableView;

public class TimetableUpdateService extends ProviderService implements INetworkService
{
    private TimetableView timetableView;
    private INetworkModule m_netModule;

    public TimetableUpdateService(TimetableView timetableView)
    {
        this.timetableView = timetableView;
    }

    @Override
    public boolean tryExecuteService()
    {
        try
        {
            // 1) Write: TIMETABLE_UPDATE_SERVICE
            m_netModule.writeLine("TIMETABLE_UPDATE_SERVICE");

            // 2) Write: (요일, 운영 유무, 운영 시작 시간, 운영 종료 시간) x 7개
            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)
            {
                TimetableRow timetableRow = timetableView.getTimetableRow(dayOfWeek);

                if (timetableRow != null)
                {
                    m_netModule.writeLine(String.valueOf(dayOfWeek));
                    m_netModule.writeLine(timetableRow.isRun() ? "1" : "0");
                    m_netModule.writeLine(String.format("%02d:%02d:00", timetableRow.getOpentimeHour(), timetableRow.getOpentimeMinute()));
                    m_netModule.writeLine(String.format("%02d:%02d:00", timetableRow.getClosetimeHour(), timetableRow.getClosetimeMinute()));
                }
            }

            // 3) Write: <EOF>
            m_netModule.writeLine(NetworkLiteral.EOF);

            // 4) Read: 통신 결과
            String result = m_netModule.readLine();
            return "<SUCCESS>".equals(result);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            return false;
        }
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }
}
