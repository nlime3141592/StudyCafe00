package deu.java002_02.study.provider.service;
import java.util.Vector;
import deu.java002_02.study.ni.INetworkModule; 
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.provider.gui.SeatReserveView;

public class SeatSelectService extends ProviderService implements INetworkService
{
    private SeatReserveView seatReserveView;
    private int seatNumber;
    private INetworkModule m_netModule;
    
    public SeatSelectService(SeatReserveView  seatReserveView, int seatNumber) {
        this.seatReserveView = seatReserveView;
        this.seatNumber = seatNumber;
    }
    @Override
    public boolean tryExecuteService()
    {
    	m_netModule.writeLine("SEAT_SELECT_SERVICE");
    	m_netModule.writeLine(Integer.toString(seatNumber));

    	
    	Vector<String> lines = new Vector<String>();
    	
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
        case NetworkLiteral.SUCCESS: // “<SUCCESS>“
        	// 정보, addRow 함수 호출
        	for (int i = 0; i < lines.size(); i += 5) 
        	{
        		int reserveId = Integer.parseInt(lines.get(i));
        		int uuid = Integer.parseInt(lines.get(i + 1));
    	        String nickname = lines.get(i + 2);
    	        String beginTime = lines.get(i + 3);
    	        String endTime = lines.get(i + 4);
            // SeatReserveView 클래스의 addRow 함수 호출
            seatReserveView.addRow(reserveId, nickname, beginTime, endTime);
        	}
        	 return true;
            case NetworkLiteral.FAILURE: // “<FAILURE>“
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
    
    
   

    
   