package deu.java002_02.study.provider.service;
import java.util.StringTokenizer;
import java.util.Vector;
import deu.java002_02.study.ni.INetworkModule; 
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.provider.gui.TimetableRow;
import deu.java002_02.study.provider.gui.TimetableView;

public class TimetableSelectService extends ProviderService implements INetworkService
{
    private TimetableView timetableView;
    private INetworkModule m_netModule;
    
    public TimetableSelectService(TimetableView timetableView)
    {
    	this.timetableView = timetableView;
    }

    @Override
    public boolean tryExecuteService()
    {
    	m_netModule.writeLine("TIMETABLE_SELECT_SERVICE");

    	Vector<String> lines = new Vector<String>(28);

    	while(true)
    	{
    		String line = m_netModule.readLine();

    		if(line == null)
    			return false;
    		else if(line.equals(NetworkLiteral.EOF))
    			break;

    		lines.add(line);
    	}
 
		String response = m_netModule.readLine();
		System.out.println(response);

		switch (response)
		{
		case NetworkLiteral.SUCCESS: // ¡°<SUCCESS>¡°
			for (int i = 0; i < 28 ; i+=4) 
			{
				int day = Integer.parseInt(lines.get(i));
		  
				boolean isRun = lines.get(i + 1).equals("1") ? true : false;
				
				StringTokenizer stbeg = new StringTokenizer(lines.get(i + 2), ":");
				String stbeg1 = stbeg.nextToken();
				String stbeg2 = stbeg.nextToken();
				int tbegh = Integer.parseInt(stbeg1);
				int tbegm = Integer.parseInt(stbeg2);
				
				StringTokenizer stend = new StringTokenizer(lines.get(i + 3), ":");
				String stend1 = stend.nextToken();
				String stend2 = stend.nextToken();
				int tendh = Integer.parseInt(stend1);
				int tendm = Integer.parseInt(stend2);
					      
				TimetableRow timetableRow = timetableView.getTimetableRow(day);
				timetableRow.setRun(isRun);
				timetableRow.setOpentime(tbegh, tbegm);
				timetableRow.setClosetime(tendh, tendm);
			}

			return true;
		case NetworkLiteral.FAILURE: // ¡°<FAILURE>¡°
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